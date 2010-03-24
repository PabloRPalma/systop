package com.systop.cms.software.catalog.webapp;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.cms.model.SoftCatas;
import com.systop.cms.software.catalog.service.SoftCatasManager;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SoftCatasAction extends DefaultCrudAction<SoftCatas, SoftCatasManager> {
  

  /**
   * 显示所有软件类别
   */
  public String index() {
    List<SoftCatas> list = getManager().getSoftCatz();
    getRequest().setAttribute("items", list);
    return INDEX;
  }

  /**
   * 保存软件类别信息
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.name", message = "软件类型名称是必须的.") })
  public String save() {
    try {  
      getManager().save(getModel());
    } catch (Exception e) {
      this.addActionError(e.getMessage());
      return INPUT;
    }
    return SUCCESS;
  }
  /**
   * 删除软件类别(若某类别下含有软件，同时删除)
   * @return
   */
  public String del(){
    getManager().delete(getModel());
    return SUCCESS;
  }

  
}
