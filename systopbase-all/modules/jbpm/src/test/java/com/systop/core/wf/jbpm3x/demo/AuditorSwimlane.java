package com.systop.core.wf.jbpm3x.demo;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
/**
 * 分配给Auditor角色
 * @author SAM
 *
 */
public class AuditorSwimlane implements AssignmentHandler {
  /**
   * 当前的状态有哪个角色来具体负责处理
   */
  public void assign(Assignable assignable, ExecutionContext ctx) 
    throws Exception {
    assignable.setActorId("ROLE_CMS_AUDITOR");
  }

}
