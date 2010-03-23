package com.systop.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * refect helper class
 * 
 * @author Cats_Tiger
 */
public final class ReflectUtil {
  /**
   * get、set方法前缀的长度
   */
  private static final int METHOD_SUBFIX_LEN = 3;

  /**
   * log for this class
   */
  private static Log log = LogFactory.getLog(ReflectUtil.class);

  /**
   * Prevent from initializing.
   */
  private ReflectUtil() {
  }

  /**
   * Load the special class
   */
  public static Class classForName(String name) throws ClassNotFoundException {
    try {
      ClassLoader contextClassLoader = Thread.currentThread()
          .getContextClassLoader();
      if (contextClassLoader != null) {
        return contextClassLoader.loadClass(name);
      } else {
        return Class.forName(name);
      }
    } catch (Exception e) {
      return Class.forName(name);
    }
  }

  /**
   * 判断指定的名字是否是一个类名。
   */
  public static boolean isClass(String name) {
    Class clazz = null;
    ClassLoader contextClassLoader = Thread.currentThread()
        .getContextClassLoader();
    try {
      if (contextClassLoader != null) {
        clazz = contextClassLoader.loadClass(name);
      } else {
        clazz = Class.forName(name);
      }
    } catch (Throwable e) {
      return false;
    }

    return (clazz != null);
  }

  /**
   * instantiating an object by special name,if failed,returns <tt>null</tt>
   */
  public static Object newInstance(String name) {
    Class clazz = null;
    try {
      clazz = classForName(name);
    } catch (ClassNotFoundException e) {
      log.error("class " + name + " not found.");
      e.printStackTrace();
    }
    return newInstance(clazz);

  }

  /**
   * 执行class.newInstance方法，并处理异常.
   * 
   * @param type Type of the object to be instantialized.
   * @return object to be instantialized, 如果失败，返回null
   */
  public static Object newInstance(Class type) {
    if (type == null) {
      return null;
    }

    try {
      return type.newInstance();
    } catch (InstantiationException e) {
      log.error("error instantiation." + e.getMessage());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      log.error("error IllegalAccess." + e.getMessage());
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Invoke the java bean's getter method.
   * 
   * @param targetObject the target object to be invoked
   * @param propertyName property name of the setter, it can be a Database's
   *          fieldname like first_name, the name like that will be trained as
   *          firstName
   * @return
   */
  public static Object get(Object targetObject, String propertyName) {
    if (targetObject == null) {
      return null;
    }

    propertyName = propertyName.replaceAll("_|-", "");

    Class clazz = targetObject.getClass();
    Method[] methods = clazz.getDeclaredMethods();
    Method potentialGetter = null;

    for (int i = 0; i < methods.length; i++) {
      String methodName = methods[i].getName();
      if (methods[i].getParameterTypes().length == 0
          && methodName.startsWith("get")) {
        if (methodName.substring(METHOD_SUBFIX_LEN).equalsIgnoreCase(
            propertyName)) {
          potentialGetter = methods[i];
          break;
        }
      }
    }

    if (potentialGetter != null && isLegalArguments(potentialGetter, null)) {
      try {
        return invoke(targetObject, potentialGetter, null);
      } catch (Exception e) {
        log.error(e.getMessage() + " method name '" + potentialGetter.getName()
            + "'");
      }
    }

    return null;
  }

  /**
   * Invoke the standard java bean's setter method.
   * 
   * @param targetObject the target object to be invoked
   * @param propertyName property name of the setter, it can be a Database's
   *          fieldname like first_name, the name like that will be trained as
   *          firstName and invoke setFirstName
   * @param value
   */
  public static void set(Object targetObject, String propertyName, 
      Object value) {
    if (targetObject == null) {
      return;
    }

    propertyName = propertyName.replaceAll("_|-", "");

    Class clazz = targetObject.getClass();
    Method[] methods = clazz.getDeclaredMethods();
    Method potentialSetter = null;

    for (int i = 0; i < methods.length; i++) {
      String methodName = methods[i].getName();
      if (methods[i].getParameterTypes().length == 1
          && methodName.startsWith("set")) {
        if (methodName.substring(METHOD_SUBFIX_LEN).equalsIgnoreCase(
            propertyName)) {
          potentialSetter = methods[i];
          break;
        }
      }
    }
    Object[] args = new Object[] { value };

    if (potentialSetter != null && isLegalArguments(potentialSetter, args)) {
      try {
        invoke(targetObject, potentialSetter, args);
      } catch (Exception e) {
        log.error(e.getMessage() + " method name '" + potentialSetter.getName()
            + "'");
      }
    }
  }

  /**����Ĳ����Ƿ�Ϸ�
   */
  public static boolean isLegalArguments(Method method, Object[] args) {
    Class[] types = method.getParameterTypes();

    if (types.length == 0 && (args == null || args.length == 0)) {
      return true;
    }

    if (types.length != args.length) {
      return false;
    }

    for (int i = 0; i < types.length; i++) {
      if (types[i].isPrimitive() && args[i] == null) {
        return false;
      }
    }

    return true;
  }

  /**
   * 反射调用某个对象的方法，返回调用结果，并处理异常。
   */
  public static Object invoke(Object targetObject, Method method, Object[] args)
      throws Exception {
    try {
      return method.invoke(targetObject, args);
    } catch (NullPointerException npe) {
      throw new NullPointerException(
          "NullPointerException occurred while calling");
    } catch (InvocationTargetException ite) {
      log.error("Exception occurred inside." + ite.getMessage());
      throw new Exception(ite);
    } catch (IllegalAccessException iae) {
      log.error("IllegalAccessException occurred while calling"
          + iae.getMessage());
      throw new Exception(iae);
    } catch (IllegalArgumentException iae) {
      log.error("IllegalArgumentException in class: "
          + targetObject.getClass().getName() + ", method of name: "
          + method.getName());
      log.error("IllegalArgumentException occurred while calling."
          + iae.getMessage());
      throw new Exception(iae);
    }

  }

  /**
   * 调用多个get方法
   */
  public static Object nestedGet(Object bean, String property) {
    if (StringUtils.isBlank(property) || bean == null) {
      return bean;
    }

    String[] propertyNames = property.split("\\.");
    Object value = bean;
    // ���������ԣ����á����Ե����ԡ���
    for (int i = 0; i < propertyNames.length; i++) {
      value = get(value, propertyNames[i]);
    }

    return value;
  }

  /**
   * 获取当前类声明的private/protected变量
   */
  static public Object getPrivateProperty(Object object, String propertyName)
      throws IllegalAccessException, NoSuchFieldException {
    Assert.notNull(object);
    Assert.hasText(propertyName);
    Field field = object.getClass().getDeclaredField(propertyName);
    field.setAccessible(true);
    return field.get(object);
  }

  /**
   * 设置当前类声明的private/protected变量
   */
  static public void setPrivateProperty(Object object, String propertyName,
      Object newValue) throws IllegalAccessException, NoSuchFieldException {
    Assert.notNull(object);
    Assert.hasText(propertyName);

    Field field = object.getClass().getDeclaredField(propertyName);
    field.setAccessible(true);
    field.set(object, newValue);
  }

  /**
   * 调用当前类声明的private/protected函数
   */
  static public Object invokePrivateMethod(Object object, String methodName,
      Object[] params) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException {
    Assert.notNull(object);
    Assert.hasText(methodName);
    Class[] types = new Class[params.length];
    for (int i = 0; i < params.length; i++) {
      types[i] = params[i].getClass();
    }
    Method method = object.getClass().getDeclaredMethod(methodName, types);
    method.setAccessible(true);

    return method.invoke(object, params);
  }

  /**
   * 调用当前类声明的private/protected函数
   */
  static public Object invokePrivateMethod(Object object, String methodName,
      Object param) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException {
    return invokePrivateMethod(object, methodName, new Object[] { param });
  }

  /**
   * 将一个Bean转换为Map，Map的"key"是属性名称，value是属性值.只包含当前对象的字段，
   * 父类的字段不包括。
   * @param bean 指定的bean
   * @param propertyNames 被转换的属性的名称列表,如果为null，返回所有字段； 
   * 如果为 空数组，则返回空Map.
   * @param containsNull 如果为true，那么如果某个属性为null，则不被转换。
   * 
   */
  static public Map toMap(Object bean, String[] propertyNames,
      boolean containsNull) {
    Map map = Collections.synchronizedMap(new HashMap());

    if (bean == null) {
      return map;
    }
    // 如果没有指定属性名称，返回所有属性组成的map
    if (propertyNames == null) {
      Method []methods = bean.getClass().getDeclaredMethods();
      List getters = new ArrayList();
      for (Method method : methods) {
        if (method.getParameterTypes().length == 0
            && method.getName().startsWith("get")) {
          getters.add(method.getName().substring("get".length()));
        }
      }
      propertyNames = (String[]) getters.toArray(new String[]{});
    }
        
    for (String fieldName : propertyNames) {
      try {
        // 获取属性值
        Object object = get(bean, fieldName);

        if (containsNull || object != null) {
          map.put(fieldName, object);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return map;
  }

  /**
   * getter 方法前缀
   */
  public static final String GETTER_PREFIX = "get";

  /**
   * setter方法前缀
   */
  public static final String SETTER_PREFIX = "set";

  /**
   * 调用<code>org.apache.commons.beanutils.BeanUitls.copyProperties</code>
   * 方法，复制bean的属性。同时，处理异常。
   * @param dest 目标bean
   * @param src 源bean
   */
  public static void copyProperties(Object dest, Object src) {
    assert (dest != null);
    assert (src != null);

    try {
      Method[] methods = src.getClass().getMethods();
      for (int i = 0; i < methods.length; i++) {
        String name = methods[i].getName();
        if (name.startsWith(GETTER_PREFIX)) {
          String subName = name.substring(GETTER_PREFIX.length());
          Class paramType = methods[i].getReturnType();
          Object obj = invoke(src, methods[i], null);
          Method setter = null;
          try {
            setter = dest.getClass().getMethod(SETTER_PREFIX + subName,
                new Class[] { paramType });
          } catch (NoSuchMethodException nsme) {
            // do nothing
          }
          if (setter != null) {
            invoke(dest, setter, new Object[] { obj });
          }
          log.debug("copy property'" + subName + "'");
        }
      }

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 按照指定的字段复制两个Bean的属性
   * @param dest 目标bean
   * @param src 源bean
   * @param propNames 给定字段名称，如果为null， 则相当于调用
   * {@link #copyProperties(Object, Object)}
   */
  public static void copyProperties(Object dest, 
      Object src, String[] propNames) {
    if (propNames == null) {
      copyProperties(dest, src);
      return;
    }

    for (int i = 0; i < propNames.length; i++) {
      Object val = get(src, propNames[i]);
      set(dest, propNames[i], val);
    }
  }
  /**
   * 复制一个List中的所有element对象到另一个List，相当于重复调用
   * {@link ReflectUtil#copyProperties(Object, Object, String[])}
   * @param dest 目标list
   * @param src 源list
   * @param fields 指定字段
   */
  public static void copyList(List dest, List src, String...fields) {
    if (src == null) {
      return;
    }
    
    for (Iterator itr = src.iterator(); itr.hasNext();) {
      Object obj = itr.next();
      
      if (obj != null) {
        if (dest == null) {
          dest = new ArrayList(dest.size());
        }
        try {
          Object destObj = obj.getClass().newInstance();
          ReflectUtil.copyProperties(destObj, obj, fields);
          dest.add(destObj);
        } catch (InstantiationException e) {
          log.error(e.getMessage());
        } catch (IllegalAccessException e) {
          log.error(e.getMessage());
        }
      }
    }
  }
  /**
   * 根据指定的字段，创建一个新的list，与原有list的内容相同
   * @see {@link ReflectUtil#copyList(List, List, String[])
   */
  public static List copyList(List src, String...fields) {
    if (src == null) {
      return ListUtils.EMPTY_LIST;
    }
    
    List dest = new ArrayList(src.size());
    ReflectUtil.copyList(dest, src, fields);
    return dest;
  }
}
