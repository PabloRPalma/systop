package com.systop.cms.webapp.taglibs;

import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * Tag 用于显示某篇文章的内容
 * @author Systop_Guo
 */
public class ViewContentTag extends BaseCMSFreeMarkerTagSupport {

	/**
	 * @see com.systop.common.webapp.taglibs.freemarker
	 * 			.BaseFreeMarkerTagSupport#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return null;
	}

	/**
	 * @see com.systop.common.webapp.taglibs.freemarker
	 * 			.BaseFreeMarkerTagSupport#setTemplateParameters(
	 * 			com.systop.common.webapp.taglibs.template.TemplateContext)
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {
		
		try { //处理contentId参数传递为非数字的异常
			Integer contentId = Integer.valueOf(
					ctx.getRequest().getParameter("contentId"));
			assert (getDao() != null);
			Content content = this.getDao().getObject(Content.class, contentId);
			
			if (content == null) {
				log.debug("in ViewContent,result of [content] is null!");
			} 
			ctx.addParameter("content", content);
			ctx.addParameter("author", "作者");
		} catch (Exception e) {
			log.error(e.getMessage() + e.getClass()
					+ "in ViewContentTag# format contentId");
		} 
		
		
	}

}
