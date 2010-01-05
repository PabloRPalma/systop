package com.systop.fsmis.office.message.webapp;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.Message;
import com.systop.fsmis.office.message.service.MessageManager;

/**
 * 内部消息管理Action
 * 
 * @author ZW
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MessageAction extends ExtJsCrudAction<Message, MessageManager> {

	/**
	 * 发信人ID
	 */
	private Integer senderId;

	/**
	 * 起始时间
	 */
	private String createTimeBegin;

	/**
	 * 起始时间
	 */
	private String createTimeEnd;
	
	/**
	 * 用户ID
	 */
	private String userId;

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 查询已接收的消息
	 */
	public String received() {
		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteriaReceived());
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 查询已接收的消息
	 */
	public String sended() {
		// 创建分页查询的Page对象
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, setupDetachedCriteriaSended());
		restorePageData(page);

		return "sended";
	}

	/**
	 * 设置接收查询条件
	 * 
	 * @return
	 */
	private DetachedCriteria setupDetachedCriteriaReceived() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
		criteria.createAlias("receiver", "user");
		User user = loginUserService.getLoginUser(getRequest());
		if (user != null) {
			criteria.add(Restrictions.eq("receiver.id", user.getId()));
		}
		if (StringUtils.isNotBlank(createTimeBegin)
				&& StringUtils.isNotBlank(createTimeEnd)) {
			try {
				criteria.add(Restrictions.between("createTime", DateUtils
						.parseDate(createTimeBegin,
								new String[] { "yyyy-MM-dd HH:mm:ss" }),
						DateUtils.parseDate(createTimeEnd,
								new String[] { "yyyy-MM-dd HH:mm:ss" })));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		}

		if (senderId != null) {
			criteria.add(Restrictions.eq("sender.id", senderId));
		}
		return criteria;
	}

	/**
	 * 设置接收查询条件
	 * 
	 * @return
	 */
	private DetachedCriteria setupDetachedCriteriaSended() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
		criteria.createAlias("sender", "user");
		User user = loginUserService.getLoginUser(getRequest());
		if (user != null) {
			criteria.add(Restrictions.eq("sender.id", user.getId()));
		}
		if (StringUtils.isNotBlank(createTimeBegin)
				&& StringUtils.isNotBlank(createTimeEnd)) {
			try {
				criteria.add(Restrictions.between("receiveTime", DateUtils
						.parseDate(createTimeBegin,
								new String[] { "yyyy-MM-dd HH:mm:ss" }),
						DateUtils.parseDate(createTimeEnd,
								new String[] { "yyyy-MM-dd HH:mm:ss" })));
			} catch (ParseException e) {
				logger.debug(e.getMessage());
			}
		}

		if (senderId != null) {
			criteria.add(Restrictions.eq("receiver.id", senderId));
		}
		return criteria;
	}

	/**
	 * 保存内部 信息
	 */
	public String save() {
		User user = loginUserService.getLoginUser(getRequest());
		if (user == null) {
			addActionError("无法发送，请先登录！");
			return INPUT;
		} 
		if(userId == null) {
			addActionError("无法发送，请选择收信人！");
			return INPUT;
		}
		logger.info("UserId: {} ",  userId);
		String [] personId = userId.split(",");
		for (int i = 0; i < personId.length; i++) {
			logger.info("PersonId: {} ",  personId[i]);
			getModel().setSender(user);
			User receiver = getManager().getDao().get(User.class, Integer.valueOf(personId[i]));
			getModel().setReceiver(receiver);
			getModel().setCreateTime(new Date());
			getModel().setIsNew(FsConstants.Y);
			getManager().save(getModel());
		}
		return SUCCESS;
	}

	/**
	 * 查看内部消息
	 */
	public String view() {
		getModel().setIsNew(FsConstants.N);
		getModel().setReceiveTime(new Date());
		getManager().save(getModel());
		return VIEW;
	}

	/**
	 * 回复内部消息
	 */
	public String reply() {
		getModel().setReceiveTime(new Date());
		getModel().setIsNew(FsConstants.N);
		getManager().save(getModel());
		getModel().setReceiver(getModel().getSender());
		getModel().setContent("");
		return "reply";
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
