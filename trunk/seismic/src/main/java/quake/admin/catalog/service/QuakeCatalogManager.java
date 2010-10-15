package quake.admin.catalog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quake.DataType;
import quake.admin.catalog.QuakeCatalogConstants;
import quake.admin.catalog.dao.ExistTableNameDao;
import quake.admin.catalog.model.QuakeCatalog;
import quake.admin.sitecfg.model.SiteCfg;
import quake.base.service.Definable;
import quake.mainpage.MainPageAction;

import com.systop.core.ApplicationException;
import com.systop.core.service.BaseGenericsManager;


/**
 * 管理地震目录service
 * @author wbb
 */
@Service
public class QuakeCatalogManager extends BaseGenericsManager<QuakeCatalog> implements Definable  {
  @Autowired(required = true)
  private ExistTableNameDao existTableNameDao;

  /**
   * 保存地震目录
   */
  @Override
  @Transactional
  public void save(QuakeCatalog model) {
    if (getDao().exists(model, "cltName")) {
      throw new ApplicationException("地震目录表名'" + model.getCltName() + "'已经存在.");
    }
    if (getDao().exists(model, "clcName")) {
      throw new ApplicationException("地震目录名称'" + model.getClcName() + "'已经存在.");
    }
    if (!existTableNameDao.exists(DataType.SEISMIC, model.getCltName())) {
      throw new ApplicationException("地震目录表名'" + model.getCltName() + "'在测震数据库中不存在.");
    }
    if (StringUtils.isNotBlank(model.getMagTname())
        && !existTableNameDao.exists(DataType.SEISMIC, model.getMagTname())) {
      throw new ApplicationException("震级表名'" + model.getMagTname() + "'在测震数据库中不存在.");
    }
    if (StringUtils.isNotBlank(model.getPhaseTname())
        && !existTableNameDao.exists(DataType.SEISMIC, model.getPhaseTname())) {
      throw new ApplicationException("震相表名'" + model.getPhaseTname() + "'在测震数据库中不存在.");
    }
    super.save(model);
    /** 实现与菜单同步,清空常量 */
    MainPageAction.resetCatalogs();
    MainPageAction.resetPhases();
    MainPageAction.resetSeeds();
  }

  /**
   * 根据地震目录表名查询地震目录信息
   * @param clcName 地震目录表名
   * @return 返回地震目录信息
   */
  public QuakeCatalog queryByCltName(String cltName) {
    String hql = "from QuakeCatalog where cltName = ?";
    List<QuakeCatalog> list = query(hql, cltName);
    return list.isEmpty() ? null : list.get(0);
  }
  
  /**
   * 得到所有地震目录，用于数据服务的显示
   * @return
   */
  public List<Map<String, String>> getCat() {
    List<QuakeCatalog> list = get();
    return setPostfix(list, "地震目录");
  }

  /**
   * 得到有震相表关联的地震目录列表，用于数据服务显示
   */
  public List<Map<String, String>> getPhaseCat() {
    String hql = "from QuakeCatalog where phaseTname is not null and phaseTname <> ''";
    List<QuakeCatalog> list = query(hql, new Object[] {});
    return setPostfix(list, "震相数据");
  }
  
  /**
   * 得到有事件波形的地震目录，用于数据服务的显示
   * @return
   */
  public List<Map<String, String>> getSeedCat() {
    String hql = "from QuakeCatalog where seedDis = ?";
    List<QuakeCatalog> list = query(hql, QuakeCatalogConstants.SEEDDIS_YES);
    return setPostfix(list, "事件波形");
  }
  
  /**
   * 设置地震目录在文名相应的后缀，如 “河北省” 目录查询时“河北省地震目录”，震相查询时“河北省震相数据”等。
   * @param list    地震目录列表
   * @param postfix 后缀字符串
   * @return
   */
  private List<Map<String, String>> setPostfix(List<QuakeCatalog> list, String postfix) {
    List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
    for (QuakeCatalog czCat : list) {
      Map<String, String> cat = new HashMap<String, String>();
      cat.put("cltName", czCat.getCltName());
      String clcName = czCat.getClcName();
      //不是地震目录树显示，除去后缀 “地震目录”
      if (!postfix.equals("地震目录") && clcName.endsWith("地震目录")) {
        clcName = clcName.replace("地震目录", "");
      }
      if (!clcName.endsWith(postfix)) {
        clcName = clcName + postfix;
      }
      cat.put("clcName", clcName);
      listMap.add(cat);
    }
    return listMap;
  }

  @Override
  @Transactional
  public void remove(QuakeCatalog model) {
    Set<SiteCfg> siteCfgs = model.getSiteCfgs();
    if (!siteCfgs.isEmpty()) {
      //解除关联
      for (SiteCfg siteCfg : siteCfgs) {
        siteCfg.setProvinceCat(null);
      }
      model.getSiteCfgs().clear();
    }
    super.remove(model);
  }

  @Override
  public boolean isDefined() {
    return get().size() > 0;
  }
}
