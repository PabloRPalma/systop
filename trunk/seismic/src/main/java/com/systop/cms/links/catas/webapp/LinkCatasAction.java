package com.systop.cms.links.catas.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.links.catas.service.LinkCatasManager;
import com.systop.cms.model.LinkCatas;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * 友情链接类别Action
 * 
 * @author Bin
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LinkCatasAction extends DefaultCrudAction<LinkCatas, LinkCatasManager> {

  /**
   * 保存类别
   */
  @Override
  public String save() {
    if (getManager().getDao().exists(getModel(), "name")) {
      addActionError("类别名称：" + getModel().getName() + " 已存在");
      return INPUT;
    }
    return super.save();
  }

  /**
   * dwr删除方法
   * 
   * @param id 类别id
   * @return SUCCESS 删除成功 ERROR 类别下存在链接
   */
  public String dwrRemove(Integer id) {
    LinkCatas linCatas = getManager().get(id);
    if (linCatas.getLinks().isEmpty()) {
      getManager().remove(linCatas);
      return SUCCESS;
    } else {
      return ERROR;
    }
  }
}
