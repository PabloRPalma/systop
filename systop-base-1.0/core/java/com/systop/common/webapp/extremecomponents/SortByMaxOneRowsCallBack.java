package com.systop.common.webapp.extremecomponents;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.extremecomponents.table.core.TableConstants;

/**
 * 在一个由“一对多”的Object组成的Collection中，按照“多”的一方的一个最大值，对
 * Collection中的元素进行排序
 * @author Sam
 *
 */
public final class SortByMaxOneRowsCallBack 
  extends AbstractCollectionSortRowsCallback {

  /**
   * @see AbstractCollectionSortRowsCallback#sortByCollectionProperty(
   * Collection, String, String, String)
   */
  public Collection sortByCollectionProperty(Collection rows,
      String collectionProperty, String keyProperty, String sortOrder) {
    
    SortByCollectionPropertyComparator comparator
      = new SortByCollectionPropertyComparator();
    comparator.setCollectionProperty(collectionProperty);
    comparator.setSortKey(keyProperty);
    
    if (sortOrder.equals(TableConstants.SORT_ASC)) {
      Collections.sort((List) rows, comparator);
    } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
      Collections.sort((List) rows, new ReverseComparator(comparator));
    }
    
    return rows;
  }
  
}
