package quake.base.model;

import com.systop.core.dao.support.Page;

/**
 * 分页相关的Model类的基类，目的是使得model类的结构一致
 * @author Sam
 *
 */
public class PageSchemaAware {
  
  private int start;
  
  private int size;
  
  /**
   * 分页对象
   */
  private Page page;
  
  /**
   * 数据库schema
   */
  private String schema;

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  /**
   * @return the page
   */
  public Page getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Page page) {
    this.page = page;
  }

  /**
   * @return the schema
   */
  public String getSchema() {
    return schema;
  }

  /**
   * @param schema the schema to set
   */
  public void setSchema(String schema) {
    this.schema = schema;
  }
}
