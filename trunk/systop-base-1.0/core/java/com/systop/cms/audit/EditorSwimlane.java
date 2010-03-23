package com.systop.cms.audit;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
/**
 * 
 * @author SAM
 *
 */
public class EditorSwimlane implements AssignmentHandler {
  /**
   * 分配给ROLE_CMS_EDITOR
   */
  public void assign(Assignable assignable, ExecutionContext ctx)
    throws Exception {
    assignable.setActorId("ROLE_CMS_EDITOR");
  }

}
