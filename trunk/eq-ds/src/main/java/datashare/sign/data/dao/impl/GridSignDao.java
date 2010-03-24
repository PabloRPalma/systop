package datashare.sign.data.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;

import datashare.sign.data.dao.AbstractSignDao;
import datashare.sign.data.model.Criteria;
import datashare.sign.data.model.DataSeries;
import datashare.sign.data.model.GridResult;
import datashare.sign.data.support.CalcHelper;
import datashare.sign.support.SignCommonDao;

/**
 * <code>SignDao</code>的实现类，进行数据查询，并根据分页情况，将查询结果 限定为所需页中的数据。
 * 
 * @author Sam
 * 
 */
@SuppressWarnings("unchecked")
@Repository
public class GridSignDao extends AbstractSignDao<GridResult> {

  /**
   * 主要用于查询台站名称，测项分量名称
   */
  private SignCommonDao signCommonDao = null;

  /**
   * 此方法不会返回null.如果查询结果数据为空，创建一个GridResult对象，并把查询的台站名称和测项分量名称赋值
   * 
   * @see {@link eq.core.qz.dao.SignDao#query(Criteria)}
   */
  @Override
  public GridResult query(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    // 根据逻辑数据分页条件，计算物理数据抓取范围
    CalcHelper helper = getCalcHelper();
    int skip = helper.getSkip(criteria);
    int max = helper.getMax(criteria);
    logger.debug("前兆数据查询Max = {}", max);
    // 执行查询
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria, skip, max);
    rows = helper.fix(rows, criteria); //处理数据日期不连贯
    // 在分页条件的约束下，从物理数据中提取逻辑数据
    GridResult gridResult = extract(criteria, rows);
    // 构建新的数据结果
    if (gridResult != null) {
      if (!CollectionUtils.isEmpty(gridResult.getDataSeries())) {
        Page page = criteria.getPage();
        Integer count = (Integer) getTemplate().queryForObject(SQL_COUNT_ID, criteria);
        page.setRows(helper.getTotalRows(count, criteria));
        gridResult.setPage(page);
      }
    } else {
      gridResult = new GridResult(Collections.EMPTY_LIST);
    }
    // 查询并设置台站名称
    gridResult.setStationName(signCommonDao.getStationName(criteria.getStationId()));
    // 查询并设置测项分量名称
    gridResult.setItemName(signCommonDao.getItemName(criteria.getItemId()));
    return gridResult;
  }

  /**
   * 从数据块物理数据中，找到分页查询所需的数据块.
   * 
   * @param criteria 查询条件，里面包含的计算时所需的条件。
   * @param rows 逻辑数据对应的物理数据行
   * 
   */
  private GridResult extract(final Criteria criteria, List<Map> rows) {
    if (CollectionUtils.isEmpty(rows)) {
      return null;
    }
    GridResult gridResult = new GridResult();
    // 设置采样率，EC表格可以根据这个使用合适的时间格式
    gridResult.setSampleRate(criteria.getSampleRate());
    // 把所有数据拼成一个大的字符串,这样方便处理呀，呵呵
    CalcHelper helper = getCalcHelper();
    StringBuffer buf = helper.merge(criteria, rows);

    // 得到原起始位置在当前页面中的起始位置（逻辑数据的起始位置总是位于当前物理数据的第一条数据中）
    int rowsOfDay = srManager.get(criteria.getSampleRate()).getDataAmount();
    int start = criteria.getPage().getStartIndex() % rowsOfDay;
    logger.debug("起始位置{}", start);
    int strIndex = 0; // 数据索引，在数据库中以空格键分割
    // 从物理数据中找到所需数据的起始位置
    for (int i = 0; i < start; i++) {
      strIndex = buf.indexOf(" ", strIndex);
      strIndex++; // 从空格的下一个索引开始搜索
    }
    // 分割数据
    int max = criteria.getPage().getPageSize();
    int rowIndex = 0; // 数据index
    StringBuilder rowBuf = new StringBuilder(15); // 当前数据段数据
    List<DataSeries> data = new ArrayList<DataSeries>(max); // 最后结果
    Date startDate = helper.getStartDate(criteria, rows); // 整个查询结果的起始时间
    logger.debug("起始时间 {}", DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", startDate));
    // 从整个查询数据中得到所需的数据块
    for (int i = strIndex; i < buf.length(); i++) {
      if (rowIndex >= max) {
        break;
      }
      if (buf.charAt(i) != ' ') {
        rowBuf.append(buf.charAt(i));
      }
      // 如果遇到空格或者最后一个字符，则追加数据段，并清空rowBuf
      if (buf.charAt(i) == ' ' || i == (buf.length() - 1)) {
        Date sampleDate = helper.getBuildDate(criteria, startDate, start + rowIndex);
        // 增加字符型日期
        String strDate = helper.getAppropriateDateString(gridResult.getSampleRate(), sampleDate);
        DataSeries dataSeries = new DataSeries(sampleDate, strDate, rowBuf.toString());
        data.add(dataSeries);
        rowBuf = new StringBuilder(15);
        rowIndex++;
      }
    }
    gridResult.setDataSeries(data);
    return gridResult;
  }
  
  /**
   * @return the signCommonDao
   */
  public SignCommonDao getSignCommonDao() {
    return signCommonDao;
  }

  /**
   * @param signCommonDao the signCommonDao to set
   */
  public void setSignCommonDao(SignCommonDao signCommonDao) {
    this.signCommonDao = signCommonDao;
  }

}
