package com.systop.common.webapp.struts2.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.systop.common.rpt.ReportException;
import com.systop.common.rpt.ReportParser;
import com.systop.common.rpt.birt.BirtEngineContext;
import com.systop.common.rpt.birt.HTMLBirtReportParser;
import com.systop.common.rpt.birt.PDFBirtReportParser;
import com.systop.common.service.Manager;

/**
 * 使用BIRT做报表的Struts2 Action基类
 * @author SAM
 * 
 * @param <E> 所使用的Manager
 */
public class BaseBirtAction<E extends Manager> extends BaseAction<E> {

  /**
   * 报表文件名，不包括路径
   */
  private String reportFile;

  /**
   * 报表文本，页面可以使用<code>${reportHtml}</code>加载报表文本
   */
  private String htmlReport;

  /**
   * 生成的PDF文件的URL路径
   */
  private String pdfFileUrl;
  /**
   * HTML报表解析器
   */
  private ReportParser htmlParser;
  /**
   * PDF报表解析器
   */
  private ReportParser pdfParser;
  /**
   * BIRT报表上下文
   */
  private BirtEngineContext birtEngineContext;

  /**
   * 返回HTML格式的报表解析器
   * @see ReportParser
   */
  protected ReportParser getHTMLParser() {
    if (htmlParser == null) {
      if (birtEngineContext == null) {
        birtEngineContext = BirtEngineContext.getInstance(getServletContext());
      }
      htmlParser = new HTMLBirtReportParser();
      htmlParser.setReportContext(birtEngineContext);
    }

    return htmlParser;
  }

  /**
   * 
   * @return PDF格式的报表解析器
   */
  protected ReportParser getPDFParser() {
    if (pdfParser == null) {
      if (birtEngineContext == null) {
        birtEngineContext = BirtEngineContext.getInstance(getServletContext());
      }
      pdfParser = new PDFBirtReportParser();
      pdfParser.setReportContext(birtEngineContext);
    }

    return pdfParser;
  }

  /**
   * 获取报表数据，适用于Scriptable DataSource的报表。
   * 子类可以覆盖这个方法，以实现业务逻辑需要 的数据。
   *  缺省的，这个方法返回<code>null</code>
   */
  protected Object getDataMode() {
    return null;
  }

  /**
   * 返回由<code>model</code>所有非空属性组成的Map.子类可覆盖这个方法，以实现特殊的参数。
   */
  protected Map getReportParams() {
  	Map paramMap = new HashMap();
  	Enumeration e = getHttpServletRequest().getParameterNames();
  	while (e.hasMoreElements()) {
  		String paramer = (String) e.nextElement();
  		paramMap.put(paramer, getHttpServletRequest().getParameter(paramer));
  	}
    return paramMap;
  }

  /**
   * 生成HTML报表
   * @return
   */
  public String htmlReport() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      getHTMLParser().parse(reportFile, out, getDataMode(), getReportParams());
      htmlReport = new String(out.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
      addActionError(e.getMessage());
      return INPUT;
    }
    return SUCCESS;
  }

  /**
   * 生成PDF报表
   */
  public String pdfReport() {
    if (birtEngineContext == null) {
      birtEngineContext = BirtEngineContext.getInstance(getServletContext());
    }

    String pdf = birtEngineContext.buildPdfFilename(); // pdf 文件名
    String pdfDir = birtEngineContext.getGeneratedPdfLocation(); // pdf路径

    OutputStream out = null;
    try {
      out = new BufferedOutputStream(new FileOutputStream(pdfDir + pdf));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      addActionError("文件不存在-" + pdfDir + pdf);
      return INPUT;
    }

    try {
      getPDFParser().parse(reportFile, out, getDataMode(), getReportParams());
    } catch (ReportException e) {
      addActionError("生成报表时发生错误:" + e.getMessage());
      return INPUT;
    }

    StringBuffer buf = new StringBuffer(getHttpServletRequest()
        .getContextPath()).append("/reports/pdf/").append(pdf);
    pdfFileUrl = buf.toString();

    return SUCCESS;
  }

  /**
   * @return the reportFile
   */
  public String getReportFile() {
    return reportFile;
  }

  /**
   * @param reportFile the reportFile to set
   */
  public void setReportFile(String reportFile) {
    this.reportFile = reportFile;
  }

  /**
   * @return the htmlReport
   */
  public String getHtmlReport() {
    return htmlReport;
  }

  /**
   * @param htmlReport the htmlReport to set
   */
  public void setHtmlReport(String htmlReport) {
    this.htmlReport = htmlReport;
  }

  /**
   * @return the pdfFileUrl
   */
  public String getPdfFileUrl() {
    return pdfFileUrl;
  }

  /**
   * @param pdfFileUrl the pdfFileUrl to set
   */
  public void setPdfFileUrl(String pdfFileUrl) {
    this.pdfFileUrl = pdfFileUrl;
  }

}
