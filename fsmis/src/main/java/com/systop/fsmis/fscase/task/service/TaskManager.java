package com.systop.fsmis.fscase.task.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
	private SmsSendManager smsSendManager;

	@Transactional
	public void save(Task task, List<Integer> deptIds, List<TaskAtt> taskAtts) {
		logger.info("TaskManager.save()-->");
		// 更新食品安全案件状态,正在处理
		FsCase fsCase = getDao().get(FsCase.class, task.getFsCase().getId());
		fsCase.setStatus(FsCaseConstants.FSCASE_STATUS_RESOLVEING);
		getDao().save(fsCase);

		// 设置任务信息,正在处理
		task.setStatus(FsCaseConstants.TASK_STATUS_RESOLVEING);
		save(task);

		// 如果部门id集合不为空
		if (CollectionUtils.isNotEmpty(deptIds)) {
			for (Integer id : deptIds) {
				TaskDetail taskDetail = new TaskDetail();
				Dept dept = getDao().get(Dept.class, id);
				taskDetail.setDept(dept);
				// 任务明细,未接收
				taskDetail.setStatus(FsCaseConstants.TASK_DETAIL_UN_RECEIVE);
				// 设定任务明细关联的任务
				taskDetail.setTask(task);

				getDao().save(taskDetail);
				save(task);
				// 向该部门发送短信
				sendTaskMessage(dept);
			}
		}

		logger.info("TaskManager.save()--11>");
	}

	private void sendTaskMessage(Dept dept) {
		Set<User> users = dept.getUsers();
		StringBuffer buf = new StringBuffer();
		buf.append(dept.getName()).append(",你部门现有一条待处理任务,请及时登录系统处理.");
		for (User u : users) {
			// User实体中没有isMesReceive属性,暂时无法判断是否是短信接收人
			/*
			 * if(){ getSmsSendManager().addMessage(mobileNum, content) }
			 */
			getSmsSendManager().addMessage(u.getMobile(), buf.toString());
		}
	}

	public SmsSendManager getSmsSendManager() {
		return smsSendManager;
	}

	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
	}

}
