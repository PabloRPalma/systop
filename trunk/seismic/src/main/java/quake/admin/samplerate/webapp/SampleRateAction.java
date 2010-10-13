package quake.admin.samplerate.webapp;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import quake.admin.samplerate.model.SampleRate;
import quake.admin.samplerate.service.SampleRateManager;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;


@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SampleRateAction extends DefaultCrudAction<SampleRate, SampleRateManager> {
  /** 覆盖原有model */
  private SampleRate model = new SampleRate();

  /**
   * 采样率列表
   */
  @Override
  public String index() {
    items = getManager().get();
    return INDEX;
  }

  /**
   * 保存采样率
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.id", message = "采样率代码是必须的."),
      @RequiredStringValidator(fieldName = "model.name", message = "采样率名称是必须的."),
      @RequiredStringValidator(fieldName = "model.dateFormat", message = "采样率时间格式是必须的.") }, 
      requiredFields = { @RequiredFieldValidator(fieldName = "model.dataAmount", message = "每天采集数据是必须的.")
      })
  public String save() {
    Assert.notNull(getModel());
    try {
      getManager().save(getModel());
      return SUCCESS;
    } catch (ApplicationException e) {
      addActionError(e.getMessage());
      return INPUT;
    }
  }

  public SampleRate getModel() {
    return model;
  }

  public void setModel(SampleRate model) {
    this.model = model;
  }

  /**
   * 更改prepare方法，id是输入方式不是自动生成。
   */
  @Override
  public void prepare() {
    if (model == null || extractId(model) == null) {
      model = new SampleRate();
      logger.debug("model or it's id is null, create new.");
    } else {
      model = (SampleRate) getManager().getDao().get(SampleRate.class, extractId(model));
      //id是输入方式不是自动生成,没有对象则新建
      if (model == null) {
        model = new SampleRate();
      }
      logger.debug("Got model by id '{}'", extractId(model));
    }
  }
  
  /**
   * 启用/不启用一个采样率
   */
  public String changeEnabled() {
    SampleRate sampleRate = getManager().get(model.getId());
    sampleRate.setEnabled(model.getEnabled());
    getManager().save(sampleRate);
    return null;
  }
  
  /**
   * 将一个采用设置为可以订阅/不可订阅
   */
  public String changeForMail() {
    SampleRate sampleRate = getManager().get(model.getId());
    sampleRate.setForMail(model.getForMail());
    getManager().save(sampleRate);
    return null;
  }
  
  /**
   * 根据采样率，返回合适的时间格式，用于Applect曲线通过URL访问.
   */
  public String dateFormat() {
    if(model == null || model.getId() == null) {
      return "yyyy-MM-dd HH:mm:ss";
    }
    SampleRate sampleRate = getManager().get(model.getId());
    render(getResponse(), sampleRate.getDateFormat(), "plain/text");
    return null;
  }

  /**
   * 更改ID转换String
   */
  @Override
  protected Serializable convertId(Serializable id) {
    return (id != null) ? id.toString() : null;
  }
}
