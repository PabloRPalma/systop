/**
 * 
 */
package com.systop.common.security.user.service;

import java.util.List;

import com.systop.common.security.user.exception
        .PermissionAlreadyExistsException;
import com.systop.common.security.user.exception.ReduplicateResourceException;
import com.systop.common.security.user.model.Permission;
import com.systop.common.security.user.model.Resource;
import com.systop.common.test.BaseTestCase;
import com.systop.common.util.CollectionUtil;

/**
 * TestCase of the {@link ResourceManager}
 * @author han
 *
 */
public class ResourceManagerTestCase extends BaseTestCase {
  private ResourceManager getResMgr() {
    return (ResourceManager) applicationContext.getBean("resourceManager");
  }

  private PermissionManager getPermMgr() {
    return (PermissionManager) applicationContext.getBean("permManager");
  }
  /**
   * Test method for {@link ResourceManager#saveResource(Resource)}.
   */
  public void testSaveResource() {
    String name = "单元测试创建的资源";
    boolean isNameInUse = getResMgr().isNameInUse(name);
    if (isNameInUse) {
      assertTrue("初始化前的错误：创建资源的操作未执行，而要创建的资源已存在？"
          , isNameInUse);
      return;
    }
    Resource res = new Resource();
    res.setName(name);
    res.setResString("资源串");
    res.setResType("url");
    res.setVersion(1);
    try {
      getResMgr().save(res);
    } catch (ReduplicateResourceException e) {
      assertTrue("错误：资源还未创建，却已经存在", false);
      e.printStackTrace();
    }
    //继续添加一个同名的资源，测试是否能添加成功，是否能触发“资源重复”的异常
    Resource sameNameRes = new Resource();
    sameNameRes.setName(name);
    sameNameRes.setResString("资源串");
    sameNameRes.setResType("url");
    sameNameRes.setVersion(1);
    try {
      getResMgr().save(sameNameRes);
      assertTrue("错误：资源已存在，仍然能创建该资源", false);
    } catch (ReduplicateResourceException e) {
      e.printStackTrace();
    }
    assertTrue("初始化后的错误：创建的角色不存在"
        , getResMgr().isNameInUse(res.getName()));
  }

  /**
   * Test method for {@link ResourceManager#isNameInUse(java.lang.String)}.
   */
  public void testIsNameInUse() {
    String name = "单元测试创建的资源";
    assertTrue("初始化前的错误：创建资源的操作未执行，而要创建的资源已存在？"
        , !getResMgr().isNameInUse(name));
    Resource res = new Resource();
    res.setName(name);
    res.setResString("资源串");
    res.setResType("url");
    res.setVersion(1);
    getResMgr().save(res);
    assertTrue("初始化后的错误：创建的资源不存在"
        , getResMgr().isNameInUse(res.getName()));
  }
  /**
   * Test method for {@link ResourceManager#getByPerm(Permission)}.
   */
  public void testGetByPerm() {
    List<Resource> reslst = getResMgr().get();
    int resNum = reslst.size();
    Permission perm = new Permission();
    perm.setResources(CollectionUtil.listToSet(reslst));
    perm.setName("单元测试创建的权限");
    perm.setOperation("*");
    perm.setStatus("1");
    perm.setVersion(1);
    try {
      getPermMgr().save(perm);
    } catch (PermissionAlreadyExistsException e) {
      e.printStackTrace();
    }
    perm = getPermMgr().get(perm.getId());
    if (perm == null) {
      assertTrue("没有查到测试所创建的权限", perm != null);
      return;
    }
    Resource[] resources = getResMgr().getByPerm(perm);
    // 检查权限拥有的资源个数是不是创建权限时设置的资源的个数
    assertTrue("资源个数与创建权限时设置的资源个数不同"
        , resources.length == resNum);
  }

}
