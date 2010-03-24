package datashare.sign.data.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.systop.core.util.DateUtil;

import datashare.sign.data.dao.AbstractSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.model.DataSeries;
import datashare.sign.data.support.CalcHelper;

@Repository
public class MailSignDao  extends AbstractSignDao<List<DataSeries>> {

  @SuppressWarnings("unchecked")
  @Override
  public List<DataSeries> query(Criteria criteria) {
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    
    CalcHelper helper = getCalcHelper();
    rows = helper.fix(rows, criteria); //处理数据日期不连贯
    StringBuffer buf = helper.merge(criteria, rows);
    List<DataSeries> data = new ArrayList<DataSeries>(500); // 最后结果
    logger.debug("ForMail:{}~{}", DateUtil.convertDateToString(criteria.getStartDate()),
        DateUtil.convertDateToString(criteria.getEndDate()));
    Date startDate = helper.getStartDate(criteria, rows); // 整个查询结果的起始时间
    int rowIndex = 0; // 数据index
    StringBuilder rowBuf = new StringBuilder(15); // 当前数据段数据
    //FIXME:冗余代码？
    for (int i = 0; i < buf.length(); i++) {
      if (buf.charAt(i) != ' ') {
        rowBuf.append(buf.charAt(i));
      }
      // 如果遇到空格或者最后一个字符，则追加数据段，并清空rowBuf
      if (buf.charAt(i) == ' ' || i == (buf.length() - 1)) {
        Date sampleDate = helper.getBuildDate(criteria, startDate, rowIndex);
        // 增加字符型日期
        String strDate = helper.getAppropriateDateString(criteria.getSampleRate(), sampleDate);
        DataSeries dataSeries = new DataSeries(sampleDate, strDate, rowBuf.toString());
        data.add(dataSeries);
        rowBuf = new StringBuilder(15);
        rowIndex++;
      }
    }
    return data;
  }

}
