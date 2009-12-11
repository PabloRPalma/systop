package com.systop.cms.webview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.model.SoftCatas;
import com.systop.cms.model.Software;
import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;

/**
 * 软件下载,模板用到的Manager
 * 
 * @author Lunch
 */
@Service
public class SoftwareFreeMarkerManager extends BaseGenericsManager<Software> {

  /**
   * 获取所有软件下载栏目
   * @return
   */
  public List<SoftCatas> getSoftCatas() {
    return getDao().get(SoftCatas.class);
  }
  
  /**
   * 根据软件类别，获得分页数据
   * @param cataId
   * @param pageNo
   * @param pageSize
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Software> getSoftwares(Integer cataId, Integer pageNo, Integer pageSize){
    String hql = "from Software s where s.softCatalog.id = ? order by s.pubDate desc";
    Page page = new Page(Page.start(pageNo, pageSize), pageSize);
    page = pageQuery(page, hql, cataId);
    return page.getData();
  }

}
