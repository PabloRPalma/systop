/**
 * 
 */
package com.systop.common.security.user.webapp;

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

import com.systop.common.dao.Page;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.service.PermissionManager;
import com.systop.common.security.user.service.RoleManager;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;

/**
 * DWR action class for <tt>Permission</tt>.
 * @author han
 * 
 */
public class PermDwrAction extends BaseDwrAjaxAction {
  /**
   * Manager for Role
   */
  private RoleManager roleManager;
  /**
   * Manager for Permission
   */
  private PermissionManager permManager;

  /**
   * 返回所有权限，同时查询指定角色所具有的权限，并在“所有”权限中标注该角色所拥有
   *  的 权限的selected状态。
   * @param roleId 指定角色id
   * @return List of permissions.
   */
  public Page getPermsOfRole(Integer roleId, int pageNo, int pageSize,
      String permName, String permOperation, String status) {
    Role role = roleManager.get(roleId);
    if (role == null) {
      log.warn("Role with id " + roleId + " does not exists.");
      return Page.EMPTY_PAGE;
    }

    // 得到该角色的权限
    Set<Permission> permsOfRole = role.getPermissions();
    log
        .debug("Role " + role.getId() + " has " + permsOfRole.size()
            + " roles.");
    Page page = null;
    // 根据前台传过来的参数动态生成查询条件，只把参数有值的参数作为查询条件
    String hsql = "";
    Object[] hsqlParams;
    List hsqlParamLst = new ArrayList();
    if (!StringUtils.isBlank(permName)) {
      hsql = hsql.concat(" and r.name like ?");
      hsqlParamLst.add("%" + permName + "%");
    }
    if (!StringUtils.isBlank(permOperation)) {
      hsql = hsql.concat(" and r.operation = ?");
      hsqlParamLst.add(permOperation);
    }
    if (!StringUtils.isBlank(status)) {
      hsql = hsql.concat(" and r.status = ?");
      hsqlParamLst.add(status);
    }
    hsqlParams = new Object[hsqlParamLst.size()];
    hsqlParamLst.toArray(hsqlParams);
    if (!hsql.equals("")) {
      hsql = hsql.substring(" and ".length());
      hsql = "from Permission r where ".concat(hsql);
    } else {
      hsql = "from Permission r";
    }
    page = permManager.query(hsql, pageNo, pageSize, hsqlParams);
    // 得到所有权限
    List allPerms = (List) page.getResult();
    // 从session中取得暂存的待分配的Permissions
    Set templatePermsAdded = getTemporaryPerms(role, TEMPLATE_ADDED_PERMS);

    // 从session中取得暂存的待反分配的Permissions
    Set templatePermsRemoved = getTemporaryPerms(role, TEMPLATE_REMOVED_PERMS);

    for (Iterator itr = allPerms.iterator(); itr.hasNext();) {
      Permission perm = (Permission) itr.next();
      perm.setSelected(false);
      if (permsOfRole.contains(perm)) { // 如果权限已经分配给角色，则选中
        perm.setSelected(true);
      }
      // 如果权限暂时分配了，则选中
      if (templatePermsAdded != null
          && templatePermsAdded.contains(perm.getId())) {
        perm.setSelected(true);
      }
      // 如果权限暂时删除了，则不选中
      if (templatePermsRemoved != null
          && templatePermsRemoved.contains(perm.getId())) {
        perm.setSelected(false);
      }
    }

    return page;
  }

  /**
   * @see PermissionManager#isNameInUse(java.lang.String)
   */
  public boolean isNameInUse(String name) {
    Permission perm = new Permission();
    perm.setName(name);
    if (log.isDebugEnabled()) {
      log.debug("check for permission name '" + name + "'");
    }
    return permManager.exists(perm, "name");
  }

  /**
   * 为角色添加的权限暂存在Session中名字
   */
  private static final String TEMPLATE_ADDED_PERMS = "perms_added_session";

  /**
   * 为角色删除的权限暂存在Session中名字
   */
  private static final String TEMPLATE_REMOVED_PERMS = "perms_removed_session";

  /**
   * 页面通过AJAX方式把为角色分配的权限Id和角色Id传入服务器端。<tt>addPermission</tt>
   * 方法将权限Id保存在session中的一个Map对象中，该对象以Role实例和PermissionId为 key-value.
   * @param permId 权限id
   * @param roleId 角色id
   * @param selected 是删除（false）还是添加（true）
   */
  public void selectPerm(Integer permId, Integer roleId, boolean selected) {
    if (permId == null || roleId == null) {
      return;
    }
    // 根据ID获得Permission和Role，其中Role将作为“key”保存在Map对象中
    Role role = roleManager.get(roleId);
    Permission perm = permManager.get(permId);
    if (role == null || perm == null) {
      return;
    }
    if (selected) {
      temporaryAddPerm(role, perm);
    } else {
      temporaryRemovePerm(role, perm);
    }

  }

  /**
   * 从Session中取得某个角色的临时Permission，包括待分配和待反分配的。
   * @param role 指定的角色
   * @param sessionName Session名字
   * 
   * @return
   */
  private Set getTemporaryPerms(Role role, String sessionName) {
    Map rolePerms = (Map) getSession().getAttribute(sessionName);
    if (rolePerms == null) {
      rolePerms = Collections.synchronizedMap(new HashMap());
      getSession().setAttribute(sessionName, rolePerms);
    }

    Set permIds = (Set) rolePerms.get(role);
    if (permIds == null) {
      permIds = Collections.synchronizedSet(new HashSet());
      rolePerms.put(role, permIds);
    }

    return permIds;
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个待分配的Permission ID
   * @param role 给定角色
   * @param perm 即将分配给角色的Permission
   */
  private void temporaryAddPerm(Role role, Permission perm) {
    // 从Session中取得该角色临时分配的权限Id
    Set permIdsAdded = getTemporaryPerms(role, TEMPLATE_ADDED_PERMS);

    // 如果新分配的Permission还没有保存在数据库中，则暂存这个Permission ID
    if (!role.getPermissions().contains(perm)) {
      permIdsAdded.add(perm.getId());
      log.debug("Temporary add permission " + perm.getId() + " for role "
          + role.getId());
    }
    // 同步即将删除的权限
    Set permIdsRemoved = getTemporaryPerms(role, TEMPLATE_REMOVED_PERMS);
    permIdsRemoved.remove(perm.getId());
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个反分配的Permission ID
   * @param role 给定角色
   * @param perm 即将从角色的权限中删除Permission
   */
  private void temporaryRemovePerm(Role role, Permission perm) {
    // 从session中取得临时反分配的Permission ID
    Set permIdsRemoved = getTemporaryPerms(role, TEMPLATE_REMOVED_PERMS);

    // 如果该权限已经分配给角色了，则暂存
    if (role.getPermissions().contains(perm)) {
      permIdsRemoved.add(perm.getId());
      log.debug("Temporary remove permission " + perm.getId() + " for role "
          + role.getId());
    }
    // 同步即将添加的权限
    Set permIdsAdded = getTemporaryPerms(role, TEMPLATE_ADDED_PERMS);
    permIdsAdded.remove(perm.getId());
  }

  /**
   * 为指定的角色分配权限
   * @param roleId Id of the role to be assigned.
   * @return true if successed ,otherwise, false.
   */
  public boolean saveRolePerms(Integer roleId) {
    if (roleId == null) {
      return false;
    }

    Role role = roleManager.get(roleId);
    if (role == null) {
      return false;
    }
    Set<Integer> perms = getTemporaryPerms(role, TEMPLATE_REMOVED_PERMS);
    for (Integer permId : perms) {
      Permission perm = permManager.get(permId);
      if (perm != null) {
        perm.getRoles().remove(role);
        role.getPermissions().remove(perm);
      }
    }

    perms = getTemporaryPerms(role, TEMPLATE_ADDED_PERMS);
    for (Integer permId : perms) {
      Permission perm = permManager.get(permId);
      if (perm != null) {
        perm.getRoles().add(role);
        role.getPermissions().add(perm);
      }
    }

    roleManager.save(role);
    clearSession(role);

    return true;
  }

  /**
   * 取消保存角色的权限信息
   * @param roleId 角色Id
   */
  public void cancelSaveRolePerms(Integer roleId) {
    Role role = roleManager.get(roleId);
    if (role == null) {
      return;
    }
    clearSession(role);
  }

  /**
   * 清空session中有关角色的数据
   * 
   */
  private void clearSession(Role role) {
    HttpSession session = getRequest().getSession();
    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_PERMS);
    if (map.containsKey(role)) {
      map.remove(role);
    }
    map = (Map) session.getAttribute(TEMPLATE_REMOVED_PERMS);
    if (map.containsKey(role)) {
      map.remove(role);
    }
    log.debug("Session of role " + role.getName() + " cleared.");
  }

  /**
   * @return the permManager
   */
  public PermissionManager getPermManager() {
    return permManager;
  }

  /**
   * @param permManager the permManager to set
   */
  public void setPermManager(PermissionManager permManager) {
    this.permManager = permManager;
  }

  /**
   * @return the roleManager
   */
  public RoleManager getRoleManager() {
    return roleManager;
  }

  /**
   * @param roleManager the roleManager to set
   */
  public void setRoleManager(RoleManager roleManager) {
    this.roleManager = roleManager;
  }

}
