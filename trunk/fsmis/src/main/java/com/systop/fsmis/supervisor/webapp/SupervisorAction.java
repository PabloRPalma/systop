package com.systop.fsmis.supervisor.webapp;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
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
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.supervisor.service.SupervisorManager;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupervisorAction extends DefaultCrudAction<Supervisor, SupervisorManager> {

	/**
	 * 上传的信息员照片
	 */
	private File photo;	
	
	/**
	 * 照片文件名称
	 */
	private String photoFileName;
	
	/**
	 * 信息员ID
	 */
	private String spId;
	
	/**
	 * json返回结果
	 */
	private Map<String, String> delResult;
	
	/**
	 * 
	 */
	@Autowired
	private LoginUserService loginUserService;
	
	/** 按姓名、监管区域、所属部门查询信息员信息*/
	public String index(){
		indexSuperviosr();
		return INDEX;
	}
	
	/**
	 * 导出手机号码
	 */
	public String exportMobileNum(){
		indexSuperviosr();
		return "exportmobilenum";
	}
	
	/**
	 * 抽取出来的查询方法
	 */
	public void indexSuperviosr(){
		//创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, setupDetachedCriteria());
		restorePageData(page);
	}
	
  /**
   * @return DetachedCriteria 查询条件
   */
  private DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria criteria = DetachedCriteria.forClass(Supervisor.class);
    //设定用户所属部门及子部门作为查询条件
    criteria.createAlias("dept", "dept");
    Dept dept = loginUserService.getLoginUserDept(getRequest());
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				criteria.add(Restrictions.like("dept.serialNo", MatchMode.START
						.toMatchString(dept.getSerialNo())));
			} else {
				criteria.add(Restrictions.eq("dept.id", dept.getId()));
			}
		}
		//选择不为null的属性作为查询条件
		if (StringUtils.isNotBlank(getModel().getName())) {
			criteria.add(Restrictions.like("name", MatchMode.ANYWHERE
					.toMatchString(getModel().getName())));
		}
		if (StringUtils.isNotBlank(getModel().getSuperviseRegion())) {
			criteria.add(Restrictions.like("superviseRegion", MatchMode.ANYWHERE
					.toMatchString(getModel().getSuperviseRegion())));
		}
		if (StringUtils.isNotBlank(getModel().getMobile())) {
			criteria.add(Restrictions.like("mobile", MatchMode.ANYWHERE
					.toMatchString(getModel().getMobile())));
		}
		if (getModel().getDept() != null && StringUtils.isNotBlank(getModel().getDept().getName())) {
			criteria.add(Restrictions.like("dept.name", MatchMode.ANYWHERE
					.toMatchString(getModel().getDept().getName())));
		}
		if(StringUtils.isNotBlank(getModel().getIsLeader())){
			criteria.add(Restrictions.eq("isLeader", getModel().getIsLeader()));
		}
		return setupSort(criteria);
  }
  
	/**
	 * 保存信息员信息
	 */
	@Override
	public String save(){
		try{
			if(photo != null){
				String fileRelativePath = null;
				fileRelativePath = UpLoadUtil.doUpload(photo, photoFileName, FsConstants.SUPERVISOR_PHOTOS_FOLDER,
			            getServletContext(), true);
				getModel().setPhotoUrl(fileRelativePath);
			}
			Dept dept = getManager().getDao().get(Dept.class, getModel().getDept().getId());
			getModel().setDept(dept);
			getManager().getDao().clear();
			getManager().save(getModel());
			return SUCCESS;
		}catch (Exception e){
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 删除信息员信息
	 */
	@Override
	public String remove(){
		Supervisor supervisor = getManager().get(getModel().getId());
		String path = getModel().getPhotoUrl();
		//getRealPath获取路径时需要保证path为非空，如果为空则直接删除
		if(StringUtils.isBlank(path)){
			return super.remove();
		}
		getManager().remove(supervisor, getRealPath(getModel().getPhotoUrl()));
		return SUCCESS;
	}
	
	/**
	 * 删除企业照片
	 */
	public String deletePhoto() {
		logger.debug("要删除的信息员ID:" + spId);
		delResult = Collections.synchronizedMap(new HashMap<String, String>());
		// 对于编辑企业信息的情况，处理删除原照片的请求
		if (StringUtils.isNotEmpty(spId)) {
			Supervisor supervisor = getManager().get(Integer.valueOf(spId));
			String relativePath = supervisor.getPhotoUrl();
			if (StringUtils.isNotEmpty(relativePath)) {
				File file = new File(getServletContext().getRealPath(relativePath));
				if (file.exists()) {
					file.delete();
				}
			}
			// 删除数据库中企业照片的路径
			supervisor.setPhotoUrl(null);
			getManager().save(supervisor);
			delResult.put("result", "success");
		} else {
			delResult.put("result", "error");
		}
		return "jsonRst";
	}
	
	public String editNew(){
		return INPUT;
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
	
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public Map<String, String> getDelResult() {
		return delResult;
	}

	public void setDelResult(Map<String, String> delResult) {
		this.delResult = delResult;
	}
}
