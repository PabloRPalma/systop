package com.systop.cms.webview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.model.Links;
import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;

/**
 * 首页链接列表
 */
@Service
public class LinkFreeMarkerManager extends BaseGenericsManager<Links> {
  /**
   * 首页链连列表
   * @param size 显示分页条数
   * @return
   */
  @SuppressWarnings("unchecked")
  public List getLinks(int size) {
    String hql = "from Links order by orderId";
    Page page = new Page(Page.start(1, size), size);
    page = pageQuery(page, hql);
    return page.getData();
  }
}
