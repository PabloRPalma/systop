package com.systop.fsmis.fscase.jointtask.service;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.fscase.jointtask.JointTaskConstants;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskAttach;
import com.systop.fsmis.model.JointTaskDetail;
/**
 * 联合整治任务service层类
 * @author ShangHua
 *
 */
@Service
public class JointTaskManager extends BaseGenericsManager<JointTask> {

	/**
	 * 联合整治任务明细管理Manager
	 */
	@Autowired
	private JointTaskDetailManager jointTaskDetailManager;
	
	/**
	 * 联合整治任务保存
	 */
	@Transactional
	public void save(JointTask jointTask, Integer deptleaderId, 
			Integer[] deptIds, List<JointTaskAttach> jTaskAttachList){
		//创建联合整治任务
		jointTask.setCreateDate(new Date());
		jointTask.setStatus(AssessMentConstants.AUDITING_WAITING);
		getDao().save(jointTask);
		
		//删除之前生成过的联合整治任务明细信息
		List<JointTaskDetail> taskDetails = this.getTaskDetails(jointTask.getId());
		for(JointTaskDetail jointTaskDetail : taskDetails){
			getDao().delete(jointTaskDetail);
		}
		
		// 设置牵头部门任务
		JointTaskDetail jointTaskDetail = new JointTaskDetail();
		Dept deptleader = getDao().get(Dept.class, deptleaderId);
		jointTaskDetail.setDept(deptleader);
		jointTaskDetail.setIsLeader(FsConstants.Y);
		jointTaskDetail.setStatus(JointTaskConstants.TASK_DETAIL_UN_RECEIVE);
		jointTaskDetail.setJointTask(jointTask);
		getDao().save(jointTaskDetail);
		
		//设置协作部门任务
		if (deptIds != null){
			for(Integer deptId : deptIds){
				JointTaskDetail jointTaskDet = new JointTaskDetail();
				Dept dept = getDao().get(Dept.class, deptId);
				jointTaskDet.setDept(dept);
				jointTaskDet.setIsLeader(FsConstants.N);
				jointTaskDet.setStatus(JointTaskConstants.TASK_DETAIL_UN_RECEIVE);
				jointTaskDet.setJointTask(jointTask);
				getDao().save(jointTaskDet);			
			}
		}
		
		//保存任务附件信息
		for(JointTaskAttach jTaskAttach : jTaskAttachList){
			jTaskAttach.setJointTask(jointTask);
			getDao().save(jTaskAttach);
		}
	}
	
	/**
   * 根据联合整治任务Id获取任务明细表的信息
	 * @param jointTaskId 联合整治任务Id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<JointTaskDetail> getTaskDetails(Integer jointTaskId) {
		String hql = "from JointTaskDetail jtd where jtd.jointTask.id = ? ";
		return getDao().query(hql, jointTaskId);
	}

	/**
	 * 提取联合整治任务审核记录表中最新的信息
	 * @param jointTaskId 联合整治任务Id
	 * @return
	 */
  @SuppressWarnings("unchecked")
  public CheckResult getCheckResult(Integer jointTaskId) {
    List<CheckResult> checkResults = this.getDao()
         .query("from CheckResult cr where cr.jointTask.id = ? " +
         		"order by cr.checkTime desc, cr.isAgree desc", jointTaskId);
    return (CollectionUtils.isNotEmpty(checkResults)) ? 
    		    (CheckResult) checkResults.get(0) : null;
  }
  
	/**
	 * 保存联合整治任务审核信息
	 * @param checkResult 公共审核类
	 */
	@Transactional
	public void auditSave(CheckResult checkResult) {
		CheckResult cResult = new CheckResult();
		//判断审核是否通过
		if (checkResult.getJointTask() != null) {
			//如果选择"是",则更新联合整治的任务状态为审核通过("1"),否则为审核未通过("2")。
			if (checkResult.getIsAgree().equals(JointTaskConstants.AUDITING_PASSED_STATE)) {
				checkResult.getJointTask().setStatus(JointTaskConstants.AUDITING_PASSED_STATE);
			  //给该联合整治任务明细中所选择的部门负责人发送短信
				jointTaskDetailManager.sendTaskMessage(checkResult.getJointTask());
			} else {
				checkResult.getJointTask().setStatus(JointTaskConstants.AUDITING_REJECT_STATE);
			}
			super.save(checkResult.getJointTask());
			cResult.setJointTask(checkResult.getJointTask());
			cResult.setChecker(checkResult.getChecker());
			cResult.setCheckTime(new Date());
			cResult.setIsAgree(checkResult.getIsAgree());
			cResult.setResult(checkResult.getResult());
			getDao().save(cResult);
		}
	}
	
  /**
   * 删除联合整治任务对应的审核数据
   * @param assessment 风险评估对象
   */
  @Transactional
  public void delCheckResults(JointTask jointTask) {
		Set<CheckResult> checkResults = jointTask.getCheckResults();
  	if (CollectionUtils.isNotEmpty(checkResults)) {
  		for (CheckResult checkResult : checkResults) {
  				getDao().evict(checkResult);
  				getDao().delete(CheckResult.class, checkResult.getId());
  	  }
    }
  }
}
