package com.systop.common.webapp.extremecomponents;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.AbstractHtmlView;
import org.extremecomponents.table.view.CompactToolbar;
import org.extremecomponents.util.HtmlBuilder;

/**
 * 将Extremetable的工具条放在下面
 * @author Sam
 * 
 */
public class CompactView extends AbstractHtmlView {
  /**
   * 生成TBody之前的工作 
   */
  protected void beforeBodyInternal(TableModel model) {
    getTableBuilder().tableStart();
    getTableBuilder().theadStart();
    getTableBuilder().titleRowSpanColumns();
    // toolbar(getHtmlBuilder(), getTableModel());
    getTableBuilder().filterRow();
    getTableBuilder().headerRow();
    getTableBuilder().theadEnd();
    getTableBuilder().tbodyStart();
  }
  
  /**
   * 生成TBody之后的工作
   */
  protected void afterBodyInternal(TableModel model) {
    getCalcBuilder().defaultCalcLayout();
    getTableBuilder().tbodyEnd();
    getTableBuilder().theadStart();
    getTableBuilder().titleRowSpanColumns();
    toolbar(getHtmlBuilder(), getTableModel());
    getTableBuilder().theadEnd();
    getTableBuilder().tableEnd();
  }
  
  /**
   * 创建ToolBar
   */
  protected void toolbar(HtmlBuilder html, TableModel model) {
    new CompactToolbar(html, model).layout();
  }
}
