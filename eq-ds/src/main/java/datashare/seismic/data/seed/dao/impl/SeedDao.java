package datashare.seismic.data.seed.dao.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import datashare.admin.seedpath.service.SeedpathManager;
import datashare.seismic.data.seed.dao.AbstractSeedDao;
import datashare.seismic.data.seed.model.StaCriteria;

/**
 * Seed操作类
 * @author dhm
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class SeedDao extends AbstractSeedDao<Page> {

  @Autowired(required = true)
  private JdbcTemplate jdbcTemplate;
  
  @Autowired(required = true)
  private CacheManager cacheManager;
  
  @Autowired
  private SeedpathManager seedpathManager;
  
  /**
   * 获取系统路径分隔符(Windows "\"，Linux "/")
   */
  private String separator = System.getProperties().getProperty("file.separator");

  /**
   * 检测事件是否存在SEED文件
   */
  public void seedExists(List<Map> list) {
    String seedName = null;
    for (Map map : list) {
      if(map.get("EVENT_ID") != null) {
        seedName = map.get("EVENT_ID").toString();
      }
      StringBuffer filePath = new StringBuffer("");
      if (seedpathManager.get() != null) {
        if(seedpathManager.get().getPath() != null){
          filePath.append(seedpathManager.get().getPath()).append("data").append(separator).append(
            "seed").append(separator).append(seedName).append(".seed");
        }
      }
      File file = new File(filePath.toString());
      if (file.exists()) {
        map.put("SEED_EXISTS", 1);
      } else {
        map.put("SEED_EXISTS", 0);
      }
      seedAnalysis(file.getPath(), map);// 判断SEED文件是否解析，文件可能被删除
      map.put("SEED_NAME", file.getName());// 获取SEED名
    }
  }

  /**
   * 判断SEED文件是否解析
   */
  @SuppressWarnings("unchecked")
  private void seedAnalysis(String seedFile, Map map) {
    int count = querySeedPlotsDataCount(seedFile);
    if (count > 0) {
      map.put("SEED_ANALYSIS", 1);
    } else {
      map.put("SEED_ANALYSIS", 0);
    }
  }

  /**
   * 查询台站中文名
   * 使用了缓存，缓存定义在classpath:ehcache.xml中
   * @param criteria
   */
  public String queryStaName(StaCriteria criteria) {
    //stationNameByCodeAndNet定义在classpath:ehcache.xml
    Ehcache cache = cacheManager.getCache("stationNameByCodeAndNet");
    if(cache == null) {
      throw new ApplicationException("Ehcach with name 'stationNameByCodeAndNet' is not exists.");
    }
    String key = criteria.getNetCode() + criteria.getStaCode();
    Element ele = cache.get(key);
    String staName = null;
    if(ele == null) {
      staName = (String) getTemplate().queryForObject(SQL_SATNAME_ID, criteria);
      cache.put(new Element(key, staName));
      logger.debug("Got station name by code and net from database.");
    } else {
      staName = (String) ele.getValue();
      logger.debug("Got station name by code and net from cache");
    }
    return staName;
  }
  
  /**
   * SEED文件是否解析,即在seed_plots表中是否有数据 
   * @param seedName Seed文件全路径
   */
  public int querySeedPlotsDataCount(String seedName) {
    return jdbcTemplate.queryForInt("select count(*) from seed_plots where seedfile = ?",
        new Object[] { seedName });
  }

  /**
   * 查询各台站对某一事件监测数据
   * @param seedName Seed文件全路径
   */
  public List<Map> querySeedPlotsData(String seedFile) {
    List<Map> list = jdbcTemplate.queryForList("select * from seed_plots where seedfile = ?",
        new Object[] { seedFile });
    return list;
  }

  /**
   * 查询所有图片路径
   * @param id mySQL数据库中所有事件波形解析数据ID
   */
  public List<Map> queryMapFiles(String[] id) {
    StringBuffer sql = new StringBuffer("select * from seed_plots where ID in (");
    for (int i = 0; i < id.length; i++) {
      if (i != 0) {
        sql.append(",");
      }
      sql.append("?");
    }
    sql.append(")");
    return jdbcTemplate.queryForList(sql.toString(), id);
  }
}
