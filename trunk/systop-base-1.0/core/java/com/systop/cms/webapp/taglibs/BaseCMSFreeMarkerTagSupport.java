package com.systop.cms.webapp.taglibs;

import com.systop.common.Constants;
import com.systop.common.dao.impl.BaseHibernateDAO;
import com.systop.common.util.ResourceBundleUtil;
import com.systop.common.webapp.taglibs.freemarker.BaseFreeMarkerTagSupport;
import com.systop.common.webapp.taglibs.template.TemplateContext;
/**
 * CMS系统所用到的FreeMarker Taglib的基类
 * 
 * @author SAM Lee
 *
 */
public abstract class BaseCMSFreeMarkerTagSupport 
  extends BaseFreeMarkerTagSupport {
  /**
   * <code>CmsTagContext</code>对象在Freamarker模板中的名字
   */
  public static final String CMS_CONTEXT_NAME = 
    ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
        "global.freemarker.cmsContext", "cmsContext");
  /**
   * the id of the tag.
   */
  private String id;
 
  /**
   * 缺省的Taglib中显示的行数.
   */
  public static final Integer DEFAULT_DISPLAY_ROWS = 5;
  /**
   * 返回BaseHibernateDAO的实例.
   */
  public BaseHibernateDAO getDao() {
    return (BaseHibernateDAO) getBean("baseDao");
  }

  /**
   * 设置HibernateTemplate的MaxResults[最大结果集]，当为传参为[0]的时候结果集不限制
   * @param arg0
   */
  public void setHTMaxResults(int arg0) {
  	assert (getDao() != null);
  	getDao().getHibernateTemplate().setMaxResults(arg0);
  }
  
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }
  
  

  /**
   * @see com.systop.common.webapp.taglibs.BaseTemplateTagSupport#release()
   */
  @Override
  public void release() {
    this.id = null;
    super.release();
  }
  /**
   * 调用父类initTempateContext，并设置id参数
   * @see BaseFreeMarkerTagSupport#initTempateContext()
   */
  @Override
  protected TemplateContext initTempateContext() {
     super.initTempateContext();
     templateContext.addParameter("id", id);
     return templateContext;
  }
}



  
  
