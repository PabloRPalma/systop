package com.systop.fsmis.supervisor.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
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
		HttpServletRequest request = (HttpServletRequest) getRequest();
    //得到Spring WebApplicationContext
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
        getServletContext());
    //得到Spring管理的LoginUserService
    LoginUserService loginUserService = (LoginUserService) ctx.getBean("loginUserService");
    Dept dept = loginUserService.getLoginUserDept(request);
    //显示登录用户所属部门下的信息员
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
		//根据姓名查询
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql = hql + " and s.name like ?";
			args.add("%" + getModel().getName() + "%");
		}
		//根据监管区域查询
		if (StringUtils.isNotBlank(getModel().getSuperviseRegion())){
			hql = hql + " and s.superviseRegion like ?";
			args.add("%" + getModel().getSuperviseRegion() + "%");
		}
		//根据部门查询
		if(getModel().getDept() != null){
			if (StringUtils.isNotBlank(getModel().getDept().getName())){
				hql = hql + " and s.dept.name like ?";
				args.add("%" + getModel().getDept().getName() + "%");
			}
		}
		//根据手机号查询
		if(getModel().getMobile() != null){
			if (StringUtils.isNotBlank(getModel().getMobile())){
				hql = hql + " and s.mobile like ?";
				args.add("%" + getModel().getMobile() + "%");
			}
		}
		//根据负责人查询
		if(getModel().getIsLeader() != null){
				hql = hql + " and s.isLeader = ?";
				args.add(getModel().getIsLeader());
		}
		hql = hql + " order by s.code";
		page = getManager().pageQuery(page, hql, args.toArray());
		restorePageData(page);
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
