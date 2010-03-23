package com.systop.common.webapp.upload;

import java.io.OutputStream;
import java.io.IOException;

/**
 * 代理OutputStream的实现，在输出数据的同时，试行监控。Proxy模式的应用。
 * 
 * @author Original : plosson on 05-janv.-2006 10:44:18 - Last modified by
 *         $Author: plosson $ on $Date: 2006/01/05 10:44:49 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */
public class MonitoredOutputStream extends OutputStream {
  /**
   * 被代理的实际对象
   */
  private OutputStream target;
  
  /**
   * 监听器，用于记录数据输出情况
   */
  private OutputStreamListener listener;
  
  /**
   * 构造器
   * @param target 实际代理对象
   * @param listener 监听器
   */
  public MonitoredOutputStream(OutputStream target,
      OutputStreamListener listener) {
    this.target = target;
    this.listener = listener;
    this.listener.start();
  }
  
  /**
   * @see OutputStream#write(byte[], int, int)
   */
  public void write(byte []b, int off, int len) throws IOException {
    target.write(b, off, len);
    listener.bytesRead(len - off);
  }
  
  /**
   * @see OutputStream#write(byte[])
   */
  public void write(byte []b) throws IOException {
    target.write(b);
    listener.bytesRead(b.length);
  }
  
  /**
   * @see OutputStream#write(int)
   */
  public void write(int b) throws IOException {
    target.write(b);
    listener.bytesRead(1);
  }
  
  /**
   * @see OutputStream#close()
   */
  public void close() throws IOException {
    target.close();
    listener.done();
  }
  
  /**
   * @see OutputStream#flush()
   */
  public void flush() throws IOException {
    target.flush();
  }
}
