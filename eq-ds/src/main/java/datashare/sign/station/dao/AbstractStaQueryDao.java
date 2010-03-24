package datashare.sign.station.dao;

import datashare.DataType;
import datashare.base.dao.AbstractDataShareDao;

/**
 * 前兆台站基本信息Dao
 * @author DU
 */
public abstract class AbstractStaQueryDao extends AbstractDataShareDao {
  /**
   * 查询台站下测点_按学科-测项-仪器
   */
  protected final static String SQL_STA_POI_ID = "qz.queryStationList";
  /**
   * 查询台站下测点_按台站
   */
  protected final static String SQL_STA_POI_FORSTA_ID = "qz.queryStationByStaList";
  /**
   * 查询所有仪器
   */
  protected final static String SQL_INST_ID = "qz.queryInstList";
  /**
   * 查询测项
   */
  protected final static String SQL_MSD_ID = "qz.queryMsdList";
  /**
   * 查询学科对应的仪器
   */
  protected final static String SQL_SUBINSTRS_ID = "qz.querySubInstrsList";
  /**
   * 查询台站信息
   */
  protected final static String SQL_STATION_ID = "qz.queryStationInfo";
  /**
   * 查询台站测点信息
   */
  protected final static String SQL_POINT_ID = "qz.queryPointInfo";
  /**
   * 查询台站测点仪器运行信息
   */
  protected final static String SQL_INSTR_ID = "qz.queryInstrInfo";
  /**
   * 查询台站测点地磁方位标
   */
  protected final static String SQL_GFLAG_ID = "qz.queryGFlagInfo";
  /**
   * 查询台站测点地磁墩差
   */
  protected final static String SQL_GDIFF_ID = "qz.queryGDiffInfo";
  /**
   * 查询台站测点测项分量
   */
  protected final static String SQL_ITEMS_ID = "qz.queryStaItemsInfo";
  /**
   * 查询台站测点测项分量
   */
  protected final static String SQL_INSTRPARAMS_ID = "qz.queryInstrParamsInfo";
  /**
   * 查询台站测点洞体
   */
  protected final static String SQL_CAVE_ID = "qz.queryStaCaveInfo";
  /**
   * 查询台站测点井泉信息
   */
  protected final static String SQL_WELL_ID = "qz.queryStaWellInfo";
  /**
   * 查询台站相关洞体信息
   */
  protected final static String SQL_STAT_CAVE_ID = "qz.queryStatCave";
  /**
   * 查询台站相关井信息
   */
  protected final static String SQL_STAT_WELL_ID = "qz.queryStatWell";
  /**
   * 查询台站相关泉信息
   */
  protected final static String SQL_STAT_SPRING_ID = "qz.queryStatSpring";
  /**
   * 查询台站相关断层信息
   */
  protected final static String SQL_STAT_FAULTS_ID = "qz.queryStatFaults";
  
  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }
}
