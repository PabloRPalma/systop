package com.systop.common.rpt.birt;

import java.util.HashMap;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.RenderOptionBase;
/**
 * 生成HTML格式报表
 * @author SAM
 *
 */
public class HTMLBirtReportParser extends AbstractBirtReportParser {

  /**
   * @see AbstractBirtReportParser#setRenderContext(IEngineTask)
   */
  @Override
  protected void setRenderContext(IEngineTask task) {
    HTMLRenderContext renderContext = new HTMLRenderContext();
    renderContext.setBaseURL(context.getBaseURL());        
   //You must define HTMLServerImageHandler to use a URL
    renderContext.setBaseImageURL(context.getBaseImageURL());
    //renderContext.setImageDirectory("myimages");
    renderContext.setImageDirectory(context.getImageDirectory());
    renderContext.setSupportedImageFormats("JPG;PNG");
    HashMap contextMap = new HashMap();
    contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, 
        renderContext);
    task.setAppContext(contextMap);    
  }

  /**
   * @see AbstractBirtReportParser#getRenderOptions()
   */
  @Override
  protected RenderOptionBase getRenderOptions() {
    HTMLRenderOption options = new HTMLRenderOption();
    options.setEmbeddable(true);
    return options;
  }
 
}
 
