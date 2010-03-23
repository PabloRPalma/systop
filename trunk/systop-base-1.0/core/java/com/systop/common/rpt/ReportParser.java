package com.systop.common.rpt;

import java.io.OutputStream;
import java.util.Map;

/**
 * 报表解析器，提供解析报表的接口。实现类根据不同的报表引擎(birt,jasperReport) 提供各自的实现。
 * @author Sam
 * 
 */
public interface ReportParser {
  /**
   * 解析一个指定的报表文件，并将结果输出到给出的<code>OutputStream</code>对象中。
   * @param reportFile 给出报表设计文件的路径和文件名.
   * @param out 给出<code>OutputStream</code>，报表解析完毕后将结果输出到这里。
   * @param model 解析报表所需的数据。
   * @param parameters 解析报表所需的参数。
   */
  void parse(String reportFile, OutputStream out, Object model, Map parameters);
  
  /**
   * 设置报表上下文
   * @param reportContext
   */
  void setReportContext(ReportContext reportContext);
}
