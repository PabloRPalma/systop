package com.systop.fsmis.fscase.task.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.FsCaseConstants;
import com.systop.fsmis.fscase.task.service.TaskManager;
import com.systop.fsmis.model.FsCase;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskAtt;

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
	/** 各个模块所共需的FsCase实例 */
	private FsCase fsCase;

	/** 为了在界面中区分当前模块的标示 */

	public FsCase getFsCase() {

		return fsCase;
	}

	public void setFsCase(FsCase fsCase) {
		this.fsCase = fsCase;
	}

	private LoginUserService loginUserService;

	/** 附件 */
	private File[] attachments;

	/** 附件保存后的名称 */
	private String[] attachmentsFileName;

	/** 所选部门ID集合 */

	private List<Integer> deptIds = new ArrayList<Integer>();

	/** 依赖部门管理类 */
	private DeptManager deptManager;

	/**
	 * 保存派遣的任务方法
	 */
	@Override
	public String save() {
		logger.info("@@@@@@@@@@@@save");
		// 设定派遣时间
		getModel().setDispatchTime(new Date());
		/**
		 * 将附件信息集合保存到任务附件实体集合中<br>
		 * 1.完成文件的上传<br>
		 * 2.将上传文件路径信息保存到任务附件实体中
		 */
		// 任务附件实体集合
		List<TaskAtt> taskAtts = new ArrayList<TaskAtt>();
		// 遍历文件数组,完成各个文件的上传并将文件路径信息保存到任务附件实体中
		if (attachments != null) {
			for (int i = 0; i < attachments.length; i++) {

				TaskAtt taskAtt = new TaskAtt();
				taskAtt.setPath(UpLoadUtil.doUpload(attachments[i],
						attachmentsFileName[i], FsConstants.TASK_ATT_FOLDER,

						getServletContext()));
				taskAtt.setTitle(attachmentsFileName[i]);

				taskAtts.add(taskAtt);
			}
		}

		logger.info("@@@@@@@@@@@@save--100");
		getManager().save(getModel(), deptIds, taskAtts);
		logger.info("@@@@@@@@@@@@save--11");

		return SUCCESS;
	}

	/**
	 * 查询任务列表
	 */
	@Override
	public String index() {
		logger.info("@@@@@@@@@@@@@@@2----------index");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer buf = new StringBuffer("from Task t where 1=1 ");
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			buf.append("and t.title like '%").append(getModel().getTitle()).append(
					"%' ");
		}
		if (StringUtils.isNotBlank(getModel().getStatus())) {
			buf.append("and t.status = '").append(getModel().getStatus())
					.append("' ");
		}
		logger.info("@@@@@@@@@@@@@@@3----------index");
		// buf.append(" and t.fsCase.county.id = ? order by t.status, t.time desc");

		// 查询属于当前区县的案件的任务
		/*
		 * page = getManager().pageQuery(page, buf.toString(),
		 * loginUserService.getLoginUserDept(getRequest()).getId());
		 */
		page = getManager().pageQuery(page, buf.toString());

		restorePageData(page);
		logger.info("############################5");

		return INDEX;
	}

	/**
	 * 单体任务状态列表返回页面
	 */
	public Map<String, String> getStateMap() {

		Map<String, String> StateMap = new LinkedHashMap<String, String>();
		StateMap.put(FsCaseConstants.TASK_STATUS_UN_RECEIVE, "未接收");
		StateMap.put(FsCaseConstants.TASK_STATUS_RESOLVEING, "已派遣");
		StateMap.put(FsCaseConstants.TASK_STATUS_RESOLVEED, "已处理");
		StateMap.put(FsCaseConstants.TASK_STATUS_RETURNED, "已退回");

		return StateMap;
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

	public DeptManager getDeptManager() {
		return deptManager;
	}

	@Autowired
	public void setDeptManager(DeptManager deptManager) {
		this.deptManager = deptManager;
	}

	public List<Integer> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Integer> deptIds) {
		this.deptIds = deptIds;
	}

	public LoginUserService getLoginUserService() {
		return loginUserService;
	}

	@Autowired
	public void setLoginUserService(LoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	public String getCurrentModel() {
		return "TASK11";
	}

}
