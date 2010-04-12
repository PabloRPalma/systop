package com.systop.fsmis.corp.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.core.webapp.upload.UpLoadUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.corp.service.CorpManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.FsCase;

/**
 * 企业信息管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
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
	 * 标注返回结果
	 */
	private Map<String, String> markResult;

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
						FsConstants.COMPANY_PHOTOS_FOLDER, getServletContext(),
						true);
				logger.info("photo path:{}", fileRelativePath);
				getModel().setPhotoUrl(fileRelativePath);

			}
			Dept dept = loginUserService.getLoginUserCounty(getRequest());
			if (getModel().getDept().getId() != null) {
				// 当更改部门时需要重新设置部门
				dept = getManager().getDao().get(Dept.class,
						getModel().getDept().getId());
			}
			getModel().setDept(dept);
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
		DetachedCriteria criteria = DetachedCriteria.forClass(Corp.class);
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		if (dept != null) {
			criteria.createAlias("dept", "dept");
			if (dept.getChildDepts().size() > 0) {
				criteria.add(Restrictions.like("dept.serialNo", MatchMode.START
						.toMatchString(dept.getSerialNo())));
			} else {
				criteria.add(Restrictions.eq("dept.id", dept.getId()));
			}
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			criteria.add(Restrictions.like("name", MatchMode.ANYWHERE
					.toMatchString(getModel().getName())));
		}
		if (StringUtils.isNotBlank(getModel().getLegalPerson())) {
			criteria.add(Restrictions.like("legalPerson", MatchMode.ANYWHERE
					.toMatchString(getModel().getLegalPerson())));
		}
		if (StringUtils.isNotBlank(getModel().getAddress())) {
			criteria.add(Restrictions.like("address", MatchMode.ANYWHERE
					.toMatchString(getModel().getAddress())));
		}
		return setupSort(criteria);
	}

	/**
	 * 删除企业信息
	 */
	@Override
	public String remove() {
		Corp corp = getManager().get(getModel().getId());
		// 检查是否与事件关联
		if (corp.getFsCases().size() != 0) {
			addActionError("该企业涉及食品安全事件，无法删除！");
			return INDEX;
		}
		// 如果存在照片，则连照片一起删除
		String Path = getModel().getPhotoUrl();
		if (StringUtils.isNotBlank(Path)) {
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
				File file = new File(getServletContext().getRealPath(
						relativePath));
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
	 * 取得企业的处罚记录
	 */
	public String getPunishRecordsOfCorp() {
		page = PageUtil.getPage(getPageNo(), getPageSize());
		List mapFsCases = new ArrayList();
		String corpId = getRequest().getParameter("corpId");
		if (StringUtils.isNotBlank(corpId) && StringUtils.isNumeric(corpId)) {
			Corp corp = getManager().get(Integer.valueOf(corpId));
			FsCase[] cases = new FsCase[]{};
			Set<FsCase> fsCases = corp.getFsCases();
			cases = fsCases.toArray(cases);
			for (FsCase fsCase : cases) {
				Map mapFsCase = ReflectUtil.toMap(fsCase, new String[] { "id",
	    		   "title","address"}, true);
				mapFsCase.put("caseTypeName", fsCase.getCaseType().getName());
				mapFsCase.put("caseTime", convertDate2String(fsCase.getCaseTime()));
				mapFsCases.add(mapFsCase);
			}
		}
		page.setData(mapFsCases);
		
		return JSON;
	}
	
	/**
	 * 标注企业地图信息
	 */
	public String mark() {
		edit();
		return "markmap";
	}
	
	/**
	 * 保存企业地图标注信息
	 */
	public String saveMapInfo() {
		markResult = Collections.synchronizedMap(new HashMap<String, String>());
		String corpId = getRequest().getParameter("corpId");
		String coordinate = getRequest().getParameter("coordinate");
		if (StringUtils.isNotBlank(corpId) && StringUtils.isNumeric(corpId)) {
			Corp corp = getManager().get(Integer.valueOf(corpId));
			corp.setCoordinate(coordinate);
			getManager().save(corp);
			markResult.put("result", "success");
		} else {
			markResult.put("result", "error");
		}
		
		return "markRst";
	}
	
	/**
	 * 查看企业在地图上的分布情况
	 */
	public String mapOfCorps() {
		List args = new ArrayList();
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		StringBuffer hql = new StringBuffer("from Corp cp where 1=1 ");
		if (dept != null) {
			if (dept.getChildDepts().size() > 0) {
				hql.append(" and cp.dept.serialNo like ?");
				args.add(MatchMode.START.toMatchString(dept.getSerialNo()));
			} else {
				hql.append(" and cp.dept.id = ?");
				args.add(dept.getId());
			}
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and cp.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getLegalPerson())) {
			hql.append(" and cp.legalPerson like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getLegalPerson()));
		}
		if (StringUtils.isNotBlank(getModel().getAddress())) {
			hql.append(" and cp.address like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getAddress()));
		}
		List<Corp> corpList = getManager().query(hql.toString(), args.toArray());
		getRequest().setAttribute("items", corpList);
		
		return "mapOfCorps";
	}
	
	/**
	 * 将日期转换成字符串
	 * @param date
	 */
	private String convertDate2String(Date date) {
    if (date != null) {
      return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm");
    }
    return "";
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
}
