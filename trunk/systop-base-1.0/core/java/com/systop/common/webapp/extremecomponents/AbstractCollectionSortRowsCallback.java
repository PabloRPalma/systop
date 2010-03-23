package com.systop.common.webapp.extremecomponents;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.callback.NullSafeBeanComparator;
import org.extremecomponents.table.callback.SortRowsCallback;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.limit.Sort;

import com.systop.common.util.ReflectUtil;

/**
 * 对Collection类型的列进行排序的Callback，由extremeTable调用
 * @author Sam
 * 
 */
public abstract class AbstractCollectionSortRowsCallback 
  implements SortRowsCallback {
  /**
   * Log of the class
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * 根据给定Collection中的对象的一个一个属性对象Collection排序，
   * 这个属性 是一个Collection的实例。
   * @param rows 指定的Collection对象，其元素必须都是同一类型的对象
   * @param collectionProperty 那个Collection类型的属性名称
   * @param keyProperty 那个Collection类型的属性排序字段
   * @param sortOrder 升序或降序（TableConstants.SORT_ASC，
   * TableConstants.SORT_DESC）
   * @return 排序之后的Collection，或null
   */
  protected abstract Collection sortByCollectionProperty(Collection rows,
      String collectionProperty, String keyProperty, String sortOrder);

  /**
   * 处理Collection类型的列
   * 
   */
  public final Collection processCollectionProperty(Collection rows,
      String property, String sortOrder) {
    if (rows == null || rows.size() == 0) {
      return rows;
    }
    // 从给定排序Collection中任意取出一个非null的元素，
    //作为判断指定属性是否为Collection的测试对象
    Object targetForTestInstance = null;
    for (Iterator it = rows.iterator(); it.hasNext();) {
      targetForTestInstance = it.next();
      if (targetForTestInstance != null) {
        break;
      }
    }

    if (targetForTestInstance == null) {
      log.debug("Test target is null.");
      return null;
    }

    String[] props = property.split("\\.");
    log.debug("array of properties " + props);
    // 取出属性列表的第一个属性
    Object porpertyObject = ReflectUtil.get(targetForTestInstance, props[0]);
    // 如果该属性为Collectioin则根据该属性排序（排序规则由子类提供）
    if (porpertyObject != null && (porpertyObject instanceof Collection)) {
      String key = property.substring(property.indexOf(".") + 1);
      log.debug("The property of '" + props[0] + "' is Collection, "
          + "we will sort by the proporties '" + key + "' of its elements");
      return sortByCollectionProperty(rows, props[0], key, sortOrder);
    }

    return null;
  }
  
  /**
   * @see SortRowsCallback#sortRows(TableModel, Collection)
   */
  public final Collection sortRows(TableModel model, Collection rows)
      throws Exception {
    boolean sorted = model.getLimit().isSorted();

    if (!sorted) {
      return rows;
    }

    Sort sort = model.getLimit().getSort();
    String property = sort.getProperty();
    if (StringUtils.isBlank(property)) {
      property = sort.getAlias();
    }

    String sortOrder = sort.getSortOrder();

    if (StringUtils.contains(property, ".")) {
      // 如果指定属性是一个Collection对象，则对其进行排序
      Collection c = processCollectionProperty(rows, property, sortOrder);
      if (c != null && c.size() > 0) {
        return c;
      }

      try {
        if (sortOrder.equals(TableConstants.SORT_ASC)) {
          Collections.sort((List) rows, new NullSafeBeanComparator(property,
              new NullComparator()));
        } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
          NullSafeBeanComparator reversedNaturalOrderBeanComparator 
            = new NullSafeBeanComparator(property, 
                new ReverseComparator(new NullComparator()));
          Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
        }
      } catch (NoClassDefFoundError e) {
        String msg = "The column property ["
            + property
            + "] is nested and requires BeanUtils 1.7" 
            + " or greater for proper sorting.";
        log.error(msg);
        throw new NoClassDefFoundError(msg); // just rethrow so it is not
        // hidden
      }
    } else {
      if (sortOrder.equals(TableConstants.SORT_ASC)) {
        BeanComparator comparator = new BeanComparator(property,
            new NullComparator());
        Collections.sort((List) rows, comparator);
      } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
        BeanComparator reversedNaturalOrderBeanComparator = new BeanComparator(
            property, new ReverseComparator(new NullComparator()));
        Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
      }
    }

    return rows;
  }

}
