package datashare.descr.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.descr.model.Descr;
import datashare.descr.service.DescrManager;

/**
 * 描述信息管理Action
 * @author DU
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DescrAction extends DefaultCrudAction<Descr, DescrManager> {

  /**
   * json数据返回结果
   */
  private String josnResult;
  
  /**
   * 描述信息
   */
  private Descr descr;
  
  /**
   * 保存描述信息
   */
  @Override
  public String save() {
    /*logger.info("标题：" + getModel().getTitle() 
        + "内容：" + getModel().getDescn() 
        + "类型：" + getModel().getType()
        + "地址：" + getModel().getAccessUrl());*/
    josnResult = super.save();
    return "jResult";
  }

  /**
   * 查询描述信息
   */
  public String queryDescribe() {
    descr = getManager().getDescribeByUrlAndType(
        getRequest().getRequestURI(), getModel().getType());
    return INDEX;
  }
  
  /**
   * 取得描述信息ajax
   */
  public String getDescrInfo() {
    descr = getManager().getDescribeByUrlAndType(
        getModel().getAccessUrl(), getModel().getType());
    return "descrResult";
  }
  
  /**
   * @return the josnResult
   */
  public String getJosnResult() {
    return josnResult;
  }

  /**
   * @param josnResult the josnResult to set
   */
  public void setJosnResult(String josnResult) {
    this.josnResult = josnResult;
  }

  /**
   * @return the descr
   */
  public Descr getDescr() {
    return descr;
  }

  /**
   * @param descr the descr to set
   */
  public void setDescr(Descr descr) {
    this.descr = descr;
  }

}
