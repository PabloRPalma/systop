package com.systop.fsmis.fscase.jointtask.webapp;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.fscase.jointtask.service.JointTaskAttachManager;
import com.systop.fsmis.model.JointTaskAttach;

/**
 * 联合整治任务附件管理Action
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
	
}
