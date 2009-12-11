package com.systop.cms.article.webapp.taglibs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Articles;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 主页显示文章列表
 * 
 * @author zhangwei
 */
@SuppressWarnings("serial")
public class ArticleListTag extends BaseFreeMarkerTagSupport {

	/*
	 * 文章列表文章数量
	 */
	private String size = "10";

	/**
	 * 默认模板名称
	 */
	@Override
	protected final String getDefaultTemplate() {
		return "articleList";
	}

	/**
	 * @see BaseFreeMarkerTagSupport#setTemplateParameters()
	 */
	@SuppressWarnings("unchecked")
  @Override
	protected final void setTemplateParameters(final TemplateContext ctx) {
		BaseHibernateDao dao = (BaseHibernateDao) getBean("baseHibernateDao");
		DetachedCriteria criteria = DetachedCriteria.forClass(Articles.class);
		// 审核后的全部文章
		criteria.add(Restrictions.eq("audited", "1"));
		//
		criteria.createCriteria("catalog").add(Restrictions.ne("id", 53444608));
		// 固顶、创建时间排序
		criteria.addOrder(Order.desc("onTop"));
		criteria.addOrder(Order.desc("createTime"));

		Session session = dao.getSessionFactory().openSession();
		try{
			Criteria cit = criteria.getExecutableCriteria(session);
			List<Articles> list = cit.setMaxResults(Integer.parseInt(getSize()))
					.list();
		
			this.log.debug("tag articleList tagid is:" + this.getId());
			this.log.debug("tag articleList size is:" + this.getSize());
			this.log.debug("tag articleList get data size:" + list.isEmpty());
			
			for (Articles a : list) {
				this.log.debug("tag articleList getArticles the title is:"
						+ a.getTitle());
			}
	
			ctx.addParameter(CmsConstants.DEFAULT_LIST_NAME, list.isEmpty() ? null
					: list);
			ctx.addParameter(CmsConstants.DEFAULT_TAG_ID, getId());
			ctx.addParameter("width", this.getWidth());
			String path = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
			log.debug("PATH is: " + path);
			ctx.addParameter("ctx", path);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public final String getSize() {
		return size;
	}

	public final void setSize(final String size) {
		this.size = size;
	}

}
