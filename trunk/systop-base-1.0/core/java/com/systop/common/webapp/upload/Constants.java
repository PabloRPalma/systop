package com.systop.common.webapp.upload;

/**
 * 文件上传模块常量类
 *
 */
public final class Constants {
  /**
   * 阻止实例化
   */
  private Constants() {    
  }
  /**
   * 缺省文件上传的位置
   */
  public static final String DEFAULT_DIRECTORY = "/uploadFiles";
  /**
   * 文件上传的位置
   */
  private static String uploadPath = DEFAULT_DIRECTORY;
  
  //上传状态
  /**
   * 开始状态
   */
  static final String UPLOAD_STATUS_START = "start";
  /**
   * 进行中
   */
  static final String UPLOAD_STATUS_PROGRESS = "progress";
  /**
   * 上传完毕
   */
  static final String UPLOAD_STATUS_DONE = "done";
  /**
   * 上传错误
   */
  static final String UPLOAD_STATUS_ERROR = "error";
  /**
   * UploadInfo对象在Session中的Name
   */
  static final String SESSION_NAME = "uploadInfo";
  /**
   * 一秒等于1000毫秒
   */
  static final int ONE_MILLISECOND = 1000;
  
  /**
   * 可以在HttpServletReqeust中设置一个parameter，以标记是否处理
   * 文件上传，HANDLE_FLAGS是这个parameter的name
   */
  public static final String HANDLE_FLAGS_NAME = "filterHandle";
  /**
   * 缺省处理标记
   */
  public static final boolean DEFAULT_HANDLE_FLAGS = true;
  
  
  /**
   * 定义在init-param中的文件存放位置的name
   */
  public static final String INIT_DIR_NAME = "path";
  
  /**
   * Sleep的时间
   */
  public static final long DELAY = 30;
  /**
   * 缺省的单个文件最大长度bytes
   */
  public static final long DEFAULT_MAX_FILE_SIZE = 2 * 1024 * 1024;
  
  /**
   * 最大文件长度在init-param中的名字
   */
  public static final String MAX_FILE_SIZE_NAME = "maxSize";
  
  /**
   * 缺省的同时上传文件数
   */
  public static final int DEFAULT_MAX_FILES = 1;
  
  /**
   * 同时上传文件数在init-param中的名字
   */
  public static final String MAX_FILE_NAME = "maxFiles";
  /**
   * 临时路径,commons fileupload用
   */
  public static final String TEMPLATE_PATH = "/temp";
  /**
   * 文件保存后，将文件名保存在Request的Atrribute中
   */
  public static final String FILES_NAME_AFTER_SAVE = "uploaded_files";
  /**
   * @return the uploadPath
   */
  public static synchronized String getUploadPath() {
    return uploadPath;
  }
  /**
   * @param uploadPath the uploadPath to set
   */
  public static synchronized void setUploadPath(String uploadPath) {
    Constants.uploadPath = uploadPath;
  }
}
