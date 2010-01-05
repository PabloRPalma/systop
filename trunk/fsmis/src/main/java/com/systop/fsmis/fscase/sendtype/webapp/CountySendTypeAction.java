package com.systop.fsmis.fscase.sendtype.webapp;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.CountySendTypeManager;
import com.systop.fsmis.model.CountySendType;
import com.systop.fsmis.model.SendType;

/**
 * CountySendTypeAction 各区县派遣类别的具体配置
 * 
 * @author Lunch
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountySendTypeAction extends
		DefaultCrudAction<CountySendType, CountySendTypeManager> {

	@Autowired
	private LoginUserService loginUserService;

	/** 被选择的部门ID */
	private String[] deptIds;

	/** 用来存当前登录区县(市) */
	private Dept county;

	/**
	 * 获得要编辑的部门派遣配置。简单说明一下,如果是第一次编辑会新建一条与派遣类别和部门关联好的数据并将其赋值给model。
	 */
	@Override
	public String edit() {
		if (getModel().getSendType() == null
				&& getModel().getSendType().getId() == null) {// 判断sendTypeId是否为空
			addActionError("没有选择有效的派遣类别,请选择正确的派遣类别.");
			return ERROR;
		}
		try {
			String hql = "from CountySendType cst where cst.sendType.id = ? and cst.county.id = ?";
			Integer sendTypeId = getModel().getSendType().getId();
			// 查询部门派遣配置
			CountySendType cst = getManager().findObject(hql,
					new Object[] { sendTypeId, getCounty().getId() });

			if (cst == null) { // 如果本部门对应的派遣类别没有配置记录，新增加一条
				cst = new CountySendType();
				// 查询stId对应的sendType
				SendType sendType = getManager().getDao().get(SendType.class,
						sendTypeId);
				cst.setSendType(sendType);
				cst.setCounty(getCounty());
				getManager().save(cst);
			}
			setModel(cst);
			return INPUT;
		} catch (ApplicationException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 保存部门派遣环节执法部门配置
	 */
	@Override
	public String save() {
		try {
			// 判断当前登录区县和Model所属的区县是否一致h
			if (getCounty().getId().equals(getModel().getCounty().getId())) {
				String generalDeptIds = "";
				if (!ArrayUtils.isEmpty(deptIds)) {
					for (String id : deptIds) {
						generalDeptIds += id + ",";
					}
				}
				getModel().setGeneralDept(generalDeptIds);
				getManager().save(getModel());
				addActionMessage("数据保存成功");
				return SUCCESS;
			} else {
				addActionError("当前登录部门和该配置所属部门不一致.");
				return ERROR;
			}
		} catch (ApplicationException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 获得当前登录用户所属区县(市)
	 */
	private Dept getCounty() {
		if (county == null) {
			county = loginUserService.getLoginUserCounty(getRequest());
		}
		if (county == null) {
			throw new ApplicationException("没有有效county部门");
		}
		return county;
	}

	public String[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String[] deptIds) {
		this.deptIds = deptIds;
	}
}
