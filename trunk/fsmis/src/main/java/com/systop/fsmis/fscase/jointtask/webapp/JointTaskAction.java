package com.systop.fsmis.fscase.jointtask.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskAttachManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailAttManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskAttach;
import com.systop.fsmis.model.JointTaskDetail;
import com.systop.fsmis.model.JointTaskDetailAttach;

/**
 * 联合整治任务Action
 * 
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskAction extends
		ExtJsCrudAction<JointTask, JointTaskManager> {
	/**
	 * 案件类型管理Manager
	 */
	@Autowired
	private CaseTypeManager caseTypeManager;
	/**
	 * 案件信息管理Manager
	 */
	@Autowired
	private FsCaseManager fsCaseManager;
	/**
	 * 部门管理Manager
	 */
	@Autowired
	private DeptManager deptManager;
	
	/**
	 * 联合整治附件管理Manager
	 */
	@Autowired
	private JointTaskAttachManager jointTaskAttachManager;
	
	/**
	 * 联合整治任务明细管理Manager
	 */
	@Autowired
	private JointTaskDetailManager jointTaskDetailManager;

	/**
	 * 联合整治任务明细附件管理Manager
	 */
	@Autowired
	private JointTaskDetailAttManager jointTaskDetailAttManager;
	
	/**
	 * 案件一级类型
	 */
	private Integer typeOneId;
	/**
	 * 案件二级类型
	 */
	private Integer typeTwoId;
	
  private Integer oneId;

  private Integer twoId;
  
	/**
	 * 牵头部门
	 */
	private Integer deptLeaderId;
	/**
	 * 协作部门
	 */
	private Integer[] deptIds;
	
	/** 
	 * 附件
	 */
	private File[] attachments;

	/** 
	 * 附件保存后的名称
	 */
	private String[] attachmentsFileName;
	
	/**
	 * 审核管理实体类
	 */
	private CheckResult checkResult;
	
	/**
	 * 联合整治任务Id
	 */
	private Integer jointTaskId;
	
  @Autowired
  private LoginUserService loginUserService;
  
  /**
   * 查询事件事发起始时间
   */
  private Date caseBeginTime;
  
  /**
   * 查询事件事发截止时间
   */
  private Date caseEndTime;

	/**
	 * 重写父类的index方法，实现分页检索联合整治任务信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String index() {
		StringBuffer hql = new StringBuffer();
		hql.append("from JointTask jt where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			hql.append(" and jt.title like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
		}
		hql.append(" order by jt.createDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}
	
	/**
	 * 联合整治添加的案件信息查询
	 */
	@SuppressWarnings("unchecked")
	public String caseIndex() {
    StringBuffer hql = new StringBuffer("from FsCase fc where fc.isSubmited = 0 ");
		List<Object> args = new ArrayList<Object>();
    // 判断是否是市级人员登录,如果不是,则需要添加根据本区县查询案件的查询条件,本逻辑需要确认
    if (loginUserService.getLoginUserCounty(getRequest()).getParentDept() != null) {
      hql.append("and fc.county.id = ? ");
      args.add(loginUserService.getLoginUserCounty(getRequest()).getId());
    }
		if (getModel().getFsCase() != null ) {
	    if (StringUtils.isNotBlank(getModel().getFsCase().getTitle())) {
	      hql.append("and fc.title like ? ");
	      args.add(MatchMode.ANYWHERE.toMatchString(getModel().getFsCase().getTitle()));	
	    }
	    if (StringUtils.isNotBlank(getModel().getFsCase().getCode())) {
	      hql.append("and fc.code = ? ");
	      args.add(getModel().getFsCase().getCode());
	    }
		}
    // 根据事发时间区间查询
    if (caseBeginTime != null && caseEndTime != null) {
      hql.append("and fc.caseTime >= ? and fc.caseTime <= ? ");
      args.add(caseBeginTime);
      args.add(caseEndTime);
    }
    
    hql.append("and fc.caseSourceType = ? ");
    args.add(CaseConstants.CASE_SOURCE_TYPE_JOINTASK);	 
    hql.append("order by fc.caseTime desc, fc.status");
    
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, hql.toString(), args.toArray());
		items = page.getData();
		restorePageData(page);
		return "caseIndex";
	}
	
	/**
	 * 添加/编辑联合整治单体案件
	 * @return
	 */
	public String addCase() {
		if (getModel().getFsCase() != null
				&& getModel().getFsCase().getId() != null) {
			FsCase fsCase = fsCaseManager.get(getModel().getFsCase().getId());
      if (fsCase.getCaseType().getCaseType() != null
          && fsCase.getCaseType().getCaseType().getId() != null) {
      	oneId = fsCase.getCaseType().getCaseType().getId();
      	twoId = fsCase.getCaseType().getId();
      } else {
      	oneId = fsCase.getCaseType().getId();
      }
			getModel().setFsCase(fsCase);
		}
		return "addCase";
	}

	/**
	 * 重写父类编辑方法传类别参数，用于联合整治任务edit.jsp显示
	 */
	@Override
	public String edit() {
		StringBuffer deptIdsBuf = new StringBuffer();
		if (getModel().getFsCase() != null
				&& getModel().getFsCase().getId() != null) {
			getModel().setFsCase(
					getManager().getDao().get(FsCase.class,
							getModel().getFsCase().getId()));
		}
		if (getModel() != null && getModel().getId() != null ){
			List<JointTaskDetail> taskDetails = getManager().getTaskDetails(getModel().getId());
			for(JointTaskDetail jointTaskDetail : taskDetails){
				if (jointTaskDetail.getIsLeader().equals(FsConstants.Y)) {
					deptLeaderId = jointTaskDetail.getDept().getId();
				} else {
					deptIdsBuf.append(jointTaskDetail.getDept().getId());
					deptIdsBuf.append(",");
				}			
			}
			if (deptIdsBuf.length() > 0 && deptIdsBuf.lastIndexOf(",") > 0) {
				String deptIdsStr = deptIdsBuf.substring(0, deptIdsBuf.length() - 1);
				getRequest().setAttribute("deptIdsStr", deptIdsStr);
			}
		}

		return INPUT;
	}

	/**
	 * 保存联合整治单体案件信息
	 */
	@Transactional
	public String fsCaseSave() {
		if (getModel().getFsCase() != null) {
			if (getManager().getDao().exists(getModel().getFsCase(), "code")) {
				addActionError("事件编号已存在！");
				getRequest().setAttribute("levelone", getLevelOne());
				return "addCase";
			}
			//联合整治业务不需要选择部门，只保存当前登录用户所在的部门信息
	    Dept dept = loginUserService.getLoginUserDept(getRequest());
	    if (dept != null ) {
	    	getModel().getFsCase().setCounty(dept);
	    } else {
	    	addActionError("当前登录用户所属部门信息为空！");
	    	getRequest().setAttribute("levelone", getLevelOne());
				return "addCase";
	    }
			CaseType cType = new CaseType();
			if (typeTwoId != null) {
				cType = getManager().getDao().get(CaseType.class, typeTwoId);
			} else {
				if (typeOneId != null) {
					cType = getManager().getDao().get(CaseType.class, typeOneId);
				}
			}
			getModel().getFsCase().setCaseTime(new Date());
			getModel().getFsCase().setStatus(CaseConstants.CASE_UN_RESOLVE);
			getModel().getFsCase().setCaseType(cType);
			getModel().getFsCase().setCounty(
					deptManager.get(getModel().getFsCase().getCounty().getId()));
			getModel().getFsCase().setIsSubmited(FsConstants.N);
			//事件添加类别
			getModel().getFsCase().setCaseSourceType(CaseConstants.CASE_SOURCE_TYPE_JOINTASK);
			//事件派遣类型(未派遣)
			getModel().getFsCase().setStatus(CaseConstants.CASE_UN_RESOLVE);
			if (StringUtils.isBlank(getModel().getFsCase().getIsMultiple())) {
				getModel().getFsCase().setIsMultiple(FsConstants.N);
			}
			fsCaseManager.save(getModel().getFsCase());
		}
		return "addJointTask";
	}

	/**
	 * 联合整治任务保存
	 */
	@Transactional
	public String taskSave() {
		if (getModel().getFsCase() != null
				&& getModel().getFsCase().getId() != null) {
			FsCase fsCase = fsCaseManager.get(getModel().getFsCase().getId());
			getModel().setFsCase(fsCase);
			// 设置申请人为当前登录用户
			getModel().setProposer(UserUtil.getPrincipal(getRequest()));
			
			List<JointTaskAttach> jTaskAttachList = new ArrayList<JointTaskAttach>(); // 存放附件路径
			// 如果附件不为空则上传
			if (attachments != null) {
				for (int i = 0; i < attachments.length; i++) {
					// 检查文件大小是否符合
					if (attachments[i].length() > AssessMentConstants.UPLOAD_ALLOWED_FILE_SIZE) {
						addActionError("附件【" + attachmentsFileName[i] + "】大于10MB，请重新选择！");
						return INPUT;
					}		
					// 检查文件类型是否符合
					String extension = attachmentsFileName[i].substring(attachmentsFileName[i]
							.lastIndexOf(".") + 1);
					boolean flag = false;
					for (String extAllowed : AssessMentConstants.UPLOAD_ALLOWED_FILE_TYPES) {
						if (StringUtils.equalsIgnoreCase(extension, extAllowed)) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						addActionError("附件【" + attachmentsFileName[i] + "】文件格式不正确，请重新选择！");
						return INPUT;
					}
					JointTaskAttach jTaskAttach = new JointTaskAttach();
					jTaskAttach.setPath(UpLoadUtil.doUpload(attachments[i],
							attachmentsFileName[i], FsConstants.JOINT_TASK_ATT_FOLDER,
							getServletContext()));
					jTaskAttach.setTitle(attachmentsFileName[i]);
					jTaskAttachList.add(jTaskAttach);
				}
			}
			getManager().save(getModel(), deptLeaderId, deptIds, jTaskAttachList);
			//事件处理类型
			getModel().getFsCase().setProcessType(CaseConstants.PROCESS_TYPE_JOIN_TASK);
			//事件派遣类型(已派遣)
			getModel().getFsCase().setStatus(CaseConstants.CASE_PROCESSING);
			fsCaseManager.save(getModel().getFsCase());
		}
		return SUCCESS;		
	}

	/**
	 * 联合整治任务审核
	 */
	public String audit() {
		return "audit";
	}
	
	/**
	 * 保存联合整治任务审核记录
	 */
	public String auditSave() {
		if (getModel().getId() != null ){
			JointTask jointTask = getManager().get(getModel().getId());
			checkResult.setJointTask(jointTask);
			//设置审核人为当前登录用户
			checkResult.setChecker(UserUtil.getPrincipal(getRequest()));
			getManager().auditSave(checkResult);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除联合整治案件信息并级联删除该案件所对应的联合整治任务及其附件和审核记录信息
	 * @return
	 */
	public String caseRemove() {
		if (getModel().getFsCase() != null
				&& getModel().getFsCase().getId() != null) {
			FsCase fsCase = fsCaseManager.get(getModel().getFsCase().getId());
			Set<JointTask> jointTasks = fsCase.getJointTaskses();
			for (JointTask jointTask : jointTasks) {
				this.taskRemove(jointTask);
			}
			fsCaseManager.remove(fsCase);
		}
		return SUCCESS;
	}
	
	/**
	 * 重写父类的remove方法，用于实现删除联合整治任务及其附件和审核记录的功能
	 */
	@Override
	public String remove() {
		JointTask jointTask = getManager().get(getModel().getId());
		//更新事件的派遣类别为"未派遣"
		jointTask.getFsCase().setStatus(CaseConstants.CASE_UN_RESOLVE);
		//更新事件的处理流程为"空"
		jointTask.getFsCase().setProcessType(null);
		fsCaseManager.save(jointTask.getFsCase());
		this.taskRemove(jointTask);
		return SUCCESS;
	}

	/**
	 * 根据联合整治任务对象删除其所有相关的信息
	 * @param jointTask
	 */
	public void taskRemove(JointTask jointTask) {
		Set<JointTaskAttach> taskAttaches = jointTask.getTaskAttachses();
		Set<JointTaskDetail> taskDetails = jointTask.getTaskDetailses();
		
		//删除联合整治任务附件信息
		for (JointTaskAttach taskAttach : taskAttaches) {
			String realPath = taskAttach.getPath();
			if (StringUtils.isNotBlank(realPath)) {
				jointTaskAttachManager.removeAttach(taskAttach, getRealPath(realPath));
			}
		}
		
		//删除联合整治任务明细信息
		for (JointTaskDetail jointTaskDetail : taskDetails) {
			Set<JointTaskDetailAttach> taskDetailAttaches = jointTaskDetail.getTaskDetailAttachses();
			//删除联合整治任务明细附件信息
			for (JointTaskDetailAttach taskDetailAttach : taskDetailAttaches) {
				String realPath = taskDetailAttach.getPath();
				if (StringUtils.isNotBlank(realPath)) {
					jointTaskDetailAttManager.removeDetailAttach(taskDetailAttach, getRealPath(realPath));
				}
			}
			jointTaskDetailManager.remove(jointTaskDetail);
		}		
		
		//删除联合整治任务审核信息		
		getManager().delCheckResults(jointTask);	
		
		//删除联合整治任务信息
		getManager().remove(jointTask);
	}

	/**
	 * 打印联合整治任务申请表
	 */
	public String print() {
		if (getModel().getId() != null ){
			checkResult = getManager().getCheckResult(getModel().getId());
		}
		return "print";
	}
	
	/**
	 * 联合整治任务查看
	 */
	public String view() {
		return "view";
	}
	
	/**
	 * 查询联合整治任务的审核记录
	 */
	public String listCheckResult() {
		if (jointTaskId != null ) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			StringBuffer sql = new StringBuffer("from CheckResult cr where 1=1 ");
			sql.append(" and cr.jointTask.id = ?");
			sql.append(" order by cr.isAgree,cr.checkTime desc");
			page = getManager().pageQuery(page, sql.toString(), jointTaskId);
			restorePageData(page);
		}
		
		return "listCheckRst";
	}
	
	/**
	 * 用于显示一级主题类，用于edit.jsp显示
	 */
	@SuppressWarnings("unchecked")
	public List getLevelOne() {
		return caseTypeManager.getLevelOneList();
	}

	/**
	 * 
	 * @return
	 */
	public Integer getTypeOneId() {
		return typeOneId;
	}

	public void setTypeOneId(Integer typeOneId) {
		this.typeOneId = typeOneId;
	}

	public Integer getTypeTwoId() {
		return typeTwoId;
	}

	public void setTypeTwoId(Integer typeTwoId) {
		this.typeTwoId = typeTwoId;
	}

	public Integer getDeptLeaderId() {
		return deptLeaderId;
	}

	public void setDeptLeaderId(Integer deptLeaderId) {
		this.deptLeaderId = deptLeaderId;
	}

	public Integer[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Integer[] deptIds) {
		this.deptIds = deptIds;
	}

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

	public String[] getAttachmentsFileName() {
		return attachmentsFileName;
	}

	public void setAttachmentsFileName(String[] attachmentsFileName) {
		this.attachmentsFileName = attachmentsFileName;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}

	public Integer getJointTaskId() {
		return jointTaskId;
	}

	public void setJointTaskId(Integer jointTaskId) {
		this.jointTaskId = jointTaskId;
	}

	public Date getCaseBeginTime() {
		return caseBeginTime;
	}

	public void setCaseBeginTime(Date caseBeginTime) {
		this.caseBeginTime = caseBeginTime;
	}

	public Date getCaseEndTime() {
		return caseEndTime;
	}

	public void setCaseEndTime(Date caseEndTime) {
		this.caseEndTime = caseEndTime;
	}

	public Integer getOneId() {
		return oneId;
	}

	public void setOneId(Integer oneId) {
		this.oneId = oneId;
	}

	public Integer getTwoId() {
		return twoId;
	}

	public void setTwoId(Integer twoId) {
		this.twoId = twoId;
	}

}
