package com.systop.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.common.webapp.extremecomponents.
  CompareBySpecialPropertyComparator;

/**
 * @author Sam version 2.0
 */
public final class CollectionUtil {
  /**Log*/
  private static Log log = LogFactory.getLog(CollectionUtil.class);
  /**
   * 取得指定对象的一个Collection类型的属性的最大元素。例如，dept（部门）对象包含
   * 一个members（成员s）列表类型为List，getMaxObjectOfCollectionProperty
   * 可以根据member(members的元素)的某个指定的字段对members进行排序，并返回最大的那个
   * 
   * @param column
   * @return
   */
  public static Object getMaxObjectOfCollectionProperty(Object rowBean,
      String collectionProperty, String comparedProperty) {
    Object obj = ReflectUtil.get(rowBean, collectionProperty);

    if (obj == null || !(obj instanceof Collection)) {
      throw new ClassCastException("Can't cast the property '"
          + collectionProperty + " to List.");
    }
    Collection c = (Collection) obj;

    return getMaxObjectOfCollection(c, comparedProperty);
  }

  /**
   * 根据某个指定的属性的大小，返回Collection中某个对象。
   * 
   * @param c 指定的Collection
   * @param comparedProperty Collection中对象的某个属性名称
   */

  public static Object getMaxObjectOfCollection(Collection c,
      String comparedProperty) {

    CompareBySpecialPropertyComparator comparator = 
      new CompareBySpecialPropertyComparator();
    comparator.setProperty(comparedProperty);

    return Collections.max(c, comparator);
  }

  /**
   * 将List类型集合的转成Set类型
   * @param list 所需转换的List
   * @return
   */
  public static Set listToSet(List list) {
    Set set = new HashSet();
    for (Object object : list) {
      set.add(object);
    }
    return set;
  }

  /**
   * 将Set类型集合的转成List类型
   * @param set 所需转换的Set
   * @return
   */
  public static List setToList(Set set) {
    List list = new ArrayList();
    for (Object object : set) {
      list.add(object);
    }
    return list;
  }

  /**
   * 将Map中为NULL字段替换为一个其他的Value,
   * @param src 原始Map
   * @param nullValue 用于替换的value
   * @return 替换之后的Map
   */
  public static Map cleanoutNullForMap(Map src, Object nullValue) {
    if (src == null || src.isEmpty()) {
      return src;
    }
    Set keys = src.keySet();
   
    for (Iterator itr = keys.iterator(); itr.hasNext();) {
      Object key = itr.next();
      if (src.get(key) == null) {
        src.put(key, nullValue);
        log.debug("cleanout Null for " + key);
      }
    }

    return src;
  }

  /**
   * private constructor
   */
  private CollectionUtil() {
  }
}
