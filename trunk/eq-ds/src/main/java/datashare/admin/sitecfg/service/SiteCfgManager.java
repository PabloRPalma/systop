package datashare.admin.sitecfg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.systop.core.service.BaseGenericsManager;

import datashare.admin.sitecfg.model.SiteCfg;
import datashare.base.service.Definable;

/**
 * 网站配置管理manager
 * @author wbb
 */
@Service
public class SiteCfgManager extends BaseGenericsManager<SiteCfg>  implements Definable {
  /**
   * 得到数据库中唯一网站配置model
   * @return
   */
  public SiteCfg getCmsConfig() {
    List<SiteCfg> list = get();
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public boolean isDefined() {
    return getCmsConfig() != null;
  }
}
