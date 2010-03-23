package com.systop.common.webapp.extremecomponents;

import java.util.Comparator;

import org.apache.commons.beanutils.BeanComparator;

/**
 * 用于比较可能为null的两个对象
 * @author Sam
 */
public final class NullComparator implements Comparator {
  /**
   * Default Constructor
   */
  public NullComparator() {
  }
  
  /**
   * @see Comparator#compare(Object, Object)
   */
  public int compare(Object o1, Object o2) {
    
    if (o1 == null && o2 != null) {
      return -1;
    }
    
    if (o1 != null && o2 == null) {
      return 1;
    }
    
    if (o1 == null && o2 == null) {
      return 0;
    }
    
    return new BeanComparator().compare(o1, o2);
  }
}
