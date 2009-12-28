package com.systop.fsmis.fscase.sendtype.webapp;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.fscase.sendtype.service.CountySendTypeManager;
import com.systop.fsmis.model.CountySendType;
import com.systop.fsmis.model.SendType;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountySendTypeAction extends
		DefaultCrudAction<CountySendType, CountySendTypeManager> {

	@Autowired
	private LoginUserService loginUserService;

	private String[] deptIds;

	/**
	 * 获得要编辑的部门派遣配置。简单说明一下,如果是第一次编辑会新建一条与派遣类别和部门关联好的数据并将其赋值给model。
	 */
	@Override
	public String edit() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		logger.debug("county is {}", county);
		if (county == null) {
			addActionError("无法获得正确类型的部门,或者您未登录.");
			return ERROR;
		}
		if (getModel().getSendType() == null
				&& getModel().getSendType().getId() == null) {
			addActionError("没有选择有效的派遣类别,请选择正确的派遣类别.");
			return ERROR;
		}
		Integer stId = getModel().getSendType().getId();
		String hql = "from CountySendType cst where cst.sendType.id = ? and cst.county.id = ?";
		// 查询部门派遣配置
		CountySendType countySendType = getManager().findObject(hql,
				new Object[] { stId, county.getId() });

		if (countySendType == null) { // 如果本部门对应的派遣类别没有配置记录，新增加一条
			countySendType = new CountySendType();
			SendType sendType = getManager().getDao().get(SendType.class, stId);
			countySendType.setSendType(sendType);
			countySendType.setCounty(county);
			getManager().save(countySendType);
		}
		setModel(countySendType);
		return INPUT;
	}

	/**
	 * 保存部门派遣环节执法部门配置
	 */
	@Override
	public String save() {
			String generalDeptIds = "";
			if (!ArrayUtils.isEmpty(deptIds )) {
				for (String id : deptIds) {
					generalDeptIds += id + ",";
				}
			}
			getModel().setGeneralDept(generalDeptIds);
			addActionMessage("数据保存成功");
			return super.save();
	}

	public String[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String[] deptIds) {
		this.deptIds = deptIds;
	}

	public static void main(String[] args) {
		String[] aa = new String[] {};
		System.out.println(org.apache.commons.lang.ArrayUtils.isEmpty(aa));
	}
}
