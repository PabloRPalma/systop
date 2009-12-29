package com.systop.common.modules.dept.webapp.tagligs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.template.TemplateContext;
import com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport;
import com.systop.fsmis.fscase.sendtype.service.CountySendTypeManager;
import com.systop.fsmis.model.CountySendType;

/**
 * 部门选中标签,为选中部门提供便捷
 * @author Lunch
 */
public class SelectDeptTag extends BaseFreeMarkerTagSupport {

	private LoginUserService loginUserService;

	private CountySendTypeManager cstManager;

	private DeptManager deptManager;

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

	/**
	 * 初始化Managers
	 */
	private void initManager() {
		loginUserService = (LoginUserService) getBean("loginUserService");
		cstManager = (CountySendTypeManager) getBean("countySendTypeManager");
		deptManager = (DeptManager) getBean("deptManager");
	}

	/**
	 * 初始化部分参数
	 */
	private void setDefaultParms() {
		column = (column == null) ? 5 : column;
		columnWidth = (columnWidth == null) ? 120 : columnWidth;
	}

	/**
	 * @see com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return defaultTemplate;
	}

	/**
	 * @see com.systop.common.modules.template.freemarker.BaseFreeMarkerTagSupport#setTemplateParameters(com.systop.common.modules.template.TemplateContext)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void setTemplateParameters(TemplateContext ctx) {
		initManager();
		setDefaultParms();

		// 获取当前登陆用户所属区县或者是市级
		Dept county = loginUserService
				.getLoginUserDept((HttpServletRequest) pageContext.getRequest());
		// 定义返回的存储相关部门的list对象
		List<Map> deptMaps = null;
		if (county != null) {
			logger.debug("当前区县:{}", county.getName());
			List<Dept> depts = deptManager.getEnforcementByCounty(county
					.getId());
			logger.debug("当前派遣环节ID:{}", sendTypeId);
			CountySendType cst = cstManager.getCountySendType(sendTypeId,
					county.getId());
			deptMaps = Util.toMap(depts, cst);
			logger.debug("CountySendTypes dept id:{}", cst.getGeneralDept());

		} else {
			ctx.addParameter("errorMsg", "当前用户所属部门无效，请重新登陆并检查所属部门类别。");
		}
		ctx.addParameter("depts", deptMaps);
		ctx.addParameter("name", name);
		ctx.addParameter("column", column);
		ctx.addParameter("itemClass", itemClass);
		ctx.addParameter("columnWidth", columnWidth);
		ctx.addParameter("splitLineStyle", splitLineStyle);

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
