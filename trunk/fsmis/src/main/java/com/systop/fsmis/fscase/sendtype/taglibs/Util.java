package com.systop.fsmis.fscase.sendtype.taglibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.systop.common.modules.dept.model.Dept;
import com.systop.core.util.ReflectUtil;

public final class Util {

	/**
	 * 将Dept对象转换成Map对象
	 * @param depts
	 * @param deptIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map> toMap(List<Dept> depts, String deptIds) {
		if (depts == null) {
			return null;
		}
		List<Map> newDatas = new ArrayList();

		String[] ids = null;
		//是否需要判断ID
		boolean isJudgeId = false;
		if (StringUtils.isNotBlank(deptIds)) {
			ids = deptIds.split(",");
			isJudgeId = true;
		}

		for (Dept dept : depts) {
			Map map = ReflectUtil.toMap(dept, new String[] { "id", "name" },
					true);
			map.put("checked", "");
			if (isJudgeId){
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
