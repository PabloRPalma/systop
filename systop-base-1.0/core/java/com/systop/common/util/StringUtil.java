package com.systop.common.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * String Utility Class This is used to encode passwords programmatically
 * 
 * <p>
 * <a h ref="StringUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class StringUtil {
  // ~ Static fields/initializers =============================================
  /**
   * prevent from initializing.
   */
  private StringUtil() {
    
  }
  
  /**
   * EMPTY_STRING
   */
  public final static String EMPTY_STRING = "";

 
  /**
   * Encode a string using Base64 encoding. Used when storing passwords as
   * cookies.
   * 
   * This is weak encoding in that anyone can use the decodeString routine to
   * reverse the encoding.
   * 
   * @param str
   * @return String
   */
  public static String encodeString(String str) {
    sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    return encoder.encodeBuffer(str.getBytes()).trim();
  }

  /**
   * Decode a string using Base64 encoding.
   * 
   * @param str
   * @return String
   */
  public static String decodeString(String str) {
    sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
    try {
      return new String(dec.decodeBuffer(str));
    } catch (IOException io) {
      throw new RuntimeException(io.getMessage(), io.getCause());
    }
  }

  /**
   * 取得完整类名的最后一截，也就是类本身名称。例如，输入java.lang.Object,则返回Object
   */
  public static String unqualify(String qualifiedName) {
    return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
  }
  
  /**
   * 例如，输入java.lang.Object,则返回java.lang
   */
  public static String qualifier(String qualifiedName) {
    int loc = qualifiedName.lastIndexOf(".");
    return (loc < 0) ? "" : qualifiedName.substring(0, loc);
  }


  /**
   * 组成完整类名
   */
  public static String qualify(String prefix, String name) {
    if (name == null || prefix == null) {
      throw new NullPointerException();
    }
    return new StringBuffer(prefix.length() + name.length() + 1).append(prefix)
        .append('.').append(name).toString();
  }
  
  /**
   * 组成完整类名
   */
  public static String[] qualify(String prefix, String[] names) {
    if (prefix == null) {
      return names;
    }
    int len = names.length;
    String[] qualified = new String[len];
    for (int i = 0; i < len; i++) {
      qualified[i] = qualify(prefix, names[i]);
    }
    return qualified;
  }
  
  /**
   * 去除字符串中的&lt;和&gt;标记.
   * @param src 给定源字符串
   * @return 去除tags后的字符串。
   */
  public static String stripTags(String src) {
    if (StringUtils.isBlank(src)) {
      return src;
    }
    return src.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
  
 
}
