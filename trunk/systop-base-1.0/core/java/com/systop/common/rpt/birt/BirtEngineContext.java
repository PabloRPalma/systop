package com.systop.common.rpt.birt;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLActionHandler;
import org.eclipse.birt.report.engine.api.HTMLEmitterConfig;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

import com.systop.common.rpt.ReportContext;
import com.systop.common.util.DateUtil;

/**
 * BIRT报表的帮助类。提供诸如启动报表引擎，以及各种路径。
 * @author SAM
 * 
 */
public final class BirtEngineContext implements ReportContext {
  /**
   * Log for the class
   */
  private static Log log = LogFactory.getLog(BirtEngineContext.class);

  /**
   * BIRT报表引擎
   */
  private IReportEngine engine;

  /**
   * BIRT日志的位置，<code>null</code>表示stdout输出
   */
  private String logDir;

  /**
   * 报表设计文件的位置
   */
  private String rptDesignLocation;

  /**
   * rptdocument文件的位置
   */
  private String rptDocLocation;

  /**
   * 报表图片文件的位置
   */
  private String imageDir;

  /**
   * 生成的PDF文件的位置
   */
  private String generatedPdfLocation;

  /**
   * 用于获取Web应用上下文的各种信息
   */
  private ServletContext servletContext;

  /**
   * 使用IPlatformContext，要求birt home必须位于WEB-INF/Platform下
   */
  private IPlatformContext platformContext;

  /**
   * BIRT engine config
   */
  private EngineConfig engineConfig = new EngineConfig();

  /**
   * 使用线程本地变量保存实例
   */
  private static ThreadLocal<BirtEngineContext> holder = new ThreadLocal();

  /**
   * <code>BirtEngineHelper</code>的实例。
   */
  private static BirtEngineContext instance;

  /**
   * <code>BirtEngineHelper</code>是一个单例类，由于BIRT引擎的初始化非常消耗
   * 资源，所以有必要使得引擎只初始化一次。而BIRT引擎在一个web应用中通常只有一个，
   * 所以 这里做成单例是没有问题的。而且<code>BirtEngineHelper</code>所需要的
   * <code>ServletContext</code>又不能通过spring注入，所以，也只好用单例模式了。
   * @param servletContext
   * @return
   */
  public static BirtEngineContext getInstance(
      ServletContext servletContext) {
    instance = holder.get();
    if (instance == null) {
      instance = new BirtEngineContext(servletContext);
      log.debug("A new BIRT engine created.");
      holder.set(instance);
    }

    return instance;
  }

  /**
   * 构造器，传入必须的<code>ServletContext</code>
   */
  private BirtEngineContext(ServletContext servletContext) {
    this.servletContext = servletContext;

  }

  /**
   * 启动报表引擎
   */
  public IReportEngine getEngine() {
    try {
      if (platformContext == null) {
        platformContext = new PlatformServletContext(servletContext);
      }
      engineConfig = new EngineConfig();
      engineConfig.setEngineHome(servletContext
          .getRealPath("/WEB-INF/platform/"));
      // 要求ENGINE HOME 位于WEB-INF/Platform
      engineConfig.setPlatformContext(platformContext);
      // This call sets the Log directory name and level
      engineConfig.setLogConfig(getLogDir(), Level.FINE);

      // 设置Emitter，渲染HTML的时候，image的地址是HTTP协议而不是File协议
      HTMLEmitterConfig emitterConfig = new HTMLEmitterConfig();
      emitterConfig.setActionHandler(new HTMLActionHandler());
      HTMLServerImageHandler imageHandler = new HTMLServerImageHandler();
      emitterConfig.setImageHandler(imageHandler);
      engineConfig.getEmitterConfigs().put("html", emitterConfig);

      // 设置日志level
      engineConfig.setLogConfig(getLogDir(), Level.WARNING);
      // 启动Platform，创建ReportEngine
      Platform.startup(engineConfig);
      IReportEngineFactory factory = (IReportEngineFactory) Platform
          .createFactoryObject(
              IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
      engine = factory.createReportEngine(engineConfig);
      engine.changeLogLevel(Level.WARNING);

      log.debug("A new engine startup.");
    } catch (BirtException e) {
      e.printStackTrace();
    }

    return engine;
  }

  /**
   * @return 存放报表图片的绝对路径
   */
  public String getImageDirectory() {
    if (imageDir == null) {
      imageDir = makeIfNotExists(
          servletContext.getRealPath("/reports/images/"));
    }
    return imageDir;
  }

  /**
   * @return servlet Context path
   */
  public String getBaseURL() {
    return "/" + servletContext.getServletContextName();
  }

  /**
   * 图片连接URL
   */
  public String getBaseImageURL() {
    return "images";
  }

  /**
   * 报表设计文件的位置
   */
  public String getRptDesignLocation() {
    if (rptDesignLocation == null) {
      rptDesignLocation = makeIfNotExists(servletContext
          .getRealPath("/reports/"));
    }
    return rptDesignLocation;
  }

  /**
   * 生成的rptdocument文件的位置
   */
  public String getRptDocumentLocation() {
    if (rptDocLocation == null) {
      rptDocLocation = makeIfNotExists(servletContext
          .getRealPath("/reports/rptdoc/"));
    }
    return rptDocLocation;
  }

  /**
   * 生成的pdf文件的位置
   */
  public String getGeneratedPdfLocation() {
    if (generatedPdfLocation == null) {
      generatedPdfLocation = makeIfNotExists(servletContext
          .getRealPath("/reports/pdf/"));
    }
    return generatedPdfLocation;
  }

  /**
   * @return the logDir
   */
  public String getLogDir() {
    return logDir;
  }
  /**
   * PDF文件名中随机生成的部分的长度
   */
  public static final int RANDOM_PDF_NAME_LEN = 3;
  /**
   * 构建一个PDF文件名
   */
  public String buildPdfFilename() {
    return new StringBuffer("pdf").append(
        DateUtil.getDateTime("yyyyMMddhhmmss", new Date()))
        .append(RandomStringUtils.randomNumeric(RANDOM_PDF_NAME_LEN))
        .append(".pdf").toString();
  }

  /**
   * 得到完整的报表设计文件的名字，包括路径和文件名
   * @param rptDesingFilename 报表设计文件的名字，可以不包括扩展名
   * @return
   */
  public String getFullRptDesignFilename(String rptDesingFilename) {
    if (rptDesingFilename.indexOf("/") >= 0
        && rptDesingFilename.indexOf("\\") >= 0) {
      return rptDesingFilename;
    }

    if (rptDesingFilename.indexOf(".rptdesign") < 0) {
      rptDesingFilename += ".rptdesign";
    }

    return getRptDesignLocation() + rptDesingFilename;
  }

  /**
   * 根据报表设计文件名称，获取报表document文件名称。
   * @param rptDesingFilename 报表设计文件名称，可以包括路径
   * @return
   */
  public String getFullRptDocumentFilename(String rptDesingFilename) {
    // 如果包含路径，则去掉路径
    int index = (rptDesingFilename.indexOf("/") >= 0) ? rptDesingFilename
        .lastIndexOf("/") : rptDesingFilename.lastIndexOf("\\");
    if (index >= 0) {
      rptDesingFilename = rptDesingFilename.substring(index + 1);
    }
    // 如果包含扩展名则去掉扩展名
    index = rptDesingFilename.lastIndexOf(".rptdesign");
    if (index >= 0) {
      rptDesingFilename = rptDesingFilename.substring(0, index);
    }

    return getRptDocumentLocation() + rptDesingFilename + ".rptdocument";

  }

  /**
   * 如果路径不存在，则创建
   */
  private String makeIfNotExists(String path) {
    File file = new File(path);
    if (!file.exists()) {
      if (!file.mkdir()) {
        return null;
      }
    }
    return path;
  }

}
