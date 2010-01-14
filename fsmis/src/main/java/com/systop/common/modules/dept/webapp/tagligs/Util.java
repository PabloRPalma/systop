package com.systop.common.modules.dept.webapp.tagligs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.util.ReflectUtil;

public final class Util {

	/**
	 * 将Dept对象转换成Map对象
	 * 
	 * @param depts
	 * @param deftDeptIds
	 *            默认选中的部门id
	 * @param theme
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map> toMap(List<Dept> depts, String[] deftDeptIds) {
		List<Map> newDatas = new ArrayList();
		// 是否判断需要选中部门
		boolean isJudgeId = deftDeptIds != null;

		if (CollectionUtils.isNotEmpty(depts)) {
			for (Dept dept : depts) {
				Map map = ReflectUtil.toMap(dept,
						new String[] { "id", "name" }, true);
				map.put("checked", "");
				map.put("selected", "");
				if (isJudgeId) {//如果存在默认选中部门则进行选中.
					for (String id : deftDeptIds) {
						if (Integer.valueOf(id).equals(dept.getId())) {
							map.put("checked", "checked=\"checked\"");
							map.put("selected", "selected=\"selected\"");
						}
					}
				}
				newDatas.add(map);
			}
		}

		return newDatas;
	}
}
