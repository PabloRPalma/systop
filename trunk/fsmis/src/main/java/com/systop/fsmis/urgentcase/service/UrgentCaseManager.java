package com.systop.fsmis.urgentcase.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.FsConstants;
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
		StringBuffer hql = new StringBuffer("from UrgentType ut where ut.county.id = ?");
		hql.append(" order by ut.sortId asc");
		if (county != null) {
			ucTypes = query(hql.toString(), county.getId());
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
  					utGroupNotOrg.setIsOriginal(FsConstants.N);
  					getDao().save(utGroupNotOrg);
  					UrgentResult urgentResult = new UrgentResult();
  					urgentResult.setDisplays("显示内容...");
  					urgentResult.setUrgentCase(urgentCase);
  					//派遣结果关联‘非原始数据组’
  					urgentResult.setUrgentGroup(utGroupNotOrg);
  					urgentResult.setCounty(county);
  					//组结果的具体内容
  					urgentResult.setContent(generateUrgentResult(urgentGroup));
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
   * 根据指挥组生成该指挥组的处理结果数据
   * @param urgentGroup
   * @return
   */
  private String generateUrgentResult(UrgentGroup urgentGroup) {
  	StringBuffer contentRest = new StringBuffer();
  	if (urgentGroup != null) {
  		logger.info("取得组的类型：{}", urgentGroup.getCategory());
  		String[] typeRst = getUrgentTypeRst(urgentGroup.getCategory());
  		String[] pubRst = UcConstants.PublicResult;
  		if (!ArrayUtils.isEmpty(typeRst)) {
  			for(int i = 0; i < typeRst.length; i++) {
    			contentRest.append(typeRst[i]).append(":").append("null").append(",");
    		}
  			for(int j = 0; j < pubRst.length-1; j++) {
    			contentRest.append(pubRst[j]).append(":").append("null").append(",");
    		}
    		contentRest.append(pubRst[pubRst.length-1]).append(":").append("null").append("");
  		}
  	}
  	logger.info("生成该指挥组的处理结果数据:{}", contentRest.toString());
  	return contentRest.toString();
  }
  
  /**
   * 根据派遣环节ID及区县ID,取得所属区县下派遣环节所对应的原始数据组
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
   * 根据应急事件ID及区县ID取得该事件指挥组的处理结果集
   * @param caseId 应急事件ID
   * @param countyId 区县ID
   */
  public Map viewResultReports(Integer caseId, Integer countyId) {
  	Map<String, Map> resultMap = new LinkedHashMap();
  	List<UrgentResult> resultList = queryGroupResult(caseId, countyId);
  	for (UrgentResult urgentResult : resultList) {
  		if (StringUtils.isNotBlank(urgentResult.getContent())) {
  			String contentJson = "{" + urgentResult.getContent() + "}";
  			//logger.info("处理结果json字符串：{}", contentJson);
  			Map contentMap = convertJsonToMap(contentJson);
  			resultMap.put(urgentResult.getUrgentGroup().getName(), contentMap);
  		}
  	}
  	return resultMap;
  }
  
  /**
   * 根据应急事件ID、区县ID及指挥组ID取得该事件的派发结果
   * 将派发结果内容转换为Map数据格式
   * @param caseId 事件ID
   * @param countyId 区县ID
   * @param groupId 指挥组ID
   */
  public Map getUrgentResultByIds(Integer caseId, Integer countyId, Integer groupId) {
  	Map contentMap = Collections.EMPTY_MAP;
		UrgentResult urgentResult = getUrgentResult(caseId, countyId, groupId);
		if (urgentResult != null) {
			String contentJson = "{" + urgentResult.getContent() + "}";
			//logger.info("json字符串：{}", contentJson);
			contentMap = convertJsonToMap(contentJson);
		}
		
		return contentMap;
  }
  
  /**
   * 根据应急事件ID、区县ID及指挥组ID取得该事件的派发结果
   * @param caseId 事件ID
   * @param countyId 区县ID
   * @param groupId 指挥组ID
   */
  private UrgentResult getUrgentResult(Integer caseId, Integer countyId, Integer groupId) {
  	StringBuffer hql = new StringBuffer("from UrgentResult ur where 1=1 ");
		hql.append(" and ur.county.id = ?");
		hql.append(" and ur.urgentCase.id = ?");
		hql.append(" and ur.urgentGroup.id = ?");
		return (UrgentResult) getDao().findObject(
				hql.toString(), countyId, caseId, groupId);
  }
  
  /**
   * 保存应急事件各个指挥组的处理结果
   * @param caseId 事件ID
   * @param countyId 区县ID
   * @param groupId 指挥组ID
   */
  @Transactional
  public void saveGroupResult(String caseId, Dept county, String groupId, String result) {
  	UrgentResult urgentResult = null;
  	if (StringUtils.isNotEmpty(caseId) && StringUtils.isNotEmpty(groupId)) {
  		if (isNumeric(caseId) && isNumeric(groupId)) {
  			if (county != null) {
  				urgentResult = getUrgentResult(Integer.valueOf(caseId), county.getId(), Integer.valueOf(groupId));
  			}
  		}
  	}
  	if (urgentResult != null) {
  		String[] rst = result.split(":");
  		String[] oldRst = null;
  		StringBuffer newRst = new StringBuffer();
  		if (StringUtils.isNotEmpty(urgentResult.getContent())) {
  			oldRst = urgentResult.getContent().split(",");
  			for (int i = 0; i < oldRst.length-1; i++) {
    			newRst.append(oldRst[i].substring(0, oldRst[i].indexOf(":")))
    			.append(":").append("'").append(rst[i]).append("'").append(",");
    		}
    		newRst.append(oldRst[oldRst.length-1].substring(0, oldRst[oldRst.length-1].indexOf(":")))
  			.append(":").append("'").append(rst[oldRst.length-1]).append("'");
    		logger.info("编辑后要保存的处理结果：{}", newRst.toString());
    		
    		urgentResult.setContent(newRst.toString());
    		//修改应急事件中指挥组的处理结果
    		getDao().save(urgentResult);
  		}
		}
  }
  
  /**
   * 取得应急事件的派遣情况对应的数据组
   * @param caseId 事件ID
   * @param county 区县
   */
  public List getGroupIdsOfCase(Integer caseId, Integer countyId) {
  	List<Integer> gourpIdList = new ArrayList();
  	List<UrgentResult> resultList = queryGroupResult(caseId, countyId);
		if (CollectionUtils.isNotEmpty(resultList)) {
			for (UrgentResult urgentResult : resultList) {
				if (urgentResult.getUrgentGroup() != null) {
					Integer groupId = urgentResult.getUrgentGroup().getId();
					gourpIdList.add(groupId);
				}
  		}
		}
		return gourpIdList;
  }
  
  /**
   * 删除应急事件的派遣情况对应的数据组
   * @param gourpIds 组ID集合
   */
  @Transactional
  public void delRroupOfCase(List<Integer> gourpIds) {
  	if (CollectionUtils.isNotEmpty(gourpIds)) {
  		for (Integer groupId : gourpIds) {
  			UrgentGroup urgentGroup = getDao().get(UrgentGroup.class, groupId);
  			if (urgentGroup != null) {
  				getDao().evict(urgentGroup);
  				getDao().delete(UrgentGroup.class, groupId);
  			}
  		}
  	}
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
			//logger.info("JSON数据键值对：{}: {}", key, value);
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
	
	/**
	 * 取得组类型对应的结果集字段
	 * @param rst
	 */
	private String[] getUrgentTypeRst(String rst) {
		String[] typeRst = null;
		
		if(rst.equals(UcConstants.DEFEND)) {
			typeRst = UcConstants.DefendResult;
		}
		if(rst.equals(UcConstants.ACCIDENT_HANDLE)) {
			typeRst = UcConstants.AccidentHandleResult;
		}
		if(rst.equals(UcConstants.AFTER_HANDLE)) {
			typeRst = UcConstants.AfterHandleResult;
		}
		if(rst.equals(UcConstants.EXPERT_TECHNOLOGY)) {
			typeRst = UcConstants.ExpertTechnologyResult;
		}
		if(rst.equals(UcConstants.MEDICAL_RESCUE)) {
			typeRst = UcConstants.MedicalRescueResult;
		}
		if(rst.equals(UcConstants.NEWS_REPORT)) {
			typeRst = UcConstants.NewsReportResult;
		}
		if(rst.equals(UcConstants.REAR_SERVICE)) {
			typeRst = UcConstants.RearServiceResult;
		}
		if(rst.equals(UcConstants.RECEIVE)) {
			typeRst = UcConstants.ReceiveResult;
		}
		return typeRst;
	}
}
