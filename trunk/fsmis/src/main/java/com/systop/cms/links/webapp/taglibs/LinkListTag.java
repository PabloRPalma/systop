package com.systop.cms.links.webapp.taglibs;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Links;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 主页显示友情链接列表
 * 
 * @author Bin
 */
@SuppressWarnings("serial")
public class LinkListTag extends BaseFreeMarkerTagSupport {

  /**
   * 友情链接显示条数
   */
  private int size = 10;

  /**
   * 默认模板名称
   */
  @Override
  protected String getDefaultTemplate() {
    return "linkList";
  }

  /**
   * 
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setTemplateParameters(TemplateContext ctx) {
    BaseHibernateDao dao = (BaseHibernateDao) getBean("dao");

    DetachedCriteria criteria = DetachedCriteria.forClass(Links.class);
    criteria.addOrder(Order.asc("orderId"));

    Criteria cit = criteria.getExecutableCriteria(dao.getSessionFactory().openSession());
    List<Links> list = cit.setMaxResults(getSize()).list();

    ctx.addParameter(CmsConstants.DEFAULT_LIST_NAME, list.isEmpty() ? null : list);
    ctx.addParameter(CmsConstants.DEFAULT_TAG_ID, getId());
    ctx.addParameter("width", this.getWidth());
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
