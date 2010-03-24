package com.systop.cms.catalog.webapp.ec;

import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import com.systop.cms.catalog.CatalogConstants;

/**
 * ec显示转化类
 * @author yun
 * 
 */
public class CatalogTypeCell extends AbstractCell {

  /**
   * 得到栏目的类别 1 内部栏目 2 外部栏目
   */
  @Override
  protected String getCellValue(TableModel model, Column column) {
    String catalogType = column.getValueAsString();
    if (catalogType.equals(CatalogConstants.CATALOG_TYPE_INNER)) {
      return "内";
    } else {
      return "外";
    }
  }
}
