package com.systop.cms.announce.webapp.taglibs;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Announces;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 主页显示网站公告列表
 * 
 * @author jun
 */
@SuppressWarnings("serial")
public class AnnounceListTag extends BaseFreeMarkerTagSupport {
  /**
   * 网站公告显示条数
   */
  private int size = 10;

  /**
   * 默认网站公告名称
   */
  @Override
  protected String getDefaultTemplate() {
    return "announceList";
  }

  /**
   * @see BaseFreeMarkerTagSupport#setTemplateParameters()
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setTemplateParameters(TemplateContext ctx) {
    BaseHibernateDao dao = (BaseHibernateDao) getBean("dao");

    DetachedCriteria criteria = DetachedCriteria.forClass(Announces.class);
    criteria.addOrder(Order.desc("creatDate"));

    Criteria cit = criteria.getExecutableCriteria(dao.getSessionFactory().openSession());
    List<Announces> list = cit.setMaxResults(getSize()).list();

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
