package com.systop.core.wf.jbpm3x.demo;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * 根据Jbpm Context变量，修改文章的审核状态
 * @author SAM
 *
 */
@Service
public class UpdateArticleStatusHandler implements ActionHandler {
  /**
   * Logger for the class.
   */
  private static Logger logger = LoggerFactory.getLogger(UpdateArticleStatusHandler.class);
  /**
   * 
   */
  public void execute(ExecutionContext ctx) throws Exception {
    ContextInstance ctxInstance = ctx.getContextInstance();
    Integer id = (Integer) ctxInstance.getVariable("articleId");
    Boolean passed = (Boolean) ctxInstance.getVariable("passed");
    if (passed == null) {
      logger.error("没有找到'passed'变量！");
      return;
    }
    if (id == null) {
      logger.error("没有找到articleId变量！");
      return;
    }
    logger.info("执行审核节点");
  }
}
