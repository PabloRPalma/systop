package com.systop.fsmis.fscase.gather.conf.webapp;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.gather.conf.service.GatherConfigerManager;
import com.systop.fsmis.model.GatherConfiger;

/**
 * 多体事件汇总配置Action
 * 
 * @author ZW
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GatherConfigerAction extends DefaultCrudAction<GatherConfiger, GatherConfigerManager> {

	/**
	 * 查询配置信息列表
	 */
	@Override
	public String index() {
		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
		return INDEX;
	}
	
	/**
	 * 设置查询条件
	 * 
	 * @return
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(GatherConfiger.class);
		return criteria;
	}
	
	/**
	 * 得到配置汇总的级别
	 */
	public Map<String, String> getLevelMap() {
		return CaseConstants.CONFIGER_CATEGORY_MAP;
	}
	
	/**
	 * 保存
	 */
	@Override
	public String save() {
		if(StringUtils.isBlank(getModel().getLevel())) {
			this.addActionError("请选择汇总级别！");
			return INPUT;
		}
		getManager().save(getModel());
		return SUCCESS;
	}
}
