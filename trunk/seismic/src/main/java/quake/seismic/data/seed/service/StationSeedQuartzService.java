package quake.seismic.data.seed.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quake.admin.seedpath.model.Seedpath;
import quake.admin.seedpath.service.SeedpathManager;
import quake.seismic.data.seed.model.StationSeed;

import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.webapp.struts2.action.BaseAction;

@Service
public class StationSeedQuartzService extends BaseAction {
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
        parseSeed(seed, seedPath);
      }
    }
    
    
  }
  
  /**
   * 执行rdseed -f seed -t,并解析执行结果，保存在数据库中
   * @param seed
   */
  void parseSeed(File seed, Seedpath seedPath) {
    //构建rdseed路径
    String command = seedPath.getPath() + "appsoft/rdseed";
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
    procBuilder.command(command, "-f", seed.getAbsolutePath(), "-t");
    Process process;
    try {
      process = procBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      doOneRec(reader, seed.getName(), false); //处理单个seed
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
  private void doOneRec(final BufferedReader reader, final String seed, final boolean print) {
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
                  doOneLine(line.trim(), seed);
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
    
    StationSeed sa = new StationSeed(cols, seed);
    dao.save(sa);
  }
  
  /**
   * 如果波形文件没有被记录，返回ture
   */
  private boolean isNoRecord(File seed) {
    String hql = "from StationSeed ss where ss.seed=?";
    return dao.findObject(hql, seed.getName()) == null;
  }
}
