package com.systop.common.webapp.taglibs.template;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.systop.common.Constants;
import com.systop.common.util.ReflectUtil;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;

/**
 * FreeMarker Template render
 * @author Sam Lee
 * 
 */
public class FreeMarkerTemplateRender implements TemplateRender {
  /**
   * Log of the FreeMarkerTemplateRender
   */
  private static Log log = LogFactory.getLog(FreeMarkerTemplateRender.class);

  /**
   * 用于获得Configruation对象
   */
  private FreeMarkerConfigurationManager configurationManager;

  /**
   * @see {@link TemplateRender#renderTemplate(TemplateContext)}
   */
  public void renderTemplate(TemplateContext templateContext) throws Exception {
    String []templateDir = parseSpringStyleDir(templateContext);
    configurationManager.setTemplateLoaderPaths(templateDir);
    Configuration cfg = configurationManager.createConfiguration();

    String templateName = parseTemplateName(templateContext.getTemplate()
        .getTemplateName());
    freemarker.template.Template template = cfg.getTemplate(templateName);
    if (template == null) {
      log.error("Template " + templateName + " is null");
      return;
    }

    Map data = null;
    // 写入template的各种属性
    data = ReflectUtil.toMap(templateContext.getTemplate(), null, true);
    //BeanUtils.populate(templateContext.getTemplate(), data);
    log.debug("Load " + data.size() + " parameters from Template");
    // 写入template的各种数据
    if (data == null) {
      data = new HashMap();
    }
    data.putAll(templateContext.getParams());
    log.debug("Load " + templateContext.getParams().size() 
        + " parameters from TemplateContext");
    log.debug("total paramters " + data.size());
    
    SimpleHash freemarkerData = new SimpleHash();
    freemarkerData.put(Constants.DEFAULT_FREEMARKER_DATAMODEL_NAME, data);

    // 处理Template，得到String类型数据.
    String templateStr = FreeMarkerTemplateUtils.processTemplateIntoString(
        template, freemarkerData);
    templateContext.getWriter().write(templateStr);
    templateContext.getWriter().flush();
  }

  /**
   * 解析模板名称，添加.ftl扩展名
   * 
   */
  static final String parseTemplateName(String name) {
    if (StringUtils.isBlank(name)) {
      log.error("Empty template name");
      return StringUtils.EMPTY;
    }
    if (!name.endsWith(".ftl")) {
      name = name + ".ftl";
    }
    return name;
  }

  /**
   * 解析
   * @param ctx
   * @return
   */
  static final String[] parseSpringStyleDir(TemplateContext ctx) {
    if (ctx == null || ctx.getTemplate() == null) {
      log.error("TemplateContext or Template is null.");
      return null;
    }

    String dir = ctx.getTemplate().getTemplateDir();

    if (dir.startsWith(Template.CLASSPATH_PREFIX)) { // 以classpath:开头
      log.debug("Load template form ClassPath." + dir);
    } else if (dir.startsWith(Template.WEB_DIR_PREFIX)) { // 以web:开头
      dir = dir.substring(Template.WEB_DIR_PREFIX.length()); // 删除web:前缀
      // 开头补充一个"/"
      if (!dir.startsWith("/") || !dir.startsWith("\\")) {
        dir = "/" + dir;
      }

      dir = ctx.getServletContext().getRealPath(dir);
      dir = "file:" + dir;
      log.debug("Load template from file:" + dir);
    } else { // 什么开头都没有，则认为以classpath:开头
      dir = "Template.CLASSPATH_PREFIX" + dir;
    }

    return new String[] {dir, dir + "/" + ctx.getTemplate().getTheme()};
  }

  /**
   * @return the configurationManager
   */
  public FreeMarkerConfigurationManager getConfigurationManager() {
    return configurationManager;
  }

  /**
   * @param configurationManager the configurationManager to set
   */
  public void setConfigurationManager(
      FreeMarkerConfigurationManager configurationManager) {
    this.configurationManager = configurationManager;
  }

}
