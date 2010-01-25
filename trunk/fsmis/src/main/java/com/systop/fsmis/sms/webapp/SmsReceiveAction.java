package com.systop.fsmis.sms.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.model.SmsReceive;
import com.systop.fsmis.sms.SmsConstants;
import com.systop.fsmis.sms.SmsReceiveManager;

/**
 * 短息接收管理的struts2 Action。
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SmsReceiveAction extends ExtJsCrudAction<SmsReceive, SmsReceiveManager> {
	
	@Autowired
	private LoginUserService loginUserService; 
	
	/**
	 * 短信接收列表
	 * @author DU
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer hql = new StringBuffer("from SmsReceive sr where 1=1 ");
		List args = new ArrayList();
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			hql.append(" and sr.county.id = ?");
			args.add(county.getId());
		}
		if (StringUtils.isNotBlank(getModel().getMobileNum())) {
			hql.append(" and sr.mobileNum like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getMobileNum()));
		}
		if (StringUtils.isNotBlank(getModel().getIsNew())) {
			hql.append(" and sr.isNew = ?");
			args.add(getModel().getIsNew());
		}
		if (StringUtils.isNotBlank(getModel().getIsReport())) {
			hql.append(" and sr.isReport = ?");
			args.add(getModel().getIsReport());
		}
		if (StringUtils.isNotBlank(getModel().getIsVerify())) {
			hql.append(" and sr.isVerify = ?");
			args.add(getModel().getIsVerify());
		}
		hql.append(" order by sr.receiveTime desc ");
		
		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		restorePageData(page);
		
		return INDEX;
	}
	/**
	 * 查看短信方法,更改短信的isNew状态为"0",即"已读",不是新短信
	 */
	public String view(){
	  getModel().setIsNew(SmsConstants.N);
	  getManager().save(getModel());
	  
	  return VIEW;
	}
	/**
	 * 根据事件编号查询反馈消息
	 * 
	 */
	public String checkedMsgIndex(){
		Integer caseId = getModel().getFsCase().getId();
		Page page = PageUtil.getPage(getPageNo(),getPageSize());
		String hql = "from SmsReceive s where s.isVerify = 1 and s.fsCase.id = ? order by s.receiveTime desc";
		getManager().pageQuery(page, hql, new Object[]{caseId});
		//读取单体事件的短息接收信息
		items = page.getData();
		restorePageData(page);		
		return "backMsgView";
	}
}
