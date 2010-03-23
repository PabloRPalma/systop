package com.systop.common.security.dept.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.systop.common.security.dept.model.Dept;
import com.systop.common.security.dept.service.DeptManager;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;

/**
 * 
 * @author qian.wang
 * 
 */
public class DeptDojoAction extends BaseDwrAjaxAction {
	/**
	 * <tt>Manager</tt> of the <tt>Dept</tt>.
	 */
	private DeptManager deptManager;

	public DeptManager getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(DeptManager deptManager) {
		this.deptManager = deptManager;
	}

	/**
	 * 根据父部门Id，列出子部门Id。
	 * 
	 * @param parentId
	 *            父部门Id，如果为<tt>null</tt>,则表示列出第一级目录.
	 * @return 将<tt>Dept</tt>对象转化为符合 
	 * <a href="http://dojotoolkits.org"> dojo</a>要求的Map对象。
	 */
	@SuppressWarnings("unchecked")
	public List getDeptsByParentId(Integer parentId) {

		List<Dept> depts = deptManager.getDeptsByParentId(parentId);
		List result = new ArrayList();
		// 转化为符合dojo要求的Map对象.
		for (Dept dept : depts) {
			Map map = new HashMap();
			map.put("title", dept.getName());
			map.put("objectId", dept.getId());
			map.put("object", dept.getDescn());
			result.add(map);
		}
		return result;
	}

	/**
	 * 根据父部门Id，列出子部门。
	 * @param parentId
	 *            父部门Id，如果为<tt>null</tt>,则表示列出第一级目录.
	 *            
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSubDeptsByParentId(Integer parentId) {

		List<Dept> depts = deptManager.getDeptsByParentId(parentId);
//		List deptsName = new ArrayList();
//		for (Dept dept : depts) {
//			deptsName.add(dept.getName());
//		}
		return depts;
	}

	/**
	 * 新增一个部门
	 * 
	 * @param parentId
	 *            父目录Id
	 * @param dept
	 *            即将保存的Dept
	 */
	public Integer saveDept(Integer parentId, Dept dept) {
		return deptManager.saveDept(parentId, dept);
	}

	/**
	 * 删除所选部门
	 * 
	 * @param deptId
	 *            即将被删除的目录ID
	 */
	public int removeDept(Integer deptId) {
		Dept dept = deptManager.get(deptId);
		int subDeptNum = deptManager.findByParent(dept).size();
		if (subDeptNum == 0) {
			deptManager.remove(dept);
		}
		return subDeptNum;
	}

	/**
	 * 准备编辑所选的部门
	 * 
	 * @param deptId
	 *            所选目录ID
	 * @return
	 */
	public Dept get(Integer deptId) {
		return deptManager.get(deptId);
	}

	/**
	 * 更新编辑好的部门
	 * 
	 * @param deptForm
	 *            从前台传来的编辑好的目录
	 * @return
	 */
	public Integer updateDept(Dept deptForm) {
		return deptManager.updateDept(deptForm);
	}

}
