package com.systop.fsmis.sms.webapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.sms.SmsConstants;
import com.systop.fsmis.sms.SmsReceiveManager;

@Controller
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SmsReceiveAction extends
		ExtJsCrudAction<SmsReceive, SmsReceiveManager> {
	@Autowired
	private LoginUserService loginUserService; 
	@Override
	public String index() {
		if (loginUserService == null
				|| loginUserService.getLoginUser(getRequest()) == null) {
			addActionError("请先登录!");
			return INDEX;
		}
		
		StringBuffer buf = new StringBuffer("from SmsReceive sr where 1=1 ");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		List<Object> args = new ArrayList<Object>();
		buf.append("and sr.isReport = ? ");
		args.add(SmsConstants.SMS_IS_REPORT_Y);
		
		buf.append("order by sr.receiveTime desc ");
		
		page = getManager().pageQuery(page,buf.toString(),args.toArray());
		restorePageData(page);
		
		
		return INDEX;
	}
}
