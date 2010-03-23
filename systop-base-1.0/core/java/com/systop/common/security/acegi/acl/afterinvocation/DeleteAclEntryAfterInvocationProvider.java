package com.systop.common.security.acegi.acl.afterinvocation;

import java.util.Iterator;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.acegisecurity.acl.basic.BasicAclExtendedDao;
import org.acegisecurity.afterinvocation.AfterInvocationProvider;
import org.acegisecurity.afterinvocation.BasicAclEntryAfterInvocationProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.systop.common.security.acegi.acl.AclUtils;
import com.systop.common.security.acegi.acl.domain.AclDomainBuilder;

/**
 * 当有acl对象被删除的时候，需要删除该对象对应的acl信息。
 * 
 * @author sshwsfc@gmail.com
 * @see AfterInvocationProvider
 */
public class DeleteAclEntryAfterInvocationProvider implements
    AfterInvocationProvider, InitializingBean, MessageSourceAware {
  // ~ Static fields/initializers =============================================
  /**
   * Log for the class
   */
  private static Log logger = LogFactory
      .getLog(BasicAclEntryAfterInvocationProvider.class);

  // ~ Instance fields ========================================================
  /**
   * Message
   */
  protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
  /**
   * 权限
   */
  private String processConfigAttribute = "AFTER_ACL_DELETE";
  /**
   * ACL DAO
   */
  private BasicAclExtendedDao aclDAO;
  /**
   * 域对象的class
   */
  private Class domainClass;
  /**
   * 方法名称
   */
  private String methodDomainObjectClass;

  public Class getDomainClass() {
    return domainClass;
  }

  public void setDomainClass(Class domainClass) {
    this.domainClass = domainClass;
  }

  public String getMethodDomainObjectClass() {
    return methodDomainObjectClass;
  }

  public void setMethodDomainObjectClass(String methodDomainObjectClass) {
    this.methodDomainObjectClass = methodDomainObjectClass;
  }

  public BasicAclExtendedDao getAclDAO() {
    return aclDAO;
  }

  public void setAclDAO(BasicAclExtendedDao aclDAO) {
    this.aclDAO = aclDAO;
  }
  /**
   * @see InitializingBean#afterPropertiesSet()
   */
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(processConfigAttribute,
        "A processConfigAttribute is mandatory");
    Assert.notNull(messages, "A message source must be set");
    Assert.notNull(aclDAO, "AclExtendedDao is mandatory");
  }

  public String getProcessConfigAttribute() {
    return processConfigAttribute;
  }

  public void setProcessConfigAttribute(String processConfigAttribute) {
    this.processConfigAttribute = processConfigAttribute;
  }

  public void setMessageSource(MessageSource messageSource) {
    this.messages = new MessageSourceAccessor(messageSource);
  }
  
  /**
   * @see AfterInvocationProvider
   */
  public Object decide(Authentication authentication, Object object,
      ConfigAttributeDefinition config, Object returnedObject) {
    Iterator iter = config.getConfigAttributes();

    while (iter.hasNext()) {
      ConfigAttribute attr = (ConfigAttribute) iter.next();
      if (this.supports(attr)) {
        // 取得刚刚删除的domain对象，使用IdParameter(即根据domainClass和ID)来构造domain对象
        AclDomainBuilder domainObject = AclUtils.getDomainObjectInstance(
            object, methodDomainObjectClass, domainClass);
        AclObjectIdentity aclId = domainObject.getAclObjectIdentity();

        if (aclId != null) {
          try {
            // 根据AclObjectIdentity删除acl信息
            aclDAO.delete(aclId);
            if (logger.isDebugEnabled()) {
              logger.debug("Delete AclObject[" + aclId + "]");
            }
          } catch (DataAccessException e) {
            if (logger.isDebugEnabled()) {
              logger.debug("Try to Delete null AclObject[" + aclId
                  + "], Ignore It!");
            }
          }
        }
      }
    }

    return returnedObject;
  }
  /**
   * @see AfterInvocationProvider#supports(ConfigAttribute)
   */
  public boolean supports(ConfigAttribute attribute) {
    return (attribute.getAttribute() != null)
        && attribute.getAttribute().equals(getProcessConfigAttribute());
  }

  /**
   * This implementation supports any type of class, because it does not query
   * the presented secure object.
   * 
   * @param clazz the secure object
   * @return always <code>true</code>
   */
  public boolean supports(Class clazz) {
    return true;
  }
}
