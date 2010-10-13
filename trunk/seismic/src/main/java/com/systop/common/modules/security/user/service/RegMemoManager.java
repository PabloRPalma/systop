package com.systop.common.modules.security.user.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.systop.common.modules.security.user.model.RegMemo;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 注册说明信息service
 * @author DU
 */
@Service
public class RegMemoManager {
  
  /**
   * 主键，因为只有一条记录，所以主键是固定的
   */
  public static final String PK = "regmemoDef";
  
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao baseHibernateDao;
  
  /**
   * 得到注册说明对象
   */
  public RegMemo get() {
    return baseHibernateDao.get(RegMemo.class, PK);
  }
  
  @Transactional
  public void save(RegMemo entity) {
    Assert.notNull(entity);
    if (StringUtils.isBlank(entity.getId())) {
      entity.setId(PK);
    }
    if (get() != null) {
      baseHibernateDao.merge(entity);
    } else {
      baseHibernateDao.save(entity);
    }
  }
}
