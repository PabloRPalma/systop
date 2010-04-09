package com.systop.fsmis.googlemap.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.fsmis.model.GoogleMap;

/**
 * GoogleMap密钥管理类
 * @author DU
 *
 */
@Service
public class GoogleMapManager {

  private BaseHibernateDao baseHibernateDao;

  /**
   * 设置主键值，googleMap密钥在数据库中仅存储一行
   */
  private final String PK = "googlemapid";

  /** 
   * 获取指定密钥
   */
  public GoogleMap get() {
    return baseHibernateDao.get(GoogleMap.class, PK);
  }

  /**
   * 保存密钥信息
   * 
   * @param googleMap
   */
  @Transactional
  public void save(GoogleMap googleMap) { 
    Assert.notNull(googleMap);
    // 若不存在密钥，为其设置主键值
    if (StringUtils.isBlank(googleMap.getId())) {
      googleMap.setId(PK);
    }
    // 保存密钥信息
    if (get() == null) {
      baseHibernateDao.save(googleMap);
    } else {
      baseHibernateDao.merge(googleMap);
    }
  }

  @Autowired(required = true)
  public void setBaseHibernateDao(BaseHibernateDao baseHibernateDao) {
    this.baseHibernateDao = baseHibernateDao;
  }

}
