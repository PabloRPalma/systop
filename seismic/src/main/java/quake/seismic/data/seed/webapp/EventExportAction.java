package quake.seismic.data.seed.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.ds.service.DataSourceManager;
import quake.seismic.data.catalog.dao.impl.GridCatDao;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.seed.dao.impl.SeedDao;
import quake.seismic.data.seed.model.StaCriteria;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.ArrayUtils;

/**
 * 事件波形导出Action
 * @author sam
 *
 */

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EventExportAction extends BaseSeedExpAction implements Preparable{
  /**
   * 全部台站
   */
  private String allStations;
  
  /**
   * 台站列表
   */
  protected String[] stations;  
  
  @Autowired
  private GridCatDao catDao;
  
  
  @Autowired
  @Qualifier("seedDao")
  private SeedDao seedDao;
  
  /**
   * 数据源Manager，用于获取测震schema
   */
  @Autowired
  private DataSourceManager dataSourceManager;
  
  /**
   * 地震目录表名
   */
  private String tableName;

  /**
   * Seed文件
   */
  private String seed;
  
  /**
   * 初始化事件波形文件目录和rdseed工作目录
   */
  @PostConstruct
  public void init() {
    seedPath = seedpathManager.get().getPath() + "data/seed/";
    //构建rdseed执行路径，用于存放执行结果
    workDir = new File(seedPath + RandomStringUtils.randomNumeric(10) + "/");
    if(!workDir.exists()) {
      workDir.mkdirs();
    }
    
    cmd = seedpathManager.get().getPath() + "appsoft/rdseed";
    logger.debug("事件波形目录'{}'", seedPath);
    logger.debug("rdseed工作目录'{}'", workDir.getAbsolutePath());
  }

  /**
   * 设置rdseed执行过程中的参数：
   * <pre>
   *  //0 Input  File (/dev/nrst0) or 'Quit' to Exit
      //1-Output File (stdout)
      //2-Volume #  [(1)-N]
      //3-Options [acCsSpRtde]
      //4-Summary file (None)
      //5-Station List (ALL)           
      //6-Channel List (ALL)      
      //7-Network List (ALL)
      //8-Loc Ids (ALL ["--" for spaces]) :
      //9-Output Format [(1=SAC), 2=AH, 3=CSS, 4=mini seed, 5=seed, 6=sac ascii, 7=SEGY] : 5
      第九步之后，如果选择SAC则：
         Output file names include endtime? [Y/(N)]
         Output poles & zeroes ? [Y/(N)]
         Check Reversal [(0=No), 1=Dip.Azimuth, 2=Gain, 3=Both]:
       如果选择AH，则：
         Output file names include endtime? [Y/(N)]
         Check Reversal [(0=No), 1=Dip.Azimuth, 2=Gain, 3=Both]: 
       如果选择CSS，则：
         Check Reversal [(0=No), 1=Dip.Azimuth, 2=Gain, 3=Both]:
       如果选择mini seed和seed，则：       
         Strip out records with zero sample count? [Y/N]? [Y/N]
       如果选择sac ascii,则:
         Check Reversal [(0=No), 1=Dip.Azimuth, 2=Gain, 3=Both]:
       如果选择SEGY，则： 
         Check Reversal [(0=No), 1=Dip.Azimuth, 2=Gain, 3=Both]:
      //Select Data Type [(E=Everything), D=Data of Undetermined State, R=Raw waveform Data, Q=QC'd data] :
      //Start Time(s) YYYY,DDD,HH:MM:SS.FFFF :
      //End Time(s)   YYYY,DDD,HH:MM:SS.FFFF :
      //Sample Buffer Length [2000000]:
      //Extract Responses [Y/(N)]
      //Input  File (/dev/nrst0) or 'Quit' to Exit: QUit      
   * </pre>
   */
  @Override
  public void prepare() throws Exception { 
    //构建seed文件的完整路径
    args[0] = seedPath + seed + "\n"; //Input  File (/dev/nrst0) or 'Quit' to Exit
    //台站列表
    logger.debug("Export all stations {}", StringUtils.isNotBlank(allStations));
    if(ArrayUtils.isNotEmpty(stations) && !StringUtils.isNotBlank(allStations)) {
      args[5] = org.springframework.util.StringUtils.arrayToCommaDelimitedString(stations) + "\n";
    }
    //通道列表
    if(ArrayUtils.isNotEmpty(channels)) {
      args[6] = org.springframework.util.StringUtils.arrayToCommaDelimitedString(channels) + "\n";
    }
    //输出格式
    if(StringUtils.isNotBlank(format)) {
      args[9] = format + "\n";    
    }
    logger.info("rdseed args {}", Arrays.toString(args));
  }
  
  /**
   * 导出地震数据并下载
   * @return
   */
  public String export() {   
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      File rdseed = new File(cmd);
      if(!rdseed.exists()) {
        logger.warn("rdseed 不存在。");
        return null;
      }
      ProcessBuilder procBuilder = new ProcessBuilder();
      procBuilder.directory(workDir); //设置执行目录
      procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
      procBuilder.command(rdseed.getAbsolutePath());
      
      Process process = procBuilder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
      readString(reader, false);
      for(String arg : args) {
        logger.debug(arg);
        writer.write(arg);
        writer.flush();
      }
      int exit = process.waitFor();
      logger.info("rdseed 执行完毕 [{}]", exit);
      
      if(exit == 0) {
        rmRdseedLog(workDir);
        download(workDir, format); //打包下载数据  
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      rmOutput(workDir); //删除输出目录，包括rdseed生成的文件和打包的文件
    }
    return null;
  }  
  
  /**
   * 解析事件波形，从中得出通道、台站、台网等信息
   * @return
   */
  public String showSeed() {
    String seedName = getRequest().getParameter("seedname");
    
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      File rdseed = new File(cmd);
      if(!rdseed.exists()) {
        logger.warn("rdseed 不存在。");
        return null;
      }
      ProcessBuilder procBuilder = new ProcessBuilder();
      procBuilder.directory(workDir); //设置执行目录
      procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
      logger.debug(cmd);
      logger.debug(seedPath + seedName);
      procBuilder.command(rdseed.getAbsolutePath(), "-f", seedPath + seedName, "-S");
      
      Process process = procBuilder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
      readString(reader, true);
      
      int exit = process.waitFor();
      logger.info("rdseed 执行完毕 [{}]", exit);
      
      if(exit == 0) {
        File stations = new File(workDir.getAbsoluteFile() + File.separator + "rdseed.stations");
        if(!stations.exists()) {
          logger.warn("{}解析失败。", seedName);
          return "show";
        }
        List<Map<String, Object>> items = parseStations(stations); //解析导出的文件
        getRequest().setAttribute("items", items);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if(writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      rmOutput(workDir); //删除输出目录，包括rdseed生成的文件和打包的文件
    }
    return "show";
  }
  
  /**
   * 解析通过-S参数得到的reseed.stations
   * 每行的数据格式:LOH   HE  +41.3196 +117.7125 +587  "LOH" "BHN BHE BHZ " 2006,365,16:00:00 2500,365,23:59:59.9999
   */
  public List<Map<String, Object>>  parseStations(File rdStations) {
    BufferedReader reader = null;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Set<String> channels = new HashSet<String>();
    try {
      reader = new BufferedReader(new FileReader(rdStations));
      String line = null;
      do {
        line = reader.readLine(); //读取rdsede.stations的一行数据
        String[] cols = parseLine(line);
        StaCriteria criteria = new StaCriteria();
        criteria.setSchema(dataSourceManager.getStationSchema());
        if(cols != null && cols.length > 8) {
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("stationCode", cols[0]);
          map.put("netCode", cols[1]); 
          map.put("station", cols[1] +  "/" + cols[0]);
          //查询中文名
          criteria.setStaCode(cols[0]);
          criteria.setNetCode(cols[1]);
          logger.debug("criteria:" + criteria.getNetCode() + "." + criteria.getStaCode());          
          String station = seedDao.queryStaName(criteria);// 获取台站中文名
          if(StringUtils.isNotBlank(station)) {
            map.put("station", station);
          }
          list.add(map);
          //从cols中得到通道数据
          channels.add(fixChannel(cols[6]));
          channels.add(fixChannel(cols[7]));
          channels.add(fixChannel(cols[8]));
        }        
        
        logger.debug(Arrays.toString(cols));
      } while(StringUtils.isNotBlank(line)); 
      //设置通道，页面可以直接使用
      getRequest().setAttribute("cha", this.toJson(channels));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return list;
  }

  
  /**
   * 根据波形文件名称查询地震目录（单条）,用于导出波形之前看到地震的情况
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Map getCatalogBySeed() {
    Criteria criteria = new Criteria();
    criteria.setTableName(tableName);
    criteria.setSchema(dataSourceManager.getSeismicSchema());
    String seedName = getRequest().getParameter("seedname");
    criteria.setSeedId(seedName.substring(0, seedName.lastIndexOf(".")) + "%");
    return (Map) catDao.getTemplate().queryForObject("cz.queryBySeed", criteria);
  }
  
  
  public String getSeed() {
    return seed;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }

  public String getAllStations() {
    return allStations;
  }

  public void setAllStations(String allStations) {
    this.allStations = allStations;
  }

  public String[] getArgs() {
    return args;
  }

  public String[] getStations() {
    return stations;
  }

  public void setStations(String[] stations) {
    this.stations = stations;
  }
  
  
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

}
