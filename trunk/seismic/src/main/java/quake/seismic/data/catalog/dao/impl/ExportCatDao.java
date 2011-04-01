package quake.seismic.data.catalog.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.ecside.util.ExtremeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.SeismicConstants;
import quake.seismic.data.catalog.dao.AbstractCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.catalog.model.MagCriteria;
import quake.seismic.data.phase.model.PhaseCriteria;

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
   * 按圆形或矩形区域查询地震目录数据
   * 并将地震目录查询出的数据按照指定的格式生成字符串
   * @param criteria 目录查询参数
   */
  @Override
  public StringBuffer query(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    String SQL = SQL_ID;
    if(SeismicConstants.ROUND_QUERY_YES.equals(criteria.getIsRoundQuery())){
      logger.debug("导出EQT WKF数据，按圆形区域....");
      SQL = SQL_ROUND_ID;
    }
    //查询地震目录    
    List<Map> rows = getTemplate().queryForList(SQL, criteria);
    StringBuffer buf = new StringBuffer();
    //添加WKF、EQT及Q01格式的文件标头
    buf.append(expTypeTitle(criteria.getExpType()));
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      //WKF格式数据
      if ("WKF".equals(criteria.getExpType())) {
        buf.append(extractWkf(row));
      } else if ("EQT".equals(criteria.getExpType())) {//EQT格式数据
        buf.append(extractEqt(row));
      } else if ("Q01".equals(criteria.getExpType())) {
        buf.append(extractQ01(row, criteria));
      }
    }
    return buf;
  }

  /**
   * 取得WKF、EQT及Q01格式的文件标头
   * @param expType
   */
  private String expTypeTitle(String expType) {
    StringBuffer buf = new StringBuffer();
    buf.append("\n");
    buf.append(StringUtils.leftPad("全国统一编目地震目录查询结果(." + expType + "格式)", 32, ' ')).append("\n").append("\n");
    if ("WKF".equals(expType)) {//WKF格式
      buf.append("年年年年,月月日日,时时,分分,纬度,经度,震级,000,深度").append("\n").append("\n");
      buf.append("注意：震级包括了Ml、Ms、mb等多种震级类型的测定结果，具体可以看.Q01格式。").append("\n").append("\n");
    } else if ("EQT".equals(expType)) {//EQT格式
      buf.append("时间 纬度 经度震级深度000").append("\n").append("\n");
      buf.append("注意：震级包括了Ml、Ms、mb等多种震级类型的测定结果，具体可以看.Q01格式。").append("\n").append("\n");
    } else if ("Q01".equals(expType)) {//Q01格式
      buf.append("时间 纬度 经度 震级 深度 位号精度单位 参考地点(S-P)").append("\n").append("\n");
    }
    
    return buf.toString();
  }
  
  /**
   * 查询需要导出的数据(VLM)
   * 将地震目录查询出的数据按照指定的格式生成字符串
   * 导出的数据格式包括：基本目录数据格式、完全目录数据格式和观测报告
   * 具体数据格式见测震数据规范相关文档
   * @param criteria 目录查询参数
   */
  public StringBuffer queryForVlm(Criteria criteria) {
    Assert.notNull(criteria, "Criteria is null.");
    //查询地震目录    
    String SQL = SQL_ID;
    if(SeismicConstants.ROUND_QUERY_YES.equals(criteria.getIsRoundQuery())){
      logger.debug("导出VLM数据，按圆形区域....");
      SQL = SQL_ROUND_ID;
    }
    List<Map> rows = Collections.EMPTY_LIST;
    if (SeismicConstants.Catalog_basic.equals(criteria.getExpType())) {
      rows = getTemplate().queryForList(SQL, criteria, 0, SeismicConstants.CATALOG_BASIC_MAX_SIZE);
    }
    if (SeismicConstants.Catalog_full.equals(criteria.getExpType())) {
      rows = getTemplate().queryForList(SQL, criteria, 0, SeismicConstants.CATALOG_FULL_MAX_SIZE);
    }
    if (SeismicConstants.Bulletin_full.equals(criteria.getExpType())) {
      rows = getTemplate().queryForList(SQL, criteria, 0, SeismicConstants.BULLETIN_FULL_MAX_SIZE);
    }
    StringBuffer buf = new StringBuffer();
    //基本目录数据格式
    if (SeismicConstants.Catalog_basic.equals(criteria.getExpType())) {
      logger.debug("基本目录数据格式下载时地震目录条数：{}", rows.size());
      buf.append(extractBasicVlm(rows, criteria));
    }
    //完全目录数据格式
    if (SeismicConstants.Catalog_full.equals(criteria.getExpType())) {
      logger.debug("完全目录数据格式下载时地震目录条数：{}", rows.size());
      buf.append(extractFullVlm(rows, criteria));
    }
    //观测报告数据格式
    if (SeismicConstants.Bulletin_full.equals(criteria.getExpType())) {
      logger.debug("观测报告数据格式下载时地震目录条数：{}", rows.size());
      buf.append(extractBulletinVlm(rows, criteria));
    }
    return buf;
  }
  
  /**
   * 查询单个震相数据(观测报告)
   * @param criteria 目录查询参数
   */
  public String queryForSingleBulletin(Criteria criteria) {
    logger.debug("查询条件：{}", criteria.getQcId() + " : " + criteria.getTableName() + " : ");
    StringBuffer buf = new StringBuffer();
    //查询地震目录
    List<Map> rows = getTemplate().queryForList("cz.queryCatlogById", criteria);
    logger.debug("地震目录：{}", rows);
    if(CollectionUtils.isNotEmpty(rows)) {
      buf.append(extractBulletinVlm(rows, criteria));
    }
    
    return buf.toString();
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
    buf.append(getVlmIndexBlock0(criteria.getExpType()));
    //Write Volume_index_block1; 
    buf.append(getVlmIndexBlock1(criteria));
    //Write Basic origin head block (HBO)
    buf.append("HBO").append(" ").append(getHBO());
    //Write Basic origin data block (DBO)
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      buf.append("DBO").append(" ").append(getDBO(row));
    }
    
    return buf.toString();
  }
  
  /**
   * 导出完全目录格式数据(FULL_VLM)
   * @param rows 地震目录
   * @param criteria 目录查询参数
   * @return
   */
  public String extractFullVlm(List<Map> rows, Criteria criteria) {
    StringBuffer buf = new StringBuffer();
    logger.debug("导出完全目录格式数据时，地震目录条数：{}", rows.size());
    //Write Volume_index_block0; 
    buf.append(getVlmIndexBlock0(criteria.getExpType()));
    //Write Volume_index_block1; 
    buf.append(getVlmIndexBlock1(criteria));
    //Write Basic origin head block (HBO)
    buf.append("HBO").append(" ").append(getHBO());
    //Write Extened origin head block (HEO)
    buf.append("HEO").append(" ").append(getHEO());
    //Write Magnitude head block (HMB)
    buf.append("HMB").append(" ").append(getHMB());
    
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      //Write Basic origin data block (DBO)
      buf.append("DBO").append(" ").append(getDBO(row));
      //Write Extened origin data block (DEO)
      buf.append("DEO").append(" ").append(getDEO(row));
      //取得地震目录相关震级
      List<Map> magList = getMagOfCatalog((String)row.get("ID"), criteria);
      //Write Magnitude data block (DMB) 
      for(Map mag : magList) {
        buf.append("DMB").append(" ").append(getDMB(mag));
      }
    }
    
    return buf.toString();
  }
  
  /**
   * 导出观测报告格式数据(BULLETIN_VLM)
   * @param rows 地震目录
   * @param criteria 目录查询参数
   * @return
   */
  public String extractBulletinVlm(List<Map> rows, Criteria criteria) {
    StringBuffer buf = new StringBuffer();
    logger.debug("导出观测报告格式数据时，地震目录条数：{}", rows.size());
    //Write Volume_index_block0; 
    buf.append(getVlmIndexBlock0(criteria.getExpType()));
    //Write Volume_index_block1; 
    buf.append(getVlmIndexBlock1(criteria));
    
    //Write Basic origin head block (HBO)
    buf.append("HBO").append(" ").append(getHBO());
    //Write Extened origin head block (HEO)
    buf.append("HEO").append(" ").append(getHEO());
    //Write Magnitude head block (HMB)
    buf.append("HMB").append(" ").append(getHMB());
    //Write phase head block (HPB)
    buf.append("HPB").append(" ").append(getHPB());
    Set nSCodeSet = new HashSet();
    for (Iterator<Map> itr = rows.iterator(); itr.hasNext();) {
      Map row = itr.next();
      //Write Basic origin data block (DBO)
      buf.append("DBO").append(" ").append(getDBO(row));
      //Write Extened origin data block (DEO)
      buf.append("DEO").append(" ").append(getDEO(row));
      //取得地震目录相关震级
      List<Map> magList = getMagOfCatalog((String)row.get("ID"), criteria);
      //Write Magnitude data block (DMB) 
      for(Map mag : magList) {
        buf.append("DMB").append(" ").append(getDMB(mag));
      }
      //取得地震目录相关震相
      List<Map> phaseList = getPhaseOfCatalog((String)row.get("ID"), criteria);
      logger.debug("地震目录ID：{},对应的震相个数：{}", (String)row.get("ID"), phaseList.size());
      //Write phase data block (DPB)
      
      for(Map phase : phaseList) {
        String netStaCode = "";
        netStaCode = (String)phase.get("NET_CODE") + ("/") + (String)phase.get("STA_CODE");
        logger.debug("震相：{}，对应的台站/台网代码：{}", phase.get("PHASE_NAME"), netStaCode);
        nSCodeSet.add(netStaCode);
        buf.append("DPB").append(" ").append(getDPB(phase));
      }
    }
    logger.debug("查询出来的台站数：{}", nSCodeSet.size());
    String hsbAndDsb = getHsbAndDsb(nSCodeSet);
    //在HBO数据块前面加入(HSB)(DSB)
    int offset = buf.indexOf("HBO");
    //Write station head block (HSB)(DSB)
    buf.insert(offset, hsbAndDsb);
    
    return buf.toString();
  }
  
  /**
   * 生成HSB和DSB数据
   * @param nSCodeSet 台网/台站代码
   */
  private String getHsbAndDsb(Set nSCodeSet) {
    StringBuffer hsbAndDsb = new StringBuffer();
    //HSB格式数据
    hsbAndDsb.append("HSB").append(" ").append(getHSB());
    quake.seismic.station.model.Criteria staCriteria = new quake.seismic.station.model.Criteria();
    staCriteria.setSchema(dataSourceManager.getStationSchema());
    for(Iterator it = nSCodeSet.iterator(); it.hasNext();) {
      String netStaCode = (String) it.next().toString();
      if(StringUtils.isNotEmpty(netStaCode)) {
        String[] sncode = netStaCode.split("/");
        logger.debug("台站代码：{}，台网代码：{}",sncode[0],sncode[1]);
        staCriteria.setNetCode(sncode[0]);
        staCriteria.setStaCode(sncode[1]);
        //根据台网和台站代码取得台站信息_单独台站信息_台站信息表中有仪器字段
        /*Map staInfo = (Map) getTemplate().queryForObject(SQL_NET_STA_CODE_ID, staCriteria);
        logger.debug("查询取得的台站信息：{}",staInfo);
        if(staInfo != null) {
          hsbAndDsb.append("DSB").append(" ").append(getDSB(staInfo));
        }*/
        //根据台网和台站代码取得台站和位置信息_多个台站信息_一个台站多个仪器
        List<Map> staLocList = getTemplate().queryForList(SQL_NET_STA_LOC_ID, staCriteria);
        hsbAndDsb.append(getDSB(staLocList));
      }
    }
    
    return hsbAndDsb.toString();
  }
  
  /**
   * HBO数据格式内容
   * @return
   */
  private String getHBO() {
    String hboFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n",new Object[] {
        "Net", "dateO_time", "Epi_lat", "Epi_lon", "Epi_depth", 
        "Mag_name", "Mag_value",  "Rms", "Qloc",  "Sum_stn", "Loc_stn", 
        "Eq_type", "Epic_id", "Source_id", "Location_cname"
    });
    //logger.debug("HBO数据格式内容：{}", hboFormat);
    return hboFormat;
  }
  
  /**
   * 取得地震目录的DBO数据
   * @param row 地震目录
   */
  private String getDBO(Map row) {
    String dboData = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n",new Object[] {
         row.get("NET_CODE"), DateUtil.getDateTime("yyyy-MM-dd HH:ss:ss.ss", (Date)row.get("O_TIME")), 
         ExportDataFormat.convertEpiOfEQT((Double)row.get("EPI_LAT"), 7, "LAT"), //6位纬度，要加上负号，保留3位小数
         ExportDataFormat.convertEpiOfEQT((Double)row.get("EPI_LON"), 8, "LON"),//7位经度，要加上负号，保留3位小数
         ExportDataFormat.convertDepth((Double)row.get("EPI_DEPTH"), 3),//3位深度
         ExportDataFormat.convertMagName((String)row.get("M_SOURCE"), 5), //5位震级名
         ExportDataFormat.convertMagVal((Double)row.get("M"), 4), //3位震级值，要加上负号，保留1位小数
         ExportDataFormat.convertRms((Double)row.get("Rms"), 6), //6位Rms值，保留3位小数
         ExportDataFormat.convertQloc((String)row.get("QLOC")),//1位QLOC
         ExportDataFormat.convertStn((Integer)row.get("Sum_stn"), 3),//3位编报震相的台站数量
         ExportDataFormat.convertStn((Integer)row.get("Loc_stn"), 3),//3位定位台站
         ExportDataFormat.convertTwoStr((String)row.get("Epic_id")),//2位号
         ExportDataFormat.convertTwoStr((String)row.get("Source_id")),//2数据来源
         ExportDataFormat.convertTwoStr(SeismicConstants.EQ_TYPE_MAP.get(row.get("Eq_type"))), //2地震类型
         ExportDataFormat.convertTwoStr((String)row.get("LOCATION_CNAME"))
    });
    //logger.debug("DBO数据格式内容：{}", dboData);
    return dboData;
  }
  
  /**
   * HEO数据格式内容
   * @return
   */
  private String getHEO() {
    String heoFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15} {16}\n",new Object[] {
        "Auto_flag", "Event_id", "Sequen_name", "Depfix_flag", "M", "M_source", 
        "SPmin", "Dmin",  "Gap_azi", "Erh",  "Erz", "Qnet", "Qcom", 
        "Sum_pha", "Loc_pha", "FE_num", "FE_sname"
    });
    //logger.debug("HEO数据格式内容：{}", heoFormat);
    return heoFormat;
  }
  
  /**
   * 取得地震目录的DEO数据
   * @param row 地震目录
   */
  private String getDEO(Map row) {
    String deoData = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15} {16}\n",new Object[] {
         row.get("Auto_flag"), row.get("EVENT_ID"), row.get("SEQUEN_NAME"),row.get("Depfix_flag"), 
           row.get("M"), row.get("M_SOURCE"), row.get("SPmin"), row.get("Dmin"), row.get("Gap_azi"),
             row.get("Erh"), row.get("Erz"), row.get("Qnet"),row.get("QCOM"), row.get("Sum_pha"),
                row.get("Loc_pha"), row.get("FE_num"), row.get("FE_sname")
    });
    //logger.debug("DEO数据格式内容：{}", deoData);
    return deoData;
  }
  
  /**
   * HMB数据格式内容
   * @return
   */
  private String getHMB() {
    String hmbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4}\n",new Object[] {
        "Mag_name", "Mag_val", "Mag_gap", "Mag_stn", "Mag_error"
    });
    //logger.debug("HMB数据格式内容：{}", hmbFormat);
    return hmbFormat;
  }
  
  /**
   * 取得地震目录的DMB数据
   * @param mag 地震目录的相关震级
   */
  private String getDMB(Map mag) {
    String dmbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4}\n",new Object[] {
        mag.get("MAG_NAME"), mag.get("MAG_VAL"), mag.get("MAG_GAP"), mag.get("MAG_STN"), mag.get("MAG_ERROR")
    });
    //logger.debug("DMB数据格式内容：{}", dmbFormat);
    return dmbFormat;
  }
  
  /**
   * HPB数据格式内容
   * @return
   */
  private String getHPB() {
    String hpbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15}\n",new Object[] {
        "Net_code", "Sta_code", "Chn_code", "Clarity", "Wsign", 
        "Phase_name", "Weight",  "Rec_type", "Phase_time",  "Resi", "Distance", 
        "Azi", "Amp", "Period", "Mag_name", "Mag_val"
    });
    //logger.debug("HPB数据格式内容：{}", hpbFormat);
    return hpbFormat;
  }
  
  /**
   * DPB数据格式内容
   * @return
   */
  private String getDPB(Map phase) {
    String hpbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15}\n",new Object[] {
        phase.get("NET_CODE"), phase.get("STA_CODE"), phase.get("CHN_CODE"), phase.get("CLARITY"), phase.get("WSIGN"), 
          phase.get("PHASE_NAME"), phase.get("WEIGHT"),  phase.get("REC_TYPE"), 
            DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)phase.get("PHASE_TIME")),  
              phase.get("RESI"), phase.get("DISTANCE"), phase.get("AZI"), phase.get("AMP"), phase.get("PERIOD"),
                phase.get("MAG_NAME"), phase.get("MAG_VAL")
    });
    //logger.debug("HPB数据格式内容：{}", hpbFormat);
    return hpbFormat;
  }
  
  /**
   * HSB数据格式内容
   * @return
   */
  private String getHSB() {
    String hsbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n",new Object[] {
        "Net_code", "Sta_code", "Sta_cname", "Sta_type", "Chan_num", 
        "Sta_lat", "Sta_lon",  "Sta_elev", "Local_depth",  "sensor", "Datarecord", 
        "Rock_type", "Sensi-NS", "Sensi-EW", "Sensi-UD"
    });
    //logger.debug("HSB数据格式内容：{}", hsbFormat);
    return hsbFormat;
  }
  
  /**
   * 取得DSB数据
   * @param station 单个台站信息
   * @deprecated 查询单个台站信息时使用，原来的数据结构中台站信息表中有仪器字段
   * 现在的表结构中没有仪器字段，也就是说现在的一个台站对应多个仪器。
   */
  @SuppressWarnings("unused")
  private String getDSB(Map station) {
    String dsbFormat = MessageFormat.format(
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n",new Object[] {
            station.get("NET_CODE"), station.get("STA_CODE"), station.get("STA_CNAME"), 
            station.get("STA_TYPE"), "chan_num", station.get("STA_LAT"), station.get("STA_LON"), 
            station.get("STA_ELEV"), "Local_depth", "sensor", "Datarecord", station.get("ROCK_TYPE"), 
            "Sensi-NS", "Sensi-EW", "Sensi-UD"
    });
    //logger.debug("DSB数据格式内容：{}", dsbFormat);
    return dsbFormat;
  }
  
  /**
   * 取得DSB数据
   * @param staLocList 台站和位置信息联合表结果集
   * @return
   */
  private String getDSB(List<Map> staLocList) {
    StringBuffer buf = new StringBuffer();
    for(Map staLoc : staLocList) {
      String dsbFormat = MessageFormat.format(
          "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n",new Object[] {
              staLoc.get("NET_CODE"), staLoc.get("STA_CODE"), staLoc.get("STA_CNAME"), 
              staLoc.get("STA_TYPE"), staLoc.get("CHN_NUM"), staLoc.get("STA_LAT"), staLoc.get("STA_LON"), 
              staLoc.get("STA_ELEV"), staLoc.get("LOCAL_DEPTH"), staLoc.get("SENSOR_MODEL"), 
              staLoc.get("DIGITIZER_MODEL"), staLoc.get("ROCK_TYPE"), 
              "Sensi-NS", "Sensi-EW", "Sensi-UD"
      });
      buf.append("DSB").append(" ").append(dsbFormat);
    }
    
    return buf.toString();
  }
  
  /**
   * 取得volume_index_block0的数据
   * @param vlmType 卷类型
   */
  private StringBuffer getVlmIndexBlock0(String vlmType) {
    StringBuffer indexBlock0 = new StringBuffer();
    indexBlock0.append("VI0").append(" ").append("Volume_type").append(" ").append(
        vlmType).append(" ").append("CSF_Ver").append(" ").append("1.0").append("\n");
    return indexBlock0;
  }
  
  /**
   * 取得volume_index_block1的数据
   * @param criteria 数据参数
   */
  private StringBuffer getVlmIndexBlock1(Criteria criteria) {
    StringBuffer indexBlock1 = new StringBuffer();
    Map networkInfo = (Map) queryNetwordInfo(criteria);
    //logger.debug("台网信息：{}",networkInfo);
    if (networkInfo != null) {
      String netCode = (String)networkInfo.get("Net_code");
      String netName = (String)networkInfo.get("Net_cname");
      //logger.debug("台网代码：{}，台网名称：{}", netCode, netName);
      String startDate = null;
      String endDate = null;
      if (networkInfo.get("Net_startdate") != null) {
        startDate = DateUtil.getDateTime("yyyy-MM-dd HH:ss:ss.ss", (Date)networkInfo.get("Net_startdate"));
      }
      if (networkInfo.get("Net_enddate") != null) {
        endDate = DateUtil.getDateTime("yyyy-MM-dd HH:ss:ss.ss", (Date)networkInfo.get("Net_enddate"));
      }
      //logger.debug("台网起始时间：{} ----- 结束时间：{}", startDate, endDate);
      
      indexBlock1.append("VI1").append(" ").append("Net_code").append(" ").append(
          netCode).append(" ").append("Net_cname").append(" ").append(
              netName).append(" ").append(startDate).append(" ").append(endDate).append("\n");
    }
    
    return indexBlock1;
  }
  
  /**
   * 根据地震目录ID，取得该目录所有的震级
   * @param catalogId 地震目录ID
   * @param criteria 目录查询条件
   */
  private List<Map> getMagOfCatalog(String catalogId, Criteria criteria) {
    MagCriteria magCriteria = new MagCriteria();
    magCriteria.setSchema(dataSourceManager.getSeismicSchema());
    //如果已经配置了震级表
    if(StringUtils.isNotEmpty(criteria.getMagTname())) {
      magCriteria.setTableName(criteria.getMagTname());
    } else {//使用默认的震级表
      magCriteria.setTableName(SeismicConstants.DEFAULT_MAG_TABLE);
    }
    magCriteria.setCatId(catalogId);
    return getTemplate().queryForList(SQL_MAG_NAME, magCriteria);
  }
  
  /**
   * 根据地震目录ID，取得该目录所有的震相
   * @param catalogId 地震目录ID
   * @param criteria 目录查询条件
   */
  private List<Map> getPhaseOfCatalog(String catalogId, Criteria criteria) {
    PhaseCriteria phaseCriteria = new PhaseCriteria();
    phaseCriteria.setSchema(dataSourceManager.getSeismicSchema());
    //如果已经配置了震相表
    if(StringUtils.isNotEmpty(criteria.getPhaseTname())) {
      phaseCriteria.setTableName(criteria.getPhaseTname());
    } else {//使用默认的震相表
      phaseCriteria.setTableName(SeismicConstants.DEFAULT_PHASE_TABLE);
    }
    phaseCriteria.setCatId(catalogId);
    String staNetCode = criteria.getStaNetCode();
    if(StringUtils.isNotEmpty(staNetCode)) {
      String[] sncode = staNetCode.split("/");
      //logger.debug("原始数据：{}", sncode);
      phaseCriteria.setNetName(sncode[0]);
      if(sncode.length > 1) {
        //logger.debug("解析出来的台网台站代码分别是：{}, {}", sncode[0], sncode[1]);
        phaseCriteria.setStaName(sncode[1]);
      }
    }
    
    phaseCriteria.setPhaseName(criteria.getPhaseName());
    phaseCriteria.setPhaseType(criteria.getPhaseType());
    return getTemplate().queryForList("cz.queryPhaseData", phaseCriteria);
  }
  
  /**
   * 查询台网信息
   * @param criteria
   */
  public Object queryNetwordInfo(Criteria criteria) {
    //查询基本信息数据库中的台网表
    criteria.setSchema(dataSourceManager.getStationSchema());
    return getTemplate().queryForObject("cz.queryNetworkInfo", criteria);
  }
  
  /**
   * 导出WKF格式
   * @param row 一行数据
   * @return
   */
  public String extractWkf(Map row) {
    String wkfData = MessageFormat.format("{0},{1},{2},{3},{4},{5},{6},{7},{8}\n",
        new Object[] { DateUtil.getDateTime("yyyy", (Date) row.get("O_TIME")),// 4位年
            DateUtil.getDateTime("MMdd", (Date) row.get("O_TIME")),// 4位月日
            DateUtil.getDateTime("HH", (Date) row.get("O_TIME")),// 2位时
            DateUtil.getDateTime("mm", (Date) row.get("O_TIME")),// 2位分
            convertEpi((Double)row.get("EPI_LAT"), 5, "LAT"),// 4位伟度，加上负号共5位，不够位数前面补空格
            convertEpi((Double)row.get("EPI_LON"), 6, "LON"),// 5位经度，加上负号共6位，不够位数前面补空格
            convertMagVal((Double)row.get("M"), 4),// 3位震级，加上负号共4位，不够位数前面补空格
            ExtremeUtils.formatNumber("000", "000"),
            convertDepth((Double)row.get("EPI_DEPTH"), 3)// 3位深度
            });
    return wkfData;
  }
  
  /**
   * 转换震级值
   * @param val
   * @param len
   */
  private String convertMagVal(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = "";
    } else {
      strVal = ExtremeUtils.formatNumber("0.0", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换深度值,将空值和-99***等值转换为000
   * @param val
   * @param len
   */
  private String convertDepth(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = ExtremeUtils.formatNumber("000", "000");
    } else {
      strVal = ExtremeUtils.formatNumber("000", val);
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * wkf文件导出时，转换伟度与经度。系统存储的是度.度 要把度.度 转成 度分）。
   * 39.98 度.度 39 + "" + 0.98 * 60 = 3959度分
   * @param val
   * @param len
   * @return
   */
  private String convertEpi(Double val, int len, String lonOrlat) {
    int copVal = -99;
    String strVal = null;
    int valInt = (int) val.doubleValue();
    if (val == null) {
      strVal = "";
    }
    if ("LON".equals(lonOrlat)) {
      copVal = -999;
    }
    if (valInt <= copVal) {
      strVal = "";
    } else {
      double valDou = Math.abs(val - valInt);
      strVal = valInt + ExtremeUtils.formatNumber("00", (valDou * 60));
    } 
    
    return StringUtils.leftPad(strVal, len, ' ');
  }

  /**
   * 导出EQT格式
   * @param row 一行数据
   * @return
   */
  public String extractEqt(Map row) {
    String eqtData = MessageFormat.format("{0}{1}{2}{3}{4}{5}\n", new Object[] {// 年前有一空格
            // 2008-10-04 12:48:14 转成 20081004124814
            DateUtil.getDateTime("yyyyMMddHHmmss", (Date) row.get("O_TIME")),
            convertEpiOfEQT((Double)row.get("EPI_LAT"), 6, "LAT"),// 5位伟度，加上负号共6位，不够位数前面补空格
            convertEpiOfEQT((Double)row.get("EPI_LON"), 7, "LON"),// 6位经度，加上负号共7位，不够位数前面补空格
            convertMagValOfEQT((Double)row.get("M"), 3),// 3位震级，加上负号共4位
            convertDepthOfEQT((Double)row.get("EPI_DEPTH"), 4),// 4位深度
            ExtremeUtils.formatNumber("000", "000")
            });
    return eqtData;
  }

  /**
   * EQT文件导出时，转换经纬度的值
   * @param val
   * @param len
   * @param lonOrlat
   * @return
   */
  private String convertEpiOfEQT(Double val, int len, String lonOrlat) {
    int copVal = -99;
    String strVal = null;
    if (val == null) {
      strVal = "";
    }
    if ("LON".equals(lonOrlat)) {
      copVal = -999;
      if (val <= copVal) {
        strVal = "";
      } else {
        strVal = ExtremeUtils.formatNumber("###.00", val);
      } 
    }
    if ("LAT".equals(lonOrlat)) {
      if (val <= copVal) {
        strVal = "";
      } else {
        strVal = ExtremeUtils.formatNumber("##.00", val);
      } 
    }
    
    return StringUtils.leftPad(strVal, len, ' ');
  }
  
  /**
   * 转换震级值,不够位数后面补零
   * @param val
   * @param len
   */
  private String convertMagValOfEQT(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = "";
    } else {
      strVal = ExtremeUtils.formatNumber("0.0", val);
    }
    
    return StringUtils.rightPad(strVal, len, ' ');
  }
  
  /**
   * 转换深度值,将空值和-99***等值转换为000
   * @param val
   * @param len
   */
  private String convertDepthOfEQT(Double val, int len) {
    String strVal = null;
    if (val == null || val <= -99) {
      strVal = ExtremeUtils.formatNumber("0000", "000");
    } else {
      strVal = ExtremeUtils.formatNumber("0000", val);
    }
    
    return StringUtils.leftPad(strVal, len, '0');
  }
  
  /**
   * 导出Q01格式数据
   * @param row 一行数据
   * @return
   */
  public String extractQ01(Map row, Criteria criteria) {
    String q01Data = MessageFormat.format("{0}{1}{2} {3} {4} {5}\n", new Object[] {
        // 2008-10-04 12:48:14 转成 20081004124814
        //DateUtil.getDateTime("yyyyMMddHHmmss", (Date) row.get("O_TIME")),
        convertDateFormat((Date) row.get("O_TIME"), String.valueOf(row.get("O_TIME_FRAC"))),
        convertEpi((Double)row.get("EPI_LAT"), 5, "LAT"),// 4位伟度，加上负号共5位，不够位数前面补空格
        convertEpi((Double)row.get("EPI_LON"), 6, "LON"),// 5位经度，加上负号共6位，不够位数前面补空格
        getFormatOfQ01((String)row.get("M_SOURCE"))+ExtremeUtils.formatNumber("0.0", row.get("M")),// 震级
        getFormatOfQ01((String)row.get("Epic_id")),//位号
        getFormatOfQ01((String)row.get("LOCATION_CNAME"))});
    return q01Data;
  }
  
  /**
   * 转换Q01格式的震级列
   * @param row
   * @param criteria
   */
  @SuppressWarnings("unused")
  private String getMagOfQ01(Map row, Criteria criteria) {
    StringBuffer mg = new StringBuffer(" ");
    MagCriteria mc = new MagCriteria();
    mc.setSchema(criteria.getSchema());
    mc.setTableName(criteria.getMagTname());
    mc.setCatId((String)row.get("ID"));
    mc.setMagName((String)row.get("M_SOURCE"));
    Double magValue = getMagVal(mc);
    if (StringUtils.isNotEmpty((String)row.get("M_SOURCE"))) {
      magValue = getMagVal(mc);
      mg.append((String)row.get("M_SOURCE"));
      mg.append(ExtremeUtils.formatNumber("0.0", magValue));
    } else {
      mg.append("M");
      mg.append(ExtremeUtils.formatNumber("0.0", row.get("M")));
    }
    //logger.debug("Q01格式的震级列：{}", mg.toString());
    return mg.toString();
  }
  
  /**
   * 转换Q01格式的值，为空的话补空格
   */
  private String getFormatOfQ01(String val) {
    if (StringUtils.isNotEmpty(val)) {
      return val;
    }
    return "  ";
  }
  
  /**
   * 转换Q01格式的发震时刻
   * @param eTime
   * @param timeFrac
   */
  private String convertDateFormat(Date eTime, String timeFrac) {
    return eqFormat(eTime, timeFrac);
  }
  
  /**
   * 将一个数字格式化为字符串，并且在前面按照一定的长度补，解决经伟度出现负数的情况. 
   * 用ExtremeUtils 的结果是 -0005.9 而要的结果为 000-5.9
   * @param value 数字
   * @param len 字符串总长度
   * @return 返回格式化好的字符串
   */
  @SuppressWarnings("unused")
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
    String s = getDateTime("yyyyMMddHHmmss", aDate);
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
