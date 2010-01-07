package com.systop.fsmis.fscase.task;

import java.util.ResourceBundle;

import com.systop.core.util.ResourceBundleUtil;

public final class TaskConstants {
	/** UPLOAD_ALLOWED_FILE_TYPES:允许上传的文件类型 **/
	public static final String[] TASK_UPLOAD_ALLOWED_FILE_TYPES = new String[] {
			"doc", "docx", "rar", "zip", "pdf", "jpg", "gif", "GIF", "txt" };

	/** UPLOAD_ALLOWED_FILE_SIZE 允许上传的文件大小，默认10MB */
	public static final long TASK_UPLOAD_ALLOWED_FILE_SIZE = getAllowedFileSize();

	/**
	 * 从properties文件中获得任务附件允许上传的最大文件大小,默认10MB
	 * 
	 * @return 允许上传的风险评估附件的大小
	 */
	private static final long getAllowedFileSize() {
		String sizeStr = ResourceBundleUtil.getString(ResourceBundle
				.getBundle("application"), "fscase_task_att_max_size",
				"10240000");
		long size = 0L;
		try {
			size = Long.parseLong(sizeStr);
		} catch (Exception e) {
			size = 10240000;
		}

		return size;
	}
}
