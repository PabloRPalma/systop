package com.systop.fsmis.fscase.task.taskdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.systop.core.test.BaseTransactionalTestCase;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.TaskDetail;
/**
 * 任务详细管理测试类
 * @author shaozhiyuan
 *
 */

@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class TaskDetailManagerTest extends BaseTransactionalTestCase {

	//注入任务详细manager
	@Autowired
	private TaskDetailManager taskDetailManager;
	
	
	/**
	 * 测试任务详细退回方法
	 */
	public void testDoReturnTaskDetail() {
		//新增任务详细记录
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setStatus("1");
		taskDetailManager.doReturnTaskDetail(taskDetail);
		
	}

	
	/**
	 * 测试任务详细提交方法
	 */
	public void testDoCommitTaskDetail() {
		//新增任务详细记录
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setStatus("1");
		Corp corp = new Corp();
		corp.setName("测试企业");
		corp.setAddress("测试地址");
		corp.setCode("5484454");
		corp.setLegalPerson("1154015");
		corp.setOperateDetails("企业介绍 ");
		corp.setProduceLicense("1548654");
		taskDetailManager.doCommitTaskDetail(taskDetail,"测试企业",corp);
	}

}
