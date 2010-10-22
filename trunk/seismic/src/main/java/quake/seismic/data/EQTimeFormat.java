package quake.seismic.data;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 以指定日期形式格式化测震数据的发震时刻
 * @author DU
 *
 */
public class EQTimeFormat {

  /**
   * 将包含发震时刻的数据以指定日期形式格式化
   * @param list 待格式化数据
   * @param eTime 发震时刻字段
   * @param timeFrac 发震时刻秒值字段
   * @return 格式化后的数据
   */
  @SuppressWarnings("unchecked")
  public static List<Map> getEqTimeValue(List<Map> list, String eTime, String timeFrac) {
    if(CollectionUtils.isEmpty(list)) {
      return Collections.EMPTY_LIST;
    }
    for(Map row : list) {
      if(row.containsKey(eTime) && row.containsKey(timeFrac)) {
        row.put("EQ_TIME", eqFormat((Date) row.get(eTime), String.valueOf(row.get(timeFrac))));
      }
    }
    
    return list;
  }
  
  /**
   * 取得指定日期形式的字符串
   * @param eTime 发震时间
   * @param timeFrac 秒值
   */
  private static String eqFormat(Date eTime, String timeFrac) {
    Integer n1 = null;
    StringBuffer rst = new StringBuffer(convertDateToString(eTime));
    if(timeFrac != null) {
      n1 = Integer.valueOf(timeFrac.substring(0,1));
      if(timeFrac.length() >= 2) {
        if(Integer.valueOf(timeFrac.substring(1,2)) >= 5){
          if(n1 < 9) {
            n1 += 1;
          }
        }
      }
    }
    return rst.append(".").append(n1).toString();
  }
  
  public static final String convertDateToString(Date aDate) {
    String s = getDateTime("yyyy-MM-dd HH:mm:ss", aDate);
    if (StringUtils.isBlank(s)) {
      return null;
    }

    return s;
  }
  
  public static final String getDateTime(String aMask, Date aDate) {
    SimpleDateFormat df = null;
    String returnValue = null;

    if (aDate != null) {
      df = new SimpleDateFormat(aMask);
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }
}
