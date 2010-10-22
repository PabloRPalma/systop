package com.systop.cms.webview.exception;

/**
 * 通过URL解析获得模板信息错误时所抛出的异常
 * @author lunch
 */
public class ParseURLToTemplateException extends Exception {

	/**
	 *
	 */
	public ParseURLToTemplateException() {
		super("");
	}

	/**
	 * @param message
	 */
	public ParseURLToTemplateException(String message) {
		super(message);
	}
}
