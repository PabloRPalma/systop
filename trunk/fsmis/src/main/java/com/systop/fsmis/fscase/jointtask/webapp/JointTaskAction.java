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
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.fscase.CaseConstants;
import com.systop.fsmis.fscase.casetype.service.CaseTypeManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskAttachManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskManager;
import com.systop.fsmis.fscase.service.FsCaseManager;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskAttach;
import com.systop.fsmis.model.JointTaskDetail;

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
	 * 案件一级类型
	 */
	private Integer typeOneId;
	/**
	 * 案件二级类型
	 */
	private Integer typeTwoId;
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
	 * 重写父类的index方法，实现分页检索风险评估信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String index() {
		StringBuffer hql = new StringBuffer();
		hql.append("from JointTask jt where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			hql.append(" and jt.title = ?");
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
	 * 添加联合整治单体案件
	 * @return
	 */
	public String addCase() {
		return "addCase";
	}

	/**
	 * 重写父类编辑方法传类别参数，用于edit.jsp显示
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
			if (getModel().getFsCase().getCounty() == null
					|| getModel().getFsCase().getCounty().getId() == null) {
				addActionError("请选择事件所属区县.");
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
	 * 重写父类的remove方法，用于实现删除联合整治任务及其附件和审核记录的功能
	 */
	@Override
	public String remove() {
		JointTask jointTask = getManager().get(getModel().getId());
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
			jointTaskDetailManager.remove(jointTaskDetail);
		}		
		
		//删除联合整治任务审核信息		
		getManager().delCheckResults(jointTask);	
		
		//删除联合整治任务信息
		getManager().remove(jointTask);
		
		return SUCCESS;
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

}