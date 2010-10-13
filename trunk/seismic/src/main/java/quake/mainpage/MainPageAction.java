package quake.mainpage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.czcatalog.service.CzCatalogManager;

import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 数据共享首页Action类，主要功能是初始化首页右侧的菜单
 * @author Sam
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainPageAction extends BaseAction{

  
  /** 地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> CZCATALOGS = null;

  /** 有震相的地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> PHASECATS = null;

  /** 有事件波形的地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> SEEDCATS = null;
  
  @Autowired
  private CzCatalogManager czCatalogManager;
  
  /**
   * 用于加载学科列表，地震目录列表，震相列表
   * 
   * @return
   */
  public String index() {
    
    if (CZCATALOGS == null) { // 查询地震目录
      CZCATALOGS = czCatalogManager.getCat();
    }
    if (PHASECATS == null) { // 查询震相
      PHASECATS = czCatalogManager.getPhaseCat();
    }
    if (SEEDCATS == null) { //查询事件波形
      SEEDCATS = czCatalogManager.getSeedCat();
    }
    return SUCCESS;
  }
  

  /** 获取地震目录列表 */
  public static List<Map<String, String>> getCZCATALOGS() {
    return CZCATALOGS;
  }

  /** 获取震项列表 */
  public static List<Map<String, String>> getPHASECATS() {
    return PHASECATS;
  }

  /** 获取事件波形列表 */
  public static List<Map<String, String>> getSEEDCATS() {
    return SEEDCATS;
  }
  
  /** 用于外部修改地震目录，与菜单同步 */
  public static void setEmptyCZCATALOGS() {
    CZCATALOGS = null;
  }

  /** 用于外部修地震目录，与菜单同步 */
  public static void setEmptyPHASECATS() {
    PHASECATS = null;
  }
  
  /** 用于外部修地震目录，与菜单同步 */
  public static void setEmptySEEDCATS() {
    SEEDCATS = null;
  }
}
