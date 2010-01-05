package com.systop.fsmis.fscase.task.taskdetail.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskDetail;

/**
 * 任务明细Service层类
 * 
 * @author WorkShopers
 * 
 */
@Service
public class TaskDetailManager extends BaseGenericsManager<TaskDetail> {
	/**
	 * 完成任务明细退回操作
	 * 
	 * @param taskDetail
	 *            要退回的任务明细实体实例
	 */
	@Transactional
	public void doReturnTaskDetail(TaskDetail taskDetail) {
		// 设定当前任务明细的状态为退回状态
		taskDetail.setStatus(CaseConstants.TASK_DETAIL_RETURNED);
		save(taskDetail);
		// 如果所有任务明细已经退回,则把任务和案件状态都置为"退回"
		if (checkIsAllTaskDetailReturned(taskDetail)) {
			Task task = taskDetail.getTask();
			task.setStatus(CaseConstants.TASK_STATUS_RETURNED);
			getDao().save(task);

			FsCase fsCase = task.getFsCase();
			fsCase.setStatus(CaseConstants.CASE_STATUS_RETURNED);
			getDao().save(fsCase);
		}

	}

	/**
	 * 完成提交任务明细(处理完毕)方法
	 * 
	 * @param taskDetail
	 *            要提交的任务明细
	 */
	@Transactional
	public void doCommitTaskDetail(TaskDetail taskDetail) {
		// 任务明细状态置为"已处理"
		taskDetail.setStatus(CaseConstants.TASK_DETAIL_RESOLVEED);
		// 任务完成时间
		taskDetail.setCompletionTime(new Date());
		save(taskDetail);
		// 如果所有任务明细已经处理,则把任务状态置为"已处理",
		// 由于一个案件允许多次任务处理,所以在这里不改变案件状态
		if (checkIsAllTaskDetailResolved(taskDetail)) {
			Task task = taskDetail.getTask();
			task.setStatus(CaseConstants.TASK_STATUS_RESOLVEED);
			getDao().save(task);
		}
	}

	/**
	 * 检查是否所有任务明细已经处理
	 * 
	 * @return 是否所有任务明细已经处理
	 */
	private boolean checkIsAllTaskDetailResolved(TaskDetail taskDetail) {
		// 遍历当前任务明细实体实例关联的任务实体的任务明细
		for (TaskDetail detail : taskDetail.getTask().getTaskDetails()) {
			// 只要有一个任务明细的状态不为"已处理",则返回false(未全部完成)
			if (CaseConstants.TASK_DETAIL_RECEIVEED.equals(detail.getStatus())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查当前任务明细实体实例关联的任务实体的任务明细是否已经全部被退回
	 * 
	 * @return 是否所有任务明细已经退回
	 */
	private boolean checkIsAllTaskDetailReturned(TaskDetail taskDetail) {
		// 如果任务明细对应的任务为null或者其id为null,则表明本任务明细没有对应的任务,直接返回false(不修改对应任务状态)
		if (taskDetail.getTask() == null
				|| taskDetail.getTask().getId() == null) {
			return false;
		}
		// 遍历当前任务明细实体实例关联的任务实体的任务明细
		for (TaskDetail detail : taskDetail.getTask().getTaskDetails()) {
			// 只要遍历到的任一个任务明细的状态不为退回,则直接返回false(未全部退回)
			if (!CaseConstants.TASK_DETAIL_RETURNED.equals(detail.getStatus())) {
				return false;
			}
		}

		return true;
	}
}
