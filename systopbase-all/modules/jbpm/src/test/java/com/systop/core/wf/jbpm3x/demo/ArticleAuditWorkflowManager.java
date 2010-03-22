package com.systop.core.wf.jbpm3x.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.systop.core.wf.jbpm3x.JbpmCallback;
import com.systop.core.wf.jbpm3x.JbpmTemplate;

/**
 * 文章审核工作流
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class ArticleAuditWorkflowManager {
  /**
   * Logger
   */
  private static Logger logger = LoggerFactory.getLogger(ArticleAuditWorkflowManager.class);
  /**
   * JbpmTemplate
   */
  @Autowired
  @Qualifier("jbpmSampleTemplate")
  private JbpmTemplate jbpmTemplate;

  public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
    this.jbpmTemplate = jbpmTemplate;
  }
  
  public JbpmTemplate getJbpmTemplate() {
    return jbpmTemplate;
  }

  /**
   * 启动工作流，定义变量，并将当前节点定位到“文章审核”
   * 
   * @param 新建或更新的文章ID
   */
  public void startWorkflow(final Integer articleId) {
    jbpmTemplate.execute(new JbpmCallback() {

      public Object doInJbpm(JbpmContext context) {
        ProcessInstance processIns = context.newProcessInstance("SampleWorkFlow");
        ContextInstance ctxIns = processIns.getContextInstance();
        ctxIns.createVariable("articleId", articleId);
        Token token = processIns.getRootToken();
        // 启动工作流
        token.signal();
        logger.debug("Current Node is " + token.getNode().getName());
        token.signal();
        logger.debug("Current Node is " + token.getNode().getName());
        context.save(processIns);
        return null;
      }

    });
  }

  /**
   * 列出所有未审核的文章Id
   * 
   * @param actorId 角色名称
   * @return List of <code>Content</code> id.
   */

  public List getUnauditedArticles(final String actorId) {
    return (List) jbpmTemplate.execute(new JbpmCallback() {

      public Object doInJbpm(JbpmContext context) {
        List tasks = context.getTaskList(actorId);
        List ids = new ArrayList(tasks.size());
        for (Iterator itr = tasks.iterator(); itr.hasNext();) {
          TaskInstance taskIns = (TaskInstance) itr.next();
          if (!taskIns.hasEnded()) {
            Integer id = (Integer) taskIns.getContextInstance().getVariable("articleId");
            ids.add(id);
          }
        }
        return ids;
      }

    });
  }

  /**
   * 审核文章
   * 
   * @param articleId 文章ID
   * @param passed 是否通过审核
   * @param actorId 角色Id
   */
  public void audit(final Integer articleId, final boolean passed, final String actorId) {
    jbpmTemplate.execute(new JbpmCallback() {
      public Object doInJbpm(JbpmContext context) {
        List tasks = context.getTaskList(actorId);
        for (Iterator itr = tasks.iterator(); itr.hasNext();) {
          TaskInstance taskIns = (TaskInstance) itr.next();
          Integer id = (Integer) taskIns.getContextInstance().getVariable("articleId");
          if (id == null) {
            return null;
          }
          if (id.equals(articleId)) {
            taskIns.getContextInstance().createVariable("passed", new Boolean(passed));
            taskIns.end();
            context.save(taskIns.getContextInstance().getProcessInstance());
          }
        }
        return null;
      }

    });
  }

}
