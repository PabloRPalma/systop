package com.systop.cms.webview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import quake.special.SpecialConstants;
import quake.special.model.Special;

import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;

/**
 * 地震专题数据
 * @author DU
 *
 */
@Service
public class SpecialFreeMarkerManager extends BaseGenericsManager<Special>{

  /**
   * 取得地震专题
   * @param size 个数
   */
  @SuppressWarnings("unchecked")
  public List<Special> getSpecialToIndex(int size) {
    String hql = "from Special s where s.state = ? order by s.createDate DESC";
    Page page = new Page(Page.start(1, size), size);
    page = pageQuery(page, hql, SpecialConstants.VERIFY);
    return page.getData();
  }
}
