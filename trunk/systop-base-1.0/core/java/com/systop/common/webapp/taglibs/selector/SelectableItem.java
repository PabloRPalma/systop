package com.systop.common.webapp.taglibs.selector;
/**
 * 可以被用于<select/>标记的对象，简化ftl编程。
 * @author Sam
 *
 */
public class SelectableItem {
  /**
   * 是否被选中
   */
  private boolean selected;
  /**
   * 值
   */
  private String value;
  /**
   * 显示值
   */
  private String label;
  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }
  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }
  /**
   * @return the selected
   */
  public boolean getSelected() {
    return selected;
  }
  /**
   * @param selected the selected to set
   */
  public void setSelected(boolean selected) {
    this.selected = selected;
  }
  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }
  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }
}
