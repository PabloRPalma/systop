package com.systop.common.security.user.dao;

import java.util.List;

import com.systop.common.Constants;
import com.systop.common.dao.impl.BaseGenericsDAO;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.User;

/**
 * <code>User</code> Data Access Object. 因为用户操作涉及面比较广泛，所以单独实现一个DAO。
 * @author Sam
 * 
 */
public class UserDao extends BaseGenericsDAO<User> {

  /**
   * 通过User找出其相对应的所有合法的<code>Permission</code>
   * @param user 给定的<code>User</code>,应该通过get或load获得(id不能为null)
   */
  public List<Permission> findPermissionByUser(User user) {
    String hql = "select permi from Permission permi"
        + " inner join permi.roles as role "
        + " inner join role.users as user where permi.status='"
        + Constants.STATUS_AVAILABLE + "' and user=?";
    
    return find(hql, user);
  }

  /**
   * 通过Permission找出其相对应的所有合法的users
   * @param permi 给定的<code>Permission</code>对象，id不能为null
   */
  public List<User> findUsersByPermission(Permission permi) {
    String hql = "select user from Permission permi "
        + "inner join permi.roles as role "
        + "inner join role.users as user where permi.status='"
        + Constants.STATUS_AVAILABLE + "' and permi=?";

    return find(hql, permi);
  }
}
