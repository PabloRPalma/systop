package com.systop.fsmis.urgentcase.webapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.UrgentCase;
import com.systop.fsmis.model.UrgentResult;
import com.systop.fsmis.urgentcase.UcConstants;
import com.systop.fsmis.urgentcase.service.UrgentCaseManager;

/**
 * 应急事件管理的struts2 Action。
 * 
 * @author DU
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UrgentCaseAction extends ExtJsCrudAction<UrgentCase, UrgentCaseManager> {
	
	/**
	 * 登陆用户信息管理
	 */
	@Autowired
	private LoginUserService loginUserService;
	
	/**
	 * json返回结果
	 */
	private Map<String, String> checkResult;
	
	/**
	 * 编辑指挥组处理结果时所用的事件ID
	 */
	private Integer caseId;
	
	/**
	 * 编辑指挥组处理结果时所用的组ID
	 */
	private Integer groupId;

	/**
	 * 查询起始事件
	 */
	private Date caseBeginTime;
	
	/**
	 * 查询截至事件
	 */
	private Date caseEndTime;
	
	private String mobelNums;
	
	private String smsContent;
	
	/**
	 * 应急事件查询列表
	 */
	@Override
	public String index() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer("from UrgentCase uc where 1=1 ");
		List args = new ArrayList();
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			sql.append(" and uc.county.id = ?");
			args.add(county.getId());
			if (StringUtils.isNotBlank(getModel().getTitle())) {
				sql.append(" and uc.title like ?");
				args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
			}
			if (StringUtils.isNotBlank(getModel().getIsAgree())) {
				sql.append(" and uc.isAgree = ?");
				args.add(getModel().getIsAgree());
			}
			if (caseBeginTime != null && caseEndTime != null) {
				sql.append("and uc.caseTime >= ? and uc.caseTime <= ? ");
				args.add(caseBeginTime);
				args.add(caseEndTime);
			}
			sql.append(" order by uc.status,uc.caseTime desc");
			page = getManager().pageQuery(page, sql.toString(), args.toArray());
			restorePageData(page);
			
			//取得该区县下的所有派遣环节
			List ucTypes = getManager().getAllUcTypeByCounty(county);
			logger.info("派遣环节数：{}", ucTypes.size());
			getRequest().setAttribute("ucTypeList", ucTypes);
		}
		return INDEX;
	}
	
	/**
	 * 保存应急事件
	 */
	@Override
	public String save() {
		try {
			Dept county = loginUserService.getLoginUserCounty(getRequest());
			if (county == null) {
				addActionError("您所在的区县为空...");
			}
			getModel().setCounty(county);
			getModel().setCreateTime(Calendar.getInstance().getTime());
			//新添加的事件状态为‘未审核’
			getModel().setStatus(UcConstants.CASE_STATUS_UNCHECK);
			getManager().save(getModel());
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	/**
	 * 删除应急事件及所对应的派遣结果数据组
	 */
	@Override
	public String remove() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			List gourpIds = getManager().getGroupIdsOfCase(getModel().getId(), county.getId());
			super.remove();
			getManager().delRroupOfCase(gourpIds);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查看应急事件的详细信息
	 */
	@Override
	public String view() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		//取得该区县下的所有派遣环节
		List ucTypes = getManager().getAllUcTypeByCounty(county);
		logger.info("派遣环节数：{}", ucTypes.size());
		getRequest().setAttribute("ucTypeList", ucTypes);
		
		return super.view();
	}
	
	/**
	 * 保存应急事件审核结果
	 */
	public String saveCheckResult() {
		checkResult = Collections.synchronizedMap(new HashMap<String, String>());
		String caseId = getRequest().getParameter("caseId");
		String isAgree = getRequest().getParameter("isAgree");
		String reason = getRequest().getParameter("reason");
		User checker = loginUserService.getLoginUser(getRequest());
		if (checker != null) {
			getManager().saveCheckResult(caseId, isAgree, reason, checker);
			checkResult.put("result", "success");
		} else {
			checkResult.put("result", "error");
		}
				
		return "jsonRst";
	}
	
	/**
	 * 任务派遣
	 */
	public String sendUrgentCase() {
		checkResult = Collections.synchronizedMap(new HashMap<String, String>());
		//根据页面选择的派遣方式查询该派遣方式对应的组
		//应急事件ID
		String caseId = getRequest().getParameter("caseId");
		//派遣环节ID
		String typeId = getRequest().getParameter("typeId");
		logger.info("应急事件ID:{}, 派遣方式ID:{}", caseId, typeId);
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		
		getManager().sendUrgentCase(caseId, typeId, county);
		
		return "jsonRst";
	}
	
	/**
	 * 查看任务派遣情况
	 */
	public String queryGroupResult() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		Map groupMap = new HashMap();
		if (county != null) {
			List<UrgentResult> urgentResults = getManager().queryGroupResult(
					getModel().getId(), county.getId());
			for(UrgentResult urgentRst : urgentResults){
				groupMap.put(urgentRst.getUrgentGroup().getCategory(), urgentRst);
			}
		}
		getRequest().setAttribute("groupMap", groupMap);
		getRequest().setAttribute("loginUser", loginUserService.getLoginUser(getRequest()));
		//logger.info("当前登录用户：{}", loginUserService.getLoginUser(getRequest()).getLoginId());
		
		return "groupResult";
	}
	
	/**
	 * 查询应急事件的审核记录
	 */
	public String listCheckResult() {
		Page page = PageUtil.getPage(getPageNo(), getPageSize());
		StringBuffer sql = new StringBuffer("from CheckResult cr where 1=1 ");
		sql.append(" and cr.urgentCase.id = ?");
		sql.append(" order by cr.isAgree desc,cr.checkTime desc");
		page = getManager().pageQuery(page, sql.toString(), getModel().getId());
		restorePageData(page);
		
		return "listCheckRst";
	}
	
	/**
	 * 编辑指挥组处理结果
	 */
	public String editGroupResult() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		logger.info("编辑处理结果时取得事件ID:{}", getModel().getId());
		if (county != null) {
			caseId = getModel().getId();
			//根据应急事件ID及处理组ID取得 处理结果的大字段信息
			Map resultMap = getManager().getUrgentResultByIds(
					getModel().getId(), county.getId(), groupId);
			getRequest().setAttribute("resultMap", resultMap);
		}
		
		return "editGroupResult";
	}
	
	/**
	 * 保存指挥组处理结果
	 */
	public String saveGroupResult() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		checkResult = Collections.synchronizedMap(new HashMap<String, String>());
		//应急事件ID
		String caseId = getRequest().getParameter("caseId");
		//指挥组ID
		String groupId = getRequest().getParameter("groupId");
		//取得结果字符串
		String rstValue = getRequest().getParameter("rstValue");
		logger.info("保存处理结果-->>事件ID:{}, 组ID:{}", caseId, groupId);
		logger.info("结果集:{}", rstValue);
		
		//根据结果集添加处理组结果
		getManager().saveGroupResult(caseId, county, groupId, rstValue);
		
		return "jsonRst";
	}
	
	/**
	 * 查看指挥组处理报告
	 */
	public String viewResultReports() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		Map reportsMap = new HashMap();
		if (county != null) {
			reportsMap = getManager().viewResultReports(getModel().getId(), county.getId());
		}
		getRequest().setAttribute("reportsMap", reportsMap);
		
		return "resultReports";
	}
	
	/**
	 * 应急事件处理完毕
	 */
	public String endUrgentCase() {
		getModel().setStatus(UcConstants.CASE_STATUS_RESOLVEED);
		getManager().save(getModel());
		return SUCCESS;
	}
	
	/**
	 * 短信发送编辑页面
	 */
	public String editSendSms() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			mobelNums = getManager().getOperatorOfGroupForCase(
					getModel().getId(), county.getId());
		}
		return "editSendSms";
	}
	
	/**
	 * 向事件派遣的相关组的负责人发送短信
	 */
	public String sendSms() {
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			logger.info("手机号码字符串：{}，内容：{}", mobelNums, smsContent);
			getManager().sendSms(mobelNums, smsContent, county);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 打印应急预案申请单
	 */
	public String printAppForm() {
		return "print";
	}
	
	/**
	 * 取得事件状态Map
	 */
	public Map<String, String> getUcStatusMap() {
		return UcConstants.UC_STATUS_MAP;
	}
	
	/**
	 * 取得预案等级
	 */
	public Map<String, String> getPlansLevelMap() {
		Map categoryMap = new LinkedHashMap();
		Dept county = loginUserService.getLoginUserCounty(getRequest());
		if (county != null) {
			if (county.getParentDept() != null) {
				categoryMap.put(UcConstants.UC_PLANS_LEVEL_FOUR, UcConstants.UC_PLANS_LEVEL_FOUR);
			} else {
				categoryMap.put(UcConstants.UC_PLANS_LEVEL_THREE, UcConstants.UC_PLANS_LEVEL_THREE);
			}
		}
		return categoryMap;
	}
	
	public Map<String, String> getIsAgreeMap() {
		return FsConstants.YN_MAP;
	}
	
	public Map<String, String> getCheckResult() {
  	return checkResult;
  }

	public void setCheckResult(Map<String, String> checkResult) {
  	this.checkResult = checkResult;
  }
	
	public Integer getCaseId() {
  	return caseId;
  }

	public void setCaseId(Integer caseId) {
  	this.caseId = caseId;
  }
	
	public Integer getGroupId() {
  	return groupId;
  }

	public void setGroupId(Integer groupId) {
  	this.groupId = groupId;
  }

	public Date getCaseBeginTime() {
  	return caseBeginTime;
  }

	public void setCaseBeginTime(Date caseBeginTime) {
  	this.caseBeginTime = caseBeginTime;
  }

	public Date getCaseEndTime() {
  	return caseEndTime;
  }

	public void setCaseEndTime(Date caseEndTime) {
  	this.caseEndTime = caseEndTime;
  }
	
	public String getMobelNums() {
  	return mobelNums;
  }

	public void setMobelNums(String mobelNums) {
  	this.mobelNums = mobelNums;
  }
	
	public String getSmsContent() {
  	return smsContent;
  }

	public void setSmsContent(String smsContent) {
  	this.smsContent = smsContent;
  }
}
