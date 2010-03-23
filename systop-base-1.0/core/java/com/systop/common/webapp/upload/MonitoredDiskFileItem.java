package com.systop.common.webapp.upload;

import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

/**
 * 代理<tt>DiskFileItem</tt>的实现，使之使用一个带有监听器的OutputStream对象.
 * @author Original : plosson on 05-janv.-2006 10:46:33 - Last modified by
 *         $Author: plosson $ on $Date: 2006/01/05 10:09:38 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public class MonitoredDiskFileItem extends DiskFileItem {
  /**
   * 带有监听器的OutputStream对象
   */
  private MonitoredOutputStream mos = null;
  /**
   * OutputStream监听器
   */
  private OutputStreamListener listener;
  
  /**
   * 构造一个 DiskFileItem 对象，同时传入OutputStreamListener对象
   */
  public MonitoredDiskFileItem(String fieldName, String contentType,
      boolean isFormField, String fileName, int sizeThreshold, File repository,
      OutputStreamListener listener) {
    super(fieldName, contentType, isFormField, fileName, sizeThreshold,
        repository);
    this.listener = listener;
  }
  
  /**
   * 返回一个完整的MonitoredOutputStream实例
   */
  public OutputStream getOutputStream() throws IOException {
    if (mos == null) {
      mos = new MonitoredOutputStream(super.getOutputStream(), listener);
    }
    return mos;
  }
}
