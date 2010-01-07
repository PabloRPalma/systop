package com.systop.fsmis.office.notice.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Notice;
import com.systop.fsmis.model.ReceiveRecord;
import com.systop.fsmis.office.notice.NoticeConstants;
import com.systop.fsmis.office.notice.service.NoticeManager;
import com.systop.fsmis.office.notice.service.ReceiveRecordManager;

/**
 * 通知管理的struts2 Action。
 * 
 * @author ZW
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NoticeAction extends ExtJsCrudAction<Notice, NoticeManager> {

	/** 附件存放路径 */
	private static final String NOTICE_ATT_FOLDER = FsConstants.NOTICE_ATT_FOLDER;

	/** 附件 */
	private File attachment;

	/** 附件保存后的名称 */
	private String attachmentFileName;

	/** 所有部门ID集合 */

	private List<Integer> deptIds = new ArrayList<Integer>();

	/** 用于操作各部门通知记录 */
	@Autowired
	private ReceiveRecordManager receiveRecordManager;

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 按通知名称查询通知
	 */
	@Override
	public String index() {
		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 设置查询条件
	 * 
	 * @return
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Notice.class);
		criteria.createAlias("pubDept", "dept");
		if (StringUtils.isNotBlank(getModel().getTitle())) {
			criteria.add(Restrictions.like("title", MatchMode.ANYWHERE
					.toMatchString(getModel().getTitle())));
		}
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if(dept != null) {
			criteria.add(Restrictions.eq("pubDept.id", dept.getId()));
		}
		return criteria;
	}

	/**
	 * 保存通知
	 */
	@Override
	public String save() {
		User user = loginUserService.getLoginUser(getRequest());
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (user == null) {
			addActionError("未登录，请登录后访问本页面。");
			return INPUT;
		}
		if (dept == null) {
			addActionError("未找到登录用户的所在部门，请使用正确用户访问本页面。");
			return INPUT;
		}
		if (CollectionUtils.isEmpty(deptIds) && getModel().getId() == null)  {
			addActionError("请选择接收部门。");
			return INPUT;
		}
		if (StringUtils.isBlank(getModel().getTitle())) {
			addActionError("请输入标题。");
			return INPUT;
		}
		getModel().setPublisher(user);
		getModel().setPubDept(dept);
		getModel().setCreateTime(new Date());
		try {
			if (attachment != null) {
				// 检查文件大小是否符合
				if (attachment.length() > NoticeConstants.UPLOAD_ALLOWED_FILE_SIZE) {
					addActionError("上传文件太大！");
					return INPUT;
				}
				// 检查文件类型是否符合
				String extension = attachmentFileName.substring(attachmentFileName
						.lastIndexOf(".") + 1);
				boolean flag = false;
				for (String extAllowed : NoticeConstants.UPLOAD_ALLOWED_FILE_TYPES) {
					if (StringUtils.equalsIgnoreCase(extension, extAllowed)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					addActionError("未正确选择上传文件类型，请重新选择！");
					return INPUT;
				}
				getModel().setAtt(
						UpLoadUtil.doUpload(attachment, attachmentFileName,
								NOTICE_ATT_FOLDER, getServletContext()));
			}
			getManager().saveDeptRecord(getModel(), deptIds);

		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * 查看通知内容
	 */
	public String view() {
		return "view";
	}

	/**
	 * 删除且级联删除发送各部门通知
	 */
	public String remove() {
		Notice notice = getManager().get(getModel().getId());
		Set<ReceiveRecord> receiveRecords = notice.getRecRecordses();
		if (CollectionUtils.isNotEmpty(receiveRecords)) {
			for (ReceiveRecord r : receiveRecords) {
				receiveRecordManager.remove(r);
			}
		}
		String path = getRealPath(notice.getAtt());
		if(StringUtils.isNotBlank(path)) {
			getManager().remove(getModel(), path);
			return SUCCESS;
		}
		return super.remove();
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

	public List<Integer> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Integer> deptIds) {
		this.deptIds = deptIds;
	}

}
