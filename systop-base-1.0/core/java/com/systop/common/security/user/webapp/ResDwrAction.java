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
import com.systop.common.security.user.model.Resource;
import com.systop.common.security.user.service.PermissionManager;
import com.systop.common.security.user.service.ResourceManager;
import com.systop.common.webapp.struts2.action.BaseDwrAjaxAction;

/**
 * @author Administrator
 * 
 */
public class ResDwrAction extends BaseDwrAjaxAction {
  /**
   * Manager for Permission
   */
  private PermissionManager permManager;
  /**
   * Manager for Resource
   */
  private ResourceManager resManager;
	/**
	 * 返回所有资源，同时查询指定权限所具有的资源，并在“所有”资源中标注该权限所
	 * 拥有 的 资源的selected状态。
	 * 
	 * @param permid 指定权限id
	 * @return List of resources.
	 */
	public Page getResourcesOfPerm(Integer permid, int pageNo, int pageSize,
			String resName, String resStr, String resType) {
		Permission perm = permManager.get(permid);
		if (perm == null) {
			log.warn("Permission with id " + permid + " does not exists.");
			return Page.EMPTY_PAGE;
		}

		// 得到该权限的资源
		Set<Resource> resourcesOfPerm = perm.getResources();
		log.debug("Permission " + perm.getId() + " has " 
				+ resourcesOfPerm.size() + " permissions.");
		Page page = null;
		// 根据前台传过来的参数动态生成查询条件，只把参数有值的参数作为查询条件
		String hsql = "";
		Object[] hsqlParams;
		List hsqlParamLst = new ArrayList();
		if (!StringUtils.isBlank(resName)) {
			hsql = hsql.concat(" and r.name like ?");
			hsqlParamLst.add("%" + resName + "%");
		}
		if (!StringUtils.isBlank(resStr)) {
			hsql = hsql.concat(" and r.resString like ?");
			hsqlParamLst.add("%" + resStr + "%");
		}
		if (!StringUtils.isBlank(resType)) {
			hsql = hsql.concat(" and r.resType = ?");
			hsqlParamLst.add(resType);
		}
		hsqlParams = new Object[hsqlParamLst.size()];
		hsqlParamLst.toArray(hsqlParams);
		if (!hsql.equals("")) {
			hsql = hsql.substring(" and ".length());
			hsql = "from Resource r where ".concat(hsql);
		} else {
			hsql = "from Resource r";
		}
		page = resManager.query(hsql, pageNo, pageSize, hsqlParams);
		// 得到所有资源
		List allResources = (List) page.getResult();
		// 从session中取得暂存的待分配的Resources
		Set templateResourcesAdded = getTemporaryResources(perm,
				TEMPLATE_ADDED_RESOURCES);

		// 从session中取得暂存的待反分配的Resources
		Set templateResourcesRemoved = getTemporaryResources(perm,
				TEMPLATE_REMOVED_RESOURCES);

		for (Iterator itr = allResources.iterator(); itr.hasNext();) {
			Resource res = (Resource) itr.next();
			res.setSelected(false);
			if (resourcesOfPerm.contains(res)) { // 如果资源已经分配给权限，则选中
				res.setSelected(true);
			}
			// 如果资源暂时分配了，则选中
			if (templateResourcesAdded != null
					&& templateResourcesAdded.contains(res.getId())) {
				res.setSelected(true);
			}
			// 如果资源暂时删除了，则不选中
			if (templateResourcesRemoved != null
					&& templateResourcesRemoved.contains(res.getId())) {
				res.setSelected(false);
			}
		}

		return page;
	}

	/**
	 * @see ResourceManager#isNameInUse(java.lang.String)
	 */
	public boolean isNameInUse(String name) {
		Resource res = new Resource();
		res.setName(name);
		if (log.isDebugEnabled()) {
			log.debug("check for resource name '" + name + "'");
		}
		return resManager.exists(res, "name");
	}

	/**
	 * 为权限添加的资源暂存在Session中名字
	 */
	private static final String TEMPLATE_ADDED_RESOURCES 
	= "resources_added_session";

	/**
	 * 为权限删除的资源暂存在Session中名字
	 */
	private static final String TEMPLATE_REMOVED_RESOURCES 
	= "resources_removed_session";

	/**
	 * 页面通过AJAX方式把为权限分配的资源Id和权限Id传入服务器端。<tt>addResource</tt>
	 * 方法将资源Id保存在session中的一个Map对象中，该对象以Permission实例和ResourceId
	 * 为 key-value.
	 * 
	 * @param resId
	 *          资源id
	 * @param permId
	 *          权限id
	 * @param selected
	 *          是删除（false）还是添加（true）
	 */
	public void selectRes(Integer resId, Integer permId, boolean selected) {
		if (resId == null || permId == null) {
			return;
		}
		// 根据ID获得Resource和Permission，其中Permission将作为“key”保存在Map对象中
		Permission perm = permManager.get(permId);
		Resource res = resManager.get(resId);
		if (perm == null || res == null) {
			return;
		}
		if (selected) {
			temporaryAddRes(perm, res);
		} else {
			temporaryRemoveRes(perm, res);
		}

	}

	/**
	 * 从Session中取得某个权限的临时Resource，包括待分配和待反分配的。
	 * 
	 * @param perm
	 *          指定的权限
	 * @param sessionName
	 *          Session名字
	 * 
	 * @return
	 */
	private Set getTemporaryResources(Permission perm, String sessionName) {
		Map permResources = (Map) getSession().getAttribute(sessionName);
		if (permResources == null) {
			permResources = Collections.synchronizedMap(new HashMap());
			getSession().setAttribute(sessionName, permResources);
		}

		Set resIds = (Set) permResources.get(perm);
		if (resIds == null) {
			resIds = Collections.synchronizedSet(new HashSet());
			permResources.put(perm, resIds);
		}

		return resIds;
	}

	/**
	 * 根据权限的授权情况，在Session中暂存一个待分配的资源 ID
	 * 
	 * @param perm
	 *          给定权限
	 * @param res
	 *          即将分配给权限的Resource
	 */
	private void temporaryAddRes(Permission perm, Resource res) {
		// 从Session中取得该权限临时分配的资源Id
		Set resIdsAdded = getTemporaryResources(perm, TEMPLATE_ADDED_RESOURCES);

		// 如果新分配的Resource还没有保存在数据库中，则暂存这个Resource ID
		if (!perm.getResources().contains(res)) {
			resIdsAdded.add(res.getId());
			log.debug("Temporary add resource " + res.getId() 
					+ " for permission " + perm.getId());
		}
		// 同步即将删除的资源
		Set resIdsRemoved = getTemporaryResources(perm, 
				TEMPLATE_REMOVED_RESOURCES);
		resIdsRemoved.remove(res.getId());
	}

	/**
	 * 根据权限的授权情况，在Session中暂存一个反分配的Resource ID
	 * 
	 * @param perm
	 *          给定权限
	 * @param res
	 *          即将从权限的资源中删除Resource
	 */
	private void temporaryRemoveRes(Permission perm, Resource res) {
		// 从session中取得临时反分配的Resource ID
		Set resIdsRemoved = getTemporaryResources(perm, 
				TEMPLATE_REMOVED_RESOURCES);

		// 如果该资源已经分配给权限了，则暂存
		if (perm.getResources().contains(res)) {
			resIdsRemoved.add(res.getId());
			log.debug("Temporary remove resource " + res.getId() 
					+ " for permission " + perm.getId());
		}
		// 同步即将添加的资源
		Set resIdsAdded = getTemporaryResources(perm, TEMPLATE_ADDED_RESOURCES);
		resIdsAdded.remove(res.getId());
	}

	/**
	 * 为指定的权限分配资源
	 * 
	 * @param permId
	 *          Id of the permissiion to be assigned.
	 * @return true if successed ,otherwise, false.
	 */
	public boolean savePermResources(Integer permId) {
		if (permId == null) {
			return false;
		}

		Permission perm = permManager.get(permId);
		if (perm == null) {
			return false;
		}
		Set<Integer> resources = getTemporaryResources(perm,
				TEMPLATE_REMOVED_RESOURCES);
		for (Integer resId : resources) {
			Resource res = resManager.get(resId);
			if (res != null) {
				res.getPermissions().remove(perm);
				perm.getResources().remove(res);
			}
		}

		resources = getTemporaryResources(perm, TEMPLATE_ADDED_RESOURCES);
		for (Integer resId : resources) {
			Resource res = resManager.get(resId);
			if (res != null) {
				res.getPermissions().add(perm);
				perm.getResources().add(res);
			}
		}

		permManager.save(perm);
		clearSession(perm);

		return true;
	}

	/**
	 * 取消保存权限的资源信息
	 * 
	 * @param permId 权限Id
	 */
	public void cancelSavePermResources(Integer permId) {
		Permission perm = permManager.get(permId);
		if (perm == null) {
			return;
		}
		clearSession(perm);
	}

	/**
	 * 清空session中有关权限的数据
	 * 
	 */
	private void clearSession(Permission perm) {
		HttpSession session = getRequest().getSession();
		Map map = (Map) session.getAttribute(TEMPLATE_ADDED_RESOURCES);
		if (map.containsKey(perm)) {
			map.remove(perm);
		}
		map = (Map) session.getAttribute(TEMPLATE_REMOVED_RESOURCES);
		if (map.containsKey(perm)) {
			map.remove(perm);
		}
		log.debug("Session of permission " + perm.getName() + " cleared.");
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
   * @return the resManager
   */
  public ResourceManager getResManager() {
    return resManager;
  }

  /**
   * @param resManager the resManager to set
   */
  public void setResManager(ResourceManager resManager) {
    this.resManager = resManager;
  }

}
