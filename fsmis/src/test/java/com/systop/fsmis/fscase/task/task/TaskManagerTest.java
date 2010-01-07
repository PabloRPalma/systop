package com.systop.fsmis.fscase.task.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.task.service.TaskManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
/**
 * 任务管理测试类
 * @author shaozhiyuan
 *
 */

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class TaskManagerTest extends BaseTransactionalTestCase {

	//注入任务manager
	@Autowired
	private TaskManager taskManager;
	
	
	/**
	 * 测试保存任务方法
	 */
	public void testSaveTaskListOfIntegerListOfTaskAtt() {
		FsCase fsCase = new FsCase();
		fsCase.setCode("11111");
		fsCase.setTitle("test");
		
		Task task= new Task();
		task.setTitle("测试任务标题");
		task.setDescn("测试任务描述");
		task.setStatus("1");
		task.setDispatchTime(new Date());
		task.setPresetTime(new Date());
		taskManager.getDao().save(fsCase);
		task.setFsCase(fsCase);
		
		taskManager.save(task, null, null);
		
		assertEquals("测试任务标题", taskManager.get(task.getId()).getTitle());	
		
	}


}
