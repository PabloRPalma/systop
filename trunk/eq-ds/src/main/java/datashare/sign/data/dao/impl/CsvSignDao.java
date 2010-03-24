package datashare.sign.data.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.systop.core.util.DateUtil;

import datashare.sign.data.dao.AbstractSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.support.CalcHelper;

/**
 * {@link SignDao}的实现类，用于将生成的数据
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class CsvSignDao extends AbstractSignDao<StringBuffer> {

  @Override
  public StringBuffer query(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);

    StringBuffer buf = new StringBuffer(100000);
    if (CollectionUtils.isNotEmpty(rows)) {
      for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
        Map row = itr.next();
        buf.append(extractCsv(criteria, row));
      }
    } else {
      buf = getCleanData(criteria);
    }

    return buf;
  }
  
  /**
   * 当查询数据为空时，为了曲线显示不出现 Error而返回的空数据
   * @param criteria
   * @return
   */
  private StringBuffer getCleanData(Criteria criteria){
    StringBuffer buf = new StringBuffer();
    buf.append(DateUtil.getDate(criteria.getStartDate()));
    buf.append(" 00:00:00;0");
    buf.append('\n');
    buf.append(DateUtil.getDate(criteria.getEndDate()));
    buf.append(" 00:00:00;0");
    return buf;
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
  private StringBuffer extractCsv(Criteria criteria, Map row) {

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
    for (int i = 0; i < value.length(); i++) {
      char c = value.charAt(i);
      if (c != ' ' && i < value.length()) {
        block.append(c);
      }
      if (c == ' ' || i == (value.length() - 1)) {
        CalcHelper helper = getCalcHelper();
        Date sampleTime = helper.getBuildDate(criteria, startDate, blockIndex);
        buf.append(helper.getAppropriateDateString(criteria.getSampleRate(), sampleTime));
        buf.append(";");
        if (!CalcHelper.isNull(block.toString())) {
          buf.append(block);
        }
        buf.append('\n');
        block = new StringBuffer(15); // 清空数据段缓存
        blockIndex++; // 数据段索引++
      }
    }
    return buf;
  }

}
