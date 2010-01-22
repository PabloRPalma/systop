package com.systop.fsmis.fscase.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskDetail;

/**
 * 事件部门上报管理类
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Service
public class ReportManager extends BaseGenericsManager<FsCase> {

	/**
	 * 保存上报事件信息及相关内容
	 * @param fsCase 上报事件
	 * @param task 任务
	 * @param taskDetail 任务详细信息
	 */
	@Transactional
	public void saveReportInfoOfCase(FsCase fsCase, Task task, 
			TaskDetail taskDetail, Corp corp, String corpName) {
		//修改上报事件时，如果企业不为空
		if (corp != null && corp.getId() != null) {
			Corp tcorp = getDao().get(Corp.class, corp.getId());
			//如果页面输入企业名字不为空
			if (StringUtils.isNotBlank(corpName)) {
				//页面输入企业的名字跟已经添加了的企业名字相同，则不更改该事件对应的企业。
				if (corpName.equalsIgnoreCase(tcorp.getName())) {
					fsCase.setCorp(tcorp);
				}else{//如果不相同
					//根据页面输入的名字查找企业
					Corp oldCorp = getCorpByName(corpName, fsCase.getCounty().getId());
					//如果该企业存在，则上报事件直接关联该企业，
					//否则保存新输入的企业信息，然后与上报事件关联.
					if (oldCorp != null) {
						fsCase.setCorp(oldCorp);
					} else {
						corp.setName(corpName);
						corp.setDept(fsCase.getCounty());
						getDao().save(corp);
						fsCase.setCorp(corp);
					}
				}
			}else{
				fsCase.setCorp(null);
			}
		} else { //新添加上报事件时
			if (corp != null && StringUtils.isNotBlank(corpName)) {
				//根据页面输入的名字查找企业
				Corp oldCorp = getCorpByName(corpName, fsCase.getCounty().getId());
				//如果该企业存在，则上报事件直接关联该企业，
				//否则保存新输入的企业信息，然后与上报事件关联.
				if (oldCorp != null) {
					fsCase.setCorp(oldCorp);
				} else {
					corp.setName(corpName);
					corp.setDept(fsCase.getCounty());
					getDao().save(corp);
					fsCase.setCorp(corp);
				}
			} else {
				fsCase.setCorp(null);
			}
		}		
		//保存上报事件
		save(fsCase);
		if (task != null && task.getId() != null) {
			Task oldTask = getDao().get(Task.class, task.getId());
			BeanUtils.copyProperties(task, oldTask);
			oldTask.setFsCase(fsCase);
			getDao().save(oldTask);
		} else {
			task.setFsCase(fsCase);
			getDao().save(task);
		}
		if (taskDetail != null && taskDetail.getId() != null) {
			TaskDetail oldTaskDetail = getDao().get(TaskDetail.class, taskDetail.getId());
			BeanUtils.copyProperties(taskDetail, oldTaskDetail);
			oldTaskDetail.setTask(task);
			getDao().save(oldTaskDetail);
		} else {
			taskDetail.setTask(task);
			getDao().save(taskDetail);
		}
	}
	
	/**
	 * 删除上报事件及相关任务
	 * @param caseId 事件ID
	 */
	@Transactional
	public void removeCase(Integer caseId) {
		FsCase fsCase = get(caseId);
		Set<Task> tasks = fsCase.getTaskses();
		for (Task task : tasks) {
			Set<TaskDetail> taskDetails = task.getTaskDetails();
			for (TaskDetail taskDetail : taskDetails) {
				getDao().delete(taskDetail);
			}
			getDao().delete(task);
		}
		getDao().delete(fsCase);
	}
	
	/**
	 * 根据上报事件取得对应事件的任务信息
	 * @param caseId 事件ID
	 */
  public Task getTaskOfCase(Integer caseId) {
		Task task = null;
		List<Task> taskList = getDao().query("from Task t where t.fsCase.id = ?", caseId);
		if (CollectionUtils.isNotEmpty(taskList)) {
			task = (Task) taskList.get(0);
		}
		return task;
	}
  
  /**
   * 根据任务ID取得该任务的详细信息
   * @param taskId 任务ID
   * @param deptId 部门ID
   */
  public TaskDetail getTaskDetailOfTask(Integer taskId, Integer deptId) {
  	TaskDetail taskDetail = null;
  	String hql = "from TaskDetail td where td.task.id = ? and td.dept.id = ?";
  	List<TaskDetail> detailList = getDao().query(hql, taskId, deptId);
  	if (CollectionUtils.isNotEmpty(detailList)) {
  		taskDetail = (TaskDetail) detailList.get(0);
		}
		return taskDetail;
  }
  
  /**
   * 根据企业名称和区县ID取得企业信息
   * @param corpName 企业名称
   * @param countyId 区县ID
   */
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
   * 根据企业名称和区县ID取得企业信息
   * @param corpName 企业名称
   * @param countyId 区县ID
   */
  public Map getCorpMapByName(String corpName, Integer countyId) {
  	Map map = new HashMap();
  	Corp corp = getCorpByName(corpName, countyId);
  	if (corp != null) {
  		map.put("id", corp.getId());
      map.put("name", corp.getName());
      map.put("address", corp.getAddress());
      map.put("legalPerson", corp.getLegalPerson());
      map.put("produceLicense", corp.getProduceLicense());
      map.put("businessLicense", corp.getBusinessLicense());
      map.put("sanitationLicense", corp.getSanitationLicense());
      map.put("operateDetails", corp.getOperateDetails());
  	}
  	return map;
  }
  
  /**
   * 根据区县ID取得该区县下所有的企业
   * @param countyId 区县ID
   */
  public String[] getCorpOfCounty(Integer countyId) {
  	String[] corps = null;
  	List<Corp> corpList = getDao().query("from Corp c where c.dept.id = ?", countyId);
  	if (CollectionUtils.isNotEmpty(corpList)) {
      int arraySize = corpList.size();
      corps = new String[arraySize];
      for (int i = 0; i < arraySize; i++) {
      	Corp corp = (Corp) corpList.get(i);
        corps[i] = (String) corp.getName();
      }
    }
    return corps;
  }
}
