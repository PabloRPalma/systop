package com.systop.common.webapp.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

/**
 * 上传文件监听器，共DWR调用
 * 
 * @author Original : plosson on 06-janv.-2006 12:19:08 - Last modified by
 *         $Author: vde $ on $Date: 2004/11/26 22:43:57 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */
public class UploadMonitor {
  /**
   * 返回Session中保存的UploadInfo对象
   */
  public UploadInfo getUploadInfo() {
    HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();

    if (req.getSession().getAttribute(Constants.SESSION_NAME) != null) {
      return (UploadInfo) req.getSession().getAttribute(Constants.SESSION_NAME);
    } else {
      return new UploadInfo();
    }
  }

  /**
   * 删除多个上传的文件
   * @param filenames 文件名数组
   * @return 返回成功删除的filename
   */
  public String[] removeUploadFiles(String[] filenames) {
    if (filenames == null || filenames.length == 0) {
      return new String[] {};
    }
    List deletedFiles = new ArrayList(filenames.length);
    for (int i = 0; i < filenames.length; i++) {
      File file = new File(Constants.getUploadPath() + "/" + filenames[i]);
      if (file.delete()) {
        deletedFiles.add(filenames[i]);
      }
    }

    return (String[]) deletedFiles.toArray(new String[] {});
  }
}
