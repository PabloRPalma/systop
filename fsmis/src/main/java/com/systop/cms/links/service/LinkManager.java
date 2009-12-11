package com.systop.cms.links.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.cms.model.LinkCatas;
import com.systop.cms.model.Links;
import com.systop.core.service.BaseGenericsManager;

/**
 * 友情链接管理
 * 
 * @author Bin
 */
@Service
public class LinkManager extends BaseGenericsManager<Links> {
  @Override
  @Transactional
  public void save(Links entity) {
    if(entity.getId() == null) {
      getDao().save(entity);
    } else {
      getDao().clear();
      getDao().merge(entity);
    }    
  }

  /**
   * 得到链接分类列表
   */
  public List<LinkCatas> listLinkCatas() {
    return getDao().get(LinkCatas.class);
  }

  /**
   * 得到链接排序列表
   */
  public List<Links> orderLinks() {
    String hql = "from Links order by orderId";
    List<Links> list = query(hql);
    return list;
  }

  /**
   * 得到排序最大值
   * 
   * @return int
   */
  @SuppressWarnings("unchecked")
  public int getNextOrderValue() {
    String hql = "select max(orderId) from Links";
    List list = query(hql);
    if (list.isEmpty() || list.get(0) == null) {
      return 1;
    } else {
      return Integer.valueOf(list.get(0).toString()).intValue() + 1;
    }
  }
}
