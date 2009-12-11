package com.systop.cms.utils;

import com.systop.core.dao.support.Page;

/**
 * 构件Page帮助类
 * @author Lunch
 */
public final class PageUtil {

  private PageUtil() {
  }
  
  /**
   * 获得新的Page对象
   * @param pageNo　分页，页码
   * @param pageSize　每页显示条数
   * @return
   */
  public static Page getPage(int pageNo, int pageSize){
    return new Page(Page.start(pageNo, pageSize), pageSize);
  }
}
