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
				.getBundle("application"), "fscase_task_att_max_size", "10240000");
		long size = 0L;
		try {
			size = Long.parseLong(sizeStr);
		} catch (Exception e) {
			size = 10240000;
		}

		return size;
	}

	/** 任务状态：未接收 */
	public static final String TASK_UN_RECEIVE = "0";

	/** 任务状态：处理中 */
	public static final String TASK_PROCESSING = "1";

	/** 任务状态：已处理 */
	public static final String TASK_PROCESSED = "2";

	/** 任务状态：已退回 */
	public static final String TASK_RETURNED = "3";

	/** 任务详细状态：未接收 */
	public static final String TASK_DETAIL_UN_RECEIVE = "0";

	/** 任务详细状态：已查看 */
	public static final String TASK_DETAIL_LOOKED = "1";

	/** 任务详细状态：已接收 */
	public static final String TASK_DETAIL_RECEIVED = "2";

	/** 任务详细状态：已退回 */
	public static final String TASK_DETAIL_RETURNED = "3";

	/** 任务详细状态：已处理 */
	public static final String TASK_DETAIL_PROCESSED = "4";
}
