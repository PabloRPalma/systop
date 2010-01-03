package com.systop.fsmis.init.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.core.util.ResourceBundleUtil;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.CaseType;
import com.systop.fsmis.model.SendType;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;

/**
 * 数据库初始化工具类
 * 
 * @author Lunch
 */
public class InitUtil {

	protected static Logger logger = LoggerFactory.getLogger(InitUtil.class);

	/** 资源文件 */
	private static final String BUNDLE_KEY = "application";

	/** 资源绑定对象 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_KEY);

	/** 默认值 */
	private static final String defValue = "";

	/** 区县名称 */
	private static final String COUNTY_NAMES = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "county_names", defValue);

	/** 部门名称 */
	private static final String DEPT_NAMES = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "dept_names", defValue);

	/** 案件类别 */
	private static final String CASE_TYPES = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "case_types", defValue);

	/** 案件派遣环节 */
	private static final String CASE_SEND_TYPES = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "case_send_type", defValue);

	/** 应急派遣环节 */
	private static final String URGENT_SEND_TYPES = ResourceBundleUtil
			.getString(RESOURCE_BUNDLE, "urgent_send_type", defValue);

	/** 应急组名称 */
	private static final String GROUP_NAMES = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "group_names", defValue);

	/**
	 * 
	 * @param names
	 *            部门名称数组
	 * @param type
	 *            类别
	 * @return
	 */
	private static List<Dept> createDepts(String deptNames, String type) {
		List<Dept> countyDepts = new ArrayList<Dept>();
		String[] names = deptNames.split(",");
		if (names != null) {
			for (String name : names) {
				countyDepts.add(new Dept(name, type));
			}
		}
		return countyDepts;
	}

	/**
	 * 获得初始化区县
	 * 
	 * @return
	 */
	public static List<Dept> getCountys() {
		logger.debug("init county(dept) is :{}", COUNTY_NAMES);
		return createDepts(COUNTY_NAMES, DeptConstants.TYPE_COUNTY);
	}

	/**
	 * 获得初始化部门
	 * 
	 * @return
	 */
	public static List<Dept> getDepts() {
		logger.debug("init dept is :{}", DEPT_NAMES);
		return createDepts(DEPT_NAMES, DeptConstants.TYPE_DEPT);
	}

	/**
	 * 获得初始化案件类别
	 * 
	 * @return
	 */
	public static List<CaseType> getCaseTypes() {
		List<CaseType> caseTypes = new ArrayList<CaseType>();
		logger.debug("init case_types is :{}", CASE_TYPES);
		String[] types = CASE_TYPES.split(",");
		if (types != null) {
			for (String type : types) {
				caseTypes.add(new CaseType(type));
			}
		}
		return caseTypes;
	}

	/**
	 * 获得初始化案件派遣类别
	 * 
	 * @return
	 */
	public static List<SendType> getSendTypes() {
		List<SendType> sendTypes = new ArrayList<SendType>();
		logger.debug("init SendType is :{}", CASE_SEND_TYPES);
		String[] types = CASE_SEND_TYPES.split(",");
		if (types != null) {
			for (String type : types) {
				String[] tmp = type.split(":");
				// 名称:排序id
				sendTypes.add(new SendType(tmp[0], Integer.valueOf(tmp[1])));
			}
		}
		return sendTypes;
	}

	/**
	 * 获得初始化应急组
	 * 
	 * @return
	 */
	public static List<UrgentGroup> getUrgentGroups() {
		List<UrgentGroup> urgentGroups = new ArrayList<UrgentGroup>();
		logger.debug("init UrgentGroup is :{}", GROUP_NAMES);
		String[] groups = GROUP_NAMES.split(",");
		if (groups != null) {
			for (String group : groups) {
				String[] tmp = group.split(":");
				urgentGroups.add(new UrgentGroup(tmp[0], tmp[1], FsConstants.Y,
						FsConstants.Y));
			}
		}
		return urgentGroups;
	}

	/**
	 * 获得系统初始化应急派遣环节
	 * 
	 * @return
	 */
	public static List<UrgentType> getUrgentSendType() {
		List<UrgentType> urgentTypes = new ArrayList<UrgentType>();
		logger.debug("init UrgentType is :{}", URGENT_SEND_TYPES);
		String[] types = URGENT_SEND_TYPES.split(",");
		if (types != null) {
			for (String type : types) {
				String[] tmp = type.split(":");
				urgentTypes
						.add(new UrgentType(tmp[0], Integer.valueOf(tmp[1])));
			}
		}
		return urgentTypes;
	}
}
