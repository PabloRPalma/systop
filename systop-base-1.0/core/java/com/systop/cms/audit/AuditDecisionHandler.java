package com.systop.cms.audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
/**
 * 根据流程变量，判断转向哪个transition
 * @author SAM
 *
 */
public class AuditDecisionHandler implements DecisionHandler {
  /**
   * log
   */
  private static Log log = LogFactory.getLog(AuditDecisionHandler.class);
  /**
   * @see DecisionHandler#decide(ExecutionContext)
   */
  public String decide(ExecutionContext ctx) throws Exception {
    Boolean passed = (Boolean) ctx.getContextInstance().getVariable("passed");
    log.debug("Passed variable is " + passed);
    if (passed == null) {
      passed = Boolean.TRUE;
    }
    return (passed.booleanValue()) ? "审核通过" : "审核未通过";
  }
  
}
