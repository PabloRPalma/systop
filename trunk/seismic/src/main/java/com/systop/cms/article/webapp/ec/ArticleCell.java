package com.systop.cms.article.webapp.ec;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import com.systop.cms.model.Articles;

/**
 * 全文检索文章链接设置
 * @author Administrator
 * 
 */
public class ArticleCell extends AbstractCell {

  /**
   * 
   */
  @Override
  protected String getCellValue(TableModel model, Column column) {
    Object obj = column.getValue();
    if (obj == null) {
      return StringUtils.EMPTY;
    }

    Articles atl = (Articles) model.getCurrentRowBean();
    String ctx = model.getContext().getContextPath();
    if (obj instanceof String) {
      return "<img src='" + ctx + "/images/icons/articleIco.gif'/>&nbsp;&nbsp;"
          + "<a href='" + ctx + atl.getPath() + "' target='_blank'"
          + " class='normal'>" + obj + "</a>";
    }
    return column.getValueAsString();
  }

}
