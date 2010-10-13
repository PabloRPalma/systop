package com.systop.cms.announce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.model.Announces;
import com.systop.cms.model.Catalogs;
import com.systop.core.service.BaseGenericsManager;

/**
 * 网站公告管理
 * 
 * @author jun
 */
@Service
public class AnnounceManager extends BaseGenericsManager<Announces> {

  /**
   * 根据栏目的ID查询栏目
   * 
   * @param catalogId 栏目ID
   * @return Catalog 栏目
   */
  @SuppressWarnings("unchecked")
  public Catalogs getCatalogById(Integer catalogId) {
    List<Catalogs> catalogList = getDao().query("from Catalogs c where c.id =?", catalogId);
    return catalogList.get(0);
  }
}
