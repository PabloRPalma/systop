package com.systop.cms.template.webapp;

/**
 * 模板资源文件
 * @author lunch
 */
public class ResFile {

	/** 文件名称	 */
	private String name;
	
	/** 文件大小 */
	private long size;
	
	/** 文件路径 */
	private String path;
	
	/** 请求地址 */
	private String reqUrl;
	
	/** 是否目录 */
	private boolean isDirectory;
	

	/**
	 * @return the reqUrl
	 */
	public String getReqUrl() {
		return reqUrl;
	}

	/**
	 * @param reqUrl the reqUrl to set
	 */
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	/**构造*/
	public ResFile(String name, long size, String path) {
		super();
		this.name = name;
		this.size = size;
		this.path = path;
	}
	
	/**构造*/
	public ResFile() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the isDirectory
	 */
	public boolean isDirectory() {
		return isDirectory;
	}

	/**
	 * @param trueOrFalse
	 */
	public void setDirectory(boolean trueOrFalse) {
		this.isDirectory = trueOrFalse;
	}

	
}
