package com.systop.cms.webview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.core.dao.support.Page;

import datashare.metadata.model.Metadata;
import datashare.metadata.service.MetadataManager;

@Service
public class MetadataFreeMarkerManager {
  @Autowired(required = true)
  private MetadataManager metadataManager;
  
  /**
   * 得到元数据
   */
  @SuppressWarnings("unchecked")
  public List<Metadata> queryMetadata(int pageNo, int pageSize) {
    String hql = "from Metadata";
    Page page = new Page(Page.start(pageNo, pageSize), pageSize);
    return metadataManager.pageQuery(page, hql, new Object[]{}).getData();
  }
  
  /**
   * 得到元数据信息
   */
  public Metadata getMatadataInfo(String type) {
    return metadataManager.getMatadataInfo(type);
  }
}
