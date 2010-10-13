package com.systop.cms.webview.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quake.admin.czcatalog.model.CzCatalog;
import quake.admin.ds.service.DataSourceManager;
import quake.admin.sitecfg.model.SiteCfg;
import quake.admin.sitecfg.service.SiteCfgManager;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;

import com.systop.core.dao.support.Page;


/**
 * 首页地震目录显示
 * @author wbb
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
  
  /**
   * 查询首页前10条地震目录信息
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Map> queryHomeCzCats(int pageNo, int pageSize) {
    CzCatalog czCat = getProvinceCat();
    if (czCat == null) {
      return Collections.EMPTY_LIST;
    }
    
    Criteria model = new Criteria();
    Page page = new Page(pageNo, pageSize);
    //倒序时间
    model.setSortProperty("O_TIME");
    model.setSortDir("desc");
    model.setPage(page);
    model.setSchema(dataSourceManager.getCzSchema());
    model.setTableName(czCat.getCltName());
    model.setMagTname(czCat.getMagTname());
    //最小地震目录（SAM）
    model.setStartM(siteCfgManager.getCmsConfig().getMinM());
    return gridCatDao.query(model).getData();
  }
  
  /**
   * 得到本省地震目录
   * @return
   */
  public CzCatalog getProvinceCat() {
    SiteCfg siteCfg = siteCfgManager.getCmsConfig();
    if (siteCfg == null) {
      return null;
    }
    
    return siteCfg.getProvinceCat();
  }
}
