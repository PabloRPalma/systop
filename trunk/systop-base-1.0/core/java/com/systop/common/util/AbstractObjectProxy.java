package com.systop.common.util;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Object代理类，用于没有接口的对象的动态代理。子类必须实现intercept方法，以确定具体的
 * 代理行为。sample:
 * <pre>
 * class DAOLogProxy extends AbstractObjectProxy {
 *   public Object intercept(Object o,Method method,Object[] 
 *   args,MethodProxy proxy) throws Throwable  {
           log.info("调用日志方法"+method.getName());
           Object result=proxy.invoke(proxyObject, args);
           return result;
      }

 * }
 * </pre>
 * usage:
 * <pre>
 * DAOLogProxy daoLogProxy = new DAOLogProxy();
 * DAO dao = (DAO) daoLogProxy.getObject(DAO.class, myDao);
 * </pre>
 * @author Sam
 */
public abstract class AbstractObjectProxy implements MethodInterceptor {
  /**
   * Log of the class
   */
  protected  Log log = LogFactory.getLog(getClass());
  
  /**
   * 被代理的原始对象
   */
  protected Object proxyObject = null;
  
  /**
   * @see {@link Enhancer}
   */
  protected Enhancer enhancer = new Enhancer();
  
  /**
   * 生成指定类的代理对象
   * @param clazz 指定的类
   * @param object 被代理的实际对象
   * @return 代理之后的对象
   */
  public Object getObject(Class clazz, Object object) {
    this.proxyObject = object;
    enhancer.setSuperclass(clazz);
    enhancer.setCallback(this);
    
    return enhancer.create();
  }

  /**
   * @see net.sf.cglib.proxy.MethodInterceptor#intercept(
   * java.lang.Object, java.lang.reflect.Method, 
   * java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
   */
  public abstract Object intercept(Object obj, Method method, Object[] args,
      MethodProxy methodProxy) throws Throwable;

}
