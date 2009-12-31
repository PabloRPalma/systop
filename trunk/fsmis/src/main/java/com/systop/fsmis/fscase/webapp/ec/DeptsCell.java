package com.systop.fsmis.fscase.webapp.ec;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import com.systop.common.modules.dept.model.Dept;
import com.systop.fsmis.model.Task;
import com.systop.fsmis.model.TaskDetail;

/**
 * 用于在Ec中以特定格式显示一系列部门的Cell扩展
 * 
 * @author WorkShopers
 * 
 */
public class DeptsCell extends AbstractCell {

	@SuppressWarnings("unchecked")
	@Override
	protected String getCellValue(TableModel model, Column column) {
		Object obj = column.getValue();
		if (obj == null) {
			return StringUtils.EMPTY;
		}
		if (obj instanceof Task) {
			return getOutputString(((Task) obj).getTaskDetails());
		} else if (obj instanceof Set) {
			Set set = (Set) obj;
			if (checkTaskDetailSet(set)) {
				return getOutputString(set);
			} else {
				return StringUtils.EMPTY;
			}
		} else {
			return column.getValueAsString();
		}
	}

	@SuppressWarnings("unchecked")
	private <T> boolean checkTaskDetailSet(Set set) {
		if (CollectionUtils.isEmpty(set)) {
			return false;
		}
		for (Object o : set) {
			if ((o instanceof TaskDetail)) {
				return true;
			}
		}

		return true;
	}

	private String getOutputString(Set<TaskDetail> taskDetails) {
		if (CollectionUtils.isNotEmpty(taskDetails)) {
			StringBuffer buf = new StringBuffer();

			for (TaskDetail td : taskDetails) {
				if (td != null) {
					Dept dept = td.getDept();
					if (dept != null) {
						buf.append("【").append(dept.getName()).append("】");
					}
				}
			}
			return buf.toString();
		} else {
			return StringUtils.EMPTY;
		}
	}
}
