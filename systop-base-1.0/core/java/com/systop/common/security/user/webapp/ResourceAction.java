package com.systop.common.security.user.webapp;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Resource;
import com.systop.common.security.user.service.ResourceManager;
import com.systop.common.webapp.struts2.action.BaseModelAction;

/**
 * <code>Resource</code>action类. 用于webwork.
 * @author Sam
 * 
 */
public class ResourceAction extends BaseModelAction<Resource, ResourceManager> {
  /**
   * 检查资源名是否重复，如果重复，返回true
   */
  public boolean isNameInUse(String name) {
    return getManager().isNameInUse(name);
  }

  /**
   * 查找权限拥有的资源
   * @param perm 权限
   * @return 权限拥有的资源
   */
  public Resource[] getByPerm(Permission perm) {
    return getManager().getByPerm(perm);
  }

  /**
   * @see BaseModelAction#setupDetachedCriteria()
   */
  @Override
  protected DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
    dc.add(Restrictions.like("name", model.getName(), MatchMode.ANYWHERE)).add(
        Restrictions
            .like("resString", model.getResString(), MatchMode.ANYWHERE));
    if (!model.getResType().equals("")) {
      dc.add(Restrictions.eq("resType", model.getResType()));
    }
    return dc;

  }

}
