package com.systop.fsmis.fscase.task.taskdetail.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
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
    // 如果所有任务明细已经退回,则把任务和事件状态都置为"退回"
    Task task = taskDetail.getTask();
    FsCase fsCase = task.getFsCase();
    if (checkIsAllTaskDetailReturned(taskDetail)) {
      task.setStatus(TaskConstants.TASK_RETURNED);
      getDao().save(task);
      //修改事件状态为：以退回
      if (fsCase != null && fsCase.getId() != null) {
        fsCase.setStatus(CaseConstants.CASE_RETURNED);
        getDao().save(fsCase);
      }
     // 如果所有任务明细没有全退回,则再去检查任务是否全部处理完毕
     }else{
    	// 如果所有任务明细已经处理,则把任务状态置为"已处理",完成时间为当前时间
    	if (checkIsAllTaskDetailResolved(taskDetail)) {
    	      task.setStatus(TaskConstants.TASK_PROCESSED);
    	      task.setClosedTime(new Date());
    	      getDao().save(task);
    	      if (fsCase != null && fsCase.getId() != null) {
    	        fsCase.setStatus(CaseConstants.CASE_PROCESSED);
    	        getDao().save(fsCase);
    	      }
    	 }
    }
  }

  /**
   * 完成提交任务明细(处理完毕)方法
   * 
   * @param taskDetail 要提交的任务明细
   * @param isNewCorp 是否新添加企业
   */
  @Transactional
  public void doCommitTaskDetail(TaskDetail taskDetail, Integer corpId) {
    // 任务明细状态置为"已处理"
    taskDetail.setStatus(TaskConstants.TASK_DETAIL_PROCESSED);
    // 任务完成时间
    taskDetail.setCompletionTime(new Date());
    save(taskDetail);
    Task task = taskDetail.getTask();
    // 作为当前案件的唯一有效任务,当前任务已处理(对应所有任务明细都已处理),则修改案件的状态为"已处理"
    FsCase fsCase = task.getFsCase();
    // 如果案件没有关联企业,而在完成任务中为案件指定了企业(创建新企业),则需要保存企业信息,
    //此功能究竟放到哪里经沟通尚未确定,待确定后调整下面这行代码
    processCorp(taskDetail, fsCase, corpId);
    // 如果所有任务明细已全部处理（包括退回）,则把任务状态置为"已处理",完成时间为当前时间
    if (checkIsAllTaskDetailResolved(taskDetail)) {
      task.setStatus(TaskConstants.TASK_PROCESSED);
      task.setClosedTime(new Date());
      getDao().save(task);
      if (fsCase != null && fsCase.getId() != null) {
        fsCase.setStatus(CaseConstants.CASE_PROCESSED);
        getDao().save(fsCase);
      }
    }

  }

  /**
   * <pre>
   * 
   * </pre>
   * 
   * @param taskDetail
   */
  private void processCorp(TaskDetail taskDetail, FsCase fsCase,
      Integer corpId) {
    // 新添加加企业
    if (corpId == null) {
      Corp corp = taskDetail.getTask().getFsCase().getCorp();
      corp.setDept(fsCase.getCounty());
      getDao().save(corp);
      fsCase.setCorp(corp);
    } else {
      // 选择得到企业
      if (taskDetail.getTask().getFsCase().getCorp() != null
          && taskDetail.getTask().getFsCase().getCorp().getId() != null) {
        FsCase fsCaseDb = (FsCase) getDao().findObject(
            "from FsCase fc where fc.id = ?", fsCase.getId());
        // 客户端传递的企业不eq数据库中原有企业
        if (!taskDetail.getTask().getFsCase().getCorp().equals(
            fsCaseDb.getCorp())) {
          //Corp corp = (Corp) getDao().findObject("from Corp c where c.id = ?", taskDetail.getTask().getFsCase().getCorp().getId());
          getDao().getHibernateTemplate().clear();
          //getDao().evict(fsCaseDb.getCorp());
          //fsCase.setCorp(corp);
          //getDao().update(fsCase);// 让事件关联选择的企业
          getDao().save(fsCase);
        }
      } else {// 没有选择也没有录入企业,企业空缺
        fsCase.setCorp(null);
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
      // 只要有任务明细的状态不为"已处理或已退回",则返回false(未全部完成)
      if (!(TaskConstants.TASK_DETAIL_PROCESSED.equals(detail.getStatus()) || 
    		  TaskConstants.TASK_DETAIL_RETURNED.equals(detail.getStatus()))) {
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

  public List<TaskDetail> getNewTasks(Dept dept, String isMultiple) {
    // 查询指定部门的未查看任务明细
    String hql = "from TaskDetail td where td.dept.id = ? and td.status = ? and td.task.fsCase.isMultiple = ?";

    return query(hql, new Object[] { dept.getId(),
        TaskConstants.TASK_DETAIL_UN_RECEIVE, isMultiple });
  }
}
