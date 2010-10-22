package quake.base.webapp;

import java.text.DecimalFormat;

/**
 * 数字小数点保留N位的工具类
 * 
 */
public final class NumberFormatUtil {
  /**
   * 将数字（Double,Float）格式化为字符串，保留N位小数。
   * @param obj 被格式化的对象，如果不是Double或Float，则返回<code>null</code>
   * @param digits 保留digits位小数，最小值为0
   */
  public static String format(Object obj, int digits) {
    if(digits < 0) {
      digits = 0;
    }
    
    StringBuffer mask = new StringBuffer("0");
    if(digits > 0) {
      mask.append(".");
    }
    for(int i = 0; i < digits; i ++) {
      mask.append("#");
    }
    if (obj != null) {
      if (obj instanceof Double) {
        return new DecimalFormat(mask.toString()).format((Double) obj);
      } else if (obj instanceof Float) {
        return new DecimalFormat(mask.toString()).format((Float) obj);
      }
    }

    return null;
  }

  private NumberFormatUtil() {

  }
  
  public static void main(String[] args) {
    System.out.println(format(34.354, 1));
    System.out.println(format(34.354, 0));
    System.out.println(format(34.354, 2));
    System.out.println(format(34.35433, 3));
  }

}
