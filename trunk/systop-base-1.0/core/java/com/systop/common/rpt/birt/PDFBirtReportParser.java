package com.systop.common.rpt.birt;

import java.util.HashMap;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.PDFRenderContext;
import org.eclipse.birt.report.engine.api.RenderOptionBase;
/**
 * 生成PDF格式报表
 * @author SAM
 *
 */
public class PDFBirtReportParser extends AbstractBirtReportParser {

  /**
   * @see AbstractBirtReportParser#setRenderContext(IEngineTask)
   */
  @Override
  protected void setRenderContext(IEngineTask task) {
    PDFRenderContext renderContext = new PDFRenderContext();
    renderContext.setEmbededFont(true);    
    HashMap contextMap = new HashMap();
    contextMap.put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT,
        renderContext);
    task.setAppContext(contextMap);        
  }

  /**
   * @see AbstractBirtReportParser#getRenderOptions()
   */
  @Override
  protected RenderOptionBase getRenderOptions() {
    RenderOptionBase options = new RenderOptionBase();
    options.setOutputFormat(RenderOptionBase.OUTPUT_FORMAT_PDF);
    return options;
  }

}
 
