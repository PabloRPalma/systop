package datashare.admin.sitecfg.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import datashare.admin.sitecfg.SiteConstants;
import datashare.admin.sitecfg.model.SiteCfg;

/**
 * 初始化网站基本信息，存入静态变量
 * @author Lunch
 */
public class SiteCfgInit {
  
  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SiteCfgManager siteCfgManager;

  /**
   * 初始化网站基本信息，存入静态变量
   */
  @PostConstruct
  public void initSiteCfgManager(){
    if (StringUtils.isBlank(SiteConstants.siteCfg.getCmsName())){
      List<SiteCfg> sites = siteCfgManager.get();
      if (!sites.isEmpty()){
        logger.debug("网站基本信息为空，进行初始化...");
        SiteConstants.changeValue(sites.get(0));
      }
    }
  }
}
