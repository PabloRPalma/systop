package com.systop.cms.template.webapp;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.systop.cms.CmsConstants;
import com.systop.core.webapp.dwr.BaseDwrAjaxAction;

/**
 * 模板管理Dwr
 * 
 * @author Bin
 */
@Component
public class TemplateDwrAction extends BaseDwrAjaxAction {
  /**
   * 判断目录是否存在
   * 
   * @param dirName 目录名称
   * @return
   */
  public boolean dirExists(String dirName) {
    String dirPath = getResRealPath(dirName);

    File dir = new File(dirPath);
    return dir.exists() && dir.isDirectory();
  }

  /**
   * 创建文件夹
   * 
   * @param dirName 需要创建的目录名称
   * @param parentDir 创建目录的父目录，即在此目录下创建文件
   * @return
   */
  public String mkdir(String dirName, String parentDir) {
    if (StringUtils.isBlank(dirName)) {
      return "请出入您要创建的目录名称";
    } else {
      dirName = dirName.replace("/", "\\");
    }

    if (CmsConstants.RES_ROOT.equals(dirName)) {
      return "创建的目录不能为：" + CmsConstants.RES_ROOT;
    }

    if (StringUtils.isBlank(parentDir)) {
      return "未指定父目录，" + dirName + "创建失败";
    } else {
      // parentDir为jsp页面传递的viewPath，对其进行转换
      parentDir = ResFileUtil.wipeResRoot(parentDir);
    }

    String dirPath = getResRealPath(parentDir + "\\" + dirName);

    File dir = new File(dirPath);
    if (dir.exists()) {
      return "当前目录下'" + dirName + "'已存在";
    }
    dir.mkdirs();

    return "success";
  }

  /**
   * 删除文件
   * 
   * @param fileName
   * @return
   */
  public String delFiles(String[] filesName) {
    // 操用是否成功
    String filePath = null;
    File file = null;
    int subFileSize = 0;
    for (String fileName : filesName) {
      filePath = getRealPath(fileName);// 实际文件路径
      file = new File(filePath);
      if (file.exists()) {
        if (file.isDirectory()) {
          subFileSize = file.listFiles().length;
        }
        if (subFileSize == 0) {// 没有子目录时进行删除
          file.delete();
        } else {
          return fileName + "中包含文件不能删除，操作终止。";
        }
      }
    }
    return "true";
  }

  /**
   * 得到实际资源文件或目录
   * 
   * @param arg 文件或目录名称
   * @return
   */
  public String getResRealPath(String arg) {
    return getRealPath(CmsConstants.RES_ROOT) + "\\" + arg;
  }

  /**
   * 得到实际文件或目录
   * 
   * @param arg 文件或目录名称
   * @return
   */
  public String getRealPath(String arg) {
    return getServletContext().getRealPath(arg);
  }
}
