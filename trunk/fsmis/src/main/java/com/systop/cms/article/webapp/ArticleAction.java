package com.systop.cms.article.webapp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.CmsConstants;
import com.systop.cms.article.ArticleConstants;
import com.systop.cms.article.service.ArticleManager;
import com.systop.cms.model.Articles;
import com.systop.cms.model.Attachments;
import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.util.DateUtil;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;

/**
 * 文章管理Action
 * 
 * @author zhangwei
 */
@SuppressWarnings({"serial","unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticleAction extends DefaultCrudAction<Articles, ArticleManager> {

  /** 链接排序编号 格式为 编号:序号,编号:序号 */
  private String seqNoList;

  /** 栏目id */
  private Integer catalogId;

  /** 文章附件存放路径 */
  private static final String ARTICLE_ATT_FOLDER = CmsConstants.ARTICLE_ATT_FOLDER;

  /** 附件 */
  private File[] attachments;

  /** 附件保存后的名称 */
  private String[] attachmentsFileName;

  /** 首页Flash显示图片 */
  private File flashImg;

  /** 首页Flash显示图片名称 */
  private String flashImgFileName;

  /** 用于查询的文章名称 */
  private String articleName = StringUtils.EMPTY;

  /** 用于查询的开始日期 */
  private Date beginDate;

  /** 用于查询的结束日期 */
  private Date endDate;

  /** 查询值 */
  private String queryValue;

  /**
   * 返回审核状态操作列表
   * 
   * @return 审核状态
   */
  public Map<String, String> getStates() {
    return ArticleConstants.STATE_PASS_MAP;
  }

  /**
   * @return 用户登录信息
   */
  private User getLoginUser() {
    return getManager().getDao().get(User.class, UserUtil.getPrincipal(getRequest()).getId());
  }

  /**
   * 文章列表，文章查询
   * 
   * @return Result name of struts2.
   */
  public String listArticles() {
    // id > 0纯粹是为了封装条件便捷，在查询上无意义
    String hql = "from Articles a where a.id > 0";
    // 查询参数
    List args = new ArrayList();
    if (StringUtils.isNotBlank(articleName)) { // 查询标题
      hql = hql + " and a.title like ?";
      args.add("%" + articleName + "%");
    }
    if (beginDate != null && endDate != null) {
      hql = hql + " and a.createTime between ? and ?";
      args.add(beginDate);
      args.add(endDate);
    }
    if (catalogId == null || catalogId == 0) {
      hql = hql + " Order by a.createTime DESC";
    } else {
      hql = hql + " and a.catalog.id = ? Order by a.createTime DESC";
      args.add(catalogId);
    }
    page = getManager()
        .pageQuery(PageUtil.getPage(getPageNo(), getPageSize()), hql, args.toArray());
    items = page.getData();
    restorePageData(page);
    return SUCCESS;
  }

  /**
   * 保存文章
   * 
   * @see BaseModelAction#save()
   */
  public String saveArticle() {
    List<String> attPath = new ArrayList<String>(); // 存放附件路径

    if (getModel().getId() == null) {
      getModel().setCreateTime(DateUtil.getCurrentDate());
      // 设置发布人
      getModel().setInputer(getLoginUser());
    }
    if (catalogId == null || catalogId == 0) {
      this.addActionError("请选择所属栏目！！");
      return INPUT;
    }
    if (StringUtils.isBlank(getModel().getIsElite())) {
      getModel().setIsElite(CmsConstants.N);
    }
    if (StringUtils.isBlank(getModel().getOnTop())) {
      getModel().setOnTop(CmsConstants.N);
    }
    if (getModel().getHits() == null) {
      getModel().setHits(0);
    }

    // 文章无论是新增还是修改，都会将状态设置成待审核
    getModel().setAudited(ArticleConstants.WAITCHECK);
    getModel().setUpdateTime(DateUtil.getCurrentDate()); // 设置修改时间

    logger.debug("文章附件上传路径：" + ARTICLE_ATT_FOLDER);
    // 如果附件不为空则上传
    if (attachments != null) {
      for (int i = 0; i < attachments.length; i++) {
        attPath.add(UpLoadUtil.doUpload(attachments[i], attachmentsFileName[i], ARTICLE_ATT_FOLDER,
            getServletContext()));
      }
    }
    addFlashImgInfo();
    getManager().saveAtricle(getModel(), fileWrapper(attPath), catalogId);
    return SUCCESS;
  }

  /**
   * 设置推荐文章的首页Flash显示图片
   */
  private void addFlashImgInfo() {
    if (CmsConstants.Y.equals(getModel().getIsElite())) {
      if (flashImg != null) {
        logger.debug("首页Flash图片是" + flashImgFileName == null ? "is null" : flashImgFileName);
        String flashImgPath = UpLoadUtil.doUpload(flashImg, flashImgFileName, ARTICLE_ATT_FOLDER,
            getServletContext());
        getModel().setFlashImg(flashImgPath);
      }
    } else {
      getModel().setFlashImg(null);
    }
  }

  /**
   * 把文件名和文件路径包装成一个Map，再放入List
   * 
   * @param attPath 附件上传后的路径
   * @return List 封装附件Map的List
   */
  private List<Map> fileWrapper(List<String> attPath) {
    List<Map> list = null;
    if (attachmentsFileName != null) {
      list = new ArrayList();
      for (int i = 0; i < attachmentsFileName.length; i++) {
        // att用来存放附件原名和上传后的路径
        Map<String, String> att = new HashMap();
        att.put("attachmentName", attachmentsFileName[i]);
        att.put("attachmentUrl", attPath.get(i));
        list.add(att);
      }
    }
    return list;
  }

  /**
   * 文章普通列表，删除文章,判断审核状态。DWR调用
   * 
   * @param articlesArray 文章列表
   * @return
   */
  public Map removeArticle(String[] articlesArray) {
    Map map = new HashMap();
    map.put("id", "0");
    for (int i = 0; i < articlesArray.length; i++) {
      Integer articleId = Integer.parseInt(articlesArray[i]);
      // 判断文章是否审核
      List articleList = this.getManager().articlesExists(articleId);
      if (articleList.size() != 0 || !articleList.isEmpty()) {
        Articles article = (Articles) getManager().getArticle(articleId).get(0);
        // 若有文章返回提示信息
        map.put("id", "1");
        map.put("name", article.getTitle());
        break;
      }
    }
    
    // 删除
    if (map.get("id").equals("0")) {
      for (int i = 0; i < articlesArray.length; i++) {
        Integer articleId = Integer.parseInt(articlesArray[i]);
        Articles article = (Articles) getManager().getArticle(articleId).get(0);
        delFlashImg(article);
        getManager().remove(article);
      }
    }
    return map;
  }

  /**
   * 删除文章，同时删除文章的
   * @see com.systop.core.webapp.struts2.action.AbstractCrudAction#remove()
   */
  @Override
  public String remove() {
    if (ArrayUtils.isEmpty(selectedItems)) {
      if (getModel().getId() != null) {
        selectedItems = new Serializable[] { getModel().getId() };
      }
    }
    if (selectedItems != null) {
      for (Serializable id : selectedItems) {
        Articles art = getManager().get(convertId(id));
        if (art != null) {
          delFlashImg(art);
          getManager().remove(art);
        }
      }
    }
    return SUCCESS;
  }

  /**
   * 删除指定文章的首页Flash显示图片
   * 
   * @param art
   */
  private void delFlashImg(Articles art) {
    if (art != null && art.getFlashImg() != null) {
      String path = this.getServletContext().getRealPath(art.getFlashImg());
      if (StringUtils.isNotBlank(path)){
        File img = new File(path);
        if (img.exists()) {
          img.delete();
          logger.debug(art.getFlashImg() + " is deleted");
        }
      }
    }
  }

  /**
   * 保存文章排序结果
   * 
   * @return Result name of struts2.
   */
  public String saveOrderArticle() {
    if (seqNoList == null) {
      return SUCCESS;
    }
    // 得到页面上排序结果数组
    for (String orderLink : seqNoList.split(",")) {
      String[] order = orderLink.split(":");
      Articles articles = getManager().get(Integer.valueOf(order[0]));
      articles.setSerialNo(Integer.valueOf(order[1]));
      getManager().save(articles);
    }
    return SUCCESS;
  }

  /**
   * 设置推荐状态
   * 
   * @return Result name of struts2.
   */
  public String updateElite() {
    if (getModel().getId() != null) {
      // 设置推荐状态
      getModel().setIsElite(
          CmsConstants.Y.equals(getModel().getIsElite()) ? CmsConstants.N : CmsConstants.Y);
      getManager().save(getModel());
    }
    return SUCCESS;
  }

  /**
   * 设置固顶状态
   * 
   * @return Result name of struts2.
   */
  public String updateOnTop() {
    if (getModel().getId() != null) {
      // 设置推荐状态
      getModel().setOnTop(
          CmsConstants.Y.equals(getModel().getOnTop()) ? CmsConstants.N : CmsConstants.Y);
      getManager().save(getModel());
    }
    return SUCCESS;
  }

  /**
   * 批量设置审核状态
   * 
   * @return Result name of struts2.
   */
  public String batchUpdateAudit() {
    if (selectedItems != null) {
      Articles a = null;
      for (Serializable id : selectedItems) {
        if (id != null && getLoginUser() != null) {
          a = getManager().get(Integer.valueOf(id.toString()));
          a.setAudited(a.getAudited().equals(CmsConstants.Y) ? CmsConstants.N : CmsConstants.Y);
          a.setAuditor(getLoginUser());
          getManager().save(a);
        } else {
          addActionError("您没有登录");
          return INPUT;
        }
      }
      logger.debug(selectedItems.length + " items audited.");
    }
    return SUCCESS;
  }

  /**
   * 设置审核状态
   * 
   * @return Result name of struts2.
   */
  public String updateAudit() {

    if (selectedItems == null || selectedItems.length == 0) {
      if (getModel() != null) {
        Serializable id = (Serializable) ReflectUtil.get(getModel(), "id");
        if (id != null) {
          selectedItems = new Serializable[] { id };
        }
      }
    }
    if (selectedItems != null) {
      Articles a = null;
      for (Serializable id : selectedItems) {
        if (id != null && getLoginUser() != null) {
          a = getManager().get(Integer.valueOf(id.toString()));
          a.setAudited(CmsConstants.Y.equals(a.getAudited()) ? CmsConstants.N : CmsConstants.Y);
          a.setAuditor(getLoginUser());
          getManager().save(a);
        } else {
          addActionError("您没有登录!");
          return INPUT;
        }
      }
      logger.debug(selectedItems.length + " items audited.");
    }
    return SUCCESS;
  }

  /**
   * 删除附件;DWR调用
   * 
   * @param attachmentId 附件ID
   * @return Result name of struts2.
   */
  public String removeAttachments(Integer attachmentId) {
    getManager().removeAttachments(getManager().getDao().get(Attachments.class, attachmentId));
    return SUCCESS;
  }

  /**
   * 得到文章排序列表 对应orderArticle.jsp中<s:select>的list属性"orderCatalogArt"
   * 
   * @return List 文章排序列表
   */
  public List<Articles> getOrderCatalogArt() {
    if (StringUtils.isBlank(queryValue) || queryValue.equals("0")) {
      return Collections.EMPTY_LIST;
    } else {
      return getManager().orderCatalogArt(Integer.parseInt(queryValue));
    }
  }

  public String getArticleName() {
    return articleName;
  }

  public void setArticleName(String articleName) {
    this.articleName = articleName;
  }

  public String getSeqNoList() {
    return seqNoList;
  }

  public void setSeqNoList(String seqNoList) {
    this.seqNoList = seqNoList;
  }

  public void setAttachments(File[] attachments) {
    this.attachments = attachments;
  }

  public void setAttachmentsFileName(String[] attachmentsFileName) {
    this.attachmentsFileName = attachmentsFileName;
  }

  public File[] getAttachments() {
    return attachments;
  }

  public String[] getAttachmentsFileName() {
    return attachmentsFileName;
  }

  public File getFlashImg() {
    return flashImg;
  }

  public void setFlashImg(File flashImg) {
    this.flashImg = flashImg;
  }

  public String getFlashImgFileName() {
    return flashImgFileName;
  }

  public void setFlashImgFileName(String flashImgFileName) {
    this.flashImgFileName = flashImgFileName;
  }

  public Integer getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(Integer catalogId) {
    this.catalogId = catalogId;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getQueryValue() {
    return queryValue;
  }

  public void setQueryValue(String queryValue) {
    this.queryValue = queryValue;
  }

}
