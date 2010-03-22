/**
 * Created on Feb 21, 2006
 *
 * $Id: JbpmAccessor.java,v 1.3 2007/02/28 13:05:02 costin Exp $
 * $Revision: 1.3 $
 */
package com.systop.core.wf.jbpm3x;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * @author Costin Leau
 *
 */
public class JbpmAccessor implements InitializingBean{

	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * LocalJbpmConfigurationFactoryBean used with this JbpmTemplate for creating the context.
	 */
	protected JbpmConfiguration jbpmConfiguration;

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (jbpmConfiguration == null)
			throw new IllegalArgumentException("jbpmConfiguration must be set");
	}
	
	/**
	 * Converts Jbpm RuntimeExceptions into Spring specific ones (if possible).
	 * @param ex
	 * @return
	 */
	public RuntimeException convertJbpmException(JbpmException ex) {
		// decode nested exceptions
		if (ex.getCause() instanceof HibernateException) {
			DataAccessException rootCause = SessionFactoryUtils.convertHibernateAccessException((HibernateException) ex.getCause());
			return rootCause;
		}

		// cannot convert the exception in any meaningful way
		return ex;
	}

	/**
	 * @return Returns the jbpmConfiguration.
	 */
	public JbpmConfiguration getJbpmConfiguration() {
		return jbpmConfiguration;
	}

	/**
	 * @param jbpmConfiguration The jbpmConfiguration to set.
	 */
	public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {
		this.jbpmConfiguration = jbpmConfiguration;
	}

}
