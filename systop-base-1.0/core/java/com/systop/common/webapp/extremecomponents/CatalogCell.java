package com.systop.common.webapp.extremecomponents;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;

import com.systop.common.catalog.CatalogUtil;

/**
 * 转换Catalog中的数据.
 * @author Sam
 * 
 */
public class CatalogCell extends AbstractSpringAwareCell {
  /**
   * 用于查询Catalog数据
   */
  private CatalogUtil catalogUtil;

  /**
   * 转换Table中采用了Catalog数据的值.
   */
  @Override
  protected String getCellValue(TableModel model, Column column) {
    if (catalogUtil == null) {
      catalogUtil = (CatalogUtil) getBean("catalogUtil", model);
    }

    return catalogUtil.getString(column.getAlias(), column.getValueAsString());

  }

}
