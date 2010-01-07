package com.systop.fsmis.urgentcase.ucgroup.webapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.UcConstants;
import com.systop.fsmis.urgentcase.ucgroup.service.UcGroupManager;

/**
 * 应急指挥组维护action
 * 
 * @author yj
 * 
 */
@SuppressWarnings( { "serial" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UcGroupAction extends
		DefaultCrudAction<UrgentGroup, UcGroupManager> {
	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;

	/** 应急指挥类别ID */
	private Integer ucTypeId;
	/**
	 * 用户ID
	 */
	private String userIds;
	/**
	 * 页面前台使用
	 */
	private String person = "";

	/**
	 * 保存组
	 */
	@Override
	public String save() {
		if (ucTypeId != null) {
			getModel().setUrgentType(getUrgentType(ucTypeId));
			getModel().setIsPublic(FsConstants.N);
		} else {
			getModel().setIsPublic(FsConstants.Y);
		}
		try {
			Dept dept = loginUserService.getLoginUserCounty(getRequest());
			if (dept == null) {
				addActionError("当前用户部门为空");
			}
			getModel().setIsOriginal(UcConstants.GROUP_ORIGINAL_YES);
			getModel().setCounty(dept);
			getManager().save(getModel());
			// 设置组和用户多对多关系
			getManager().setUserUrgentGroup(userIds, getModel());
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * 组查询列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer(
				"from UrgentGroup ug where ug.isOriginal = ? ");
		List args = new ArrayList();
		args.add(UcConstants.GROUP_ORIGINAL_YES);
		Dept dept = loginUserService.getLoginUserCounty(getRequest());
		if (dept != null) {
			sql.append(" and ug.county.id = ? and ( ");
			args.add(dept.getId());
			if (ucTypeId != null) {
				sql.append(" ug.urgentType.id = ? or ");
				args.add(ucTypeId);
				// 列表页面显示类别信息使用
				getRequest().setAttribute("ucType", getUrgentType(ucTypeId));
				getRequest().setAttribute("msg",
						getManager().getUcGroupName(getUrgentType(ucTypeId)));
			}
			sql.append(" ug.urgentType.id is null) order by ug.isPublic desc");
			page = getManager().pageQuery(page, sql.toString(), args.toArray());
			restorePageData(page);
		}
		return INDEX;
	}

	/**
	 * 编辑页面显示操作人员
	 */
	public String edit() {
		Set<User> userSet = getModel().getUsers();
		int length = 0;
		for (User u : userSet) {
			if (length == userSet.size() - 1) {
				person += u.getName();
				break;
			}
			person += u.getName() + ",";
			length++;
		}
		return INPUT;
	}

	public String remove() {
		getManager().setUserUrgentGroup(null, getModel());
		getManager().remove(getModel());
		return SUCCESS;
	}

	/**
	 * 根据id获得应急类别
	 * 
	 * @param id
	 * @return
	 */
	private UrgentType getUrgentType(Integer id) {
		return getManager().getDao().get(UrgentType.class, id);
	}

	@SuppressWarnings("unchecked")
	public Map getIsPublicList() {
		return FsConstants.YN_MAP;
	}

	@SuppressWarnings("unchecked")
	public Map getSortMap() {
		Map SortMap = new LinkedHashMap();
		return SortMap;
	}

	@SuppressWarnings("unchecked")
	public Map getCategoryMap() {
		Map categoryMap = new LinkedHashMap();
		if (ucTypeId != null) {
			categoryMap.put(UcConstants.AFTER_HANDLE, "善后处理");
			categoryMap.put(UcConstants.ACCIDENT_HANDLE, "事故调查处理");
		} else {
			categoryMap.put(UcConstants.LEADERSHIP, "指挥部");
			categoryMap.put(UcConstants.OFFICE, "办公室");
			categoryMap.put(UcConstants.DEFEND, "警戒保卫");
			categoryMap.put(UcConstants.MEDICAL_RESCUE, "医疗救护");
			categoryMap.put(UcConstants.REAR_SERVICE, "后勤保障");
			categoryMap.put(UcConstants.NEWS_REPORT, "新闻报道");
			categoryMap.put(UcConstants.EXPERT_TECHNOLOGY, "专家技术");
			categoryMap.put(UcConstants.RECEIVE, "接待");
		}

		return categoryMap;
	}

	public Integer getUcTypeId() {
		return ucTypeId;
	}

	public void setUcTypeId(Integer ucTypeId) {
		this.ucTypeId = ucTypeId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
}
