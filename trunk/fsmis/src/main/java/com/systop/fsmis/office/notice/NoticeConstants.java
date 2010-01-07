package com.systop.fsmis.office.notice;

import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

/*
 * 通知模块常量类
 */
public final class NoticeConstants {

	/** UPLOAD_ALLOWED_FILE_TYPES:允许上传的文件类型 **/
	public static final String[] UPLOAD_ALLOWED_FILE_TYPES = new String[]{"doc","docx","rar","zip","pdf","jpg","gif","GIF","txt"};
	
	/** UPLOAD_ALLOWED_FILE_SIZE 允许上传的文件大小，默认10MB*/
	public static final long UPLOAD_ALLOWED_FILE_SIZE = getAllowedFileSize();
	
	/**
	 * 获得风险评估结果附件允许上传的最大文件大小,默认10MB
	 * @return 允许上传的风险评估附件的大小	long
	 */
	private static final long getAllowedFileSize() {
		String sizeStr = ResourceBundleUtil.getString(ResourceBundle.getBundle("application"),
				"assessment_file_max_size", "10240000");
		long size = 0L;
		try{
			size = Long.parseLong(sizeStr);
		}catch(Exception e){
			size = 10240000;
		}
		return size;
	}
}
