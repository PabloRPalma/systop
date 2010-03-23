package com.systop.common.webapp.taglibs.template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.systop.common.Constants;
import com.systop.common.test.BaseTestCase;
/**
 * Test case of {@link FreeMarkerTemplateRender}
 * @author Sam Lee
 *
 */
public class FreeMarkerRenderTestCase extends BaseTestCase {
  /**
   * Test {@link FreeMarkerTemplateRender#renderTemplate(TemplateContext)}
   *
   */
  public void testRenderTemplate() {
    FreeMarkerTemplateRender render = (FreeMarkerTemplateRender) 
      applicationContext.getBean("freemarkerTemplateRender");
    LayoutTemplate template = new LayoutTemplate(
        "classpath:com/systop/common/webapp/taglibs/template", //模板位置
        "ftl", //模板主题
        "test", //模板名称
        "100", //x位置
        "500", //y位置
        "200px", //宽度
        "true"); //是否显示
    //模板的数据
    List list = new ArrayList();
    list.add("黑客帝国");
    list.add("白客帝国");
    
    TemplateContext ctx = new TemplateContext();
    ctx.addParameter(Constants.DEFAULT_LIST_NAME, list);
    ctx.setTemplate(template);
    Writer writer = new StringWriter();
    ctx.setWriter(writer);
    
    try {
      render.renderTemplate(ctx);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println(writer.toString());
  }
}
