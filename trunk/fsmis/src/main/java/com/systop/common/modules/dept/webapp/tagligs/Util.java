package com.systop.common.modules.dept.webapp.tagligs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.util.ReflectUtil;
import com.systop.fsmis.model.CountySendType;

public final class Util {

	/**
	 * 将Dept对象转换成Map对象
	 * 
	 * @param depts
	 * @param deptIds
	 * @param theme
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map> toMap(List<Dept> depts, CountySendType cst) {
		
		if (CollectionUtils.isEmpty(depts)) {
			return null;
		}
		List<Map> newDatas = new ArrayList();

		String[] ids = null;
		// 是否需要判断ID,用于显示部门是否被选中
		boolean isJudgeId = false;
		if (cst != null && StringUtils.isNotBlank(cst.getGeneralDept())) {
			ids = cst.getGeneralDept().split(",");
			isJudgeId = true;
		}

		for (Dept dept : depts) {
			Map map = ReflectUtil.toMap(dept, new String[] { "id", "name" },
					true);
			map.put("checked", "");
			if (isJudgeId) {
				for (String id : ids) {
					if (Integer.valueOf(id).equals(dept.getId())) {
						map.put("checked", "checked=\"checked\"");
					}
				}
			}
			newDatas.add(map);
		}
		return newDatas;
	}
}
