package com.systop.common.webapp.upload;

/**
 * 上传文件过程的信息
 * 
 * @author Original : plosson on 06-janv.-2006 12:19:14 - Last modified by
 *         $Author: vde $ on $Date: 2004/11/26 22:43:57 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */
public class UploadInfo {
  /**
   * 总的字节数
   */
  private long totalSize = 0;

  /**
   * 已经读取的字节数
   */
  private long bytesRead = 0;

  /**
   * 超时
   */
  private long elapsedTime = 0;

  /**
   * 当前状态
   */
  private String status = Constants.UPLOAD_STATUS_DONE;

  /**
   * 当前文件索引
   */
  private int fileIndex = 0;

  /**
   * 缺省的构造器
   */
  public UploadInfo() {
  }

  /**
   * 完整的构造器
   */
  public UploadInfo(int fileIndex, long totalSize, long bytesRead,
      long elapsedTime, String status) {
    this.fileIndex = fileIndex;
    this.totalSize = totalSize;
    this.bytesRead = bytesRead;
    this.elapsedTime = elapsedTime;
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  public long getBytesRead() {
    return bytesRead;
  }

  public void setBytesRead(long bytesRead) {
    this.bytesRead = bytesRead;
  }

  public long getElapsedTime() {
    return elapsedTime;
  }

  public void setElapsedTime(long elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  public boolean isInProgress() {
    return Constants.UPLOAD_STATUS_PROGRESS.equals(status)
        || Constants.UPLOAD_STATUS_START.equals(status);
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }
}
