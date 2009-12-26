package com.systop.fsmis.expert.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.expert.service.CategoryManager;
import com.systop.fsmis.model.ExpertCategory;

/**
 * 专家类别管理Action
 * @author ShangHua
 *
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryAction extends ExtJsCrudAction<ExpertCategory, CategoryManager>{
	/**
	 * 重写父类的index方法，实现分页检索风险评估信息
	 */
	@Override
	public String index() {
		StringBuffer hql = new StringBuffer();
		hql.append("from ExpertCategory ec where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and ec.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}
	
	/**
	 * 删除专家信息
	 */
	@Override
	public String remove(){
		ExpertCategory expertCategory = getManager().get(getModel().getId());
		if(expertCategory.getExperts().size() > 0){
			addActionError("该专家类别【"+ expertCategory.getName() +"】存在专家信息！");
			return "error";
		}
		return super.remove();
	}
}
