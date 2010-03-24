package datashare.sign.data.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systop.core.util.DateUtil;

import datashare.GlobalConstants;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.sign.data.model.Criteria;

@SuppressWarnings("unchecked")
public class CalcHelper {
  public static final String NULL_ALL = "NULLALL";
  
  private SampleRateManager srManager;
  
  public CalcHelper(SampleRateManager srManager) {
    this.srManager = srManager;  
  }
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(CalcHelper.class);


  
  /**
   * 根据采样率，将时间对象转换为格式合适的字符串：
   * <ul>
   *    <li>日均值格式为yyyy-MM-dd</li>
   *    <li>小时值格式为yyyy-MM-dd HH</li>
   *    <li>分钟值格式为yyyy-MM-dd HH:mm</li>
   *    <li>秒值格式为yyyy-MM-dd HH:mm:ss</li>
   * </ul>
   * @param sampleRate 采样率
   * @param date 时间
   * @param IllegalArgumentException如果采样率错误或者<code>date</code>为<code>null</code>
   */
  public String getAppropriateDateString(String sampleRate, Date date) {
    String fmt = srManager.get(sampleRate).getDateFormat();
    if(date != null && StringUtils.isNotBlank(fmt)) {
      return DateUtil.getDateTime(fmt, date);
    } else {
      throw new IllegalArgumentException("采样率" + sampleRate + "不支持,或者日期为null.");
    }
  }
  
  /**
   * 将所有数据的OBSVALUE合并为一个大的字符串，方便处理
   */
  public StringBuffer merge(Criteria criteria, List<Map> rows) {
    int rowsOfDay = srManager.get(criteria.getSampleRate()).getDataAmount();
    StringBuffer buf = new StringBuffer(rowsOfDay * 10 * rows.size());
    for(Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if(row == null) {
        continue;
      } 
      String value = (String) row.get("OBSVALUE");
      //为了方便处理，将"NULLALL"数据转换为许多null+空格
      if(NULL_ALL.equalsIgnoreCase(value) ||
          value == null) {
        buf.append(buildAllNull(rowsOfDay));
      } else {
        //如果需要降序排列,将这一天的数据reverse，因为数据库中已经考虑到排序了
        //所以不能将整个数据翻转，只能reverse当天的.
        buf.append(reverse(criteria, value));
      }
      if(itr.hasNext()) {
        buf.append(" "); //在每一个大的数据块后面加一个空格
      }
    }
    
    return buf;
  }  
  
  /**
   * 得到合适的时间格式
   */
  public String getDateFormat(Criteria criteria) {
    return srManager.get(criteria.getSampleRate()).getDateFormat();
  }
  /**
   * 得到easycharts所需的timeScale参数
   */
  public String getTimeScale(Criteria criteria) {
    int dataAmount = srManager.get(criteria.getSampleRate()).getDataAmount();
    return new Integer((int) (86400 / dataAmount)).toString();
  }
  
  /**
   * 修补数据，用于处理时间间隔不连续的问题
   */
  public List<Map> fix(List<Map> rows, Criteria criteria) {
    if(CollectionUtils.isEmpty(rows) || rows.size() == 1) {
      return rows;
    }
    List<Map> target = new ArrayList<Map>(rows.size());
    String start = DateUtil.convertDateToString((Date) rows.get(0).get("STARTDATE"));
    for(Map row : rows) {
      if(!StringUtils.equals(start, DateUtil.convertDateToString((Date) row.get("STARTDATE")))){
        Map r = new HashMap();
        r.put("OBSVALUE", NULL_ALL);
        try {
          r.put("STARTDATE", DateUtil.convertStringToDate(start));
        } catch (ParseException e) {
        }
        target.add(r);
      } 
      int delta = isDesc(criteria) ? -1 : 1;
      start = DateUtil.convertDateToString(DateUtil.add((Date) row.get("STARTDATE"), Calendar.DATE, delta));
      target.add(row);
    }
    return target;
  }
  
  /**
   * 根据采样率和索引值构造采样时间
   * @param criteria
   * @param index 从0开始，数据段索引
   * @return
   */
  public Date getBuildDate(Criteria criteria, Date start, int index) {
    long time = start.getTime();
    /*int x;
    
    if(GlobalConstants.SAMPLE_RATE_HOUR.equals(criteria.getSampleRate())) {
      x = 3600 * 1000;
    } else if(GlobalConstants.SAMPLE_RATE_MIN.equals(criteria.getSampleRate())) {
      x = 60 * 1000;
    }else if(GlobalConstants.SAMPLE_RATE_SEC.equals(criteria.getSampleRate())) {
      x = 1000;
    } else {
      x = 86400 * 1000;
    }*/
    int x = srManager.get(criteria.getSampleRate()).getMselInc();    
    long coe = isDesc(criteria) ? -1L : 1L;
    //logger.info("采样时间{}", DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss",new Date(time + (coe * (long) index) * ((long) x))));
    return new Date(time + (coe * (long) index) * ((long) x)); 
  }
  /**
   * 处理ALLNULL数据
   */
  private static StringBuilder buildAllNull(int rowsOfDay) {
    StringBuilder buf = new StringBuilder(rowsOfDay * 10);
    for(int i = 0; i < rowsOfDay; i++) {
      buf.append("NULL");
      if(i < (rowsOfDay - 1)) {
        buf.append(" ");
      }
    }
    return buf;
  }
  
  /**
   * 判断一个数据块是否为“NULL”
   */
  
  public static boolean isNull(String block) {
    if(StringUtils.isBlank(block)) {
      return false;
    }
    
    return "NULL".equalsIgnoreCase(block);
  }
  

  /**
   * 从数据库物理数据中找到起始时间
   */

  public Date getStartDate(Criteria criteria, List<Map> rows) {
    if(rows == null || CollectionUtils.isEmpty(rows)) {
      return null;
    }
    //第一条数据的时间，就是起始时间，因为数据库已经排序，所以这里不被考虑排序问题
    Map row = rows.get(0);
    if((Date) row.get("STARTDATE") == null) {
      return null;
    }
    
    return (isDesc(criteria)) ? 
        DateUtil.lastSecondOfDate((Date) row.get("STARTDATE")): //降序取当天最后一秒
        DateUtil.firstSecondOfDate((Date) row.get("STARTDATE"));//升序取当天第一秒
  }
  
  /**
   * 判断是否是降序排列
   */
  public boolean isDesc(Criteria criteria) {
    if(criteria.getPage() == null) {
      return false;
    }
    
    return GlobalConstants.ORDER_DESC.equalsIgnoreCase(criteria.getOrder());
  }
  
  /**
   * 从数据库中取出的数据是升序排列的，这个方法可以使他们降序排列.
   * 在查询数据库的时候，已经是排序的数据了，所以<code>reverse</code>方法只能用于将
   * <strong>一天</strong>的数据进行翻转。
   */
  public String reverse(Criteria criteria, String value) {
    logger.debug("排序顺序{}", criteria.getOrder());
    if(!isDesc(criteria)) {
      return value;
    }
    StringBuffer total = new StringBuffer(value.length());
    StringBuffer sub = new StringBuffer(10);
    for(int i = (value.length() - 1); i >= 0; i--) {
      if(value.charAt(i) != ' ') {
        sub.append(value.charAt(i));
      }
      
      if(value.charAt(i) == ' ' || i == 0) {
        total.append(sub.reverse());
        if(i != 0) {
         total.append(' '); 
        }
        sub = new StringBuffer(10);
      }
    }
    
    return total.toString();
  }
  
  /**
   * 显示的时候和数据库中实际存储的行数是不同的，{{@link #getMax(Criteria)} 方法可以根据<code>QzCriteria</code>,计算实际需要从数据库提取多少数据。
   * 
   */
  public int getMax(Criteria criteria) {
    //如果每天就最多产生一条数据，则返回原始的pageSize
    if(srManager.get(criteria.getSampleRate()).getDataAmount().equals(1)) {
      return criteria.getPage().getPageSize();
    }
    
    int pageSize = criteria.getPage().getPageSize(); //逻辑数据分页容量
    //每天多少条逻辑数据
    int rowsOfDay = srManager.get(criteria.getSampleRate()).getDataAmount();
    //计算一页逻辑数据存储在多少条物理数据中，这里特别调整总是多出一条数据，这不会影响后续的分页
    return (int) Math.floor(pageSize / rowsOfDay) + 1;
  }

  /**
   * 显示的时候和数据库中实际存储的行数是不同的，{{@link #getSkip(Criteria)} 方法可以根据<code>QzCriteria</code>,计算实际需要从数据库忽略多少数据。
   */
  public int getSkip(Criteria criteria) {
    int skip = criteria.getPage().getStartIndex(); //逻辑数据起始位置
    //每天多少条数据
    int rowsOfDay = srManager.get(criteria.getSampleRate()).getDataAmount();
    //计算逻辑数据起始位置跨过了多少天（因为每天一条数据，所以这也就是跳过多少条数据）
    return (int) Math.floor(skip / rowsOfDay);
  }

  /**
   * 根据起止时间和采样率，计算共有多少数据(包括起始日期和中止日期)。起止时间需要根据QzCriteria 中实际起止时间进行调整：<br>
   * 起始时间调整为原起始时间当天的第一秒；<br>
   * 中止实际调整为原中止时间第二天的第一秒<br>
   * @param count 总记录数
   */
  public int getTotalRows(Integer count, Criteria criteria) {
    int rowsOfDay = srManager.get(criteria.getSampleRate()).getDataAmount();
    return count * rowsOfDay;
  }

}
