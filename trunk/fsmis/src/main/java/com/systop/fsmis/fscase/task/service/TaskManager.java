package com.systop.fsmis.fscase.task.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.fscase.FsCaseConstants;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskAtt;
import com.systop.fsmis.model.TaskDetail;
import com.systop.fsmis.sms.SmsSendManager;

@Service
public class TaskManager extends BaseGenericsManager<Task> {
	@Autowired
	private SmsSendManager smsSendManager;

	/**
	 * 保存派遣任务方法
	 * 
	 * @param task
	 *          任务实体实例
	 * @param deptIds
	 *          部门id集合
	 * @param taskAtts
	 *          任务附件实体集合
	 */
	@Transactional
	public void save(Task task, List<Integer> deptIds, List<TaskAtt> taskAtts) {

		// 得到任务关联事件实体
		FsCase fsCase = getDao().get(FsCase.class, task.getFsCase().getId());
		// 更新食品安全案件状态,正在处理,并保存
		fsCase.setStatus(FsCaseConstants.FSCASE_STATUS_RESOLVEING);
		getDao().save(fsCase);

		// 设置任务信息,正在处理,并保存
		task.setStatus(FsCaseConstants.TASK_STATUS_RESOLVEING);

		// 根据任务选择的部门集合,作任务明细信息操作
		// 如果部门id集合不为空,遍历部门构建任务明细实例.
		if (CollectionUtils.isNotEmpty(deptIds)) {
			for (Integer id : deptIds) {
				TaskDetail taskDetail = new TaskDetail();
				Dept dept = getDao().get(Dept.class, id);
				taskDetail.setDept(dept);
				// 任务明细状态属性,未接收
				taskDetail.setStatus(FsCaseConstants.TASK_DETAIL_UN_RECEIVE);
				// 设定任务明细关联的任务
				taskDetail.setTask(task);
				task.getTaskDetails().add(taskDetail);

				getDao().save(taskDetail);

				// 向该部门发送短信
				sendTaskMessage(dept);
			}
		}
		// 设置任务附件实体和任务实体的关联并保存任务附件实体
		if (CollectionUtils.isNotEmpty(taskAtts)) {
			for (TaskAtt taskAtt : taskAtts) {
				taskAtt.setTask(task);
				task.getTaskAtts().add(taskAtt);
				getDao().save(taskAtt);
			}
		}

		save(task);
	}

	private void sendTaskMessage(Dept dept) {
		Set<User> users = dept.getUsers();
		StringBuffer buf = new StringBuffer();
		buf.append(dept.getName()).append(",你部门现有一条待处理任务,请及时登录系统处理.");
		for (User u : users) {
			// User实体中没有isMesReceive属性,暂时无法判断是否是短信接收人,待加上该属性后,启用本段代码
			/*
			 * if(){ getSmsSendManager().addMessage(mobileNum, content) }
			 */
			smsSendManager.addMessage(u.getMobile(), buf.toString());
		}
	}

}
