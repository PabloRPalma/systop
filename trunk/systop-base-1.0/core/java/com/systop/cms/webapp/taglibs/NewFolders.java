package com.systop.cms.webapp.taglibs;

import java.util.List;

import com.systop.cms.Constants;
import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * @author Systop_Guo
 */		
public class NewFolders extends BaseCMSFreeMarkerTagSupport {

	/**
	 * 显示几个最新目录
	 */
	private Integer displayRows;
	
	/**
	 */
	@Override
	protected String getDefaultTemplate() {
		return "newFolders";
	}

	/**
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {
		assert (getDao() != null);
		this.setHTMaxResults(displayRows == null 
				? DEFAULT_DISPLAY_ROWS : displayRows);
		List<Content> data = getDao().find(
				"from Content c where c.type=? order by c.createTime desc" ,
				Constants.TYPE_FOLDER);
		ctx.addParameter(com.systop.common.Constants.DEFAULT_LIST_NAME, data);
	}
	
	/**
	 * @return displayRows
	 */
	public Integer getDisplayRows() {
		return displayRows;
	}

	/**
	 * @param displayRows the displayRows to set
	 */
	public void setDisplayRows(Integer displayRows) {
		this.displayRows = displayRows;
	}

}
