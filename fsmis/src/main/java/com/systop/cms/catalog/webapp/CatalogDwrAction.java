package com.systop.cms.catalog.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.systop.cms.CmsConstants;
import com.systop.cms.catalog.CatalogConstants;
import com.systop.cms.catalog.service.CatalogManager;
import com.systop.cms.model.Catalogs;
import com.systop.cms.model.Templates;
import com.systop.cms.template.TemConstants;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.webapp.dwr.BaseDwrAjaxAction;

/**
 * CatalogDwrAction
 * 
 * @author yun
 */
@SuppressWarnings( { "unchecked" })
@Component
public class CatalogDwrAction extends BaseDwrAjaxAction {

  /**
   * 栏目管理Manager
   */
  @Autowired(required = true)
  @Qualifier("catalogManager")
  private CatalogManager catalogManager;

  /**
   * 获取当前登录的用户信息
   */
  private User user;

  /**
   * 查询栏目
   * 
   * @return List 栏目树状图
   */
  public List getCatalog(String type, String catalogId) {
	 /* 竟然用静态变量当缓存！
    if (CatalogConstants.CATALOG_LIST == null) {
      CatalogConstants.CATALOG_LIST = createNewCatalog(type, catalogId);
      return CatalogConstants.CATALOG_LIST;
    } else {
      return CatalogConstants.CATALOG_LIST;
    }*/
	  return createNewCatalog(type, catalogId);
  }

  /**
   * 生成新的栏目列表
   * 
   * @param type 类别
   * @param catalogId 栏目ID
   * @return
   */
  private ArrayList createNewCatalog(String type, String catalogId) {
    logger.info("create catalogList");
    if (StringUtils.isBlank(catalogId)) {
      catalogId = "1";
    }
    // 栏目树
    ArrayList list = new ArrayList();
    Map parent = new HashMap();
    // 默认的根目录
    parent.put("id", CatalogConstants.TOP_CATALOG_ID);
    parent.put("text", CatalogConstants.FIRST_CATALOG_NAME);
    // 将栏目存入树中(只有在添加栏目时，才将根栏目加入树中，其它的不加）
    if (type.equals("catalog")) {
      list.add(parent);
    }

    // 查询子栏目
    getChildCatalog(list, parent, 0, catalogId);
    return list;
  }

  /**
   * 查询模板
   * 
   * @return List 模板列表
   */
  public List getTemplate(String type) {
    List<Templates> tempList = new ArrayList();
    List<Templates> templates;
    if (type.equals("catalog")) {
      getDefTemp(TemConstants.TEMPLATE_CATALOG, tempList);
      templates = getDao().query("from Templates t where t.type = ? and isDef = ?",
          new Object[] { TemConstants.TEMPLATE_CATALOG, CmsConstants.N });
      tempList.addAll(templates);
      return listToMap(tempList);
    } else {
      getDefTemp(TemConstants.TEMPLATE_ARTICLE, tempList);
      templates = getDao().query("from Templates t where t.type = ? and isDef = ? ",
          new Object[] { TemConstants.TEMPLATE_ARTICLE, CmsConstants.N });
      tempList.addAll(templates);
      return listToMap(tempList);
    }
  }

  /**
   * 查询默认模板
   */
  public void getDefTemp(String type, List<Templates> tempList) {
    List<Templates> templates = getDao().query("from Templates t where t.type = ? and isDef = ?",
        new Object[] { type, CmsConstants.Y });
    if (!templates.isEmpty()) {
      Templates temp = templates.get(0);
      temp.setName(temp.getName() + "    (默认)");
      tempList.add(temp);
    }
  }

  /**
   * 得到子栏目,每一个栏目用一个<code>java.util.Map</code>表示。本方法是一个递归函数
   * 
   * @param list 栏目的树形列表
   * @param parent 父栏目
   * @param signNumber 符号的个数,用于区别栏目的级别.
   */
  public void getChildCatalog(ArrayList list, Map parent, int signNumber, String catalogId) {
    // 栏目级别的符号
    String sign = null;
    // 得到子栏目
    List<Catalogs> catalogs = getByParentId((Integer) parent.get("id"), Integer.valueOf(catalogId));
    // 若查询结果为空,说明此栏目下没有子栏,退出.
    if (catalogs.isEmpty() || catalogs.size() == 0) {
      return;
    }
    // 转换所有子栏目为Map对象，防止dwr造成延迟加载，
    for (int i = 0; i < catalogs.size(); i++) {
      Catalogs catalog = catalogs.get(i);
      if (i == catalogs.size() - 1) {
        // 末尾的级别符号
        sign = "└";
        sign = getSign(sign, signNumber);
      } else {
        // 中国的级别符号
        sign = "├";
        sign = getSign(sign, signNumber);
      }
      Map map = new HashMap();
      map.put("text", sign + catalog.getName());
      map.put("id", catalog.getId());
      // 将子栏目加入栏目的树型结构
      list.add(map);
      // 子栏目的级别加1
      int number = signNumber + 1;
      // 递归查询子栏目
      getChildCatalog(list, map, number, catalogId);
      logger.info(i + ":" + catalogId);
    }
  }

  /**
   * 根据指定的父栏目id查询子栏目
   * 
   * @parentCatalogId 父栏的id
   */
  private List<Catalogs> getByParentId(Integer parentCatalogId, Integer catalogId) {
    List list = Collections.EMPTY_LIST;
    if (parentCatalogId == null || parentCatalogId == CatalogConstants.TOP_CATALOG_ID) {
      // 查询二级栏目
      list = getDao().query(
          "from Catalogs c where c.parentCatalog is null and c.type =? "
              + "and c.id != ? order by orderId",
          new Object[] { CatalogConstants.CATALOG_TYPE_INNER, catalogId });
    } else {
      // 查询子栏目
      list = getDao().query(
          "from Catalogs c where c.parentCatalog.id = ? and c.type =?  "
              + "and c.id != ? order by orderId",
          new Object[] { parentCatalogId, CatalogConstants.CATALOG_TYPE_INNER, catalogId });
    }
    return list;
  }

  /**
   * 计算栏目的级别
   * 
   * @param sign 栏目的级别符号
   * @param signNumber 栏目的级别
   * @return String 栏目的级别
   */
  public String getSign(String sign, int signNumber) {
    for (int number = 0; number < signNumber; number++) {
      sign = "│" + sign;
    }
    return sign;
  }

  /**
   * 将list转化成map,防止dwr造成延迟加载
   * 
   * @param templates 模板列表
   * @return List 模板列表
   */
  public List listToMap(List templates) {
    List templateList = new ArrayList();
    for (Iterator<Templates> itr = templates.iterator(); itr.hasNext();) {
      Templates template = (Templates) itr.next();
      Map templateMap = new HashMap();
      templateMap.put("id", template.getId());
      templateMap.put("name", template.getName());
      templateList.add(templateMap);
    }
    return templateList;
  }

  /**
   * 根据栏目ID查询栏目下有没有子栏目或文章
   * 
   * @param catalogArray 栏目列表
   * @return
   */
  public Map removeCatalog(String[] catalogArray) {
    Map map = new HashMap();
    map.put("id", "0");
    for (int i = 0; i < catalogArray.length; i++) {
      Integer catalogId = Integer.parseInt(catalogArray[i]);
      // 判断栏目下是不有文章
      List articleList = catalogManager.articleExists(catalogId);
      if (articleList.size() != 0 || !articleList.isEmpty()) {
        Catalogs catalog = (Catalogs) catalogManager.getCatalog(catalogId).get(0);
        // 若有栏目返回提示信息
        map.put("id", "1");
        map.put("name", catalog.getName());
        break;
      }
      // 判断栏目下是否有子栏目
      List catalogList = catalogManager.catalogExists(catalogId);
      if (catalogList.size() != 0 || !catalogList.isEmpty()) {
        Catalogs catalog = (Catalogs) catalogManager.getCatalog(catalogId).get(0);
        // 若有文章返回提示信息
        map.put("id", "2");
        map.put("name", catalog.getName());
        break;
      }
    }
    // 删除
    if (map.get("id").equals("0")) {
      for (int i = 0; i < catalogArray.length; i++) {
        Integer catalogId = Integer.parseInt(catalogArray[i]);
        Catalogs catalog = catalogManager.getCatalogById(catalogId);
        catalog.setCataTemplate(null);
        catalogManager.remove(catalog);
      }
    }
    return map;
  }

  public CatalogManager getCatalogManager() {
    return catalogManager;
  }

  public void setCatalogManager(CatalogManager catalogManager) {
    this.catalogManager = catalogManager;
  }

  /**
   * 获取当前登录的用户信息
   * 
   * @return
   */
  public User getUser() {
    if (UserUtil.getPrincipal(getRequest()) != null) {
      user = getDao().get(User.class, UserUtil.getPrincipal(getRequest()).getId());
    }
    return user;
  }
}
