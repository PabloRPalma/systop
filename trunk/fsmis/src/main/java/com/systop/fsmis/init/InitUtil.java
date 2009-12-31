package com.systop.fsmis.init;

import java.util.ArrayList;
import java.util.List;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.model.UrgentGroup;
import com.systop.fsmis.model.UrgentType;

public class InitUtil {

	/** 区县名称 */
	private static final String[] COUNTY_NAMES = { "桥东区", "桥西区", "新华区", "长安区",
			"裕华区" };

	/** 部门名称 */
	private static final String[] DEPT_NAMES = { "农业局", "工商局", "卫生局", "食药监局",
			"质量技术监督局", "商务局", "供销社", "畜牧水产局", "林业局", "公安局", "环保局" };

	/** 应急组名称 */
	private static final String[] GROUP_NAMES = { "指挥部:Leadership",
			"办公室:Office", "警戒保卫:Defend", "医疗救护:MedicalRescue",
			"后勤保障:RearService", "新闻报道:NewsReport", "技术专家:ExpertTechnology",
			"接待:Receive" };

	/** 应急组名称 */
	private static final String[] GROUP_TYPES = { "初级农产品环节:1", "生产加工环节:2",
			"食品流通环节:3", "餐饮消费环节:4", "其他环节:5" };

	/**
	 * 
	 * @param names
	 *            部门名称数组
	 * @param type
	 *            类别
	 * @return
	 */
	private static List<Dept> createDepts(String[] deptNames, String type) {
		List<Dept> countyDepts = new ArrayList<Dept>();
		for (String name : deptNames) {
			countyDepts.add(new Dept(name, type));
		}
		return countyDepts;
	}

	/**
	 * 获得初始化区县
	 * 
	 * @return
	 */
	public static List<Dept> getCountys() {
		return createDepts(COUNTY_NAMES, DeptConstants.TYPE_COUNTY);
	}

	/**
	 * 获得初始化部门
	 * 
	 * @return
	 */
	public static List<Dept> getDepts() {
		return createDepts(DEPT_NAMES, DeptConstants.TYPE_DEPT);
	}

	/**
	 * 获得初始化应急组
	 * 
	 * @return
	 */
	public static List<UrgentGroup> getUrgentGroups() {
		List<UrgentGroup> urgentGroups = new ArrayList<UrgentGroup>();
		for (String group : GROUP_NAMES) {
			String[] tmp = group.split(":");
			urgentGroups.add(new UrgentGroup(tmp[0], tmp[1], FsConstants.Y,
					FsConstants.Y));
		}
		return urgentGroups;
	}

	/**
	 * 获得系统初始化应急派遣环节
	 * 
	 * @return
	 */
	public static List<UrgentType> getUrgentType() {
		List<UrgentType> urgentTypes = new ArrayList<UrgentType>();
		for (String type : GROUP_TYPES) {
			String[] tmp = type.split(":");
			urgentTypes.add(new UrgentType(tmp[0], Integer.valueOf(tmp[1])));
		}
		return urgentTypes;
	}
}
