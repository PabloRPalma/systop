package datashare.sign.support;

import javax.annotation.PostConstruct;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import datashare.DataType;
import datashare.admin.ds.service.DataSourceManager;
import datashare.admin.samplerate.model.SampleRate;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.base.dao.AbstractDataShareDao;
import datashare.sign.data.model.Criteria;

@Repository
public class SignCommonDao extends AbstractDataShareDao {

  @Autowired
  private DataSourceManager dataSourceManager;
  /**
   * 查询台站名称的SQl语句
   */
  public final static String SQL_STATION_NAME_BY_ID = "qz.common.queryStationName";

  /**
   * 查询测项分量名称的SQl语句
   */
  public final static String SQL_ITEM_NAME_BY_ID = "qz.common.queryItemName";

  /**
   * 查询测点名称的SQl语句
   */
  public final static String SQL_POINT_NAME = "qz.common.queryPointName";

    /**
   * 用于缓存staticID和staticName的对应关系
   */
  private Ehcache stationCache;
  /**
   * 用于缓存itemID和itemName的对应关系
   */
  private Ehcache itemCache;
  
  /**
   * 用于缓存pointId和pointName的对应关系
   */
  private Ehcache pointCache;
  
  @Autowired(required = true)
  private CacheManager cacheManager;
  
  @Autowired(required = true)
  private SampleRateManager sampleRateManager;
  
  /**
   * 初始化各个Cache
   */
  @PostConstruct
  public void init() {
    stationCache = cacheManager.getCache("stations");
    itemCache = cacheManager.getCache("items");
    pointCache = cacheManager.getCache("points");
  }
  

  @Override
  protected DataType getDataType() {
    return DataType.SIGN;
  }

  /**
   * 根据给出的StationID,查询该Station的名字
   */
  public String getStationName(String stationId) {
    String stationName = read(stationCache, stationId);
    
    if (stationName == null) {
      Criteria criteria = new Criteria();
      criteria.setStationId(stationId);
      criteria.setSchema(dataSourceManager.getQzSchema());
      stationName = (String) getTemplate().queryForObject(SQL_STATION_NAME_BY_ID, criteria);
      write(stationCache, stationId, stationName);
      logger.debug("Got a station name from DATABASE.");
    } else {
      logger.debug("Got a station name from station cache.");
    }
    return stationName;
  }

  /**
   * 根据测项分量ID查询测项分量名称
   */
  public String getItemName(String itemId) {
    String itemName = read(itemCache, itemId);
    if (itemName == null) {
      Criteria criteria = new Criteria();
      criteria.setItemId(itemId);
      criteria.setSchema(dataSourceManager.getQzSchema());
      itemName = (String) getTemplate().queryForObject(SQL_ITEM_NAME_BY_ID, criteria);
      logger.debug("Got a item name from DATABASE.");
      write(itemCache, itemId, itemName);
    } else {
      logger.debug("Got a station name from item cache.");
    }
    return itemName;
  }

  /**
   * 
   * 根据台站ID，测点ID，查询测点名称
   * 
   * @param stationId 台站ID
   * @param pointId 测点ID
   */
  public String getPointName(String stationId, String pointId) {
    String pointName = read(pointCache, stationId + pointId);
    if (pointName == null) {
      Criteria criteria = new Criteria();
      criteria.setStationId(stationId);
      criteria.setPointId(pointId);
      criteria.setSchema(dataSourceManager.getQzSchema());
      pointName = (String) getTemplate().queryForObject(SQL_POINT_NAME, criteria);
      logger.debug("Got a point name from DATABASE.");
      write(pointCache, stationId + pointId, pointName);
    } else {
      logger.debug("Got a point name from point cache");
    }
    return pointName;
  }
  
  /**
   * 根根采样率代码查询采样率名称
   */
  public String getSampleRateName(String sampleRate) {
    SampleRate sr = sampleRateManager.get(sampleRate);
    return (sr != null) ? sr.getName() : null;
  }
  
  /**
   * 从缓存中读取数据
   */
  private String read(Ehcache cache, String key) {
    Element ele = cache.get(key);
    return (ele != null) ? (String) ele.getValue() : null;
  }
  
  /**
   * 将数据写入缓存
   */
  private void write(Ehcache cache, String key, String value) {
    Element ele = new Element(key, value);
    cache.put(ele);
  }
}
