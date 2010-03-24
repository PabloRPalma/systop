package com.systop.common.modules.template.freemarker.servlet;

import java.util.Map;

/**
 * 用于存放模板名称和对应的数据[Model]
 * 
 * @author lunch
 */
public class StringTemplateContext {

  /** 模板名称 */
  private String templateName;

  /** 模板内容 */
  private String templateContent;

  /** 模板数据 */
  @SuppressWarnings("unchecked")
  private Map model;

  /**
   * 默认构造
   */
  public StringTemplateContext() {
    this.templateName = null;
    this.model = null;
  }

  /**
   * 构造方法
   */
  @SuppressWarnings("unchecked")
  public StringTemplateContext(String tName, Map model) {
    this.templateName = tName;
    this.model = model;

  }

  @SuppressWarnings("unchecked")
  public Map getModel() {
    return model;
  }

  @SuppressWarnings("unchecked")
  public void setModel(Map model) {
    this.model = model;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateContent() {
    return templateContent;
  }

  public void setTemplateContent(String templateContent) {
    this.templateContent = templateContent;
  }
}
