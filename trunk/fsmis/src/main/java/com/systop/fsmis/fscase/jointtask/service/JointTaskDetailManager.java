package com.systop.fsmis.fscase.jointtask.service;


import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.jointtask.JointTaskConstants;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskDetail;
import com.systop.fsmis.sms.SmsSendManager;
import com.systop.fsmis.sms.util.MobileNumChecker;
/**
 * 联合整治任务明细service层类
 * @author ShangHua
 *
 */
@Service
public class JointTaskDetailManager extends BaseGenericsManager<JointTaskDetail> {
	
	@Autowired
	private SmsSendManager smsSendManager;
	
	/**
	 * 给该联合整治任务明细中所选择的部门负责人发送短信
	 * @param jointTask 联合整治任务
	 * @return
	 */
	@Transactional
	public void sendTaskMessage(JointTask jointTask) {
		Set<JointTaskDetail> taskDetails = jointTask.getTaskDetailses();
		for (JointTaskDetail jointTaskDetail : taskDetails) {
			Set<User> users = jointTaskDetail.getDept().getUsers();
			for (User user : users) {
				StringBuffer jointTakskBuf = new StringBuffer();
				jointTakskBuf.append(user.getName()).append(
						JointTaskConstants.MSG_JOINT_TASK_SMSSEND);
				// 给该条评估申请所选择的专家发送短信
				if(MobileNumChecker.checkMobilNumberDigit(user.getMobile())) {
					smsSendManager.addMessage(user.getMobile(), jointTakskBuf.toString());
				}
			}
		}
	}
	
	/**
	 * 查看时确定为已查看状态
	 */
	@Transactional
	public void view(JointTaskDetail jointTaskDetail) {
		if (jointTaskDetail.getStatus().equals(JointTaskConstants.TASK_DETAIL_UN_RECEIVE)) {
			if (jointTaskDetail.getIsLeader().equals(FsConstants.Y)){
				jointTaskDetail.setStatus(JointTaskConstants.TASK_DETAIL_LOOK_OVERED);
			}else{
				//协办部门没有处理权限,只有查看
				jointTaskDetail.setStatus(JointTaskConstants.TASK_DETAIL_RECEIVEED);
			}
		}
		save(jointTaskDetail);
	}
	
	/**
	 * 接收任务
	 */
	@Transactional
	public void receiveTask(JointTaskDetail jointTaskDetail){
		jointTaskDetail.setStatus(JointTaskConstants.TASK_DETAIL_RECEIVEED);
		jointTaskDetail.setReceiveTime(new Date());
		save(jointTaskDetail);
	}
	
	/**
	 * 处理任务
	 */
	@Transactional
	public void saveResult(JointTaskDetail jointTaskDetail){
		jointTaskDetail.setStatus(JointTaskConstants.TASK_DETAIL_RESOLVEED);
		jointTaskDetail.setCompletionTime(new Date());
		save(jointTaskDetail);
	}
}
