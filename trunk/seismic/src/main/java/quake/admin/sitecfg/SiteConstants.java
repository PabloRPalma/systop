package quake.admin.sitecfg;

import quake.admin.sitecfg.model.SiteCfg;

/**
 * 网站基本信息常量类
 * 
 * @author Lunch
 * 
 */
public class SiteConstants {

  private SiteConstants() {
  }

  public static SiteCfg siteCfg = new SiteCfg();

  /**
   * 将更改后的网站信息重新存储到静态变量中
   */
  public static void changeValue(SiteCfg site) {
    siteCfg.setCmsName(site.getCmsName());
    siteCfg.setProvinceName(site.getProvinceName());
    siteCfg.setCopyright(site.getCopyright());
    siteCfg.setAddress(site.getAddress());
    siteCfg.setEmail(site.getEmail());
    siteCfg.setZipCode(site.getZipCode());
    siteCfg.setIcpCode(site.getIcpCode());
  }

}
