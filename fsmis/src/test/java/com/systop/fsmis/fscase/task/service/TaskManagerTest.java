package com.systop.fsmis.fscase.task.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.model.Task;


@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class TaskManagerTest extends BaseTransactionalTestCase {

	@Autowired
	private TaskManager taskManager;
	
	public void testSaveTaskListOfIntegerListOfTaskAtt() {
		Task task= new Task();
		task.setTitle("测试任务标题");
		task.setDescn("测试任务描述");
		task.setStatus("1");
		task.setDispatchTime(new Date());
		task.setPresetTime(new Date());
		
		taskManager.save(task, null, null);
	}


}
