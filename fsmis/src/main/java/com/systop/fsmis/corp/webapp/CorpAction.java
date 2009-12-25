package com.systop.fsmis.corp.webapp;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.model.Corp;

/**
 * 企业信息管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CorpAction extends ExtJsCrudAction<Corp, CorpManager> {

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
	private Integer corpId;
	/**
	 * json返回结果
	 */
	private Map<String, String> delResult;
	/**
	 * 处罚记录
	 */
  private List punishRecords;
  /**
   * 处罚记录数
   */
	private Integer psRecordSize;
	
	@Autowired
	private LoginUserService loginUserService;
	
	/**
	 * 企业信息查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 保存企业信息，其中企业名称、地址、编号为必填
	 */
	@Override
	public String save() {
		try {
			if (photo != null) {
				String fileRelativePath = null;
				fileRelativePath = UpLoadUtil.doUpload(photo, photoFileName,
						FsConstants.COMPANY_PHOTOS_FOLDER, getServletContext(), true);
				logger.info("photo path:{}", fileRelativePath);
				getModel().setPhotoUrl(fileRelativePath);
	
			}
			Dept dept = loginUserService.getLoginUserDept(getRequest());
			if (getModel().getDept().getId() != null) {
			  // 当更改部门时需要重新设置部门
				dept = getManager().getDao().get(Dept.class,
				    getModel().getDept().getId());
			}
			getModel().setDept(dept);
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
		DetachedCriteria criteria = DetachedCriteria.forClass(Corp.class);
		criteria.createAlias("dept", "dept");
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				criteria.add(Restrictions.like("dept.serialNo", 
						MatchMode.START.toMatchString(dept.getSerialNo())));
			} else {
				criteria.add(Restrictions.eq("dept.id", dept.getId()));
			}
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			criteria.add(Restrictions.like("name", 
					MatchMode.ANYWHERE.toMatchString(getModel().getName())));
		}
		return setupSort(criteria);
	}
	
	/**
	 * 删除企业信息
	 */
	@Override
	public String remove(){
		Corp corp = getManager().get(getModel().getId());
		//检查是否与事件关联
		if(corp.getGenericCases().size() != 0)
		{
			addActionError("该企业涉及食品安全事件，无法删除！");
			return "error";
		}
		//如果存在照片，则连照片一起删除
		String Path = getModel().getPhotoUrl();
		if(StringUtils.isNotBlank(Path)){
			getManager().remove(corp, getRealPath(Path));
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
		if (corpId != null) {
			Corp corp = getManager().get(corpId);
			String relativePath = corp.getPhotoUrl();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			// 删除数据库中企业照片的路径
			corp.setPhotoUrl(null);
			getManager().save(corp);
			delResult.put("result", "success");
		} else {
			delResult.put("result", "error");
		}
		return "jsonRst";
	}
	
	/**
	 * 查看企业信息
	 */
	public String view() {
		punishRecords = Collections.EMPTY_LIST;
		/*
		 * 取得企业的处罚记录
		 * 处罚记录的取得依赖于其他模块的完成，暂时测试用，有待完善。
		 */
		/*punishRecords = queryPunishRecords();*/
		getRequest().setAttribute("psRecords", punishRecords);
		psRecordSize = 2; //punishRecords.size();
		return super.view();
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
	
	public Integer getCorpId() {
  	return corpId;
  }

	public void setCorpId(Integer corpId) {
  	this.corpId = corpId;
  }
	
	public List getPunishRecords() {
  	return punishRecords;
  }

	public void setPunishRecords(List punishRecords) {
  	this.punishRecords = punishRecords;
  }
	
	public Integer getPsRecordSize() {
  	return psRecordSize;
  }

	public void setPsRecordSize(Integer psRecordSize) {
  	this.psRecordSize = psRecordSize;
  }
}
