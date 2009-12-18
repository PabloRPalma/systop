package com.systop.fsmis.enterprise.webapp;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.enterprise.EnterpriseConstants;
import com.systop.fsmis.enterprise.service.EnterpriseManager;
import com.systop.fsmis.model.Enterprise;

/**
 * 企业信息管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EnterpriseAction extends ExtJsCrudAction<Enterprise, EnterpriseManager> {

	/** 
	 * 企业照片
	 */
	private File photo;
	
	/** 
	 * 照片存储名称
	 */
	private String photoFileName;
	
	/**
	 * 企业信息查询列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 保存企业信息，其中公司名称、地址、编号为必填
	 */
	@Override
	public String save() {
		try {
			if (photo != null) {
				String fileRelativePath = null;
				fileRelativePath = UpLoadUtil.doUpload(photo, photoFileName,
						EnterpriseConstants.COMPANY_PHOTOS_FOLDER, getServletContext(), true);
				logger.debug("photo path:" + fileRelativePath);
				getModel().setPhotoUrl(fileRelativePath);
			}
			/*// 当更改部门时需要重新设置部门
			Dept dept = getManager().getDao().get(Dept.class,
			    getModel().getDept().getId());
			getModel().setDept(dept);*/
			getManager().getDao().clear();
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 设定查询条件
	 */
	private DetachedCriteria setupDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Enterprise.class);
		if (StringUtils.isNotBlank(getModel().getName())) {
			criteria.add(Restrictions.like("name", "%" + getModel().getName() + "%"));
		}
		return setupSort(criteria);
	}
	
	public File getPhoto() {
  	return photo;
  }

	public void setPhoto(File photo) {
  	this.photo = photo;
  }

	public String getPhotoFileName() {
  	return photoFileName;
  }

	public void setPhotoFileName(String photoFileName) {
  	this.photoFileName = photoFileName;
  }
}
