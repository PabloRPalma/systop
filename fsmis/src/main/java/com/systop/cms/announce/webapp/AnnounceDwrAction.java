package com.systop.cms.announce.webapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.systop.cms.announce.service.AnnounceManager;
import com.systop.cms.model.Announces;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.webapp.struts2.action.DefaultCrudAction;

/**
 * AnnounceDwrAction
 * 
 * @author jun
 */
@SuppressWarnings("serial")
@Component
public class AnnounceDwrAction extends DefaultCrudAction<Announces, AnnounceManager> {
  /**
   * 栏目管理Manager
   */
  private AnnounceManager announceManager;

  /**
   * 查询部门
   * 
   * @return List 部门列表
   */
  @java.lang.Deprecated
  @SuppressWarnings("unchecked")
  public List getDepts() {
    // 将对象转化Map后的list
    List deptList = new ArrayList();
    // 得到登录用户的
    // Dept userDept = getUser().getDept();
    // if (userDept == null) {
    // List<Dept> depts = getManager().getDao().query("from Dept");
    // for (Iterator<Dept> itr = depts.iterator(); itr.hasNext();) {
    // Dept dept = itr.next();
    // deptList.add(deptToMap(dept));
    //    }
    // } else {
    // deptList.add(deptToMap(userDept));
    // }
    return deptList;
  }

  /**
   * 获取当前登录的用户信息
   * 
   * @return
   */
  public User getUser() {
    return com.systop.common.modules.security.user.UserUtil.getPrincipal(getRequest());
  }

  /**
   * 将Dept对象转化成Map
   * 
   * @param dept 部门
   * @return
   */
  // @java.lang.Deprecated
  // @SuppressWarnings("unchecked")
  // private Map deptToMap(Dept dept) {
  // Map deptMap = new HashMap();
  // deptMap.put("id", dept.getId());
  // deptMap.put("name", dept.getName());
  // return deptMap;
  //  }

  public AnnounceManager getAnnounceManager() {
    return announceManager;
  }

  public void setAnnounceManager(AnnounceManager announceManager) {
    this.announceManager = announceManager;
  }

  /**
   * 根据公告的ID查询部门
   * 
   * @param announceId 公告ID
   * @return String 部门ID
   */
  @java.lang.Deprecated
  public String getDeptByAnnouneId(String announceId) {
    // List<Announces> announcesList = getManager().getDao().query("from Announces a where a.id =
    // ?",
    // Integer.valueOf(announceId));
    // if (!announcesList.isEmpty()) {
    // Announces announce = announcesList.get(0);
    // return announce.getDept().getId().toString();
    // }
    return "";
  }
}
