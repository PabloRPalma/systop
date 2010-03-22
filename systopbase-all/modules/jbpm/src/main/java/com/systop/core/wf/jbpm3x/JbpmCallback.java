/**
 * Created on Feb 20, 2006
 *
 * $Id: JbpmCallback.java,v 1.1 2006/03/02 14:56:01 costin Exp $
 * $Revision: 1.1 $
 */
package com.systop.core.wf.jbpm3x;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;

/**
 * Jbpm 3.1 callback which allows code to be executed directly on the jbpmContext.
 * 
 * @author Costin Leau
 *
 */
public interface JbpmCallback {

	/**
	 * JbpmContext callback.
	 * 
	 * @param context
	 * @return
	 * @throws JbpmException
	 */
	public Object doInJbpm(JbpmContext context) throws JbpmException;
}
