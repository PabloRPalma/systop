package com.systop.cms.catalog.webapp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.CmsConstants;
import com.systop.cms.catalog.CatalogConstants;
import com.systop.cms.catalog.service.CatalogManager;
import com.systop.cms.model.Catalogs;
import com.systop.cms.model.Templates;
import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * 文章管理Action
 * 
 * @author yun
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CatalogAction extends DefaultCrudAction<Catalogs, CatalogManager> {

  /** 链接排序编号 格式为 编号:序号,编号:序号 */
  private String seqNoList;

  /** 父栏目id */
  private String parentId;

  /** 栏目模板 */
  private String catalogTemplate;

  /** 文章栏目模板 */
  private String articleTemplate;

  /** 查询值 */
  private String queryValue;

  /** 获取当前登录的用户信息 */
  private User user;

  /**
   * 新建栏目时的初始
   */
  public String newCatalog() {
    // 设置默认值
    getModel().setShowOnIndex(CmsConstants.N);
    getModel().setShowOnTop(CmsConstants.N);
    getModel().setShowOnParlist(CmsConstants.N);
    getModel().setIsEnable(CmsConstants.Y);
    return SUCCESS;
  }

  /**
   * 执行查询
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
  protected DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria dc = DetachedCriteria.forClass(Catalogs.class);
    if (StringUtils.isBlank(queryValue)) {
      if (StringUtils.isNotBlank(parentId) && !parentId.equals("0")) {
        dc.createCriteria("parentCatalog").add(Restrictions.eq("id", Integer.valueOf(parentId)));
      } else {
        dc.add(Restrictions.isNull("parentCatalog"));
      }
    } else {
      dc.add(Restrictions.like("name", "%" + queryValue + "%"));
    }
    // 显示栏目树型结构时使用
    dc.addOrder(Order.desc("groupId"));
    dc.addOrder(Order.asc("orderId"));
    // dc.addOrder(Order.asc("rootPath"));
    return dc;
  }

  /**
   * 覆盖父类
   */
  @SuppressWarnings("unchecked")
  public String save() {
    // 栏目路径
    String path = "/";
    //
    if (parentId == null) {
      addActionError("所属栏目没有加载成功，请重新再试一次");
      return INPUT;
    }
    // 设置父栏目
    if ("0".equals(parentId)) {
      getModel().setParentCatalog(null);
      path = path + getModel().getRoot();
      // 对栏目分组是为了栏目显示时可以显示出栏目的树形结构（暂留）
      if (getModel().getId() == null) {
        getModel().setGroupId(getManager().getNextGroupValue());
      }
      getModel().setRootPath(path);
    } else {
      Catalogs parenCata = getManager().get(Integer.parseInt(parentId));
      if (parenCata != null) {
        getModel().setParentCatalog(parenCata);
        // 设置栏目所属组
        getModel().setGroupId(getCatalog(parenCata).getGroupId());
        path = path + getModel().getRoot();
        path = "/" + parenCata.getRoot() + path;
        // 得到栏目的存放目录
        parenCata = parenCata.getParentCatalog();
        while (parenCata != null) {
          path = "/" + parenCata.getRoot() + path;
          parenCata = parenCata.getParentCatalog();
        }
        getModel().setRootPath(path);

      }
    }
    if (getModel().getType().equals("")
        || getModel().getType().equals(CatalogConstants.CATALOG_TYPE_INNER)) {
      // 设置栏目类别
      getModel().setType(CatalogConstants.CATALOG_TYPE_INNER);
      // 判断目录名是否为空
      if (StringUtils.isBlank(getModel().getRoot())) {
        addActionError("目录名不能为空！");
        return INPUT;
      }
      // 判断栏目名称是否已存在
      if (getManager().getDao().exists(getModel(), "root")) {
        addActionError("目录名称'" + getModel().getRoot() + "'已存在!");
        return INPUT;
      }
      // 设置栏目的模板
      if (StringUtils.isBlank(catalogTemplate)) {
        addActionError("系统内还没有栏目模板,请先添加栏目模板！");
        return INPUT;
      } else {
        getModel().setCataTemplate(getTemplate(Integer.parseInt(catalogTemplate)));
      }
      // 设置文章的模板
      if (StringUtils.isBlank(articleTemplate)) {
        addActionError("系统内还没有文章模板,请先添加文章模板！");
        return INPUT;
      } else {
        getModel().setArtTemplate(getTemplate(Integer.parseInt(articleTemplate)));
      }

      getModel().setLinkUrl(null);
    } else {
      // 设置栏目类别
      getModel().setType(CatalogConstants.CATALOG_TYPE_EXTERNAL);
      // 外部栏目
      if (StringUtils.isBlank(getModel().getLinkUrl()) || getModel().getLinkUrl().equals("http://")) {
        addActionError("请填写完整链接地址！" + "（如http://www.systop.com）");
        return INPUT;
      }
      getModel().setArtTemplate(null);
      getModel().setCataTemplate(null);
    }
    if (getModel().getId() == null) {
      // 设置排序号
      getModel().setOrderId(getManager().getNextOrderValue());
    }
    getManager().save(getModel());
    // 重设栏目列表
    resetCatalogList();
    return SUCCESS;
  }

  /**
   * 查询子栏目的根栏目(设置栏目所属组)
   * 
   * @param catalog 子栏目
   * @return 根栏目
   */
  public Catalogs getCatalog(Catalogs catalog) {
    while (catalog.getParentCatalog() != null) {
      catalog = catalog.getParentCatalog();
    }
    return catalog;
  }

  /**
   * 保存排序后栏目
   */
  public String saveOrderCatalog() {
    if (seqNoList == null) {
      return SUCCESS;
    }
    // 得到页面上排序结果数组
    for (String ordercCatalog : seqNoList.split(",")) {
      String[] catalogs = ordercCatalog.split(":");
      Catalogs catalog = getManager().get(Integer.valueOf(catalogs[0]));
      catalog.setOrderId(Integer.valueOf(catalogs[1]));
      getManager().save(catalog);
    }
    return SUCCESS;
  }

  /**
   * 更新栏目列表（延时加载）
   */
  public void resetCatalogList() {
    CatalogDwrAction dwrAction = new CatalogDwrAction();
    dwrAction.setDao(getManager().getDao());
    // 先清空数据，否则不会重新构件栏目树
    CatalogConstants.CATALOG_LIST = null;
    CatalogConstants.CATALOG_LIST = dwrAction.getCatalog("catalog", "");
  }

  /**
   * 添加子栏目
   */
  public String addChildCatalog() {
    // 由于SystopBaseAPI升级，用法改变。此段代码注释于2009-03-26 GHL
    // 有下面的新代码替代，测试通过后注释代码可删除.
    // Serializable id = (Serializable) ReflectUtil.get(model, "id");
    // if (id != null) {
    // Catalogs catalog = (Catalogs) getManager().get(getEntityClass(), id);
    // model.setId(null);
    // model.setParentCatalog(catalog);
    // model.setDept(catalog.getDept());
    // // 设置默认值
    // newCatalog();
    // return SUCCESS;
    // }

    if (getModel().getId() != null) {
      Catalogs catalog = new Catalogs();
      catalog.setParentCatalog(getModel());
      setModel(catalog);
      // 设置默认值
      newCatalog();
      return SUCCESS;
    }

    return INPUT;
  }

  /**
   * 根据模板的ID查询模板
   * 
   * @param templateId 模板ID
   * @return Templates 模板
   */
  @SuppressWarnings("unchecked")
  public Templates getTemplate(Integer templateId) {
    Templates t = getManager().getDao().get(Templates.class, templateId);
    return t;
  }

  /**
   * 得到栏目排序列表
   */
  public List<Catalogs> getOrderCatalogs() {
    if (StringUtils.isBlank(queryValue)) {
      return getManager().orderCatalogs();
    } else {
      return getManager().getOrderCatalogsById(Integer.parseInt(queryValue));
    }
  }

  /**
   * 返回是与否Map列表（通用）
   */
  public Map<String, String> getStates() {
    return CmsConstants.YN_MAP;
  }

  /**
   * 获取当前登录的用户信息
   * 
   * @return
   */
  public User getUser() {
    if (UserUtil.getPrincipal(getRequest()) != null) {
      user = getManager().getDao().get(User.class, UserUtil.getPrincipal(getRequest()).getId());
    }
    return user;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getArticleTemplate() {
    return articleTemplate;
  }

  public void setArticleTemplate(String articleTemplate) {
    this.articleTemplate = articleTemplate;
  }

  public String getCatalogTemplate() {
    return catalogTemplate;
  }

  public void setCatalogTemplate(String catalogTemplate) {
    this.catalogTemplate = catalogTemplate;
  }

  public String getQueryValue() {
    return queryValue;
  }

  public void setQueryValue(String queryValue) {
    this.queryValue = queryValue;
  }

  public String getSeqNoList() {
    return seqNoList;
  }

  public void setSeqNoList(String seqNoList) {
    this.seqNoList = seqNoList;
  }
}
