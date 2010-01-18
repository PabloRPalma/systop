package com.systop.fsmis.sms.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.systop.fsmis.model.SmsSend;
import com.systop.fsmis.sms.SmsConstants;
import com.systop.fsmis.sms.SmsSendManager;

/**
 * 短息管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SmsSendAction extends ExtJsCrudAction<SmsSend, SmsSendManager> {

	@Autowired
	private LoginUserService loginUserService;

	private String smsSendId;
	
	private Map smsSendInfo;
	
	/**
	 * 短信发送
	 */
	public String save(){
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		String mobiles = getRequest().getParameter("mobiles");
		String content = getRequest().getParameter("content");
		String[] mobNums = new String[]{};
		if (StringUtils.isNotEmpty(mobiles)) {
			mobNums = mobiles.split(";");
			for (String num : mobNums){
				SmsSend smsSend = new SmsSend();
				//新短信
				smsSend.setIsNew(SmsConstants.SMS_SMS_SEND_IS_NEW);
				smsSend.setMobileNum(num);
				smsSend.setContent(content);
				smsSend.setCreateTime(new Date());
				smsSend.setCounty(county);
				//mesSendManager.statisticsSendConnt(num);
				getManager().save(smsSend);
			}
		}
				
		return SUCCESS;
	}
	
	/**
	 * 短信发送列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer hql = new StringBuffer("from SmsSend s where 1=1");
		List args = new ArrayList();
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			hql.append(" and s.county.id = ?");
			args.add(county.getId());
		}
		if (StringUtils.isNotBlank(getModel().getName())) {
			hql.append(" and s.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (StringUtils.isNotBlank(getModel().getMobileNum())) {
			hql.append(" and s.mobileNum like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getMobileNum()));
		}
		if (StringUtils.isNotBlank(getModel().getIsNew())) {
			hql.append(" and s.isNew = ?");
			args.add(getModel().getIsNew());
		}
		hql.append(" order by s.isNew desc,s.createTime desc");
		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		restorePageData(page);
		
		return INDEX;
	}
	
	/**
	 * 查看发送的短信内容
	 */
	public String viewSmsSendInfo() {
		if (StringUtils.isNotBlank(smsSendId)) {
			smsSendInfo = getManager().getSmsMapById(smsSendId);;
		}
  	return "jsonRst";
	}
	
	public String getSmsSendId() {
  	return smsSendId;
  }

	public void setSmsSendId(String smsSendId) {
  	this.smsSendId = smsSendId;
  }

	public Map getSmsSendInfo() {
  	return smsSendInfo;
  }

	public void setSmsSendInfo(Map smsSendInfo) {
  	this.smsSendInfo = smsSendInfo;
  }
}
