package com.systop.cms.links.webapp;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.CmsConstants;
import com.systop.cms.links.service.LinkManager;
import com.systop.cms.model.LinkCatas;
import com.systop.cms.model.Links;
import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;

/**
 * 友情链接Action
 * 
 * @author Bin
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LinkAction extends DefaultCrudAction<Links, LinkManager> {

  /** logo存放路径 */
  private static final String LINK_SITE_LOGO = CmsConstants.LINK_SITE_LOGO;

  /** 网站logo */
  private File logo;

  /** 网站logo名称 */
  private String logoFileName;

  /** 链接排序编号 格式为 编号:序号,编号:序号 */
  private String seqNoList;

  /** 保存排序链接 */
  public String saveOrderLink() {
    if (seqNoList == null) {
      return SUCCESS;
    }
    // 得到页面上排序结果数组
    for (String orderLink : seqNoList.split(",")) {
      String[] order = orderLink.split(":");
      Links links = getManager().get(Integer.valueOf(order[0]));
      links.setOrderId(Integer.valueOf(order[1]));
      getManager().save(links);
    }
    return SUCCESS;
  }

  /**
   * 保存友情链接
   */
  @Override
  public String save() {
    // 设置排序值
    if (getModel().getId() == null) {
      getModel().setOrderId(getManager().getNextOrderValue());
    }

    String file = UpLoadUtil.doUpload(logo, logoFileName, LINK_SITE_LOGO, getServletContext());
    if (file != null) {
      // 设置新文件
      logger.debug("Site log UpLoad SUCCESS：" + file);
      getModel().setSiteLogo(file);
    }
    return super.save();
  }

  /**
   * new连接
   * 
   * @return
   */
  public String newLink() {
    // 设置默认值
    getModel().setIsElite(CmsConstants.N);
    return SUCCESS;
  }

  /**
   * 链接列表显示
   * 
   * @see com.systop.core.webapp.struts2.action.DefaultCrudAction#pageQuery()
   */
  @Override
  protected Page pageQuery() {
    return getManager().pageQuery(PageUtil.getPage(getPageNo(), getPageSize()),
        setupDetachedCriteria());
  }

  /**
   * 封装查询条件
   */
  private final DetachedCriteria setupDetachedCriteria() {
    Example example = Example.create(getModel()).ignoreCase().setPropertySelector(
    /**
     * 选择不为null的属性作为查询条件
     */
    new PropertySelector() {
      public boolean include(Object propertyValue, String propertyName, Type type) {
        return propertyValue != null;
      }
    }).enableLike(MatchMode.ANYWHERE);

    DetachedCriteria criteria = DetachedCriteria.forClass(getModel().getClass()).add(example)
        .createAlias("linkCatas", "linkCatas");

    // 根据类别进行查询
    if (getModel() != null && getModel().getLinkCatas() != null
        && getModel().getLinkCatas().getId() != null) {
      criteria.add(Restrictions.eq("linkCatas.id", getModel().getLinkCatas().getId()));
    }
    return super.setupSort(criteria);
  }

  /**
   * 设置推荐状态
   * 
   * @return
   */
  public String updateElite() {
    if (getModel().getId() != null) {
      logger.debug("Prepare editing object '" + getModel() + "'");
      // 设置推荐状态
      getModel().setIsElite(
          CmsConstants.Y.equals(getModel().getIsElite()) ? CmsConstants.N : CmsConstants.Y);
      getManager().save(getModel());
    }
    return SUCCESS;
  }

  /**
   * 设置审核状态
   * 
   * @return
   * 
   * public String updateAudit() { Serializable id = (Serializable) ReflectUtil.get(model, "id"); if
   * (id != null) { // 得到链连信息 model = getManager().get(getEntityClass(), id); if
   * (log.isDebugEnabled()) { log.debug("Prepare editing object '" + model + "'"); } //设置审核状态
   * model.setIsPassed(CmsConstants.Y.equals(model.getIsPassed()) ? CmsConstants .N :
   * CmsConstants.Y); getManager().save(model); } return SUCCESS; }
   */

  /**
   * 得到类别列表
   */
  public List<LinkCatas> getCatas() {
    return getManager().listLinkCatas();
  }

  /**
   * 得到链接排序列表
   */
  public List<Links> getOrderLinks() {
    return getManager().orderLinks();
  }

  /**
   * 返回是与否Map列表（通用）
   */
  public Map<String, String> getStates() {
    return CmsConstants.YN_MAP;
  }

  public void setSeqNoList(String seqNoList) {
    this.seqNoList = seqNoList;
  }

  public void setLogo(File logo) {
    this.logo = logo;
  }

  public void setLogoFileName(String logoFileName) {
    this.logoFileName = logoFileName;
  }
}
