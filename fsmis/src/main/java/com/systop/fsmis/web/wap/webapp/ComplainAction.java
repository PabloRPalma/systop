package com.systop.fsmis.web.wap.webapp;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.Complain;
import com.systop.fsmis.web.wap.service.ComplainManager;

/**
 * 投诉举报WAP管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ComplainAction extends
    DefaultCrudAction<Complain, ComplainManager> {

	/** message 提示信息 */
	private String message;

	/** secureStr 身份验证码 */
	private String secureStr;

	private String reportTime;

	/**
	 * 保存举报投诉信息
	 */
	@Override
	public String save() {
		logger.info("标题为：{}", getModel().getTitle());
		if (StringUtils.isBlank(getModel().getTitle())) {
			message = "事件标题不能为空!";
			return INPUT;
		}
		if (StringUtils.isBlank(getModel().getDescn())) {
			message = "事件描述不能为空！";
			return INPUT;
		}
		if (StringUtils.isBlank(reportTime)) {
			message = "事发时间不能为空！";
			return INPUT;
		}
		if (StringUtils.length(reportTime) != 16) {
			message = "事发时间必须是16位!";
			return INPUT;
		}
		try {
			Date reportDate = DateUtils.parseDate(StringUtils.trim(reportTime),
			    new String[] { "yyyy-MM-dd HH:mm" });
			getModel().setReportTime(reportDate);
		} catch (ParseException e1) {
			message = "事发时间的格式有误（注意日期和小时之间的空格），请检查！";
			return INPUT;
		}
		if (StringUtils.isBlank(getModel().getAddr())) {
			message = "事发地点不能为空！";
			return INPUT;
		}
		if (StringUtils.isBlank(getModel().getReporter())) {
			message = "举报人不能为空！";
			return INPUT;
		}
		if (StringUtils.isBlank(getModel().getPhoneNo())) {
			message = "举报电话不能为空！";
			return INPUT;
		}
		Pattern pattern = Pattern.compile("\\d{8,20}");
		Matcher matcher = pattern
		    .matcher(StringUtils.trim(getModel().getPhoneNo()));
		if (!matcher.matches()) {
			message = "举报电话的格式有误！";
			return INPUT;
		}
		try{
			getManager().saveComplain(getModel());
		}catch(Exception e){
			message = "举报信息发送失败，请速与系统管理员联系...";
			e.printStackTrace();
			return INPUT;			
		}
		message = "举报信息发送成功，请等待接收系统返回的验证码信息!";
		
		return SUCCESS;
	}

	/**
	 * 跳转到身份验证页面
	 */
	public String identit() {
		return "identit";
	}

	/**
	 * 举报投诉身份确认
	 */
	public String identitComp() {
		message = "身份验证通过，系统已授理您提交的事件信息，" +
			 				"事件的处理结果将以短信形式反馈给您，同时感谢您为创建" +
			 				"食品安全卫生城市所做的每一件事！";
		if (StringUtils.isBlank(secureStr)) {
			message = "身份验证码不能为空！";
			return "identit";
		}
		try {
			getManager().identitComp(secureStr);
		} catch (Exception e) {
			message = e.getMessage();
			return "identit";
		}
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getSecureStr() {
		return secureStr;
	}

	public void setSecureStr(String secureStr) {
		this.secureStr = secureStr;
	}
}
