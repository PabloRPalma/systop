package quake.mainpage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.catalog.service.QuakeCatalogManager;

import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 数据共享首页Action类，主要功能是初始化首页右侧的菜单
 * @author Sam
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainPageAction extends BaseAction{

  
  /** 地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> catalogs = null;

  /** 有震相的地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> phases = null;

  /** 有事件波形的地震目录 静态化，避免重复查询 */
  private static List<Map<String, String>> seeds = null;
  
  @Autowired
  private QuakeCatalogManager catalogManager;
  
  /**
   * 用于加载学科列表，地震目录列表，震相列表
   * 
   * @return
   */
  public String index() {
    
    if (catalogs == null) { // 查询地震目录
      catalogs = catalogManager.getCat();
    }
    if (phases == null) { // 查询震相
      phases = catalogManager.getPhaseCat();
    }
    if (seeds == null) { //查询事件波形
      seeds = catalogManager.getSeedCat();
    }
    return SUCCESS;
  }
  

  /** 获取地震目录列表 */
  public static List<Map<String, String>> getCatalogs() {
    return catalogs;
  }

  /** 获取震项列表 */
  public static List<Map<String, String>> getPhases() {
    return phases;
  }

  /** 获取事件波形列表 */
  public static List<Map<String, String>> getSeeds() {
    return seeds;
  }
  
  /** 用于外部修改地震目录，与菜单同步 */
  public static void resetCatalogs() {
    catalogs = null;
  }

  /** 用于外部修地震目录，与菜单同步 */
  public static void resetPhases() {
    phases = null;
  }
  
  /** 用于外部修地震目录，与菜单同步 */
  public static void resetSeeds() {
    seeds = null;
  }
}
