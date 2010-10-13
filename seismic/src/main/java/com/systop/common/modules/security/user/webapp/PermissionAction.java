package com.systop.common.modules.security.user.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.Permission;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.service.PermissionManager;
import com.systop.common.modules.security.user.service.RoleManager;
import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;

/**
 * 权限Action
 * 
 * @author Sam Lee
 */
@SuppressWarnings({"unchecked", "serial"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PermissionAction extends
		ExtJsCrudAction<Permission, PermissionManager> {
  /**
   * 为用户添加的权限暂存在Session中
   */
  private static final String TEMPLATE_ADDED_PERMISSIONS = 
    "permissions_added_session";

  /**
   * 为角色删除的权限暂存在Session中
   */
  private static final String TEMPLATE_REMOVED_PERMISSIONS = 
    "permissions_removed_session";
  /**
   * The role service.
   */
  private RoleManager roleManager;
  /**
   * The role that will be assigned permissions for.
   */
  private Role role = new Role();
  
	/**
	 * 按权限名称和角色查询
	 * 
	 * @see BaseModelAction#query()
	 */
	@Override
	public String index() {
		if (StringUtils.isBlank(getModel().getName())
				&& StringUtils.isBlank(getModel().getDescn())) {
			items = getManager().query("from Permission");
		} else {
			items = getManager().query(
					"from Permission p where p.name like ? and p.descn like ?",
					new Object[] {
							MatchMode.ANYWHERE.toMatchString(getModel().getName()),
							MatchMode.ANYWHERE.toMatchString(getModel().getDescn()) });
		}
		return INDEX;
	}

	/**
	 * 返回权限操作（权限类型）列表
	 */
	public Map<String, String> getOperations() {
		return UserConstants.PERMISSION_OPERATIONS;
	}

	@Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.name", message = "权限名称是必须的."),
      @RequiredStringValidator(fieldName = "model.descn", message = "权限描述是必须的.")})
  @Override
  public String save() {
	  if (!getModel().getName().startsWith("AUTH_")) {
      addActionError("权限名称必须以AUTH_开头.");
      return INPUT;
    }
	  return super.save();
  }
	
	//The following methods are used for assigning permissions for role.
  /**
   * 返回所有权限，同时查询指定角色所具有的权限， 并在“所有”权限中标注该角色所拥有的权限的选择状态
   * @param roleId 指定角色id
   * 
   * @return
   */
  public String permissionsOfRole() {
    initRole();
    // 得到该角色的权限
    Set<Permission> permissionsOfRole = role.getPermissions();
    logger.debug("Role {} has {} permissions.",  
        role.getId(), permissionsOfRole.size());
    page = new Page(Page.start(getPageNo(), getPageSize()), getPageSize());
    if (StringUtils.isBlank(getModel().getName())) {
      page = getManager().pageQuery(page, getOrderHQL("from Permission p"));
    } else {
      page = getManager().pageQuery(page, getOrderHQL("from Permission p where p.name like ?"), "%" + getModel().getName() + "%");
    }
    // 得到所有权限
    List allPermissions = page.getData();
    // 从session中取得暂存的待分配的权限
    Set templatePermissionsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);
    // 从session中取得暂存的待反分配的权限
    Set templatePermissionsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    List mapPerms = new ArrayList(allPermissions.size());
    
    for (Iterator itr = allPermissions.iterator(); itr.hasNext();) {
      Permission permission = (Permission) itr.next();
      permission.setChanged(false);
      if (permissionsOfRole.contains(permission)) { // 如果权限已经分配给角色则选中
        permission.setChanged(true);
      }
      // 如果权限暂时分配了则选中
      if (templatePermissionsAdded != null
          && templatePermissionsAdded.contains(permission.getId())) {
        permission.setChanged(true);
      }
      // 如果权限暂时删除了则不选
      if (templatePermissionsRemoved != null
          && templatePermissionsRemoved.contains(permission.getId())) {
        permission.setChanged(false);
      }
     //    转换为Map，防止延迟加载
      Map mapPerm = ReflectUtil.toMap(permission, 
          new String[]{"id", "name", "descn"}, true);
      mapPerm.put("changed", permission.getChanged());
      mapPerms.add(mapPerm);
    }
    page.setData(mapPerms);
    
    return JSON;
  }
  
  /**
   * 取消保存角色的权限信息
   */
  public String cancelSaveRolePermissions() {
    initRole();
    clearSession(role);
    return JSON;
  }
  
  /**
   * 客户端通过checkbox选择一个角色，通知到服务器端，将这个role存入session.
   */
  public String selectPermission() {
    if(getModel() == null || getModel().getId() == null ||
        role == null || role.getId() == null) {
      throw new ApplicationException("Please select a permission at least.");
    }
    
    selectPermission(getModel().getId(), role.getId(), true);
    return JSON;
  }
  /**
   * 客户端通过checkbox反选择一个角色，通知到服务器端，将这个role从session中去除.
   */
  public String deselectPermission() {
    if(getModel() == null || getModel().getId() == null ||
        role == null || role.getId() == null) {
      throw new ApplicationException("Please select a role at least.");
    }
    
    selectPermission(getModel().getId(), role.getId(), false);
    return JSON;
  }
  
  /**
   * 为指定的角色分配权限
   * @param roleId 分配角色的id
   * @return 成功：successed 失败： false.
   */
  public String saveRolePermissions() {
    initRole();
    Set<Integer> permissions = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    for (Integer permissionId : permissions) {
      Permission permission = getManager().get(permissionId);
      if (permission != null) {
        permission.getRoles().remove(role);
        role.getPermissions().remove(permission);
      }
    }

    permissions = getTemporaryPermissions(role, TEMPLATE_ADDED_PERMISSIONS);
    for (Integer permissionId : permissions) {
      Permission permission = getManager().get(permissionId);
      if (permission != null) {
        permission.getRoles().add(role);
        role.getPermissions().add(permission);
      }
    }

    roleManager.save(role);
    clearSession(role);

    return JSON;
  }

  /**
   * Build a hql with order clause
   */
  private String getOrderHQL(String initHQL) {
    if(StringUtils.isBlank(getSortProperty())) {
      return initHQL;
    }
    return new StringBuffer(100).append(initHQL).append(" order by p.")
      .append(getSortProperty()).append(" ").append(getSortDir()).toString();
  }
  /**
   * 向Sesson中存入一个Permission ID, 用于支持页面上分页的时候记录选择的Permission
   */
  private void selectPermission(Integer permissionId, Integer roleId, boolean selected) {
    if (permissionId == null) {
      return;
    }
    initRole();
    setModel(getManager().get(getModel().getId()));
    if (role == null || getModel() == null) {
      return;
    }
    if (selected) {
      temporaryAddPermission(role, getModel());
    } else {
      temporaryRemovePermission(role, getModel());
    }
  }
  
  /**
   * 初始<code>role</code>对象
   */
  private void initRole() {
    if (role == null || role.getId() == null) {
      logger.warn("No role found.");
      throw new ApplicationException("No role found, check your 'currentRoleId' var in js.");
    }

    role = getManager().getDao().get(Role.class, role.getId());
    if (role == null) {
      logger.warn("No role found {}", role.getId());
      throw new ApplicationException("No role found, check your 'currentRoleId' var in js.");
    }
  }

  /**
   * 从Session中取得某个角色的临时权限，包括待分配和待反分配的
   * @param role 指定的角色
   * @param sessionName Session名字
   * 
   * @return
   */
  private Set getTemporaryPermissions(Role role, String sessionName) {
    Map rolePermissions = (Map) getRequest().getSession().getAttribute(
        sessionName);
    if (rolePermissions == null) {
      rolePermissions = Collections.synchronizedMap(new HashMap());
      getRequest().getSession().setAttribute(sessionName, rolePermissions);
    }
    Set permissionIds = (Set) rolePermissions.get(role.getId());
    if (permissionIds == null) {
      permissionIds = Collections.synchronizedSet(new HashSet());
      rolePermissions.put(role.getId(), permissionIds);
    }

    return permissionIds;
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个待分配的Permission ID
   * @param role 给定角色
   * @param permission 即将分配给角色的Permission
   */
  private void temporaryAddPermission(Role role, Permission permission) {
    // 从Session中取得该角色临时分配的权限Id
    Set permissionIdsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);

    // 如果新分配的Permission还没有保存在数据库中，则暂存这个Permission ID
    if (!role.getPermissions().contains(permission)) {
      permissionIdsAdded.add(permission.getId());
      logger.debug("Temporary add permission {} for role {}", 
          permission.getId(), role.getId());
    }
    // 同步即将删除的权限
    Set permissionIdsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    permissionIdsRemoved.remove(permission.getId());
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个反分配的Permission ID
   * @param role 给定角色
   * @param permission 即将从角色的权限中删除Permission
   */
  private void temporaryRemovePermission(Role role, Permission permission) {
    // 从session中取得临时反分配的Permission ID
    Set permissionIdsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    // 如果该权限已经分配给角色了，则暂存
    if (role.getPermissions().contains(permission)) {
      permissionIdsRemoved.add(permission.getId());
      logger.debug("Temporary remove permission {} for role {}",
          permission.getId(), role.getId());
    }
    // 同步即将添加的权限
    Set permissionIdsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);
    permissionIdsAdded.remove(permission.getId());
  }

  /**
   * 清空session中有关角色的数据
   * 
   */
  private void clearSession(Role role) {
    HttpSession session = getRequest().getSession();
    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_PERMISSIONS);
    if (map.containsKey(role.getId())) {
      map.remove(role.getId());
    }
    map = (Map) session.getAttribute(TEMPLATE_REMOVED_PERMISSIONS);
    if (map.containsKey(role.getId())) {
      map.remove(role.getId());
    }
    logger.debug("Session of role {} cleared.", role.getName());
  }

  /**
   * @param roleManager the roleManager to set
   */
  @Autowired
  public void setRoleManager(RoleManager roleManager) {
    this.roleManager = roleManager;
  }

  /**
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role the role to set
   */
  public void setRole(Role role) {
    this.role = role;
  }

}
