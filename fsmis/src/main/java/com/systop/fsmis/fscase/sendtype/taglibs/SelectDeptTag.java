package com.systop.fsmis.fscase.sendtype.taglibs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport;
import com.systop.core.ApplicationException;
import com.systop.fsmis.fscase.sendtype.service.CountySendTypeManager;
import com.systop.fsmis.model.CountySendType;

/**
 * 
 * @author Lunch
 */
public class SelectDeptTag extends BaseFreeMarkerTagSupport {
	
	private LoginUserService loginUserService;

	private CountySendTypeManager cstManager;

	private DeptManager deptManager;

	private void initManager() {
		loginUserService = (LoginUserService) getBean("loginUserService");
		cstManager = (CountySendTypeManager) getBean("countySendTypeManager");
		deptManager = (DeptManager) getBean("deptManager");
	}
	
	/** 标签的name属性，用于表单提交 */
	private String name;

	/** 显示表格有几列 */
	private Integer column;

	/** 每列宽度 */
	private Integer columnWidth;

	/** 分割线 */
	private String splitLineStyle;

	/** 具体项目的css样式 */
	private String itemClass;

	/** 派遣类别ID */
	private Integer sendTypeId;

	/** 默认模板名称 */
	private String defaultTemplate = "selectDeptTag";

	@Override
	protected String getDefaultTemplate() {
		return defaultTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setTemplateParameters(TemplateContext ctx) {
		initManager();
		deptManager = (DeptManager) getBean("deptManager");

		List<Dept> depts = getDepts();
		List<Map> deptMaps = Util.toMap(depts, getCountyStype()
				.getGeneralDept());
		
		logger.debug("CountySendTypes select dept id:{}", getCountyStype()
				.getGeneralDept());
		ctx.addParameter("depts", deptMaps);
		ctx.addParameter("column", column);
		ctx.addParameter("itemClass", itemClass);
		ctx.addParameter("columnWidth", columnWidth);
		ctx.addParameter("splitLineStyle", splitLineStyle);
	}

	/**
	 * 获得当前登陆区县的执法部门
	 * 
	 * @return
	 */
	private List<Dept> getDepts() {
		return deptManager.getDeptsByCounty(getCurrentCounty().getId());
	}

	/**
	 * 获得当前登陆区县的CountySendType
	 * 
	 * @return
	 */
	private CountySendType getCountyStype() {
		return cstManager.getBySendTypeAndCounty(sendTypeId, getCurrentCounty()
				.getId());
	}

	/**
	 * 获得当前登陆用户所属的部门(区县)
	 * 
	 * @return
	 */
	private Dept getCurrentCounty() {
		Dept county = loginUserService
				.getLoginUserDept((HttpServletRequest) pageContext.getRequest());
		if (county == null) {
			throw new ApplicationException("用户未登陆,或者无有效部门");
		}
		logger.debug("当前所属区县:{}", county.getName());
		return county;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(Integer columnWidth) {
		this.columnWidth = columnWidth;
	}

	public String getSplitLineStyle() {
		return splitLineStyle;
	}

	public void setSplitLineStyle(String splitLineStyle) {
		this.splitLineStyle = splitLineStyle;
	}

	public String getItemClass() {
		return itemClass;
	}

	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	public Integer getSendTypeId() {
		return sendTypeId;
	}

	public void setSendTypeId(Integer sendTypeId) {
		this.sendTypeId = sendTypeId;
	}

}
