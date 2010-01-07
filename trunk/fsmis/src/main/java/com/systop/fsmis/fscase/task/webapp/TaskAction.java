package com.systop.fsmis.fscase.task.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.util.ArrayUtils;
import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.CaseConstants;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.fscase.task.service.TaskManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskAtt;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 任务Action
 * 
 * @author WorkShopers
 * 
 */
@Controller
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskAction extends DefaultCrudAction<Task, TaskManager> {
	// 是否为综合案件,用于在页面跳转间传递是一般案件操作还是综合案件操作
	private String isMultipleCase;
	// 默认显示的Tab序号,用于在view页面默认显示哪个Tab
	private String modelId;

	private FsCase fsCase;

	/** 食品安全事件id */
	private Integer caseId;
	/** 派遣方式id */
	private Integer sendTypeId;

	@Autowired
	private LoginUserService loginUserService;

	/** 附件数组 */
	private File[] attachments;

	/** 附件保存后的名称 数组 */
	private String[] attachmentsFileName;

	/** 所选部门ID集合 */

	private List<Integer> deptIds = new ArrayList<Integer>();

	/** 依赖部门管理类 */
	@SuppressWarnings("unused")
	@Autowired
	private DeptManager deptManager;
	// 查询起始时间
	private Date taskBeginTime;
	// 查询截至时间
	private Date taskEndTime;

	/**
	 * 保存派遣的任务方法
	 */
	@Override
	public String save() {
		if (CollectionUtils.isEmpty(deptIds)) {
			addActionError("请至少选择一个部门!");
			return INPUT;
		}
		Assert.notNull(getModel().getFsCase());
		Assert.notNull(getModel().getFsCase().getId());
		Assert.notEmpty(deptIds);

		// 设定派遣时间
		getModel().setDispatchTime(new Date());

		// 将附件信息集合保存到任务附件实体集合中
		// 1.完成文件的上传
		// 2.将上传文件路径信息保存到任务附件实体中
		List<TaskAtt> taskAtts = new ArrayList<TaskAtt>(); // 任务附件实体集合
		// 遍历文件数组,完成各个文件的上传并将文件路径信息保存到任务附件实体中
		if (ArrayUtils.isNotEmpty(attachments)
				&& ArrayUtils.isNotEmpty(attachmentsFileName)) {
			for (int i = 0; i < attachments.length; i++) {
				if (attachments[i] != null && attachmentsFileName[i] != null) {
					if (attachments[i].length() > TaskConstants.TASK_UPLOAD_ALLOWED_FILE_SIZE) {
						addActionError("上传文件太大");
						return INPUT;
					}
					// 检查文件类型是否符合既定文件类型条件
					String extension = attachmentsFileName[i]
							.substring(attachmentsFileName[i].lastIndexOf(".") + 1);
					Collection<String> types = new ArrayList<String>();
					CollectionUtils.addAll(types,
							TaskConstants.TASK_UPLOAD_ALLOWED_FILE_TYPES);
					if (!types.contains(extension)) {
						addActionError("未正确选择上传文件类型，请重新选择！");
						return INPUT;
					}

					TaskAtt taskAtt = new TaskAtt();
					// 上传文件并且把文件信息保存在任务附件实体中
					taskAtt.setPath(UpLoadUtil.doUpload(attachments[i],
							attachmentsFileName[i],
							FsConstants.TASK_ATT_FOLDER, getServletContext()));
					taskAtt.setTitle(attachmentsFileName[i]);

					// 将附件实例保存到附件实体集合中
					taskAtts.add(taskAtt);
				}
			}
		}
		getManager().save(getModel(), deptIds, taskAtts);

		return SUCCESS;
	}

	/**
	 * 查询任务列表
	 */
	@Override
	public String index() {
		if (loginUserService == null
				|| loginUserService.getLoginUser(getRequest()) == null) {
			addActionError("请先登录!");
			return INDEX;
		}

		StringBuffer buf = new StringBuffer("from Task t where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		// 区分是否综合(单体/多体)案件
		if (StringUtils.isNotBlank(isMultipleCase)) {
			buf.append("and t.fsCase.isMultiple = ? ");
			args.add(isMultipleCase);
		}
		// 判断是否是市级人员登录,如果不是,则需要添加根据本区县查询案件的查询条件,本逻辑需要确认
		if (loginUserService.getLoginUserCounty(getRequest()).getParentDept() != null) {
			buf.append("and t.fsCase.county.id = ? ");
			args.add(loginUserService.getLoginUserCounty(getRequest()).getId());
		}
		// 根据title查询
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			buf.append("and t.title like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
		}
		// 根据状态查询
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			buf.append("and t.status = ?");
			args.add(getModel().getStatus());
		}
		// 根据任务派发时间查询
		if (taskBeginTime != null && taskEndTime != null) {

			buf.append(" and t.dispatchTime >= ? and t.dispatchTime <= ?");
			args.add(taskBeginTime);
			args.add(taskEndTime);
		}

		// 查询属于当前区县的案件的任务,现阶段(20091226)项目组长说暂时不考虑,待以后加上此功能时,启用代码
		// buf.append(" and t.fsCase.county.id = ? order by t.status, t.time desc");
		/*
		 * page = getManager().pageQuery(page, buf.toString(),
		 * loginUserService.getLoginUserDept(getRequest()).getId());
		 */

		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, buf.toString(), args.toArray());

		restorePageData(page);

		return INDEX;
	}

	/**
	 * 编辑任务方法
	 */
	@Override
	public String edit() {
		fsCase = getManager().getDao().get(FsCase.class, caseId);
		getModel().setFsCase(fsCase);

		return INPUT;
	}

	/**
	 * 删除任务方法
	 */
	public String remove() {
		if (getModel() != null && getModel().getId() != null) {
			for (TaskAtt taskAtt : getModel().getTaskAtts()) {
				removeTaskAtt(taskAtt);
			}
		}
		getManager().remove(getModel());

		return SUCCESS;
	}

	/**
	 * 删除任务附件文件
	 * 
	 * @param taskAtt
	 */
	private void removeTaskAtt(TaskAtt taskAtt) {
		File file = new File(getRealPath(taskAtt.getPath()));
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 单体任务状态列表返回页面
	 */
	public Map<String, String> getStateMap() {

		Map<String, String> StateMap = new LinkedHashMap<String, String>();
		StateMap.put(CaseConstants.TASK_STATUS_UN_RECEIVE, "未接收");
		StateMap.put(CaseConstants.TASK_STATUS_RESOLVEING, "已派遣");
		StateMap.put(CaseConstants.TASK_STATUS_RESOLVEED, "已处理");
		StateMap.put(CaseConstants.TASK_STATUS_RETURNED, "已退回");

		return StateMap;
	}

	public FsCase getFsCase() {
		return fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
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

	public List<Integer> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Integer> deptIds) {
		this.deptIds = deptIds;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getSendTypeId() {
		return sendTypeId;
	}

	public void setSendTypeId(Integer sendTypeId) {
		this.sendTypeId = sendTypeId;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getIsMultipleCase() {
		return isMultipleCase;
	}

	public void setIsMultipleCase(String isMultipleCase) {
		this.isMultipleCase = isMultipleCase;
	}

}
