package com.systop.common.webapp.extremecomponents;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.beanutils.BeanComparator;

import com.systop.common.util.ReflectUtil;

/**
 * 根据POJO的某个Collection类型的属性的某个元素的某个属性进行比较。例如：
 * <pre>
 * //Dept是部门类，members是它的一个属性，类型为List，保存该部门的成员类Member。
 * class Dept {
 *   private List members;
 * }
 * //Member是部门成员类
 * class Member {
 *   private String name;
 *   private Date birthday;
 * }
 * 
 * //现在需要根据members中年龄最大的Member，对部门进行排序。
 * SortByCollectionPropertyComparator sbcpc = 
 *   new SortByCollectionPropertyComparator();
 * sbcpc.setCollectionProperty("members");
 * sbcpc.setSortkey("birthday");
 * sbcpc.compare(...);
 * </pre>
 * @author Sam
 *
 */

public class SortByCollectionPropertyComparator extends BeanComparator {
  /**
   * 类型为Collection的那个属性的名字。
   */
  private String collectionProperty;
  
  /**
   * Collection类型的属性中，Pojo元素的某个property名称，该property作为判断Pojo大小的依据
   */  
  private String sortKey;
  
  public final String getCollectionProperty() {
    return collectionProperty;
  }

  public final void setCollectionProperty(String collectionProperty) {
    this.collectionProperty = collectionProperty;
  }

  public final String getSortKey() {
    return sortKey;
  }

  public final void setSortKey(String sortKey) {
    this.sortKey = sortKey;
  }
  
  /**
   * @see java.util.Comparator#compare(Object, Object)
   * @see CompareBySpecialPropertyComparator
   */
  public final int compare(Object o1, Object o2) {
    if (o1 == null || o2 == null) {
      return new NullComparator().compare(o1, o2);
    }
    //取得Collection类型的属性
    Object p1 = ReflectUtil.get(o1, collectionProperty);
    Object p2 = ReflectUtil.get(o2, collectionProperty);
    
    if (!(p1 instanceof Collection) || !(p2 instanceof Collection)) {
      return 0;
    }
    
    Collection c1 = (Collection) p1;
    Collection c2 = (Collection) p2;
    
    //Pojo Comparator
    CompareBySpecialPropertyComparator compareBySpecialProperty = 
      new CompareBySpecialPropertyComparator();
    compareBySpecialProperty.setProperty(sortKey);
    
    //取出Collection中最大的
    Object c1Max = Collections.max(c1, compareBySpecialProperty);
    Object c2Max = Collections.max(c2, compareBySpecialProperty);
    
    //Collection中最大的成员大小，就是包含Collection的对象的大小。
    return compareBySpecialProperty.compare(c1Max, c2Max);
  }

}
