package com.systop.cms.webapp.taglibs;

import java.util.List;

import com.systop.cms.Constants;
import com.systop.cms.model.Content;
import com.systop.common.webapp.taglibs.template.TemplateContext;

/**
 * 此标签用于显示最新通过审核的若干条文章
 * @author GHL
 */
public class NewArticles extends BaseCMSFreeMarkerTagSupport {

	/** 显示几条最新文章*/
	private Integer displayRows = DEFAULT_DISPLAY_ROWS;
	
	/**	点击文章标题打开的文章阅读页面 */
	private String openPage = "eg/veiwContent.jsp";
	
	/**	目标 如同&lt;a&gt;　元素中的target属性 */
	private String target = "_blank";
	

	@Override
	protected String getDefaultTemplate() {
		return "newArticles";
	}

	/**
	 * 
	 */
	@Override
	protected void setTemplateParameters(TemplateContext ctx) {
		assert (getDao() != null);
		this.setHTMaxResults(displayRows);
		List<Content> data = getDao().find(
				"from Content c where c.type=? order by c.createTime desc" ,
				Constants.TYPE_RICHTEXT);
		ctx.addParameter(com.systop.common.Constants.DEFAULT_LIST_NAME, data);
		
		ctx.addParameter("openPage", this.openPage);
	  ctx.addParameter("target", this.target);
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

	public String getOpenPage() {
		return openPage;
	}

	public void setOpenPage(String openPage) {
		this.openPage = openPage;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
