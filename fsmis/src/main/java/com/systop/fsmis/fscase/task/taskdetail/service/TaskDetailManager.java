package com.systop.fsmis.fscase.task.taskdetail.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.corp.service.CorpManager;
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
	
	@Autowired
    private CorpManager corpManager;
	
	
  /**
   * 完成任务明细退回操作
   * 
   * @param taskDetail 要退回的任务明细实体实例
   */
  @Transactional
  public void doReturnTaskDetail(TaskDetail taskDetail) {
    // 设定当前任务明细的状态为退回状态
    taskDetail.setStatus(TaskConstants.TASK_DETAIL_RETURNED);
    taskDetail.setCompletionTime(new Date());
    save(taskDetail);
    // 如果所有任务明细已经退回,则把任务和事件状态都置为"退回"
    Task task = taskDetail.getTask();
    FsCase fsCase = task.getFsCase();
    if (checkIsAllTaskDetailReturned(taskDetail)) {
      task.setStatus(TaskConstants.TASK_RETURNED);
      getDao().save(task);
      //修改事件状态为：已退回
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
   */
  @Transactional
  public void doCommitTaskDetail(TaskDetail taskDetail, String corpName, Corp corp) {
    // 任务明细状态置为"已处理"
    taskDetail.setStatus(TaskConstants.TASK_DETAIL_PROCESSED);
    // 任务完成时间
    taskDetail.setCompletionTime(new Date());
    Task task = taskDetail.getTask();
    // 作为当前案件的唯一有效任务,当前任务已处理(对应所有任务明细都已处理),则修改案件的状态为"已处理"
    FsCase fsCase = task.getFsCase();
    // 如果案件没有关联企业,而在完成任务中为案件指定了企业(创建新企业),则需要保存企业信息
    try {
    	processCorp(fsCase, corpName, corp);
    }
    catch (Exception e) {
		throw new ApplicationException(e.getMessage());
	}
    save(taskDetail);
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
   * 完成企业的添加并事件关联
   * 
   * @param taskDetail
   * @param fsCase
   * @param corpName
   * @param corp
   */
  private void processCorp(FsCase fsCase, String corpName,
		  Corp corp) {
	//根据页面输入的名字查找企业
	Corp oldCorp = getCorpByName(corpName, fsCase.getCounty().getId());
    if (oldCorp != null) {
    	fsCase.setCorp(oldCorp);
    // 新添加企业
    }else {
    	if(StringUtils.isNotBlank(corpName)){
    		if (corpManager.getDao().exists(corp, "name")) {
				throw new ApplicationException("企业'" + corp.getName() + "'已存在。");
			}
    		if (StringUtils.isNotEmpty(corp.getBusinessLicense())) {
    			if (corpManager.getDao().exists(corp, "businessLicense")) {
    				throw new ApplicationException("添加的营业执照号已存在。");
    			}
    		}
    		if (StringUtils.isNotEmpty(corp.getProduceLicense())) {
    			if (corpManager.getDao().exists(corp, "produceLicense")) {
    				throw new ApplicationException("添加的生产许可证号已存在。");
    			}
    		}
    		if (StringUtils.isNotEmpty(corp.getSanitationLicense())) {
    			if (corpManager.getDao().exists(corp, "sanitationLicense")) {
    				throw new ApplicationException("添加的卫生许可证号已存在。");
    			}
    		}
    		if (StringUtils.isNotEmpty(corp.getCode())) {
    			if (corpManager.getDao().exists(corp, "code")) {
    				throw new ApplicationException("添加的企业编号已存在。");
    			}
    		}
        	 corp.setName(corpName);
             corp.setDept(fsCase.getCounty());
             getDao().save(corp);
             fsCase.setCorp(corp);
    	}else{// 没有选择也没有录入企业,企业空缺
    		 fsCase.setCorp(null);
    	}
    	
    }
  }

	/**
	 * 根据企业名称和区县ID取得企业信息
	 * 
	 * @param corpName
	 *            企业名称
	 * @param countyId
	 *            区县ID
	 */
	@SuppressWarnings("unchecked")
	private Corp getCorpByName(String corpName, Integer countyId) {
		Corp corp = null;
		String hql = "from Corp c where c.name = ? and c.dept.id = ?";
		List<Corp> corpList = getDao().query(hql, corpName, countyId);
		if (CollectionUtils.isNotEmpty(corpList)) {
			corp = (Corp) corpList.get(0);
		}
		return corp;
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
