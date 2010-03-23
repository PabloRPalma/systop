package com.systop.common.webapp.extremecomponents;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;

import com.systop.common.util.ReflectUtil;
import com.systop.common.util.StringUtil;

/**
 * 显示Collection类型的属性。
 * 只显示Collection中最大的那个元素的某个属性。
 * 例如，Dept类有一个List类型的members属性：
 * <pre>
 * class Dept {
 *   private List members;
 * }
 * //Member是部门成员类
 * class Member {
 *   private String name;
 *   private Date birthday;
 * }
 * //我们需要显示每个部门中，年龄最大的那个人的名字。property属性的写法是
 * members.birthday
 * 别名是 name.
 * 也就是说，property填写Collection类型的属性名字和需要排序的字段名字。
 * 别名是需要显示的字段的名字。
 * 
 * </pre>
 * @author Sam
 *
 */
public class DisplayMaxOneCell extends AbstractCell {
  /**
   * Log of the class
   */
  protected Log log = LogFactory.getLog(getClass());
 
  /**
   * @see AbstractCell#getHtmlDisplay(TableModel, Column)
   */
  public final String getHtmlDisplay(TableModel model, Column column) {
    ColumnBuilder columnBuilder = new ColumnBuilder(column);
    columnBuilder.tdStart();
    columnBuilder.tdBody(getCellValue(model, column));
    columnBuilder.tdEnd();
    
    return columnBuilder.toString();
  }
  
  /**
   * 取得指定对象的一个Collection类型的属性的最大元素。例如，dept（部门）对象包含
   * 一个members（成员s）列表类型为List，getMaxObjectOfCollectionProperty
   * 可以根据member(members的元素)的某个指定的字段对members进行排序，并返回最大的那个
   * @param column 
   * @return
   */
  private Object getMaxObjectOfCollectionProperty(Object rowBean, 
      String collectionProperty, String comparedProperty) {
      Object obj = ReflectUtil.get(rowBean, collectionProperty);
      
      if (obj == null || !(obj instanceof Collection)) {
        throw new ClassCastException("Can't cast the property '"
            + collectionProperty + " to List.");
      }
      Collection c = (Collection) obj;
      
      CompareBySpecialPropertyComparator comparator = 
        new CompareBySpecialPropertyComparator();
      comparator.setProperty(comparedProperty);
      
      return Collections.max(c, comparator);
  }

  
  /**
   * 取得Collection属性中，最大的那个元素的需要显示的字段
   */
  public final String getCellValue(TableModel model, Column column) {
    String property = column.getProperty();
    if (property.indexOf(".") < 0) {
      return column.getValueAsString();
    }
    //取得当前行
    Object rowBean = model.getCurrentRowBean();
    
    String []props = property.split("\\."); //property拆分
    //子属性，也就是排序的属性
    String subProp = property.substring(property.indexOf(".") + 1);
    
    try {
      //取得Collection列的最大元素
      Object maxOne = getMaxObjectOfCollectionProperty(rowBean, props[0],
        subProp);
      //最大元素的指定属性
      Object result = ReflectUtil.get(maxOne, 
          column.getAlias().replaceAll("_", ".")); //alias才是需要显示的字段
      log.debug("get max object '" + result + "'");
      return (result == null) ? StringUtil.EMPTY_STRING : result.toString();
    } catch (Exception e) {
      return StringUtil.EMPTY_STRING;
    }
  }

  

}
