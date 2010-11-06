package quake.seismic.data.catalog.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ecside.util.ExtremeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.SeismicConstants;
import quake.seismic.data.catalog.dao.AbstractCatDao;
import quake.seismic.data.catalog.model.Criteria;

import com.systop.core.util.DateUtil;

/**
 * 导出地震目录数据DAO
 * @author DU
 */
@SuppressWarnings("unchecked")
@Repository
public class ExportCatDao extends AbstractCatDao<StringBuffer> {
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  /**
   * 查询需要导出的数据
   */
  @Override
  public StringBuffer query(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    //查询地震目录    
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    StringBuffer buf = new StringBuffer(100000);
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      if ("WKF".equals(criteria.getExpType())) {
        buf.append(extractWkf(row));
      } else {
        buf.append(extractEqt(row));
      }
    }
    return buf;
  }

  /**
   * 查询需要导出的数据(VLM)
   */
  public StringBuffer queryForVlm(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    //查询地震目录    
    List<Map> rows = getTemplate().queryForList(SQL_ID, criteria);
    StringBuffer buf = new StringBuffer(100000);
    //基本目录数据格式
    if (SeismicConstants.Catalog_basic.equals(criteria.getExpType())) {
      buf.append(extractBasicVlm(rows, criteria));
    }
    
    return buf;
  }
  
  /**
   * 导出基本目录格式数据(BASIC_VLM)
   * @param rows 地震目录
   * @param criteria 目录查询参数
   * @return
   */
  public String extractBasicVlm(List<Map> rows, Criteria criteria) {
    StringBuffer buf = new StringBuffer();
    logger.debug("导出基本目录格式数据时，地震目录条数：{}", rows.size());
    //Write Volume_index_block0; 
    buf.append("VI0").append(" ").append("Volume_type").append(" ").append(
        SeismicConstants.Catalog_basic).append(" ").append("CSF_Ver").append(" ").append("1.0").append("\n\r");
    //Write Volume_index_block1; 
    Map networkInfo = (Map) queryNetwordInfo(criteria);
    logger.debug("台网信息：{}",networkInfo);
    if (networkInfo != null) {
      String netCode = (String)networkInfo.get("Net_code");
      String netName = (String)networkInfo.get("Net_cname");
      logger.debug("台网代码：{}，台网名称：{}", netCode, netName);
      String startDate = null;
      String endDate = null;
      if (networkInfo.get("Net_startdate") != null) {
        startDate = DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)networkInfo.get("Net_startdate"));
      }
      if (networkInfo.get("Net_enddate") != null) {
        endDate = DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)networkInfo.get("Net_enddate"));
      }
      logger.debug("台网起始时间：{} ----- 结束时间：{}", startDate, endDate);
      
      buf.append("VI1").append(" ").append("Net_code").append(" ").append(
          netCode).append(" ").append("Net_cname").append(" ").append(
              netName).append(" ").append(startDate).append(" ").append(endDate).append("\n\r");
    }
    //Write Basic origin head block (HBO)
    buf.append("HBO").append(" ").append(getHBO());
    //Write Basic origin data block (DBO)
    
    
    return buf.toString();
  }
  
  /**
   * HBO数据格式内容
   * @return
   */
  private String getHBO() {
    String hboFormat = MessageFormat.format("{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15}\n\r",new Object[] {
        "Net", "date", "O_time", "Epi_lat", "Epi_lon", "Epi_depth", 
        "Mag_name", "Mag_value",  "Rms", "Qloc",  "Sum_stn", "Loc_stn", 
        "Eq_type", "Epic_id", "Source_id", "Location_cname"
    });
    logger.debug("HBO数据格式内容：{}", hboFormat);
    return hboFormat;
  }
  
  /**
   * 查询台网信息
   * @param criteria
   */
  public Object queryNetwordInfo(Criteria criteria) {
    //查询基本信息数据库中的台网表
    criteria.setSchema(dataSourceManager.getQzSchema());
    return getTemplate().queryForObject("cz.queryNetworkInfo", criteria);
  }
  
  /**
   * 导出WKF格式
   * @param row 一行数据
   * @return
   */
  public String extractWkf(Map row) {
    String wkfData = MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7},{8},{9}\n",
        new Object[] { DateUtil.getDateTime("yyyy", (Date) row.get("O_TIME")),// 4位年
            DateUtil.getDateTime("MMdd", (Date) row.get("O_TIME")),// 4位月日
            DateUtil.getDateTime("HH", (Date) row.get("O_TIME")),// 2位时
            DateUtil.getDateTime("mm", (Date) row.get("O_TIME")),// 2位分
            convertEpi((Double)row.get("EPI_LAT"), 4),// 4位伟度
            convertEpi((Double)row.get("EPI_LON"), 5),// 5位经度
            ExtremeUtils.formatNumber("0.0", row.get("MAG_VAL")),// 3位震级
            ExtremeUtils.formatNumber("000", row.get("EPI_DEPTH")),// 3位深度
            row.get("SEQUEN_NAME") == null ? "000" : StringUtils.leftPad((String) row
                .get("SEQUEN_NAME"), 3, '0'),// 3位序列号
            row.get("M_SOURCE") });
    return wkfData;
  }
  
  /**
   * wkf文件导出时，转换伟度与经度。系统存储的是度.度 要把度.度 转成 度分）。
   * 39.98 度.度 39 + "" + 0.98 * 60 = 3959度分
   * @param val
   * @param len
   * @return
   */
  private String convertEpi(Double val, int len) {
    String strVal = null;
    if (val == null) {
      strVal = "";
    }
    
    int valInt = (int) val.doubleValue();
    double valDou = val - valInt;
    strVal = valInt + ExtremeUtils.formatNumber("00", (valDou * 60));
    return StringUtils.leftPad(strVal, len, '0');
  }

  /**
   * 导出EQT格式
   * @param row 一行数据
   * @return
   */
  public String extractEqt(Map row) {
    String eqtData = MessageFormat.format(" {0}{1}{2}{3}{4}{5}{6}\n", new Object[] {// 年前有一空格
            // 2008-10-04 12:48:14 转成 20081004124814
            DateUtil.getDateTime("yyyyMMddHHmmss", (Date) row.get("O_TIME")),
            formatNumber((Double) row.get("EPI_LAT"), 6),// 6位伟度 含（正|负）值 正值 0或空格表示
            formatNumber((Double) row.get("EPI_LON"), 7),// 7位伟度 含（正|负）值 正值 0或空格表示
            ExtremeUtils.formatNumber("0.00", row.get("MAG_VAL")),// 4位震级
            ExtremeUtils.formatNumber("000", row.get("EPI_DEPTH")),// 3位深度
            row.get("SEQUEN_NAME") == null ? "000" : StringUtils.leftPad((String) row
                .get("SEQUEN_NAME"), 3, '0'),// 3位序列号
            row.get("M_SOURCE") });
    return eqtData;
  }

  /**
   * 将一个数字格式化为字符串，并且在前面按照一定的长度补，解决经伟度出现负数的情况. 
   * 用ExtremeUtils 的结果是 -0005.9 而要的结果为 000-5.9
   * @param value 数字
   * @param len 字符串总长度
   * @return 返回格式化好的字符串
   */
  private String formatNumber(Double val, int len) {
    String strVal = null;
    // 值为空时
    if (val == null) {
      strVal = ExtremeUtils.formatNumber("###.00", 0);
    } else {
      strVal = ExtremeUtils.formatNumber("###.00", val);
    }
    return StringUtils.leftPad(strVal, len, '0');
  }
}