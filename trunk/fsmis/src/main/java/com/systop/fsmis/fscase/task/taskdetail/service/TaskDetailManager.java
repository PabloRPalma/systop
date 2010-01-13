package com.systop.fsmis.fscase.task.taskdetail.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.model.Corp;
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
   * @param taskDetail 要退回的任务明细实体实例
   */
  @Transactional
  public void doReturnTaskDetail(TaskDetail taskDetail) {
    // 设定当前任务明细的状态为退回状态
    taskDetail.setStatus(TaskConstants.TASK_DETAIL_RETURNED);
    save(taskDetail);
    // 如果所有任务明细已经退回,则把任务和案件状态都置为"退回",
    if (checkIsAllTaskDetailReturned(taskDetail)) {
      Task task = taskDetail.getTask();
      task.setStatus(TaskConstants.TASK_RETURNED);
      getDao().save(task);
      // 系统限定:如果一个案件的一个任务未处理或者未退回(全部任务明细未全部处理或者退回),则不会再次派遣任务,
      // 所以不会出现一个案件的多个任务并行状态,也就不会引起案件状态的冲突.
      // 作为当前案件的有效任务,当前任务已退回(对应所有任务明细都已退回),则修改案件的状态为"退回"
      FsCase fsCase = task.getFsCase();
      if (fsCase != null && fsCase.getId() != null) {
        fsCase.setStatus(CaseConstants.CASE_RETURNED);
        getDao().save(fsCase);
      }
    }
  }

  /**
   * 完成提交任务明细(处理完毕)方法
   * 
   * @param taskDetail 要提交的任务明细
   */
  @Transactional
  public void doCommitTaskDetail(TaskDetail taskDetail) {
    // 任务明细状态置为"已处理"
    taskDetail.setStatus(TaskConstants.TASK_DETAIL_PROCESSED);
    // 任务完成时间
    taskDetail.setCompletionTime(new Date());
    save(taskDetail);
    Task task = taskDetail.getTask();
    // 作为当前案件的唯一有效任务,当前任务已处理(对应所有任务明细都已处理),则修改案件的状态为"已处理"
    FsCase fsCase = task.getFsCase();
    // 如果案件没有关联企业,而在完成任务中为案件指定了企业(创建新企业),则需要保存企业信息
    logger.info("----");
    logger.info(taskDetail.getTask().getFsCase().getCorp().getId()
        + "-------------->");
    if (taskDetail.getTask().getFsCase().getCorp() != null
        && StringUtils.isNotBlank(taskDetail.getTask().getFsCase().getCorp()
            .getName())
        && taskDetail.getTask().getFsCase().getCorp().getId() == null) {
      Corp corp = new Corp();
      BeanUtils
          .copyProperties(taskDetail.getTask().getFsCase().getCorp(), corp);
      getDao().save(corp);
      fsCase.setCorp(corp);
    }
    if (taskDetail.getTask().getFsCase().getCorp() != null
        && StringUtils.isBlank(taskDetail.getTask().getFsCase().getCorp()
            .getName())
        && taskDetail.getTask().getFsCase().getCorp().getId() == null) {
      fsCase.setCorp(null);
    }
    // 如果所有任务明细已经处理,则把任务状态置为"已处理",
    if (checkIsAllTaskDetailResolved(taskDetail)) {

      task.setStatus(TaskConstants.TASK_PROCESSED);
      getDao().save(task);
      if (fsCase != null && fsCase.getId() != null) {
        fsCase.setStatus(CaseConstants.CASE_PROCESSED);
        getDao().save(fsCase);
      }
    }

  }

  /**
   * 检查是否所有任务明细已经处理
   * 
   * @return 是否所有任务明细已经处理
   */
  private boolean checkIsAllTaskDetailResolved(TaskDetail taskDetail) {
    if (taskDetail.getTask() == null || taskDetail.getTask().getId() == null) {
      return false;
    }
    // 遍历当前任务明细实体实例关联的任务实体的任务明细
    for (TaskDetail detail : taskDetail.getTask().getTaskDetails()) {
      // 只要有一个任务明细的状态不为"已处理",则返回false(未全部完成)
      if (!TaskConstants.TASK_DETAIL_PROCESSED .equals(detail.getStatus())) {
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
    if (taskDetail.getTask() == null || taskDetail.getTask().getId() == null) {
      return false;
    }
    // 遍历当前任务明细实体实例关联的任务实体的任务明细
    for (TaskDetail detail : taskDetail.getTask().getTaskDetails()) {
      // 只要遍历到的任一个任务明细的状态不为退回,则直接返回false(未全部退回)
      if (!TaskConstants.TASK_DETAIL_RETURNED.equals(detail.getStatus())) {
        return false;
      }
    }

    return true;
  }
}
