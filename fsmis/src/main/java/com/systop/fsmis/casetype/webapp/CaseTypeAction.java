package com.systop.fsmis.casetype.webapp;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.casetype.service.CaseTypeManager;
import com.systop.fsmis.model.CaseType;


/**
 * 事件类别
 * 
 * @author shaozhiyuan
 * 
 */
@SuppressWarnings({"serial","unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CaseTypeAction extends
       DefaultCrudAction<CaseType, CaseTypeManager>{


	private Integer parentId;

	


	/**
	 * 查询类别列表，分页查询
	 */
	public String index() {
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		String sql = "from CaseType ct where ct.caseType.id is null ";
		List args = new ArrayList();
		if (StringUtils.isNotBlank(getModel().getName())) {
			sql = sql + "and ct.name like ? ";
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
			
		}
		
		page = getManager().pageQuery(page, sql,args.toArray());
		restorePageData(page);
		return INDEX;
	}	

	/**
	 * 查询子类别列表，分页查询
	 */
	public String listchildType(){
		Page page = new Page(Page.start(getPageNo(), getPageSize()),
				getPageSize());
		String sql = "from CaseType ct where ct.caseType.id = ? ";
		List args = new ArrayList();
		args.add(getModel().getId());
		
		page = getManager().pageQuery(page, sql,args.toArray());
		restorePageData(page);
		return "listchildType";
	}
	
	/**
	 * 删除类别
	 * @return
	 */
	public String remove(){
		//判断是否有上级类别，有返回上级类别列表
		if (getModel().getCaseType()!=null && 
			getModel().getCaseType().getId() != null){
			parentId=getModel().getCaseType().getId();
			getManager().remove(getModel());
			return "listchildSuccess";
		}
		getManager().remove(getModel());
		return SUCCESS;
	}

	/**
	 * 获得一级类别 Map方法
	 * @return
	 */
	public Map getLevelOne() {
		return getManager().getLevelOneMap();
	}
	
	/**
	 * 获得一级类别  List方法
	 * @return
	 */
	public List getLevelOneList() {
		return getManager().getLevelOneList();
	}
	
	/**
	 * 根据一级ID得到二级类别
	 * @return
	 */
	public List getLevelTwoList(Integer id) {
		return getManager().getLevelTwoList(id);
	}
	

	public String save() {
		//判断是否有重复名称
		if(getManager().getDao().exists(getModel(), "name")){
			addActionError("类别名称已存在！");
			return INPUT;
		}
		
		//判断是否有上级类别，有返回上级类别列表
		if (getModel().getCaseType()!= null && 
			getModel().getCaseType().getId() != null) {
		    parentId=getModel().getCaseType().getId();
            getManager().save(getModel());
            return "listchildSuccess";
		}
		return super.save();
	}
	
	/**
	 * 重写父类方法，用于添加二级类别
	 * @return
	 */
	@Override
	public String edit(){
		if (getModel().getCaseType()!= null && 
			getModel().getCaseType().getId() != null){
			getRequest().setAttribute("parentName",getManager().getParentName(getModel().getCaseType().getId()));
		}
		return super.edit();		
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	

	

}
