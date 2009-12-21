package com.systop.fsmis.supervisor.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.supervisor.service.SupervisorManager;

@SuppressWarnings({ "unchecked", "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupervisorAction extends DefaultCrudAction<Supervisor, SupervisorManager> {

	/**
	 * 上传的监管员照片
	 */
	private File photo;	
	
	/**
	 * 照片文件名称
	 */
	private String photoFileName;
	
	/** 按姓名、监管区域、所属部门查询监管员信息*/
	@SuppressWarnings("unchecked")
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
		//Page page = PageUtil.buildPage(getPageNo(), getPageSize());
		HttpServletRequest request = (HttpServletRequest) getRequest();
    //得到Spring WebApplicationContext
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        getServletContext());
    //得到Spring管理的LoginUserService
    LoginUserService loginUserService = (LoginUserService) ctx.getBean("loginUserService");
    Dept dept = loginUserService.getLoginUserDept(request);
    //显示登录用户所属部门下的监管员
		String hql = "";
		if (dept != null) {
			//顶级部门则查询全部
			if (dept.getName().equals(DeptConstants.TOP_DEPT_NAME)) {
				hql = hql + "from Supervisor s where 1=1";
			} else if (dept.getChildDepts().size() > 0) {//非顶级部门则查询本部门和所有下属部门
				hql = hql + "from Supervisor s where s.dept.id = "
					+ Integer.toString(dept.getId());
				for (Dept childen : dept.getChildDepts()) {
					hql = hql + " or s.dept.id = " + Integer.toString(childen.getId());
				}
			} else {
				hql = hql + "from Supervisor s where s.dept.id = "
						+ Integer.toString(dept.getId());
			}
		} else {
			hql = hql + "from Supervisor s where 1=1";
		}
		List args = new ArrayList();
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql = hql + " and s.name like ?";
			args.add("%" + getModel().getName() + "%");
		}
		if (StringUtils.isNotBlank(getModel().getSuperviseRegion())){
			hql = hql + " and s.superviseRegion like ?";
			args.add("%" + getModel().getSuperviseRegion() + "%");
		}
		if(getModel().getDept() != null){
			if (StringUtils.isNotBlank(getModel().getDept().getName())){
				hql = hql + " and s.dept.name like ?";
				args.add("%" + getModel().getDept().getName() + "%");
			}
		}
		if(getModel().getMobile() != null){
			if (StringUtils.isNotBlank(getModel().getMobile())){
				hql = hql + " and s.mobile like ?";
				args.add("%" + getModel().getMobile() + "%");
			}
		}
		if(getModel().getIsLeader() != null){
				hql = hql + " and s.isLeader = ?";
				args.add(getModel().getIsLeader());
		}
		hql = hql + " order by s.code";
		//page = getManager().pageQuery(page, hql, args.toArray());
		//items = page.getData();
		//restorePageData(page);
		items = getManager().query(hql, args.toArray());
	}
	
	/**
	 * 保存监管员信息
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.name",message = "请填写姓名！"),
			@RequiredStringValidator(fieldName = "model.code",message = "请填写监管员编号！")},
				regexFields = {
			@RegexFieldValidator(expression = "\\d{11,11}",type = ValidatorType.FIELD,fieldName = "model.mobile",message = "输入的手机号格式有误，请检查！"),
			@RegexFieldValidator(expression = "^(\\d{3,5}-)?\\d{7,20}$",type = ValidatorType.FIELD,fieldName = "model.phone",message = "输入的固话格式有误，请检查！")	})
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
	 * 删除监管员信息
	 */
	@Override
	public String remove(){
		Supervisor supervisor = getManager().get(getModel().getId());
		if(supervisor.getGenericCases().size() != 0){
			addActionError("无法删除该监管员，该监管员已经与某事件关联！");
			return "error";
		}
		String path = getModel().getPhotoUrl();
		if(StringUtils.isBlank(path)){
			return super.remove();
		}
		getManager().remove(supervisor, getRealPath(getModel().getPhotoUrl()));
		return SUCCESS;
	}
	
	/**
	 * 查看监管员信息
	 */
	public String look(){
		return "look";
	}
	
	public String editNew(){
		return INPUT;
	}
	
	public Map getGenderMap(){
		return FsConstants.SEX_MAP;
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
