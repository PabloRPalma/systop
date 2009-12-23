package com.systop.common.modules.security.user.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.common.modules.security.user.model.RegMemo;
import com.systop.common.modules.security.user.service.RegMemoManager;
import com.systop.core.webapp.struts2.action.BaseAction;

/**
 * 注册说明信息管理Action
 * @author DU
 */
@Deprecated
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegMemoAction extends BaseAction implements ModelDriven<RegMemo> {
  
  private RegMemo model = new RegMemo();
  
  /**
   * 管理类
   */
  @Autowired(required = true)
  private RegMemoManager manager;
  
  /**
   * 修改或新建{@link RegMemo}对象，定位到编辑页面
   */
  public String edit() {
    model = manager.get();
    if (model == null) {
      model = new RegMemo();
    }
    return INPUT;
  }
  
  /**
   * 保存注册说明信息
   */
  public String save() {
    Assert.notNull(model);
    try {
      manager.save(model);
    } catch (Exception e) {
      addActionError(e.getMessage());
      return INPUT;
    }
    return SUCCESS;
  }

  @Override
  public RegMemo getModel() {
    return model;
  }
}
