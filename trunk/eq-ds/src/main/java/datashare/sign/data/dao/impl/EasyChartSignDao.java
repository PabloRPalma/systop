package datashare.sign.data.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import datashare.sign.data.dao.AbstractSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.model.EasyChartModel;
import datashare.sign.data.support.CalcHelper;

/**
 * {@link SignDao}的实现类，用于将生成的数据
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class EasyChartSignDao extends AbstractSignDao<EasyChartModel> {

  @Override
  public EasyChartModel query(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    
    StringBuffer buf = new StringBuffer(100000);
    
    String minRange = "";
    String maxRange = "";
    String minDate = "";
    String maxDate = "";
    if (CollectionUtils.isNotEmpty(rows)) {
      int index = 0;
      for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
        EasyChartModel ecModel = this.extractCsv(criteria, itr.next());
        try {
          //找到整个数据中最大数据
          if(StringUtils.isBlank(maxRange)) {
            maxRange = ecModel.getMaxRange();
          } else {
            if(Double.valueOf(ecModel.getMaxRange()) > Double.valueOf(maxRange)) {
              maxRange = ecModel.getMaxRange();   
            }
          }
          //找到整个数据中最小数据
          if(StringUtils.isBlank(minRange)) {
            minRange = ecModel.getMinRange();
          } else {
            if(Double.valueOf(ecModel.getMinRange()) < Double.valueOf(minRange)) {
              minRange = ecModel.getMinRange();   
            }
          }
        } catch (NumberFormatException e) {
          logger.warn("前兆数据中可能无法找到边界值，{}", e.getMessage());
        }
        //找到最小日期
        if(index == 0) {
          minDate = ecModel.getMinDate();
        }
        //找到最大日期
        if(!itr.hasNext()) {
          maxDate = ecModel.getMaxDate();
        }
         
        buf.append(ecModel.getData());
        index++;
      }
    }
    
    return new EasyChartModel(buf.toString(), maxDate, minDate, maxRange, minRange, 
        getCalcHelper().getDateFormat(criteria),
        getCalcHelper().getTimeScale(criteria));
  }
  
 
  /**
   * 将单行数据提取为CSV格式的字符串，格式例子：
   * 
   * <pre>
   * 2008-01-01,345.4
   * 2008-01-02,-245.4
   * </pre>
   * 
   * 如果遇到"NULL",则是：
   * 
   * <pre>
   * 2008-01-01,345.4
   * 2008-01-01,
   * 2008-01-02,-245.4
   * </pre>
   * 
   * @param row 单行QZ数据
   * @return String as csv format
   */
  private EasyChartModel extractCsv(Criteria criteria, Map row) {

    String value = (String) row.get("OBSVALUE");
    if (StringUtils.isBlank(value)) {
      logger.warn("发现一个OBSVALUE是null.");
      return null;
    }
    Date startDate = (Date) row.get("STARTDATE");
    if (startDate == null) {
      logger.warn("采样时间为null.");
      return null;
    }
    StringBuffer buf = new StringBuffer(value.length() * 2);
    StringBuffer block = new StringBuffer(15);// 临时保存数据段
    int blockIndex = 0; // 数据段索引
    String minRange = "";
    String maxRange = "";
    String minDate = "";
    String maxDate = "";
    CalcHelper helper = getCalcHelper();
    
    for (int i = 0; i < value.length(); i++) {
      char c = value.charAt(i);
      if (c != ' ' && i < value.length()) {
        block.append(c);
      }
      if (c == ' ' || i == (value.length() - 1)) {//如果遇到空格或者数据序列结束
       
        Date sampleTime = helper.getBuildDate(criteria, startDate, blockIndex);
        String dateStr = helper.getAppropriateDateString(criteria.getSampleRate(), sampleTime);
        if(blockIndex == 0) { //找到最小的时间
          minDate = dateStr;
        }
        if(i == (value.length() - 1)) { //找到最大的时间
          maxDate = dateStr;
        }
        //加入时间
        buf.append(dateStr);
        buf.append("|");
        //找到最大最小数据，并追加一个value
        if (!CalcHelper.isNull(block.toString())) {
          try{
            double val = Double.valueOf(block.toString());
            //找到最大的数据：
            if(StringUtils.isBlank(maxRange)) {
              maxRange = block.toString();
            } else {
              if(val > Double.valueOf(maxRange)) {
                maxRange = block.toString();   
              }
            }
            //找到最小的数据
            if(StringUtils.isBlank(minRange)) {
              minRange = block.toString();
            } else {
              if(val < Double.valueOf(minRange)) {
                minRange = block.toString();   
              }
            }
          }catch(NumberFormatException e) {
            logger.warn("前兆数据中存在错误:{}", block);
          }
          //加入一个数据
          buf.append(block);  
        }
        buf.append(",");       
        block = new StringBuffer(15); // 清空数据段缓存
        blockIndex++; // 数据段索引++
      }
    }
    return new EasyChartModel(buf.toString(), maxDate, minDate, maxRange, minRange, "", "");
  }
  
  public static void main(String []args) {
    System.out.println(Double.valueOf("-13345.3"));
    System.out.println(Double.parseDouble("-13345.3"));
  }
}
