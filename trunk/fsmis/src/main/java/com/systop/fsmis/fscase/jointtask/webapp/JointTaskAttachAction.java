package com.systop.fsmis.fscase.jointtask.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.systop.fsmis.fscase.jointtask.service.JointTaskAttachManager;
import com.systop.fsmis.fscase.jointtask.service.JointTaskManager;
import com.systop.fsmis.model.JointTask;
import com.systop.fsmis.model.JointTaskAttach;

/**
 * 针对风险评估实体选择专家组成员
 * @author ShangHua
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JointTaskAttachAction extends
		ExtJsCrudAction<JointTaskAttach, JointTaskAttachManager> {
	/**
	 * 附件Id
	 */
	private Integer attachId;
	
	/**
	 * 联合整治任务Id
	 */
	private Integer jointTaskId;
	
	/**
	 * JSON返回结果
	 */
	private Map<String, String> delResult;
	
	/**
	 * 上传的风险评估文件
	 */
	private File attachment;

	/**
	 * 上传的风险评估文件名称
	 */
	private String attachmentFileName;

	/**
	 * 错误提示信息
	 */
	private List<String> errorMsg;
	
	/**
	 * 联合整治任务
	 */
	@Autowired
	private JointTaskManager jointTaskManager;
	
	/**
	 * 重写父类的index方法，实现分页检索任务附件信息
	 */
	@Override
	public String index() {	
		if (jointTaskId != null) {
			Page page = PageUtil.getPage(getPageNo(), getPageSize());
			String hql = "from JointTaskAttach jta where jta.jointTask.id = ?";
			getManager().pageQuery(page, hql, jointTaskId);
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
						FsConstants.ASSESSMENT_ATT_FOLDER, getServletContext(), true);
				// 相对路径，可用于下载地址
				JointTaskAttach jointTaskAttach = new JointTaskAttach();
				JointTask jointTask = jointTaskManager.get(jointTaskId);
				jointTaskAttach.setPath(fileRelativePath);
				jointTaskAttach.setTitle(attachmentFileName);
				jointTaskAttach.setJointTask(jointTask);
				getManager().save(jointTaskAttach);
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
		JointTaskAttach jointTaskAttach = getManager().get(getModel().getId());
		if (jointTaskAttach != null) {
			String path = jointTaskAttach.getPath();
			if (StringUtils.isNotBlank(path)) {
				getManager()
						.removeAttach(jointTaskAttach, getRealPath(jointTaskAttach.getPath()));
				return SUCCESS;
			}
		}
		return super.remove();
	}
	
	/**
	 * AJAX方式删除附件
	 */
	public String deleteAttach() {
		delResult = Collections.synchronizedMap(new HashMap<String, String>());
		if (attachId != null) {
			JointTaskAttach jointTaskAttach = getManager().get(attachId);
			String relativePath = jointTaskAttach.getPath();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			getManager().remove(jointTaskAttach);
			delResult.put("result", "success");
		} else {
			delResult.put("result", "error");
		}
		return "jsonRst";
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

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public Map<String, String> getDelResult() {
		return delResult;
	}

	public void setDelResult(Map<String, String> delResult) {
		this.delResult = delResult;
	}

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

	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
