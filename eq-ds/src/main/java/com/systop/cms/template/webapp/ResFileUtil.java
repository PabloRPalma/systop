package com.systop.cms.template.webapp;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.systop.cms.CmsConstants;

/**
 * 
 * @author lunch
 * 
 */
public final class ResFileUtil {

	/** 阻止实例化 */
	private ResFileUtil() {
	}

	/**
	 * 实际使用的rul相对路径 
	 */
	public static String switchUsePath(String path) {
		if (StringUtils.isBlank(path)) {
			return null;
		}
		return path.substring(path.indexOf(CmsConstants.RES_ROOT));
	}

	/**
	 * 将文件物理路径转化成web访问路径
	 */
	public static String switchWebPath(String path) {
		if (StringUtils.isBlank(path)) {
			return "";
		}
		if (path.indexOf(CmsConstants.RES_ROOT) == -1) {
			return "";
		}

		int p = path.indexOf(CmsConstants.RES_ROOT)
				+ CmsConstants.RES_ROOT.length();
		
		if (p == path.length()) {
			return "";
		}

		return path.substring(path.indexOf(CmsConstants.RES_ROOT)
				+ CmsConstants.RES_ROOT.length() + 1);
	}
	
	/**
	 * 路径如果不是以CmsConstants.RES_ROOT开始，变成以CmsConstants.RES_ROOT开始。
	 * @param path
	 * @return
	 */
	public static String addResRoot(String path){
	  path = (path == null) ? "" : path;
	  path = (CmsConstants.RES_ROOT.equals(path)) ? "" : path;
	  if (path.startsWith(CmsConstants.RES_ROOT)){
	    return path;
	  }
	  return CmsConstants.RES_ROOT + File.separator + path;
	}
	
	
  /**
   * 路径如果以CmsConstants.RES_ROOT开始，将其擦去。
   * @param path
   * @return
   */
  public static String wipeResRoot(String path) {
    if(StringUtils.isBlank(path)){
      return null;
    }
    // 如果是Windows系统，将路径中的斜线统一替换成'\'
    if(StringUtils.equalsIgnoreCase("windows", System.getProperty("os.name"))) {
      path = path.replace(File.separator, "\\");
    }
    // 如果路径开始位置为'\',将其擦去。
    path = path.startsWith(File.separator) ? path.substring(1) : path;
    //如果路径开始位置为CmsConstants.RES_ROOT,将其擦去。
    path = path.startsWith(CmsConstants.RES_ROOT) ? path.substring(CmsConstants.RES_ROOT.length())
        : path;

    return path;
  }
	
	/***
	 * 根据文件物理路径，解析成系统中能够使用的web路径，并取得该路径的父路径。
	 * 将CmsConstants.RES_ROOT去掉
	 * @param path
	 * @return
	 */
	public static String paresParentPath(String path) {
		if (path.indexOf(CmsConstants.RES_ROOT) == -1) {
			return "";
		}
		
		if (path.endsWith(CmsConstants.RES_ROOT)) {
			return CmsConstants.RES_ROOT;
		}
		
		return path.substring(path.indexOf(CmsConstants.RES_ROOT)
				+ CmsConstants.RES_ROOT.length() + 1);
		
	}
}
