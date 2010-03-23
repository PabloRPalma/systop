package com.systop.common.security.user.webapp;

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
import com.systop.common.security.user.model.Role;
import com.systop.common.security.user.model.User;
import com.systop.common.security.user.service.RoleManager;
import com.systop.common.security.user.service.UserManager;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;
/**
 * DWR action class for <tt>Role</tt>.
 * 
 * @author Sam
 *
 */
public class RoleDwrAction extends BaseDwrAjaxAction {
  /**
   * Manager for User
   */
  private UserManager userManager;
  /**
   * Manager for Role
   */
  private RoleManager roleManager;
  /**
   * 返回所有角色，同时查询指定用户所具有的角色，并在“所有”角色中标注该用户所拥有 的
   * 角色的selected状态。
   * @param userId 指定用户id
   * @return List of roles.
   */
  public Page getRolesOfUser(Integer userId, int pageNo, int pageSize, 
      String roleName) {
    User user = userManager.get(userId);
    if (user == null) {
      log.warn("User with id " + userId + " does not exists.");
      return Page.EMPTY_PAGE;
    }

    // 得到该用户的角色
    Set<Role> rolesOfUser = user.getRoles();
    log
        .debug("User " + user.getId() + " has " + rolesOfUser.size()
            + " roles.");
    Page page = null;
    if (StringUtils.isBlank(roleName)) {
      page = roleManager.query("from Role r", pageNo, pageSize);
    } else {
      page = roleManager.query("from Role r where r.name like ?", pageNo,
          pageSize, "%" + roleName + "%");
    }
    // 得到所有角色
    List allRoles = (List) page.getResult();
    // 从session中取得暂存的待分配的Roles
    Set templateRolesAdded = getTemporaryRoles(user, TEMPLATE_ADDED_ROLES);

    // 从session中取得暂存的待反分配的Roles
    Set templateRolesRemoved = getTemporaryRoles(user, TEMPLATE_REMOVED_ROLES);

    for (Iterator itr = allRoles.iterator(); itr.hasNext();) {
      Role role = (Role) itr.next();
      role.setSelected(false);
      if (rolesOfUser.contains(role)) { // 如果角色已经分配给用户，则选中
        role.setSelected(true);
      }
      // 如果角色暂时分配了，则选中
      if (templateRolesAdded != null
          && templateRolesAdded.contains(role.getId())) {
        role.setSelected(true);
      }
      // 如果角色暂时删除了，则不选中
      if (templateRolesRemoved != null
          && templateRolesRemoved.contains(role.getId())) {
        role.setSelected(false);
      }
    }

    return page;
  }

  /**
   * 检查角色名是否正被使用
   * @param rolename 角色名
   * @return true:角色名已经被使用;false:角色名未被使用
   */
  public boolean isRoleNameInUse(String rolename) {
    Role role = new Role();
    role.setName(rolename);
    if (log.isDebugEnabled()) {
      log.debug("check for role name '" + rolename + "'");
    }
    //  如果给定name不唯一，则表示正在使用
    return roleManager.exists(role, "name"); 
  }

  /**
   * 为用户添加的角色暂存在Session中名字
   */
  private static final String TEMPLATE_ADDED_ROLES = "roles_added_session";

  /**
   * 为用户删除的角色暂存在Session中名字
   */
  private static final String TEMPLATE_REMOVED_ROLES = "roles_removed_session";

  /**
   * 页面通过AJAX方式把为用户分配的角色Id和用户Id传入服务器端。<tt>addRole</tt>
   * 方法将角色Id保存在session中的一个Map对象中，该对象以User实例和RoleId为key-value.
   * @param roleId 角色id
   * @param userId 用户id
   * @param selected 是删除（false）还是添加（true）
   */
  public void selectRole(Integer roleId, Integer userId, boolean selected) {
    if (roleId == null || userId == null) {
      return;
    }
    // 根据ID获得Role和User，其中User将作为“key”保存在Map对象中
    User user = userManager.get(userId);
    Role role = roleManager.get(roleId);
    if (user == null || role == null) {
      return;
    }
    if (selected) {
      temporaryAddRole(user, role);
    } else {
      temporaryRemoveRole(user, role);
    }

  }

  /**
   * 从Session中取得某个用户的临时Role，包括待分配和待反分配的。
   * @param user 指定的用户
   * @param sessionName Session名字
   * 
   * @return
   */
  private Set getTemporaryRoles(User user, String sessionName) {
    Map userRoles = (Map) getSession().getAttribute(sessionName);
    if (userRoles == null) {
      userRoles = Collections.synchronizedMap(new HashMap());
      getSession().setAttribute(sessionName, userRoles);
    }

    Set roleIds = (Set) userRoles.get(user);
    if (roleIds == null) {
      roleIds = Collections.synchronizedSet(new HashSet());
      userRoles.put(user, roleIds);
    }

    return roleIds;
  }

  /**
   * 根据用户的授权情况，在Session中暂存一个待分配的Role ID
   * @param user 给定用户
   * @param role 即将分配给用户的Role
   */
  private void temporaryAddRole(User user, Role role) {
    // 从Session中取得该用户临时分配的角色Id
    Set roleIdsAdded = getTemporaryRoles(user, TEMPLATE_ADDED_ROLES);

    // 如果新分配的Role还没有保存在数据库中，则暂存这个Role ID
    if (!user.getRoles().contains(role)) {
      roleIdsAdded.add(role.getId());
      log.debug("Temporary add role " + role.getId() + " for user "
          + user.getId());
    }
    // 同步即将删除的角色
    Set roleIdsRemoved = getTemporaryRoles(user, TEMPLATE_REMOVED_ROLES);
    roleIdsRemoved.remove(role.getId());
  }

  /**
   * 根据用户的授权情况，在Session中暂存一个反分配的Role ID
   * @param user 给定用户
   * @param role 即将从用户的角色中删除Role
   */
  private void temporaryRemoveRole(User user, Role role) {
    // 从session中取得临时反分配的Role ID
    Set roleIdsRemoved = getTemporaryRoles(user, TEMPLATE_REMOVED_ROLES);

    // 如果该角色已经分配给用户了，则暂存
    if (user.getRoles().contains(role)) {
      roleIdsRemoved.add(role.getId());
      log.debug("Temporary remove role " + role.getId() + " for user "
          + user.getId());
    }
    // 同步即将添加的角色
    Set roleIdsAdded = getTemporaryRoles(user, TEMPLATE_ADDED_ROLES);
    roleIdsAdded.remove(role.getId());
  }

  /**
   * 为指定的用户分配角色
   * @param userId Id of the user to be assigned.
   * @return true if successed ,otherwise, false.
   */
  public boolean saveUserRoles(Integer userId) {
    if (userId == null) {
      return false;
    }

    User user = userManager.get(userId);
    if (user == null) {
      return false;
    }
    Set<Integer> roles = getTemporaryRoles(user, TEMPLATE_REMOVED_ROLES);
    for (Integer roleId : roles) {
      Role role = roleManager.get(roleId);
      if (role != null) {
        role.getUsers().remove(user);
        user.getRoles().remove(role);
      }
    }

    roles = getTemporaryRoles(user, TEMPLATE_ADDED_ROLES);
    for (Integer roleId : roles) {
      Role role = roleManager.get(roleId);
      if (role != null) {
        role.getUsers().add(user);
        user.getRoles().add(role);
      }
    }

    userManager.save(user);
    clearSession(user);

    return true;
  }

  /**
   * 取消保存用户的角色信息
   * @param userId 用户Id
   */
  public void cancelSaveUserRoles(Integer userId) {
    User user = userManager.get(userId);
    if (user == null) {
      return;
    }
    clearSession(user);
  }

  /**
   * 清空session中有关用户的数据
   * 
   */
  private void clearSession(User user) {
    HttpSession session = getRequest().getSession();
    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_ROLES);
    if (map.containsKey(user)) {
      map.remove(user);
    }
    map = (Map) session.getAttribute(TEMPLATE_REMOVED_ROLES);
    if (map.containsKey(user)) {
      map.remove(user);
    }
    log.debug("Session of user " + user.getName() + " cleared.");
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

  /**
   * @return the userManager
   */
  public UserManager getUserManager() {
    return userManager;
  }

  /**
   * @param userManager the userManager to set
   */
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }
}
