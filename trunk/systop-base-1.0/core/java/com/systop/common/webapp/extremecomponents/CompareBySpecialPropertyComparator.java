package com.systop.common.webapp.extremecomponents;

import java.util.Comparator;

import com.systop.common.util.ReflectUtil;

/**
 * 根据pojo的某个properyt进行比较的Comparator
 * @author Sam
 *
 */
public class CompareBySpecialPropertyComparator implements Comparator {
  /**
   * 指定的被比较的Property名称，可以嵌套，例如:<pre>dept.name</pre>
   */
  private String property;

  public final String getProperty() {
    return property;
  }

  public final void setProperty(String property) {
    this.property = property;
  }

  /**
   * @see Comparator#compare(Object, Object)
   */
  public final int compare(Object o1, Object o2) {
    if (o1 == null || o2 == null) {
      return new NullComparator().compare(o1, o2);
    }
    
    Object p1 = ReflectUtil.nestedGet(o1, property);
    Object p2 = ReflectUtil.nestedGet(o2, property);
    
    return new NullComparator().compare(p1, p2);
  }


}
