package com.systop.common.webapp.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.util.RequestUtil;

/**
 * 用于上传文件的Filter
 * @author Sam
 * 
 */
public class FileUploadFilter implements Filter {
  /**
   * Log for the filter
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * 缺省文件存放位置
   */
  protected String uploadPath = Constants.DEFAULT_DIRECTORY;

  /**
   * 单个文件最大长度
   */
  protected long maxFileSize = Constants.DEFAULT_MAX_FILE_SIZE;

  /**
   * 最多上传文件数
   */
  protected int maxFiles = Constants.DEFAULT_MAX_FILES;

  /**
   * commons-fileupload使用的临时目录
   */
  protected File templatePath;

  /**
   * @see {@link Filter#destroy()}
   */
  public void destroy() {
    uploadPath = Constants.DEFAULT_DIRECTORY;
    maxFileSize = Constants.DEFAULT_MAX_FILE_SIZE;
    maxFiles = Constants.DEFAULT_MAX_FILES;
    if (templatePath.exists()) {
      templatePath.delete();
    }
    templatePath = null;
  }

  /**
   * @see {@link Filter#doFilter(ServletRequest, ServletResponse, FilterChain)}
   */
  public void doFilter(ServletRequest servletRequest, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    request.setCharacterEncoding("UTF-8");
    // 检查是否是multipart/form-data
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    // 是否需要处理文件上传
    boolean isHandle = RequestUtil.getParam(request,
        Constants.HANDLE_FLAGS_NAME, Constants.DEFAULT_HANDLE_FLAGS);

    if (isMultipart && isHandle) {
      PlainHttpServletRequest requestWrapper = new PlainHttpServletRequest(
          request);
      handleUpload(requestWrapper);
      // 将multipart请求转化为普通请求，可以用getParameter获取数据
      request = requestWrapper;
    }
    chain.doFilter(request, response);
  }

  /**
   * 处理文件上传，这里用commons fileupload做简单处理
   * @param request
   */
  protected void handleUpload(PlainHttpServletRequest request) {
    List<FileItem> fileItems = parseRequest((HttpServletRequest) request
        .getRequest());
    for (FileItem item : fileItems) {
      if (item.isFormField()) {
        doFormField(item, request);
      } else {
        doUpload(item, request);
      }
    }
  }

  /**
   * 处理普通的parameter
   */
  protected void doFormField(FileItem item, PlainHttpServletRequest request) {
    String name = item.getFieldName();
    String value = item.getString();
    request.addParameter(name, value);
  }

  /**
   * 处理上传文件
   */
  protected void doUpload(FileItem item, PlainHttpServletRequest request) {
    log.debug(item.getName());
    String fileName = item.getName();
    int index = (fileName.lastIndexOf("/") < 0) ? fileName.lastIndexOf("\\")
        : fileName.lastIndexOf("/");
    if (index >= 0) {
      fileName = fileName.substring(index + 1);
    }
    
    File file = new File(uploadPath + "/" + fileName);
    try {
      item.write(file); // 保存文件
      // 将文件名以uploaded_files为名称，保存在attribute中
      Object obj = request.getAttribute(Constants.FILES_NAME_AFTER_SAVE);
      List files = null;
      if (obj == null || !(obj instanceof List)) {
        files = new ArrayList();
      } else {
        files = (List) obj;
      }
      files.add(fileName);
      request.setAttribute(Constants.FILES_NAME_AFTER_SAVE, files);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 解析HttpServletRequest，将参数转化为FileItem的List对象
   */
  protected List<FileItem> parseRequest(HttpServletRequest request) {
    UploadListener listener = new UploadListener(request, Constants.DELAY);

    // Create a factory for disk-based file items
    DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
    factory.setRepository(templatePath);
    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setFileSizeMax(maxFileSize);
    upload.setSizeMax(maxFileSize * maxFiles);

    List<FileItem> fileItems = Collections.EMPTY_LIST;
    try {
      // process uploads ..
      fileItems = upload.parseRequest(request);
    } catch (FileUploadException e) {
      e.printStackTrace();
    }

    return fileItems;
  }

  /**
   * 得到文件上传的真实路径
   */
  protected String getUploadPath(FilterConfig cfg) {
    String path = cfg.getInitParameter(Constants.INIT_DIR_NAME);
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    log.debug("save file in '" + cfg.getServletContext().getRealPath(path)
        + "'");
    return cfg.getServletContext().getRealPath(path);
  }

  /**
   * 得到单个文件最大长度
   */
  protected long getMaxFileSize(FilterConfig cfg) {
    final int kb = 1024;

    String val = cfg.getInitParameter(Constants.MAX_FILE_SIZE_NAME);
    if (val == null) {
      return Constants.DEFAULT_MAX_FILE_SIZE;
    }
    val = val.toLowerCase();
    try {
      if (val.indexOf("m") > 0) {
        return Long.parseLong(val.substring(0, val.indexOf("m"))) * kb * kb;
      } else if (val.indexOf("kb") > 0) {
        return Long.parseLong(val.substring(0, val.indexOf("kb"))) * kb;
      } else if (val.indexOf("b") > 0) {
        return Long.parseLong(val.substring(0, val.indexOf("b")));
      } else {
        return Long.parseLong(val);
      }
    } catch (NumberFormatException e) {
      return Constants.DEFAULT_MAX_FILE_SIZE;
    }
  }

  /**
   * 从init-param中得到最多同时上传文件数
   */
  protected int getMaxFiles(FilterConfig cfg) {
    String val = cfg.getInitParameter(Constants.MAX_FILE_NAME);
    try {
      return Integer.parseInt(val);
    } catch (NumberFormatException e) {
      return Constants.DEFAULT_MAX_FILES;
    }
  }

  /**
   * @see {@link Filter#init(FilterConfig)}
   */
  public void init(FilterConfig cfg) throws ServletException {
    uploadPath = getUploadPath(cfg);
    File file = new File(uploadPath);
    if (!file.exists()) {
      boolean successed = file.mkdir();
      log.debug("Build upload path " + successed);
    }
    // 将文件上传的位置保存为static变量，以便于其他类处理
    Constants.setUploadPath(uploadPath);

    templatePath = new File(uploadPath + Constants.TEMPLATE_PATH);
    if (!templatePath.exists()) {
      boolean successed = templatePath.mkdir();
      log.debug("Build upload path " + successed);
    }

    maxFileSize = getMaxFileSize(cfg);
    log.debug("max file size - " + maxFileSize + " bytes.");

    maxFiles = getMaxFiles(cfg);
  }

}
