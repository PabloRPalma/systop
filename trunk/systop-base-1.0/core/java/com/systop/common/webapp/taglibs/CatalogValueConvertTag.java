package com.systop.common.webapp.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.systop.common.catalog.CatalogUtil;

/**
 * 转换catalog值的对象
 * @author Sam
 *
 */
public class CatalogValueConvertTag extends BaseSpringAwareBodyTagSupport {
  /**
   * Catalog类别名称
   */
  private String catalog;
  /**
   * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
   */
  @Override
  public int doEndTag() throws JspException {
    String referenceValue = bodyContent.getString();
    CatalogUtil cu = (CatalogUtil) getBean("catalogUtil");
    String strValue = cu.getString(catalog, referenceValue);
    try {
      pageContext.getOut().println(strValue);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return EVAL_PAGE;
  }
  /**
   * @return the catalog
   */
  public String getCatalog() {
    return catalog;
  }
  /**
   * @param catalog the catalog to set
   */
  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }
  
}
