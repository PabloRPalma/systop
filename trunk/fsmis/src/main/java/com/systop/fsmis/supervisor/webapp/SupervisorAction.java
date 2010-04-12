package com.systop.fsmis.supervisor.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Supervisor;
import com.systop.fsmis.supervisor.service.SupervisorManager;

@SuppressWarnings("serial")
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
	
	@Autowired
	private DeptManager deptManager;

	@Autowired
	private LoginUserService loginUserService;
	
	/**
	 * 地图坐标
	 */
	private String coordinate;
	/** 按姓名、监管区域、所属部门查询信息员信息*/
	@SuppressWarnings("unchecked")
	public String index(){
	//创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		getManager().pageQuery(page, setupDetachedCriteria());
		items = page.getData();
		generatMobileNum(items);
		restorePageData(page);
		return INDEX;
	}
	
  /**
   * @return DetachedCriteria 查询条件
   */
  private DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria criteria = DetachedCriteria.forClass(Supervisor.class);
    //设定用户所属部门及子部门作为查询条件
    criteria.createAlias("dept", "dept");
    Dept dept = loginUserService.getLoginUserDept(getRequest());
    if (getModel().getDept() != null && getModel().getDept().getId() != null) {
    	dept = deptManager.get(getModel().getDept().getId());
		}
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
	/**
	 * 取得信息员的手机号码
	 * @param items
	 */
	private void generatMobileNum(Collection<Supervisor> items) {
		StringBuffer nums = new StringBuffer();
		int hasNum = 0;
		int noNum = 0;
		if (items != null && items.size() > 0) {
			for (Supervisor supervisor : items) {
				if (StringUtils.isNotEmpty(supervisor.getMobile())) {
					nums.append(supervisor.getMobile()).append(";");
					hasNum++;
					if (hasNum % 5 == 0) {
						nums.append("<br/>");
					}
				} else {
					noNum++;
				}
			}
		}
		getRequest().setAttribute("mobileNums", nums.toString());
		getRequest().setAttribute("hasNum", hasNum);
		getRequest().setAttribute("noNum", noNum);
	}
	
	/**
	 * 地图标注
	 */
	public String markmap() {
		return "markmap";
	}
	
	/**
	 * 保存地图坐标信息
	 */
	public String saveMapInfo() {
		if (getModel().getId() != null ){
			Supervisor supervisor = getManager().get(getModel().getId());
			supervisor.setCoordinate(coordinate);
			getManager().save(supervisor);
		}
		return SUCCESS;
	}
	
	/**
	 * 所有监管员地图上的分布情况
	 */
	@SuppressWarnings("unchecked")
	public String mapOfSupervisors() {
		List args = new ArrayList();
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		StringBuffer hql = new StringBuffer("from Supervisor s where 1=1 ");
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				hql.append(" and s.dept.serialNo like ?");
				args.add(MatchMode.START.toMatchString(dept.getSerialNo()));
			} else {
				hql.append(" and s.dept.id = ?");
				args.add(dept.getId());
			}
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and s.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getSuperviseRegion())) {
			hql.append(" and s.superviseRegion like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getSuperviseRegion()));
		}
		if (StringUtils.isNotBlank(getModel().getMobile())) {
			hql.append(" and s.mobile like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getMobile()));
		}
		if (StringUtils.isNotBlank(getModel().getIsLeader())) {
			hql.append(" and s.isLeader = ?");
			args.add(getModel().getIsLeader());
		}		
		List<Supervisor> supervisorList = getManager().query(hql.toString(), args.toArray());
		getRequest().setAttribute("items", supervisorList);
		
		return "mapOfSupervisors";
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

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

}
