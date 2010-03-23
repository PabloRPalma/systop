package agileweb.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import agileweb.Constants;

/**
 * 提供数据绑定能力,适用于不采用任何MVC框架的情况。子类需要给出实际需要绑定的类。
 * @author SAM
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class WebBean<T> {
  /**
   * Logger,子类可以直接使用。
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());
  /**
   * 实例化泛型类，并将request中的数据绑定。
   * @param request 给出{@link HttpServletRequest}对象
   * @return <code>T</code>的实例。
   */
  
  protected T bind(HttpServletRequest request) {
    Class clazz = GenericsUtil.getGenericClass(getClass());
    T object = (T) ReflectUtil.newInstance(clazz);
    ServletRequestDataBinder binder = new ServletRequestDataBinder(object);
    initBinder(binder);
    binder.bind(request);
    return (T) binder.getTarget();
  }
  /**
   * 初始化Binder，见Spring Reference中关于数据绑定的章节。
   * 子类可以覆盖这个方法，以添加自定义的Editor
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
 
  }
}
