package com.systop.core.wf.jbpm3x.demo;

import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

import com.systop.core.wf.jbpm3x.JbpmCallback;
import com.systop.core.wf.jbpm3x.JbpmTemplate;

@ContextConfiguration(locations = { "classpath*:test-applicationContext.xml" })
public class SampleWorkflowTest extends AbstractTransactionalJUnit38SpringContextTests {
  @Autowired
  private ArticleAuditWorkflowManager wfManager;
  
  @Autowired
  @Qualifier("jbpmSampleTemplate")
  private JbpmTemplate jbpmTemplate;
  
  public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
    this.jbpmTemplate = jbpmTemplate;
  }

  public void setWfManager(ArticleAuditWorkflowManager wfManager) {
    this.wfManager = wfManager;
  }
  
  public void testJT() {
    jbpmTemplate.execute(new JbpmCallback() {

      @Override
      public Object doInJbpm(JbpmContext context) throws JbpmException {
        //ProcessInstance ins = context.newProcessInstance("SampleWorkflow");
        return null;
      }
      
    });
  }

  public void testPath() {
    wfManager.startWorkflow(100);
    wfManager.audit(100, true, "ROLE_CMS_AUDITOR");
  }
  
  @SuppressWarnings("unchecked")
  public void testUnpath() {
    wfManager.startWorkflow(300);
    wfManager.audit(300, false, "ROLE_CMS_AUDITOR");
    List l = wfManager.getUnauditedArticles("ROLE_CMS_AUDITOR");
    System.out.println(l.size());
  }
}
