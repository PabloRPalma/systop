package com.systop.common.rpt.birt;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.engine.api.RenderOptionBase;

import com.systop.common.rpt.ReportContext;
import com.systop.common.rpt.ReportParser;

/**
 * 抽象的BIRT报表解析器，提供一些通用的方法。
 * @author SAM
 * 
 */
public abstract class AbstractBirtReportParser implements ReportParser {
  /**
   * Log of the class
   */
  protected Log log = LogFactory.getLog(getClass());
  /**
   * 用于获取ReportEngine实例和其他路径等信息
   */
  protected BirtEngineContext context;

  /**
   * 运行指定的报表，将它保存为.rptdoucment文件
   * @param rptDesign
   * @return full filename of the generated .rptdocument
   */
  public String run(String rptDesign) {
    assert (context != null);
    String doc = null;
    IReportEngine engine = context.getEngine();
    // Open a report design
    IReportRunnable design = null;
    try {
      design = engine.openReportDesign(context
          .getFullRptDesignFilename(rptDesign));
      // Create task to run the report - use the task to
      // execute the report and save to disk.
      IRunTask task = engine.createRunTask(design);
      doc = context.getFullRptDocumentFilename(rptDesign);
      // run the report and destroy the engine
      task.run(doc);
      
      log.debug("save rpt design to " + doc);
      task.close();
    } catch (EngineException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    
    return doc;
  }
  /**
   * 用给出的数据渲染报表文件
   * @deprecated use {@link AbstractBirtReportParser#runAndRender(
   *   String, OutputStream, Object, Map)}
   */
  protected void render(String rptDesign, OutputStream out, Object scriptObj,
      Map params) {
    assert (context != null);

    String docFilename = run(rptDesign);
    IReportEngine engine = context.getEngine();
    try {
      IReportDocument iReportDocument = engine.openReportDocument(docFilename);
      //Create Render Task
      IRenderTask task = engine.createRenderTask(iReportDocument);
      task.addScriptableJavaObject("jsBirtObject", 
          getScriptableObject(scriptObj));
      if (params != null && !params.isEmpty()) {
        task.setParameterValues(params);
      }
      setRenderContext(task); //设置Render Context，具体由子类实现
      //设置RenderOptions属性
      RenderOptionBase renderOpts = getRenderOptions();
      renderOpts.setOutputStream(out);
      task.setRenderOption(renderOpts);
      
      task.render();
      task.close();
    } catch (EngineException e) {
      e.printStackTrace();
    } finally {
      engine.shutdown();
    }
  }
  /**
   * 渲染报表文件，但是不保存.rptdocument
   * 
   */
  protected void runAndRender(String rptDesign, OutputStream out,
      Object scriptObj, Map params) {
    IReportEngine engine = context.getEngine();
    // Open a report design
    IReportRunnable design = null;
    try {
      design = engine.openReportDesign(context
          .getFullRptDesignFilename(rptDesign));
      // Create task to run the report - use the task to
      // execute the report and save to disk.
      IRunAndRenderTask task = engine.createRunAndRenderTask(design);
      task.addScriptableJavaObject("jsBirtObject", 
          getScriptableObject(scriptObj));
      if (params != null && !params.isEmpty()) {
        task.setParameterValues(params);
      }
      setRenderContext(task); //设置Render Context，具体由子类实现
      //    设置RenderOptions属性
      RenderOptionBase renderOpts = getRenderOptions();
      renderOpts.setOutputStream(out);
      task.setRenderOption(renderOpts);
      task.run();
      task.close();
    } catch (EngineException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }
  /**
   * 建造一个<code>ScriptableObject</code>实例
   */
  protected Object getScriptableObject(Object obj) {
    if (obj != null) {
      if (obj instanceof List) {
        ScriptableObject so = new ScriptableObject();
        so.setDataModel((List) obj);
        return so;
      }
    }
    
    return obj;
  }
 
  /**
   * 设置<code>IRenderTask</code>的RenderContext。下面的例子是设置HTMLRenderContext，
   * 设置PDF的类似。
   * 
   * <pre>
   * HTMLRenderContext renderContext = new HTMLRenderContext();
   * renderContext.setBaseURL(&quot;http://localhost/&quot;);
   * 
   * //You must define HTMLServerImageHandler to use a URL
   * renderContext.setBaseImageURL(&quot;http://localhost/&quot;);
   * renderContext.setImageDirectory(&quot;myimages&quot;);
   * renderContext.setImageDirectory(&quot;C:/xampplite/htdocs/myimages&quot;);
   * renderContext.setSupportedImageFormats(&quot;JPG;PNG;BMP;SVG&quot;);
   * HashMap contextMap = new HashMap();
   * contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
   *  renderContext);
   * rtask.setAppContext(contextMap);
   * </pre>
   * 
   * @param task
   */
  protected abstract void setRenderContext(IEngineTask task);
  /**
   * 返回<code>RenderOptions</code>其实主要是格式不同。例如，
   * 下面的例子设置HTMLRenderOption 
   * <br>
   * 
   * <pre>
   * HTMLRenderOption options = new HTMLRenderOption();
   * options.setEmbeddable(true);
   * </pre>
   * 
   * @param task
   * @param out
   */
  protected abstract RenderOptionBase getRenderOptions();

  

  /**
   * @return the birtEngineContext
   */
  public BirtEngineContext getReportContext() {
    return context;
  }

  /**
   * @param birtEngineContext the birtEngineContext to set
   */
  public void setReportContext(ReportContext birtEngineContext) {
    this.context = (BirtEngineContext) birtEngineContext;
  }
  
  /**
   * @see ReportParser#parse(String, OutputStream, Object, Map)
   */
  public void parse(String reportFile, OutputStream out, 
      Object model, Map parameters) {
    //render(reportFile, out, model, parameters);
    this.runAndRender(reportFile, out, model, parameters);
  }

}
