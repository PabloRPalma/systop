package com.systop.fsmis.fscase.task.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.task.service.TaskManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskAtt;
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
	public void testSaveTaskListOfIntegerListOfTaskAtt() throws IOException{
		//新增事件记录
		FsCase fsCase = new FsCase();
		fsCase.setCode("11111");
		fsCase.setTitle("test");
		//新增部门记录
		//List<Integer> deptIds = new ArrayList<Integer>();
		//deptIds.add(50);
		String[] deptIds = new String[]{"50"};
		//新增任务记录
		Task task= new Task();
		task.setTitle("测试任务标题");
		task.setDescn("测试任务描述");
		task.setStatus("1");
		task.setDispatchTime(new Date());
		task.setPresetTime(new Date());
		taskManager.getDao().save(fsCase);
		task.setFsCase(fsCase);
		//新增任务附件
		TaskAtt taskatt = new TaskAtt();
		taskatt.setTitle("任务附件");
		taskatt.setPath("c:/test.doc");
		//创建一磁盘文件，作为测试附件
		File file = new File("c:/test.doc");
		file.createNewFile();
		List<TaskAtt> taskAtts = new ArrayList<TaskAtt>();
		taskAtts.add(taskatt);
		
		taskManager.save(task,deptIds, taskAtts);
		
		assertEquals("测试任务标题", taskManager.get(task.getId()).getTitle());	
		
	}


}
