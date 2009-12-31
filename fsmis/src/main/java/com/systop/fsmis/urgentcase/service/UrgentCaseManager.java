package com.systop.fsmis.urgentcase.service;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.model.CheckResult;
import com.systop.fsmis.model.UrgentCase;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentResult;
import com.systop.fsmis.model.UrgentType;
import com.systop.fsmis.urgentcase.UcConstants;

/**
 * 应急事件管理类
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Service
public class UrgentCaseManager extends BaseGenericsManager<UrgentCase> {

	/**
	 * 保存应急事件审核结果
	 * @param caseId 应急事件ID
	 * @param isAgree 是否同意
	 * @param reason 具体意见
	 */
	@Transactional
	public void saveCheckResult(String caseId, String isAgree, String reason, User checker) {
		if (StringUtils.isNotEmpty(caseId)) {
			if (isNumeric(caseId)) {
				UrgentCase urgentCase = getDao().get(UrgentCase.class, Integer.valueOf(caseId));
				urgentCase.setStatus(isAgree);
				urgentCase.setIsAgree(isAgree);
				CheckResult checkResult = new CheckResult();
				checkResult.setChecker(checker);
				checkResult.setCheckTime(new Date());
				checkResult.setIsAgree(isAgree);
				checkResult.setResult(reason);
				checkResult.setUrgentCase(urgentCase);
				getDao().save(urgentCase);
				getDao().save(checkResult);
			}
		}
	}
	
	/**
	 * 根据用户所在区县取得该区县下的所有派遣环节
	 * @param county 所在区县
	 */
  public List getAllUcTypeByCounty(Dept county) {
		List ucTypes = Collections.EMPTY_LIST;
		if (county != null) {
			ucTypes = query("from UrgentType ut where ut.county.id = ?", county.getId());
		}
		return ucTypes;
	}
	
  /**
   * 应急事件任务派遣
   * 选择派遣方式后，根据派遣方式取得对应的原始应急指挥组数据
   * 拷贝原始组里面的数据生成新的指挥组数据
   * 将新的指挥组数据(非原始数据)与事件的派遣结果关联
   * 保存事件派遣结果
   * 更新应急事件状态
   * @param caseId 应急事件ID
   * @param typeId 派遣方式ID
   * @param county 所属区县
   */
  @Transactional
  public void sendUrgentCase(String caseId, String typeId, Dept county) {
  	if (StringUtils.isNotEmpty(caseId) && StringUtils.isNotEmpty(typeId)) {
  		if (isNumeric(caseId) && isNumeric(typeId)) {
  			UrgentCase urgentCase = getDao().get(UrgentCase.class, Integer.valueOf(caseId));
  			UrgentType urgentType = getDao().get(UrgentType.class, Integer.valueOf(typeId));
  			List<UrgentGroup> groups = Collections.EMPTY_LIST;
  			if (county != null) {
  				groups = getUrgentGroupByIDs(Integer.valueOf(typeId), county.getId());
  				logger.info("派遣环节：{}，对应的组的个数：{}", 
  						urgentType.getName(), String.valueOf(groups.size()).toString());
  				for(UrgentGroup urgentGroup : groups) {
  					//拷贝原始组里面的数据生成新的实例
  					UrgentGroup utGroupNotOrg = new UrgentGroup();
  					BeanUtils.copyProperties(urgentGroup, utGroupNotOrg, new String[]{"isOriginal"});
  					//将新的组设置为‘非原始数据’
  					utGroupNotOrg.setIsOriginal(UcConstants.GROUP_ORIGINAL_NO);
  					getDao().save(utGroupNotOrg);
  					UrgentResult urgentResult = new UrgentResult();
  					urgentResult.setDisplays("显示内容...");
  					urgentResult.setUrgentCase(urgentCase);
  					//派遣结果关联‘非原始数据组’
  					urgentResult.setUrgentGroup(utGroupNotOrg);
  					urgentResult.setCounty(county);
  					//组处理的具体内容,暂时设置测试数据，有待完善....
  					urgentResult.setContent("出警数量:5,疏散人数:6,处理时间:2009-12-31,处理过程:'生生世世是',处理结果:'打发打发打发'");
  					getDao().save(urgentResult);
  				}
  				//修改应急事件状态为‘已派遣’
  				urgentCase.setStatus(UcConstants.CASE_STATUS_SENDDED);
  				getDao().save(urgentCase);
  			}
  		}
  	}
  }
  
  /**
   * 根据ID取得所属区县下派遣环节所对应的原始数据组
   * @param typeId 派遣环节ID
   * @param countyId 区县ID
   */
  private List getUrgentGroupByIDs(Integer typeId, Integer countyId) {
  	StringBuffer hql = new StringBuffer("from UrgentGroup ug where 1=1 ");
		hql.append(" and ug.county.id = ?");
		hql.append(" and ug.isOriginal = ?");
		hql.append(" and (ug.urgentType.id = ? or ug.urgentType.id is null)");
		
		return getDao().query(hql.toString(), 
				countyId, UcConstants.GROUP_ORIGINAL_YES, typeId);
  }
  
  /**
   * 根据应急事件ID及区县ID取得该事件的派发情况
   * @param caseId 应急事件ID
   * @param countyId 区县ID
   */
  public List queryGroupResult(Integer caseId, Integer countyId) {
  	StringBuffer hql = new StringBuffer("from UrgentResult ur where 1=1 ");
		hql.append(" and ur.county.id = ?");
		hql.append(" and ur.urgentCase.id = ?");
		
  	return getDao().query(hql.toString(), countyId, caseId);
  }
  
  /**
   * 根据应急事件ID、区县ID及指挥组ID取得该事件的派发结果
   * @param caseId 事件ID
   * @param countyId 区县ID
   * @param groupId 指挥组ID
   */
  public Map getUrgentResultByIds(Integer caseId, Integer countyId, Integer groupId) {
  	Map contentMap = Collections.EMPTY_MAP;
  	StringBuffer hql = new StringBuffer("from UrgentResult ur where 1=1 ");
		hql.append(" and ur.county.id = ?");
		hql.append(" and ur.urgentCase.id = ?");
		hql.append(" and ur.urgentGroup.id = ?");
		UrgentResult urgentResult = (UrgentResult) getDao().findObject(
				hql.toString(), countyId, caseId, groupId);
		if (urgentResult != null) {
			String contentJson = "{" + urgentResult.getContent() + "}";
			logger.info("json字符串：{}", contentJson);
			contentMap = convertJsonToMap(contentJson);
		}
		
		return contentMap;
  }
  
  /**
   * 将大字段信息转换成Map
   */
  private Map convertJsonToMap(String content) {
  	Map valueMap = new LinkedHashMap();
		JSONObject jsonObject = JSONObject.fromObject(content);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		while (keyIter.hasNext())
		{
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			logger.info("JSON数据键值对：{}: {}", key, value);
			valueMap.put(key, value);
		}
		
		return valueMap;
  }
  
	/**
	 * 判断字符串是否由数字组成
	 * @param str
	 */
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	} 
}
