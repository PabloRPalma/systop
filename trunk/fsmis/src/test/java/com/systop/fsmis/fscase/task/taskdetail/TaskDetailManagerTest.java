package com.systop.fsmis.fscase.task.taskdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.TaskDetail;


@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class TaskDetailManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private TaskDetailManager taskDetailManager;
	
	public void testDoReturnTaskDetail() {
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setStatus("1");
		taskDetailManager.doReturnTaskDetail(taskDetail);
		
	}

	public void testDoCommitTaskDetail() {
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setStatus("1");
		taskDetailManager.doCommitTaskDetail(taskDetail);
	}

}
