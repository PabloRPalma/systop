package com.systop.fsmis.fscase.jointtask.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.jointtask.JointTaskConstants;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskManager;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskDetail;

/**
 * 联合整治任务明细管理Action
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskDetailAction extends
		ExtJsCrudAction<JointTaskDetail, JointTaskDetailManager> {
	
	/**
	 * 联合整治任务Id
	 */
	private Integer jointTaskId;
	
	/**
	 * JSON返回结果
	 */
	private Map<String, String> checkResult;
	
	/**
	 * 联合整治任务管理Manager
	 */
	@Autowired
	private JointTaskManager jointTaskManager;
	
  /**
   * 逾期开始时间
   */
  private Date presetBeginTime;
  
  /**
   * 逾期截止时间
   */
  private Date presetEndTime;
  
	/**
	 * 登录用户的新联合整治任务数
	 */
	private String mesCount;
	
  @Autowired
  private LoginUserService loginUserService;
  
	/**
	 * 重写父类的index方法，实现分页检索任务附件信息
	 */
	@Override
	public String index() {	
		if (jointTaskId != null) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			String hql = "from JointTaskDetail jtd where jtd.jointTask.id = ?";
			getManager().pageQuery(page, hql, jointTaskId);
			items = page.getData();
			restorePageData(page);
		}
		return INDEX;
	}
	
	
	/**
	 * 根据部门ID获得对应部门的任务明细，部门ID传参为model.dept.id
	 * @return
	 */
	public String deptTaskDetailIndex() {
		//获得当前登录用户
		User user = UserUtil.getPrincipal(getRequest());
		StringBuffer hql = new StringBuffer();
		hql.append("from JointTaskDetail jtd where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		//联合整治任务必须已审核
		hql.append(" and jtd.jointTask.status = ?");
		args.add(FsConstants.Y);
		//根据用户登录部门查询
		if((user.getDept() != null) && (user.getDept().getId() != null)){
			hql.append(" and jtd.dept.id = ?");
			args.add(user.getDept().getId());
		}
		//根据联合整治任务表的任务标题查询
		if(getModel().getJointTask() != null && StringUtils.isNotBlank(getModel().getJointTask().getTitle())){
			hql.append(" and jtd.jointTask.title like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getJointTask().getTitle()));
		}
		hql.append(" order by jtd.status asc, jtd.jointTask.createDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return "deptTaskDetailIndex";
	}
	
	/**
	 * 查询联合整治逾期未处理任务信息
	 * @return
	 */
	public String expiredIndex() {
		StringBuffer hql = new StringBuffer();
		hql.append("from JointTaskDetail jtd where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		//根据联合整治任务表的任务标题查询
		if(getModel().getJointTask() != null && StringUtils.isNotBlank(getModel().getJointTask().getTitle())){
			hql.append(" and jtd.jointTask.title like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getJointTask().getTitle()));
		}
		//联合整治任务明细中是牵头部门的任务
		hql.append(" and jtd.isLeader = ?");
		args.add(FsConstants.Y);
		//联合整治任务必须已审核
		hql.append(" and jtd.jointTask.status = ?");
		args.add(FsConstants.Y);
		//联合整治任务明细状态不等于"已处理"
		hql.append(" and jtd.status <> ? ");
		args.add(JointTaskConstants.TASK_DETAIL_RESOLVEED);
		
		hql.append(" and jtd.jointTask.presetTime <= ? ");
		args.add(new Date());
    // 根据规定完成时间查询
    if (presetBeginTime != null && presetEndTime != null) {
      hql.append("and jtd.jointTask.presetTime >= ? and jtd.jointTask.presetTime <= ? ");
      args.add(presetBeginTime);
      args.add(presetEndTime);
    }
		hql.append(" order by jtd.jointTask.createDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return "expiredIndex";
	}
	
	/**
	 * 联合整治任务查看
	 */
	public String view(){
		if (getModel().getId() != null ){
			getManager().view(getModel());	
		}
		return "view";
	}
	
	/**
	 * 联合整治任务接收
	 */
	public String receive(){
		if(getModel().getId() != null ){
			getManager().receiveTask(getModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 联合整治任务处理
	 */
	public String result(){
		return "result";
	}
	
	/**
	 * AJAX方式检查联合整治任务是否可以处理
	 */
	public String checkResult() {
		checkResult = Collections.synchronizedMap(new HashMap<String, String>());
		StringBuffer detailBuf = new StringBuffer();
		if (jointTaskId != null) {
      JointTask jointTask = jointTaskManager.get(jointTaskId);
      Set<JointTaskDetail> jointTaskDetails = jointTask.getTaskDetailses();
      for (JointTaskDetail jointTaskDetail : jointTaskDetails) {
      	if (jointTaskDetail.getStatus().equals(JointTaskConstants.TASK_DETAIL_UN_RECEIVE)) {
      		detailBuf.append("【");
      		detailBuf.append(jointTaskDetail.getDept().getName());
      		detailBuf.append("】");
      		detailBuf.append(",");
      	}
      }
  		if (detailBuf.length() > 0 && detailBuf.lastIndexOf(",") > 0) {
  			String detailStr = detailBuf.substring(0, detailBuf.length() -1 );
  			checkResult.put("result", detailStr);
  		}
  		else {
  			checkResult.put("result", null);
  		}
		} 
		return "jsonCheckRst";
	}
	
  /**
   * 得到当前登录人员的部门的新联合整治任务数量,被客户端Ajax访问,形成提示信息
   * 
   * @return
   */
  public String getDeptJointTaskDetailMes() {
    // 得到当前登录人员的部门
    Dept dept = loginUserService.getLoginUserDept(getRequest());
    if (dept != null && dept.getId() != null) {
    	List<JointTaskDetail> jointTasks = getManager().getNewJointTasks(dept);
    	if (CollectionUtils.isNotEmpty(jointTasks)) {
    		mesCount = String.valueOf(jointTasks.size());
    	}
    }
		this.renderJson(getResponse(), mesCount);
		return null;
  }
  
	/**
	 * 联合整治任务处理结果保存
	 */
	public String resultSave(){
		if (getModel().getId() != null ) {
			getModel().setInputer(UserUtil.getPrincipal(getRequest()));
		  getManager().saveResult(getModel());
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getJointTaskId() {
		return jointTaskId;
	}

	public void setJointTaskId(Integer jointTaskId) {
		this.jointTaskId = jointTaskId;
	}

	public Map<String, String> getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Map<String, String> checkResult) {
		this.checkResult = checkResult;
	}

	public Date getPresetBeginTime() {
		return presetBeginTime;
	}

	public void setPresetBeginTime(Date presetBeginTime) {
		this.presetBeginTime = presetBeginTime;
	}

	public Date getPresetEndTime() {
		return presetEndTime;
	}

	public void setPresetEndTime(Date presetEndTime) {
		this.presetEndTime = presetEndTime;
	}

	public String getMesCount() {
		return mesCount;
	}

	public void setMesCount(String mesCount) {
		this.mesCount = mesCount;
	}

}
