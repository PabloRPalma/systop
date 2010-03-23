package com.systop.common.security.impl;


import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionMap;
import org.acegisecurity.intercept.web.FilterInvocationDefinitionSourceEditor;
import org.acegisecurity.intercept.web.PathBasedFilterInvocationDefinitionMap;
import org.acegisecurity.vote.AffirmativeBased;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;

import com.systop.common.security.AccessDecisionManager;
import com.systop.common.security.Authentication;
import com.systop.common.security.exception.AccessDeniedException;
/**
 * 利用Acegi实现AccessDecisionManager接口.
 * @author Sam
 *
 */
public final class AccessDecisionManagerAcegiImpl
  implements AccessDecisionManager {
  /**
   * Log for the class.
   */
  private static Log log = LogFactory
      .getLog(AccessDecisionManagerAcegiImpl.class);
    
  /**
   * the Spring BeanFactory object.
   */
  private BeanFactory spring = null;
  
  /**
   * @see AccessDecisionManager#decide(Authentication, Object)
   */
  
  public void decide(final Authentication auth, Object secured)
      throws AccessDeniedException {
    if (secured != null && !secured.toString().startsWith("/")) {
      secured = "/" + secured.toString();
    }
    // 安全配置
    String securityCfg = (String) spring
        .getBean("httpRequestSecurityDefinition");
    // 将安全配置字符串转换为FilterInvocationDefinitionMap
    FilterInvocationDefinitionSourceEditor def =
      new FilterInvocationDefinitionSourceEditor();
    def.setAsText(securityCfg);
    FilterInvocationDefinitionMap defMap = 
      (FilterInvocationDefinitionMap) def.getValue();
    ConfigAttributeDefinition configAttrDef = null;
    //找到与指定资源相关的安全配置 
    //defMap是PathBasedFilterInvocationDefinitionMap
    //或RegExpBasedFilterInvocationDefinitionMap
    // 的实例，但是却无法确定到底是哪个，所以使用reflect调用
    try {
      //Method m = defMap.getClass().getMethod("lookupAttributes",
      //    new Class[] { String.class });
      
      PathBasedFilterInvocationDefinitionMap p = 
        (PathBasedFilterInvocationDefinitionMap) defMap;
      configAttrDef = p.lookupAttributes((String) secured);
      //Object obj = ReflectUtil.invoke(defMap, m, new Object[] { secured });
      //if (obj instanceof ConfigAttributeDefinition) {
      //  configAttrDef = (ConfigAttributeDefinition) obj;
      //}
    } catch (SecurityException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    //利用acegi的配置进行权限验证
    if (configAttrDef != null) {
      if (SecurityContextHolder.getContext() != null) {
        AffirmativeBased decision = 
          (AffirmativeBased) spring.getBean("httpRequestAccessDecisionManager");
        try {
          decision.decide(
              SecurityContextHolder.getContext().getAuthentication(), 
              secured, configAttrDef);
        } catch (org.acegisecurity.AccessDeniedException e) {
          throw new AccessDeniedException(e.getMessage());
        } catch (Throwable t) {
          throw new AccessDeniedException("Some exception occurs.", t);
        }
      }
    }
  }

  /**
   * @param spring The spring to set.
   */
  void setSpring(final BeanFactory spring) {
    this.spring = spring;
  }

  /**
   * @see AccessDecisionManager#setSecurityContext(Object)
   */
  public void setSecurityContext(final Object context) {
    if (context instanceof BeanFactory) {
      setSpring((BeanFactory) context);
    }
  }

}
