package com.systop.fsmis.fscase.sendtype.webapp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("loginUserService")
	private LoginUserService userService;

	@Override
	public String edit() {
		Dept county = userService.getLoginUserCounty(this.getRequest());

		if (county == null) {
			return ERROR;
		}
		if (getModel().getSendType() == null
				&& getModel().getSendType().getId() == null) {
			return ERROR;
		}
		Integer stId = getModel().getSendType().getId();
		String hql = "from CountySendType cst where cst.sendType.id = ? and cst.county.id = ?";
		List<CountySendType> countySendTypes = getManager().query(hql,
				new Object[] { stId, county.getId() });
		CountySendType countySendType = null;
		if (CollectionUtils.isEmpty(countySendTypes)){
			countySendType = new CountySendType();
			SendType sendType = getManager().getDao().get(SendType.class, stId);
			countySendType.setSendType(sendType);
			countySendType.setCounty(county);
		}else{
			countySendType = countySendTypes.get(0);
		}
		this.setModel(countySendType);

		return super.edit();
	}

}
