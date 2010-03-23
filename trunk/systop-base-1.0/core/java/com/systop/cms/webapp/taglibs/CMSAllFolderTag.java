package com.systop.cms.webapp.taglibs;

import java.util.List;

import com.systop.cms.Constants;
import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * Tag 显示所以目录
 * @author Systop_Guo 
 */
public class CMSAllFolderTag extends BaseCMSFreeMarkerTagSupport {

	@Override
	protected String getDefaultTemplate() {
		return "allFolder";
	}

	/**
	 * 执行封装数据的方法
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {

		assert (getDao() != null);
		
	  this.setHTMaxResults(0);
		List<Content> data = getDao().find("from Content c where c.type=?",
				Constants.TYPE_FOLDER);

		ctx.addParameter(com.systop.common.Constants.DEFAULT_LIST_NAME, data);
	}

}
