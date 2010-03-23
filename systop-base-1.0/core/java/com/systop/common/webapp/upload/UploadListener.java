package com.systop.common.webapp.upload;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件监听器，OutputStreamListener的实现类
 * 
 * @author Original : plosson on 06-janv.-2006 15:05:44 - Last modified by
 *         $Author: vde $ on $Date: 2004/11/26 22:43:57 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */
public class UploadListener implements OutputStreamListener {
  /**
   * 当前上传文件的HttpServletRequest对象
   */
  private HttpServletRequest request;

  /**
   * 调试器延迟时间
   */
  private long delay = 0;

  /**
   * 开始时间
   */
  private long startTime = 0;

  /**
   * 读取的总数
   */
  private int totalToRead = 0;

  /**
   * 读取的总字节数
   */
  private int totalBytesRead = 0;

  /**
   * 上传文件数
   */
  private int totalFiles = -1;

  /**
   * 构造器
   */
  public UploadListener(HttpServletRequest request, long debugDelay) {
    this.request = request;
    this.delay = debugDelay;
    totalToRead = request.getContentLength();
    this.startTime = System.currentTimeMillis();
  }

  /**
   * 文件开始上传，更新Session中的UploadInfo
   */
  public void start() {
    totalFiles++;
    updateUploadInfo(Constants.UPLOAD_STATUS_START);
  }

  /**
   * 记录读取的字节数，并更新Session中的UploadInfo
   */
  public void bytesRead(int bytesRead) {
    totalBytesRead = totalBytesRead + bytesRead;
    updateUploadInfo(Constants.UPLOAD_STATUS_PROGRESS);

    try {
      Thread.sleep(delay); // 为了一个Session同时上传多个文件
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 记录错误
   */
  public void error(String message) {
    updateUploadInfo(Constants.UPLOAD_STATUS_ERROR);
  }

  /**
   * 记录完成状态
   */
  public void done() {
    updateUploadInfo(Constants.UPLOAD_STATUS_DONE);
  }

  /**
   * 更新Session中的UploadInfo对象
   * @param status 状态
   */
  private void updateUploadInfo(String status) {
    long delta = (System.currentTimeMillis() - startTime)
        / Constants.ONE_MILLISECOND;
    request.getSession().setAttribute(Constants.SESSION_NAME,
        new UploadInfo(totalFiles, totalToRead, totalBytesRead, delta, status));
  }

}
