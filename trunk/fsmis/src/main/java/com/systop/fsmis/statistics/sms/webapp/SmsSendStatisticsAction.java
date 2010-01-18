package com.systop.fsmis.statistics.sms.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;
import com.systop.fsmis.model.SmsCount;
import com.systop.fsmis.statistics.sms.service.SmsSendStatisticsManager;


@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SmsSendStatisticsAction extends DefaultCrudAction<SmsCount,SmsSendStatisticsManager> {

	/** 查询起始时间 */
	private String beginDate;

	/** 查询结束时间 */
	private String endDate;
	
	
	/**
	 * 查询发送短信的日期、移动或联通其他发的数量
	 */
	@SuppressWarnings("unchecked")
	public String statisticsSmsSend() {
		StringBuffer hql = new StringBuffer(
				"from SmsCount s where 1=1 ");
		List args = new ArrayList();
		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
			hql.append(" and s.sendDate between ? and ?");
			args.add(beginDate);
			args.add(endDate);
		}
		
		hql.append(" order by s.sendDate desc ");
		
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		page = getManager().pageQuery(page, hql.toString(), args.toArray());
		restorePageData(page);
		return "statisticssmssend";
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
