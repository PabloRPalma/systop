package com.systop.fsmis.office.notice.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.ReceiveRecord;
import com.systop.fsmis.office.notice.service.ReceiveRecordManager;

/**
 * 接收记录管理类
 * 
 * @author ZW
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ReceiveRecordAction extends
		ExtJsCrudAction<ReceiveRecord, ReceiveRecordManager> {

	/** 标题 */
	private String title;

	/** 发送时间 */
	private Date sendTime;

	/** 通知ID */
	private Integer noticeId;

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 部门登录后查看的通知纪录列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String index() {
		Dept dept = loginUserService.getLoginUserDept(getRequest());
		StringBuffer hql = new StringBuffer();
		hql.append("from ReceiveRecord r where 1=1");
		// 标题
		List args = new ArrayList();
		if (StringUtils.isNotBlank(title)) {
			hql.append(" and notice.title like ?");
			args.add("%" + title + "%");
		}
		if (dept != null) {
			hql.append(" and dept.id = ?");
			args.add(dept.getId());
		}
		hql.append(" order by receiveDate desc");
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		restorePageData(page);
		return INDEX;
	}

	/**
	 * 查看通知
	 * 
	 * @return
	 */
	public String view() {
		getModel().setIsNew(FsConstants.N);
		if (getModel().getReceiveDate() == null) {
			getModel().setReceiveDate(DateUtil.getCurrentDate());
		}
		getManager().save(getModel());
		return VIEW;
	}

	/**
	 * 查看各部门通知
	 * 
	 * @return
	 */
	public String listbyNoticeId() {
		String hql = "from ReceiveRecord r where 1=1 ";
		Assert.notNull(noticeId);
		hql += " and r.notice.id = ?";
		items = getManager().query(hql, noticeId);
		return "listbyNoticeId";
	}

	/**
	 * 删除接收通知记录
	 * 
	 * @return
	 */
	@Override
	public String remove() {
		noticeId = getModel().getNotice().getId();
		getManager().remove(getModel());
		return SUCCESS;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
}
