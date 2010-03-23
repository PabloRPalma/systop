package com.systop.common.webapp.upload;

/**
 * OutputStream监听器接口
 * 
 * @author Original : plosson on 04-janv.-2006 9:59:27 - Last modified by
 *         $Author: plosson $ on $Date: 2006/01/05 10:09:38 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public interface OutputStreamListener {
  /**
   * 准备开始监听
   */
  public void start();
  
  /**
   * 监听读取的字节数
   * @param bytesRead 字节数
   */
  public void bytesRead(int bytesRead);
  
  /**
   * 监听错误信息
   */
  public void error(String message);
  
  /**
   * 输出完毕
   *
   */
  public void done();
}
