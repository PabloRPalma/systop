package com.systop.fsmis.supervisor.webapp;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.systop.core.webapp.dwr.BaseDwrAjaxAction;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.supervisor.service.SupervisorManager;


@Component
public class SupervisorDwrAction extends BaseDwrAjaxAction {

	/** 监管员管理类*/
	@Autowired
	private SupervisorManager supervisorManager;

	/**
	 * 删除照片
	 * 
	 * @return
	 */
	public String deletePhoto(String spId) {
		// 对于编辑监管员信息的情况，处理删除原照片的请求
		if (StringUtils.isNotEmpty(spId)) {
			Supervisor sv = supervisorManager.get(Integer.valueOf(spId));
			String relativePath = sv.getPhotoUrl();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(
						relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			// 删除数据库中监管员照片的路径
			sv.setPhotoUrl(null);
			supervisorManager.superSave(sv);
		}
		return "success";
	}
}
