package com.systop.common.modules.dept.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.systop.common.modules.dept.DeptConstants;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.service.DeptManager;
import com.systop.common.modules.dept.service.DeptSerialNoManager;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.core.util.RequestUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;

/**
 * 部门管理Action
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DeptAction extends ExtJsCrudAction<Dept, DeptManager> {
	/**
	 * 当前上级部门ID
	 */
	private Integer parentId;
	// 除去部门管理以外，页面显示部门标识
	private String noLowerDept;

	/**
	 * 部门序列号管理器
	 */
	private DeptSerialNoManager serialNoManager;
	/**
	 *获得当前登陆部门
	 */
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 用于查询的部门名称
	 */
	private String deptName = StringUtils.EMPTY;

	private List depts;

	/**
	 * 部门查询。根据指定的上级部门id(通过{@link #parentId}属性)，查询下级部门。 如果{@link #parentId}
	 * 为null,则查询顶级部门（没有上级部门的）
	 */
	@Override
	@SkipValidation
	public String index() {
		return INDEX;
	}

	/**
	 * Build a tree as json format.
	 */
	public String deptTree() {
		if (RequestUtil.isJsonRequest(getRequest())) {
			Dept parent = null;
			
			// 当前登陆用户 注意admin部门为空
			Dept dept = loginUserService.getLoginUserDept(getRequest());
			if (parentId != null) {// 部门编辑，用户添加使用
				parent = getManager().get(parentId);
			} else {// 部门列表树显示使用
				if (dept != null && dept.getParentDept() != null) {
					// 显示当前登陆用户部门
					parent = dept;
				} else {
					// 显示所有部门
					parent = getManager().findObject(
							"from Dept d where d.parentDept is null");
				}
			}
			Map parentMap = null;
			if (parent != null) {
				parentMap = new HashMap();
				parentMap.put("id", parent.getId());
				parentMap.put("text", parent.getName());
				parentMap.put("type", parent.getType());
			}

			Map deptTree = getDeptTree(parentMap, true);
			depts = new ArrayList();
			depts.add(deptTree);
			return JSON;
		}
		return INDEX;
	}

	/**
	 * 根据指定的父部门id查询子部门
	 */
	private List<Dept> getByParentId(Integer parentDeptId) {
		List list = Collections.EMPTY_LIST;
		if (parentDeptId == null
				|| parentDeptId.equals(DeptConstants.TOP_DEPT_ID)) {
			list = getManager().query("from Dept d where d.parentDept is null");
		} else {
			list = getManager().query("from Dept d where d.parentDept.id = ?",
					parentDeptId);
		}

		return list;
	}

	/**
	 * 返回部门树形列表，每一个部门用一个<code>java.util.Map</code>表示，子部门
	 * 用Map的“childNodes”key挂接一个<code>java.util.List</code>.<br>
	 * 本方法供DWR调用，Map中key符合jsam dojo Tree的要求。
	 * 
	 * @param parent
	 *            父部门，如果为null，则表示顶级部门
	 * @param nested
	 *            是否递归查询子部门，true表示递归查询子部门
	 * @return
	 */
	public Map getDeptTree(Map parent, boolean nested) {
		List<Dept> depts;
		if (parent == null || parent.isEmpty() || parent.get("id") == null) {
			return null;
		}
		// 得到子部门，区县用户直接返回，市级或admin用户查询24个区县
		if (noLowerDept == null) {
			depts = this.getByParentId((Integer) parent.get("id"));
		} else {
			Dept dept = getManager().get((Integer) parent.get("id"));
			if (dept.getParentDept() == null) {
				depts = this.getByParentId((Integer) parent.get("id"));
			} else {
				parent.put("leaf", true);
				return parent;
			}
		}

		logger.debug("Dept {} has {} children.", parent.get("text"), depts
				.size());
		// 转换所有子部门为Map对象，一来防止dwr造成延迟加载，
		// 二来可以符合Ext的数据要求.
		List children = new ArrayList();
		for (Iterator<Dept> itr = depts.iterator(); itr.hasNext();) {
			Dept dept = itr.next();
			Map child = new HashMap();
			child.put("id", dept.getId());
			child.put("text", dept.getName());
			child.put("descn", dept.getDescn());
			child.put("type", dept.getType());
			// noSonDept不为空，无须递归查询
			if (noLowerDept == null) {
				if (nested) { // 递归查询子部门
					child = this.getDeptTree(child, nested);
				}
			}
			// 标识当前节点为叶子节点，其实是区县部门
			if (noLowerDept != null) {
				child.put("leaf", true);
			}
			children.add(child);

		}
		// noSonDept不为空，代表事件或部门不需要查看下级部门
		if (!children.isEmpty() || noLowerDept != null) {
			parent.put("children", children);
			parent.put("childNodes", children);
			parent.put("leaf", false);
		} else {
			parent.put("leaf", true);
		}

		return parent;
	}

	/**
	 * @return 当前上级部门ID
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 得到当前部门（通过{@link #parentId}指定）的上级部门.
	 */
	public Dept getParent() {
		if (parentId == null || parentId.equals(DeptConstants.TOP_DEPT_ID)) {
			Dept dept = new Dept();
			dept.setName(DeptConstants.TOP_DEPT_NAME);
			return dept;
		}

		return getManager().get(parentId);
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 覆盖父类，处理父部门ID为{@link DeptConstants#TOP_DEPT_ID}的情况。
	 */
	@Override
	@Validations(requiredStrings = { @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "model.name", message = "部门名称是必须的.") })
	public String save() {
		// 如果页面选择了顶级部门作为父部门，则设置父部门为null
		if (getModel().getParentDept() != null
				&& getModel().getParentDept().getId() != null
				&& getModel().getParentDept().getId().equals(
						DeptConstants.TOP_DEPT_ID)) {
			getModel().setParentDept(null);
			return super.save();
		}
		if (getModel().getParentDept() == null
				|| getModel().getParentDept().getId() == null) {
			//限制添加两个根部门 2009-12-28
			if (getManager().getDao().exists(getModel().getParentDept(), "parentDept")) {
				addActionError("请选择上级部门！");
				return INPUT;
			}
			
			logger.debug("保存第一 级部门.");
		}
		
		return super.save();
	}

	/**
	 * 处理parentDept为null的情况
	 */
	@Override
	@SkipValidation
	public String edit() {
		if (getModel().getId() != null) {
			setModel(getManager().get(getModel().getId()));
			if (getModel().getParentDept() == null) {
				Dept dept = new Dept(); // 构建一个父部门
				dept.setId(DeptConstants.TOP_DEPT_ID);
				dept.setName(DeptConstants.TOP_DEPT_NAME);
				getModel().setParentDept(dept);
				getManager().getDao().evict(getModel()); // 将dept脱离hibernate
				logger.debug("编辑第一级部门");
			}
		}
		return INPUT;
	}

	/**
	 * 重置所有部门编号
	 */
	@SkipValidation
	public String updateSerialNo() {
		serialNoManager.updateAllSerialNo();
		return SUCCESS;
	}

	public DeptSerialNoManager getSerialNoManager() {
		return serialNoManager;
	}

	@Autowired(required = true)
	public void setSerialNoManager(DeptSerialNoManager serialNoManager) {
		this.serialNoManager = serialNoManager;
	}

	public List getDepts() {
		return depts;
	}

	public void setDepts(List depts) {
		this.depts = depts;
	}

	public String getNoLowerDept() {
		return noLowerDept;
	}

	public void setNoLowerDept(String noLowerDept) {
		this.noLowerDept = noLowerDept;
	}
}
