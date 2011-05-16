package quake.seismic.data.seed.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.codehaus.plexus.util.FileUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quake.admin.seedpath.model.Seedpath;
import quake.admin.seedpath.service.SeedpathManager;
import quake.seismic.data.seed.model.StationSeed;
import quake.seismic.data.seed.webapp.BaseSeedExpAction;

import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 连续波形定时处理类，因为连续波形需要处理多个波形文件，并且-S参数执行rdseed不能
 * 正确的得到连续波形的时间范围，而-t参赛不能得到正确的通道信息。也就是说，正确的信息
 * 必须执行两次rdseed才能得到，这显然会影响性能。所以用这个类定时处理。
 * @author sam
 *
 */
@Service
public class StationSeedQuartzService {
  private static Logger logger = LoggerFactory.getLogger(StationSeedQuartzService.class);
  
  @Autowired
  private SeedpathManager seedpathManager;
  @Autowired
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;
  /**
   * 解析连续波形文件，将波形文件的信息保存在数据库中
   * @see @{link StationSeed}
   */
  public void parseStationSeed() {
    Seedpath seedPath = seedpathManager.get();
    if (seedPath == null) {
      logger.warn("Seed 路径未设置！");
      return;
    }
    //连续波形路径
    String staSeed = seedPath.getPath() + "data/staseed";
    File staSeedPath = new File(staSeed);
    if(!staSeedPath.exists() && !staSeedPath.isDirectory()) {
      logger.warn("Seed Path '{}' is a invalid path.", staSeed);
      return;
    }
    //列出目录下的所有Seed文件
    File[] seeds = staSeedPath.listFiles(new FileFilter(){
      @Override
      public boolean accept(File file) {
        if(file != null && file.exists() && file.isFile()) {
          return StringUtils.equalsIgnoreCase("seed", FileUtils.extension(file.getName()));
        }
        return false;
      }
    });
    
    if(seeds == null || seeds.length <= 0) {
      logger.warn("There is no file in Path '{}'.", staSeed);
      return;
    }
    for(File seed : seeds) {
      if(isNoRecord(seed)) {
        try {
          parseTimes(seed, seedPath); //保存时间数据
        } catch (Exception e) {
          e.printStackTrace();
          continue;
        }
        updateChannel(seed, seedPath); //因为-t不能得到正确的通道信息，所以要用-S更新通道信息  
      }
    }
  }
  
  /**
   * 更新StationSeed的通道字段，（-t解析的通道数据不对，但是-S解析的时间数据不对）
   */
  @Transactional 
  private void updateChannel(File seed, Seedpath seedPath) {
    Set<String> channels = parseChannels(seedPath, seed); //执行-S操作，得到通道数据
    StringBuffer buf = new StringBuffer(100);
    for(Iterator<String> itr = channels.iterator(); itr.hasNext();) {
      buf.append(itr.next());
      if(itr.hasNext()) {
        buf.append(",");
      }
    }
    Session s = dao.getSessionFactory().openSession();
    try {
      s.createQuery("update StationSeed s set s.cha=? where s.seed=?")
      .setString(0, buf.toString()).setString(1, seed.getName()).executeUpdate();
    } finally {
      s.close();
    }
  }
  
  /**
   * 执行rdseed -f seed -t,并解析执行结果，保存在数据库中
   * @param seed
   */
  void parseTimes(File seed, Seedpath seedPath) {
    //构建rdseed路径
    String command = seedPath.getPath() + "appsoft/rdseed";
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
    procBuilder.command(command, "-f", seed.getAbsolutePath(), "-t");
    Process process;
    try {
      process = procBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      doOneRec(reader, seed.getName(), false, process); //处理单个seed
      
      int exit = process.waitFor();
      logger.info("rdseed 执行完毕 [{}]", exit);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  
  /**
   * 将rdseed -f seed -t输出的数据保存到数据库
   */
  private void doOneRec(final BufferedReader reader, final String seed, final boolean print, final Process process) {
    new Thread(new Runnable() {
      public void run() {
        try {
          String line = "";
          //如果出现Rec#开头的行，则表示下面的数据需要处理，
          //此时将begin设置关为true
          boolean begin = false; 
          do {
            line = reader.readLine();
            if (print == true) {
              System.out.println(line);
            }
            if(StringUtils.isNotBlank(line)) {
              if(begin) {
                if(line.startsWith("rdseed")) {
                  break;
                } else {
                  try {
                    doOneLine(line.trim(), seed);
                  } catch (Exception e) {
                    logger.warn("RDSEED中途退出。");
                    process.destroy();
                  }
                }
              }
              //如果以Rec#开头，则改变标示，下个循环将处理数据
              if(line.trim().startsWith("Rec#")) {
                begin = true;
              }
            }
          } while (StringUtils.isNotBlank(line));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
  
  /**
   * 处理rdseed -f seed -t 输出数据的一行
   */
  @Transactional
  private void doOneLine(String line, String seed) {
    String[] cols = line.split("\\s *");
    
    if(ArrayUtils.isEmpty(cols) || cols.length < 9) {
      logger.warn("解析数据不符合标准 {}", line);
      return;
    }
    try {
      StationSeed sa = new StationSeed(cols, seed);
      dao.save(sa);
    } catch (Exception e) {
      logger.error("连续波形命令rdseed -f seed -t解析错误" + line);     
      throw new ApplicationException("连续波形命令rdseed -f seed -t解析错误" + line);
    }
    
  }
  /**
   * 如果波形文件没有被记录，返回ture
   */
  private boolean isNoRecord(File seed) {
    String hql = "from StationSeed ss where ss.seed=?";
    return dao.findObject(hql, seed.getName()) == null;
  }
  
  /**
   * 得到通道信息
   * @return
   */
  public Set<String> parseChannels(Seedpath seedPath, File rdseed) {
    BufferedReader reader = null;
    BufferedWriter writer = null;
    File workDir = new File(
        rdseed.getParentFile().getAbsolutePath() + File.separator + RandomStringUtils.randomNumeric(5));
    workDir.mkdir();
    try {
      String command = seedPath.getPath() + "appsoft/rdseed";
      ProcessBuilder procBuilder = new ProcessBuilder();
     
      procBuilder.directory(workDir); //设置执行目录
      procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
      
      procBuilder.command(command, "-f", rdseed.getAbsolutePath(), "-S");
      
      Process process = procBuilder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
      BaseSeedExpAction.readString(reader, true);
      
      int exit = process.waitFor();
      logger.info("rdseed 执行完毕 [{}]", exit);
      
      if(exit == 0) {
        File stations = new File(workDir.getAbsoluteFile() + File.separator + "rdseed.stations");
        if(!stations.exists()) {
          logger.warn("{}解析失败。", rdseed);
          return Collections.emptySet();
        }
        return parseRdseedStations(stations); //解析台站信息和通道信息
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
      
      BaseSeedExpAction.rmOutput(workDir); //删除输出目录，包括rdseed生成的文件和打包的文件
    }
    return Collections.emptySet();
  }
  /**
  * 解析通过-S参数得到的reseed.stations
  * 每行的数据格式:LOH   HE  +41.3196 +117.7125 +587  "LOH" "BHN BHE BHZ " 2006,365,16:00:00 2500,365,23:59:59.9999
  */
 public Set<String> parseRdseedStations(File rdStations) {
   BufferedReader reader = null;
   Set<String> channels = new HashSet<String>();
   try {
     reader = new BufferedReader(new FileReader(rdStations));
     String line = null;
     do {
       line = reader.readLine(); //读取rdsede.stations的一行数据
       String[] cols = BaseSeedExpAction.parseLine(line);
       
       if(cols != null && cols.length > 8) {
         //从cols中得到通道数据
         channels.add(BaseSeedExpAction.fixChannel(cols[6]));
         channels.add(BaseSeedExpAction.fixChannel(cols[7]));
         channels.add(BaseSeedExpAction.fixChannel(cols[8]));
       }        
       
       logger.debug(Arrays.toString(cols));
     } while(StringUtils.isNotBlank(line)); 
     
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
   return channels;
 }
}
