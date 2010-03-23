package com.systop.common.webapp.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.File;

/**
 * 代理DiskFileItemFactory实现，使之构建一个MonitoredDiskFileItem对象
 * 
 * @author Original : plosson on 05-janv.-2006 10:46:26 - Last modified by
 *         $Author: plosson $ on $Date: 2006/01/05 10:09:38 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public class MonitoredDiskFileItemFactory extends DiskFileItemFactory {
  /**
   * 监听器
   */
  private OutputStreamListener listener = null;
  
  /**
   * 构造方法，传入监听器对象
   */
  public MonitoredDiskFileItemFactory(OutputStreamListener listener) {
    super();
    this.listener = listener;
  }
  /**
   * 构造方法，传入监听器对象
   */
  public MonitoredDiskFileItemFactory(int sizeThreshold, File repository,
      OutputStreamListener listener) {
    super(sizeThreshold, repository);
    this.listener = listener;
  }
  /**
   * 创建MonitoredDiskFileItem对象
   */
  public FileItem createItem(String fieldName, String contentType,
      boolean isFormField, String fileName) {
    return new MonitoredDiskFileItem(fieldName, contentType, isFormField,
        fileName, getSizeThreshold(), getRepository(), listener);
  }
}
