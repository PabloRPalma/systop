package com.systop.cms.catalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.model.Articles;
import com.systop.cms.model.Catalogs;
import com.systop.cms.model.Templates;
import com.systop.core.service.BaseGenericsManager;

/**
 * 栏目管理
 * 
 * @author yun
 */
@Service
public class CatalogManager extends BaseGenericsManager<Catalogs> {

  /**
   * 根据栏目的ID查询栏目
   * 
   * @param catalogId 栏目ID
   * @return List
   */
  @SuppressWarnings("unchecked")
  public List getCatalog(Integer catalogId) {
    List<Catalogs> catalogList = query("from Catalogs c where c.id =?", catalogId);
    return catalogList;
  }

  /**
   * 根据部门的ID查询部门
   * 
   * @param deptId 部门ID
   */
  @java.lang.Deprecated
  @SuppressWarnings("unchecked")
  public List getDept(Integer deptId) {
    List deptList = getDao().query("from Dept d where d.id = ?", deptId);
    return deptList;
  }

  /**
   * 根据模板的ID查询模板
   * 
   * @param tempId 模板ID
   * @return List 模板列表
   */
  @SuppressWarnings("unchecked")
  public List getTemplate(Integer tempId) {
    List<Templates> tempList = getDao().query("from Templates t where t.id =?", tempId);
    return tempList;
  }

  /**
   * 根据栏目的ID查询文章
   * 
   * @param catalogId 栏目ID
   * @return List 文章列表
   */
  @SuppressWarnings("unchecked")
  public List articleExists(Integer catalogId) {
    List<Articles> articleList = getDao().query("from Articles a where a.catalog.id =?", catalogId);
    return articleList;
  }

  /**
   * 根据栏目的ID查询子栏目
   * 
   * @param catalogId 栏目ID
   * @return List 栏目列表
   */
  @SuppressWarnings("unchecked")
  public List catalogExists(Integer catalogId) {
    List<Catalogs> catalogList = query("from Catalogs c where c.parentCatalog.id =?", catalogId);
    return catalogList;
  }

  /**
   * 根据栏目的ID查询栏目
   * 
   * @param catalogId 栏目ID
   * @return Catalog 栏目
   */
  public Catalogs getCatalogById(Integer catalogId) {
    List<Catalogs> catalogList = query("from Catalogs c where c.id =?", catalogId);
    return catalogList.get(0);
  }

  /**
   * 得到排序最大值
   * 
   * @return int 序列号
   */
  @SuppressWarnings("unchecked")
  public int getNextOrderValue() {
    String hql = "select max(orderId) from Catalogs";
    List list = query(hql);
    if (list.isEmpty() || list.get(0) == null) {
      return 1;
    } else {
      return Integer.valueOf(list.get(0).toString()).intValue() + 1;
    }
  }

  /**
   * 得到组最大值
   * 
   * @return int 序列号
   */
  @SuppressWarnings("unchecked")
  public int getNextGroupValue() {
    String hql = "select max(groupId) from Catalogs";
    List list = query(hql);
    if (list.isEmpty() || list.get(0) == null) {
      return 1;
    } else {
      return Integer.valueOf(list.get(0).toString()).intValue() + 1;
    }
  }

  /**
   * 得到栏目排序列表
   * 
   * @return List 排序列表
   */
  public List<Catalogs> orderCatalogs() {
    String hql = "from Catalogs c where c.parentCatalog " + "is null order by orderId";
    List<Catalogs> list = query(hql);
    return list;
  }

  /**
   * 根据部门ID得到栏目排序列表
   * 
   * @param deptId 部门ID
   * @return List 排序列表
   */
  @java.lang.Deprecated
  public List<Catalogs> orderCatalogs(Integer deptId) {
    String hql = "from Catalogs c where c.parentCatalog "
        + "is null and c.dept.id =? order by orderId";
    List<Catalogs> list = query(hql, deptId);
    return list;
  }

  /**
   * 根据栏目名称得到子栏目排序列表
   * 
   * @param catalogName 栏目名称
   * @return List 排序列表
   */
  public List<Catalogs> getOrderCatalogsByName(String catalogName) {
    String hql = "from Catalogs c where c.parentCatalog.name =? " + "order by orderId";
    List<Catalogs> list = query(hql, catalogName);
    return list;
  }

  /**
   * 根据栏目ID得到子栏目排序列表
   * 
   * @param catalogId 栏目ID
   * @return List 排序列表
   */
  public List<Catalogs> getOrderCatalogsById(Integer catalogId) {
    String hql = "from Catalogs c where c.parentCatalog.id =? " + "order by orderId";
    List<Catalogs> list = query(hql, catalogId);
    return list;
  }

  /**
   * 根据栏目ID和部门ID得到子栏目排序列表
   * 
   * @param catalogId 栏目ID
   * @param deptId 部门ID
   * @return 排序列表
   */
  public List<Catalogs> getOrderCatalogsById(Integer catalogId, Integer deptId) {
    String hql = "from Catalogs c where c.parentCatalog.id =? "
        + "and c.dept.id = ? order by orderId";
    List<Catalogs> list = query(hql, new Object[] { catalogId, deptId });
    return list;
  }
}
