package com.systop.cms.webview.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quake.admin.catalog.model.QuakeCatalog;
import quake.admin.catalog.service.QuakeCatalogManager;
import quake.admin.ds.service.DataSourceManager;
import quake.admin.sitecfg.model.SiteCfg;
import quake.admin.sitecfg.service.SiteCfgManager;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;

import com.systop.core.dao.support.Page;


/**
 * 首页地震目录显示
 * @author DU
 *
 */
@Service
public class CzCatFreeMarkerManager{
  @Autowired(required = true)
  private SiteCfgManager siteCfgManager;
  
  @Autowired(required = true)
  private GridCatDao gridCatDao;
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  @Autowired
  private QuakeCatalogManager catalogManager;
  
  /**
   * 查询首页前10条地震目录信息
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryHomeCzCats(int pageNo, int pageSize) {
    QuakeCatalog czCat = getProvinceCat();
    if (czCat == null) {
      return Collections.EMPTY_LIST;
    }
    
    Criteria model = new Criteria();
    Page page = new Page(pageNo, pageSize);
    //倒序时间
    model.setSortProperty("O_TIME");
    model.setSortDir("desc");
    model.setPage(page);
    model.setSchema(dataSourceManager.getSeismicSchema());
    model.setTableName(czCat.getCltName());
    model.setMagTname(czCat.getMagTname());
    //limit查询，设定start和size
    model.setStart(pageNo);
    model.setSize(pageSize);
    
    //最小地震目录（SAM）
    model.setStartM(siteCfgManager.getCmsConfig().getMinM());
    return gridCatDao.query(model).getData();
  }
  
  /**
   * 取得首页下拉菜单显示的地址目录列表
   * @return
   */
  public List<Map<String, String>> queryMenuCzCats() {
    return catalogManager.getCat();
  }
  
  /**
   * 得到本省地震目录
   * @return
   */
  public QuakeCatalog getProvinceCat() {
    SiteCfg siteCfg = siteCfgManager.getCmsConfig();
    if (siteCfg == null) {
      return null;
    }
    
    return siteCfg.getProvinceCat();
  }
}
