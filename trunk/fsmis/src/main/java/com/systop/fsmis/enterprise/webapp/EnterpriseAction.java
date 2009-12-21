package com.systop.fsmis.enterprise.webapp;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
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
	 * 企业ID
	 */
	private String corpId;
		/**
	 * json返回结果
	 */
	private Map<String, String> delResult;
	
	/**
	 * 企业信息查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		items = page.getData();
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 保存企业信息，其中企业名称、地址、编号为必填
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.name",message = "请填写企业名称！"),
			@RequiredStringValidator(fieldName = "model.address",message = "请填写企业地址！"),
			@RequiredStringValidator(fieldName = "model.code",message = "请填写企业编号！")			
	},requiredFields = {@RequiredFieldValidator(fieldName = "model.dept.id",message = "请选择所属部门！")})
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
			// 当更改部门时需要重新设置部门
			Dept dept = getManager().getDao().get(Dept.class,
			    getModel().getDept().getId());
			getModel().setDept(dept);
			//getManager().getDao().clear();
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
	
	/**
	 * 删除企业信息
	 */
	@Override
	public String remove(){
		Enterprise enterprise = getManager().get(getModel().getId());
		//检查是否与事件关联
		if(enterprise.getGenericCases().size() != 0)
		{
			addActionError("该企业涉及食品安全事件，无法删除！");
			return "error";
		}
		//如果存在照片，则连照片一起删除
		String Path = getModel().getPhotoUrl();
		if(StringUtils.isNotBlank(Path)){
			getManager().remove(enterprise, getRealPath(Path));
			return SUCCESS;
		}
		return super.remove();
	}
	
	/**
	 * 删除企业照片
	 */
	public String deletePhoto() {
		logger.debug("要删除的企业ID:" + corpId);
		delResult = Collections.synchronizedMap(new HashMap<String, String>());
		// 对于编辑企业信息的情况，处理删除原照片的请求
		if (StringUtils.isNotEmpty(corpId)) {
			Enterprise enterprise = getManager().get(Integer.valueOf(corpId));
			String relativePath = enterprise.getPhotoUrl();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			// 删除数据库中企业照片的路径
			enterprise.setPhotoUrl(null);
			getManager().save(enterprise);
			delResult.put("result", "success");
		} else {
			delResult.put("result", "error");
		}
		return "jsonRst";
	}
	
	/**
	 * 查看企业信息
	 */
	public String look(){
		return "look";
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
	
	public Map<String, String> getDelResult() {
  	return delResult;
  }

	public void setDelResult(Map<String, String> delResult) {
  	this.delResult = delResult;
  }
	
	public String getCorpId() {
  	return corpId;
  }

	public void setCorpId(String corpId) {
  	this.corpId = corpId;
  }
}
