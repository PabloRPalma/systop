package quake.seismic.data.catalog.dao.impl;

import java.text.MessageFormat;
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n\r",new Object[] {
         row.get("NET_CODE"), DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)row.get("O_TIME")), 
           row.get("EPI_LAT"), row.get("EPI_LON"), row.get("EPI_DEPTH"), row.get("M_SOURCE"), row.get("M"), 
             row.get("Rms"), row.get("QLOC"),  row.get("Sum_stn"), row.get("Loc_stn"), 
               SeismicConstants.EQ_TYPE_MAP.get(row.get("Eq_type")), 
                 row.get("Epic_id"), row.get("Source_id"), row.get("LOCATION_CNAME")
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15} {16}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15} {16}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14} {15}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n\r",new Object[] {
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
        "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n\r",new Object[] {
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
          "{0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}\n\r",new Object[] {
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
        vlmType).append(" ").append("CSF_Ver").append(" ").append("1.0").append("\n\r");
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
        startDate = DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)networkInfo.get("Net_startdate"));
      }
      if (networkInfo.get("Net_enddate") != null) {
        endDate = DateUtil.getDateTime("yyyy/MM/dd HH:ss:ss.ss", (Date)networkInfo.get("Net_enddate"));
      }
      //logger.debug("台网起始时间：{} ----- 结束时间：{}", startDate, endDate);
      
      indexBlock1.append("VI1").append(" ").append("Net_code").append(" ").append(
          netCode).append(" ").append("Net_cname").append(" ").append(
              netName).append(" ").append(startDate).append(" ").append(endDate).append("\n\r");
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
