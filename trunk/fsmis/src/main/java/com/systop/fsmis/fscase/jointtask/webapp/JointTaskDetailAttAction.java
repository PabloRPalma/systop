package com.systop.fsmis.fscase.jointtask.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.assessment.AssessMentConstants;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailAttManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskDetailManager;
import com.systop.fsmis.model.JointTaskDetailAttach;

/**
 * 联合整治任务明细附件管理Action
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskDetailAttAction extends
		ExtJsCrudAction<JointTaskDetailAttach, JointTaskDetailAttManager> {
	
	/**
	 * 上传的任务明细文件
	 */
	private File attachment;

	/**
	 * 上传的任务明细文件名称
	 */
	private String attachmentFileName;
	
	/**
	 * 联合整治任务
	 */
	@Autowired
	private JointTaskDetailManager jointTaskDetailManager;

	/**
	 * 联合整治任务明细Id
	 */
	private Integer jointTaskDetailId;
	
	/**
	 * 错误提示信息
	 */
	private List<String> errorMsg;
	
	/**
	 * 重写父类的index方法，实现分页检索任务附件信息
	 */
	@Override
	public String index() {	
		if (jointTaskDetailId != null) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			String hql = "from JointTaskDetailAttach jtda where jtda.jointTaskDetail.id = ?";
			getManager().pageQuery(page, hql, jointTaskDetailId);
			items = page.getData();
			restorePageData(page);
		}
		return INDEX;
	}
	
	/**
	 * 上传任务明细评估附件
	 * 
	 * @return
	 */
	public String upload() {
		errorMsg = new ArrayList<String>();
		try {
			if (attachment != null) {
				// 检查文件大小是否符合
				if (attachment.length() > AssessMentConstants.UPLOAD_ALLOWED_FILE_SIZE) {
					errorMsg.add("只能上传小于10MB的文件！");
					return INPUT;
				}
				// 检查文件类型是否符合
				String extension = attachmentFileName.substring(attachmentFileName
						.lastIndexOf(".") + 1);
				boolean flag = false;
				for (String extAllowed : AssessMentConstants.UPLOAD_ALLOWED_FILE_TYPES) {
					if (StringUtils.equalsIgnoreCase(extension, extAllowed)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					errorMsg.add("未正确选择上传文件类型，请重新选择！");
					return INPUT;
				}
				String fileRelativePath = null;
				// 保存文件不重命名
				fileRelativePath = UpLoadUtil.doUpload(attachment, attachmentFileName,
						FsConstants.JOINT_TASK_DETAIL_ATT_FOLDER, getServletContext(), true);
				// 相对路径，可用于下载地址
				JointTaskDetailAttach jointTaskDetailAttach = new JointTaskDetailAttach();
				jointTaskDetailAttach.setPath(fileRelativePath);
				jointTaskDetailAttach.setTitle(attachmentFileName);
				jointTaskDetailAttach.setJointTaskDetail(jointTaskDetailManager.get(jointTaskDetailId));
				getManager().save(jointTaskDetailAttach);
			}
		} catch (Exception e) {
			errorMsg.add("文件上传失败！");
			//addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 重写父类的remove方法
	 */
	@Override
	public String remove() {
		JointTaskDetailAttach jointTaskDetailAttach = getManager().get(jointTaskDetailId);
		if (jointTaskDetailAttach != null) {
			String path = jointTaskDetailAttach.getPath();
			if (StringUtils.isNotBlank(path)) {
				getManager()
						.removeDetailAttach(jointTaskDetailAttach, getRealPath(jointTaskDetailAttach.getPath()));
				return SUCCESS;
			}
		}
		return super.remove();
	}
	
	/**
	 * 
	 * @return
	 */
	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public Integer getJointTaskDetailId() {
		return jointTaskDetailId;
	}

	public void setJointTaskDetailId(Integer jointTaskDetailId) {
		this.jointTaskDetailId = jointTaskDetailId;
	}
	
	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
