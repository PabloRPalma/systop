package com.systop.fsmis.enterprise;

import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

public class EnterpriseConstants {

	/** 
	 * 资源文件 
	 */
  public static final String BUNDLE_KEY = "application";

  /** 
   * 资源绑定对象 
   */
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_KEY);
  
	/** 
	 * 企业照片上传路径
	 */
  public static final String COMPANY_PHOTOS_FOLDER = ResourceBundleUtil.getString(RESOURCE_BUNDLE,
      "enterprise_photos_file_path", "/uploadFiles/enterprise/");
}
