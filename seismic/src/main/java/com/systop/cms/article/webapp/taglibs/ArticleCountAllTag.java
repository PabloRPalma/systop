package com.systop.cms.article.webapp.taglibs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.systop.cms.model.Articles;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 文章总数标签
 * 
 * @author zhangwei
 */
@SuppressWarnings("serial")
public class ArticleCountAllTag extends BodyTagSupport {

  private static Log logger = LogFactory.getLog(ArticleCountAllTag.class);

  /*
   * @see BodyTagSupport#doEndTag()
   */
  @SuppressWarnings("unchecked")
  @Override
  public int doEndTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    // 获取当前登录用户
    User user = UserUtil.getPrincipal(request);
    BaseHibernateDao dao = (BaseHibernateDao) getBean("baseHibernateDao");
    DetachedCriteria criteria = DetachedCriteria.forClass(Articles.class);
    logger.debug("display-" + user);
    Session session = dao.getSessionFactory().openSession();
    if (user != null) {
      try {
        criteria.createAlias("inputer", "inp").add(
            Restrictions.eq("inp.id", Integer.valueOf(UserUtil.getPrincipal(request).getId())));

        Criteria cit = criteria.getExecutableCriteria(session);

        List<Articles> list = cit.list();

        pageContext.getOut().print(list.size());
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        session.close();
      }
    }
    return EVAL_PAGE;
  }

  /**
   * 从spring中获取一个bean.
   */
  public Object getBean(String beanName) {
    ServletContext servletCtx = pageContext.getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
    return ctx.getBean(beanName);
  }
}
