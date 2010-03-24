package datashare.sign.prequery.dao;

import datashare.DataType;
import datashare.base.dao.AbstractDataShareDao;

/**
 * @author Lunch
 */
public class AbstractPreQueryDao extends AbstractDataShareDao{

  /**
   * 查询符合条件且存在的表名称
   */
  protected final static String SQL_TABLENAME_ID = "qz.queryTable";
  
  /** 
   * 查询台站、测点、测项分量组合唯一的数据记录 
   */
  protected final static String SQL_QUERY_NODE_ID = "qz.queryNodeInfo";
  
  /**
   * 查询台站名称的SQl语句
   */
  protected final static String SQL_QUERY_STATION_NAME_ID = "qz.queryStationName";
  
  /**
   * 查询测项分量名称的SQl语句
   */
  protected final static String SQL_QUERY_ITEM_NAME_ID = "qz.queryItemName";

  /**
   * 查询测点名称的SQl语句
   */
  protected final static String SQL_QUERY_POINT_NAME_ID = "qz.queryPointName";
  
  /**
   * 查询测项分量数据对应开始时间，结束时间的SQl语句
   */
  protected final static String SQL_QUERY_START_END_DATE_ID  = "qz.queryStartEndDate";
  
  /**
   * @see datashare.base.dao.AbstractDataShareDao#getDataType()
   */
  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }
  

}
