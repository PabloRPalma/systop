package com.systop.cms.webview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.cms.CmsConstants;
import com.systop.cms.announce.AnnounceConstants;
import com.systop.cms.model.Announces;
import com.systop.cms.model.Articles;
import com.systop.cms.model.Catalogs;
import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;
import com.systop.core.util.DateUtil;

/**
 * @author yj
 */
@Service
public class AnnounceFreeMarkerManager extends BaseGenericsManager<Announces> {
  /**
   * 首页影讯演出公告列表
   * 
   * @param size 显示分页条数
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Articles> getArticles(String name, int size) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by createTime DESC";
    List<Articles> list = getDao().query(hql, 1, size, new Object[] { name, CmsConstants.Y });
    return list;
  }

  /**
   * 是否显示首页网站公告列表
   * 
   * @param size 显示分页条数
   * @param type 显示类型弹出或滚动
   * @param catalogId 栏目ID， 0为无栏目
   * @param exist 确定是否“更多”显示
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Announces> getAnnounceExist(int size, int type, Integer catalogId, int exist) {
    String hql = "from Announces  a  where a.showType = ? order by creatDate desc ";
    List<Announces> list = null;
    Page page = new Page(1, size);
    if (catalogId == 0) {
      if (type == 0) {
        list = pageQuery(page, hql, new Object[] {AnnounceConstants.UNSHOETYPE }).getData();
      } else {
        list = pageQuery(page, hql, new Object[] {AnnounceConstants.SHOETYPE }).getData();
      }
    } 
    
    List<Announces> returnList = new ArrayList();
    List<Announces> myList = checkOutDate(list);
    if (exist == 0) {
      if (myList.size() > 0 && myList != null) {
        returnList.add(0, myList.get(0));
      }
    } else {
      return myList;
    }
    return returnList;
  }

  /**
   * 对List中有效期的验证
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Announces> checkOutDate(List<Announces> list) {
    List<Announces> announceList = new ArrayList<Announces>();
    for (Iterator itr = list.iterator(); itr.hasNext();) {
      Announces announce = (Announces) itr.next();
      if (announce.getOutTime().intValue() != 0) {
        if (dateCompare(DateUtil.getCurrentDate(), DateUtil.getDate(announce.getCreatDate(),
            announce.getOutTime().intValue()))) {
          announceList.add(announce);
        }
      } else {
        announceList.add(announce);
      }
    }
    return announceList;
  }

  /**
   * 日期比较
   * 
   * @param dat1 dat2
   * @return Catalog 栏目
   */
  public static boolean dateCompare(Date dat1, Date dat2) {
    boolean dateComPareFlag = true;
    if (dat2.compareTo(dat1) != 1) {
      dateComPareFlag = false; // 
    }
    return dateComPareFlag;
  }

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
