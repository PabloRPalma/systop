package com.systop.fsmis.statistics.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.core.service.BaseGenericsManager;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.model.Task;

/**
 * 任务统计管理
 * 
 * @author zzg
 * 
 */
@Service
public class TaskStatisticsManager extends BaseGenericsManager<Task> {

	/**
	 * 部门管理
	 */
	@Autowired
	private DeptManager deptManager;

	/**
	 * 按任务状态进行统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param county
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTaskStatusStatistic(Date beginDate, Date endDate,
			Dept county) {
		List<Object[]> result = null;
		StringBuffer sql = new StringBuffer(
				"select ts.status,count(ts.status) from Task ts where 1=1");
		List args = new ArrayList();
		if (county.getParentDept() != null) {
			sql.append(" and ts.fsCase.county.id = ?");
			args.add(county.getId());
		}
		if(beginDate != null && endDate != null){
			sql.append(" and ts.dispatchTime between ? and ? ");
			args.add(beginDate);
			args.add(endDate);
		}
		sql.append(" group by ts.status");
		result = getDao().query(sql.toString(), args.toArray());
		
		StringBuffer cvsData = new StringBuffer();
		if (CollectionUtils.isNotEmpty(result)) {
			for (Object[] objs : result) {
				if(objs != null && objs[0] != null){
					objs[0] = TaskConstants.TASK_MAP.get(objs[0]);
				}
				cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
			}
		} else {
			cvsData.append("无数据;0\\n");
		}
		return cvsData.toString();
	}

	/**
	 * 按任务区县进行统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTaskCountyStatistic(Date beginDate, Date endDate ,String status) {
		StringBuffer cvsData = new StringBuffer();
		
		//获取顶级部门
		Dept d = deptManager.findObject("from Dept d where d.parentDept is null");
		
		//获取各区县
		String sql = "from Dept d where d.type = ? and d.parentDept.id = ?";
		List<Dept> depts = deptManager.query(sql, DeptConstants.TYPE_COUNTY, d
				.getId());
		
		for (Dept dp : depts) {
			StringBuffer hql = new StringBuffer("select ts.fsCase.county.name,count(ts.id) from Task ts where 1=1 ");
			List args = new ArrayList();
			//设置按时间查询条件
			if(beginDate != null && endDate != null){
				hql.append(" and ts.dispatchTime between ? and ?  ");
				args.add(beginDate);
				args.add(endDate);
			}
		//设置按状态查询条件
			if(status != null && StringUtils.isNotBlank(status)){
				hql.append(" and ts.status = ?");
				args.add(status);
			}
			hql.append(" and ts.fsCase.county.id=? ");
			args.add(Integer.valueOf(dp.getId()));
			hql.append("group by ts.fsCase.county.id,ts.fsCase.county.name ");
			List<Object[]> result = getDao().query(hql.toString(), args.toArray());
			if (result.size() > 0) {
				for (Object[] objs : result) {
					cvsData.append(objs[0] + ";").append(objs[1] + "\\n");
				}
			} else {
				cvsData.append(dp.getName() + ";").append("0" + "\\n");
			}
		}
		return cvsData.toString();
	}
}
