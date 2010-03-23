package com.systop.cms.audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.systop.cms.model.Content;
import com.systop.cms.service.impl.ContentManagerImpl;
/**
 * 根据Jbpm Context变量，修改文章的审核状态
 * @author SAM
 *
 */
public class UpdateArticleStatusHandler implements ActionHandler {
  /**
   * Log for the class.
   */
  private static Log log = LogFactory.getLog(UpdateArticleStatusHandler.class);
  /**
   * <code>ContentManagerImpl</code>
   */
  private ContentManagerImpl contentManager;
  /**
   * 修改文章审核状态
   */
  public void execute(ExecutionContext ctx) throws Exception {
    ContextInstance ctxInstance = ctx.getContextInstance();
    Integer id = (Integer) ctxInstance.getVariable("articleId");
    Boolean passed = (Boolean) ctxInstance.getVariable("passed");
    if (passed == null) {
      log.error("没有找到'passed'变量！");
      return;
    }
    if (id == null) {
      log.error("没有找到articleId变量！");
      return;
    }
    Content content = contentManager.get(id);
    if (content != null) {
      content.setAudited((passed.booleanValue()) ? "1" : "0");
      contentManager.save(content);
      log.debug("更新文章'" + content.getTitle() + "'的审核状态为" + passed);
    }
  }
  /**
   * @return the contentManager
   */
  public ContentManagerImpl getContentManager() {
    return contentManager;
  }
  /**
   * @param contentManager the contentManager to set
   */
  public void setContentManager(ContentManagerImpl contentManager) {
    this.contentManager = contentManager;
  }

}
