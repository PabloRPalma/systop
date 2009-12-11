package com.systop.cms.webview;

import com.systop.cms.CmsConstants;

/**
 * 解析URL的工具类
 * 
 * @author lunch
 */
public final class URLUtil {

	/** 阻止实例化 */
	private URLUtil() {
	}

	/**
	 * 将字符串转换成准确的url请求，将'\'转换成'/'，并且将重叠的'///'正确解析
	 * 
	 * @param url
	 * @return
	 */
	public static String conHealthURL(String url) {
		// 将'\'转换成'/'
		url = url.replace("\\", "/");

		while (url.indexOf("//") != -1) {
			url = url.replace("//", "/");
		}
		return url;
	}

	/**
	 * 将url请求中的项目名称［/culturecms］去掉．
	 */
	public static String urlNoStartRoot(String url) {

		url = conHealthURL(url);

		if (url.startsWith(CmsConstants.START_ROOT)) {
			url = url.substring(CmsConstants.START_ROOT.length());
		}
		return url;
	}

	/**
	 * 从URL中获取访问的目录
	 */
	public static String getRootForURL(String url) {

		url = urlNoStartRoot(url);
		int position = url.lastIndexOf("/");

		// urlRoot相当于取[/culturecms／temproot／index.shtml]中的[/temproot]
		String urlRoot = url.substring(0, position);
		return urlRoot;
	}

	/**
	 * 从URL中获取访问的文件名
	 */
	public static String getFileNameForURL(String url) {

		url = urlNoStartRoot(url);
		int start = url.lastIndexOf("/") + 1;
		int end = url.lastIndexOf(".");
    //Wang Bin 08-05-08
    String fileName = "index";
    if (start < end) {
      fileName = url.substring(start, end);
    }
		return fileName;
	}
	
	/**/
	public static void main(String[] args) {
		String url = "///culturecms///\\dept//index.shtml";
		System.out.println(conHealthURL(url));
		System.out.println(urlNoStartRoot(url));
		System.out.println(getRootForURL(url));
		System.out.println(getFileNameForURL(url));
	}
	
}
