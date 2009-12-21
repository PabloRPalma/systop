package com.systop.fsmis.casetype.webapp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

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

	  

	/**
	 * 删除类别
	 * @return
	 */
	public String remove(){
		getManager().remove(getModel());
		return SUCCESS;
	}

	public Map getLevelOne() {
		return getManager().getLevelOneMap();
	}
	
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "请填写名称。") })
	public String save() {
		//判断上级类别是否是自己，不允许将自身设置为上级类别
		if (getModel().getCaseType().getId() != null
				&& getModel().getId() != null) {
			if (getModel().getId().equals(getModel().getCaseType().getId())) {
				addActionError("不允许将自身设置为上级类别。");
				return INPUT;
			}

		}
		return super.save();
	}
	

}
