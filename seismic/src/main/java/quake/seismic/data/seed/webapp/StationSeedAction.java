package quake.seismic.data.seed.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import quake.seismic.data.seed.model.StationSeed;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.util.DateUtil;
/**
 * 连续波形导出
 * @author sam
 *
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StationSeedAction extends BaseSeedExpAction 
  implements ModelDriven<StationSeed>, Preparable {
  @Autowired
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;
  
  private StationSeed model = new StationSeed();
  
  /**
   * 初始化连续波形文件目录和rdseed工作目录
   */
  @PostConstruct
  public void init() {
    seedPath = seedpathManager.get().getPath() + "data/staseed/";
    workDir = new File(seedPath + RandomStringUtils.randomNumeric(5) + "/");
   
    if(!workDir.exists()) {
      workDir.mkdirs();
    }
    cmd = seedpathManager.get().getPath() + "appsoft/rdseed";
    logger.debug("连续波形目录'{}'", seedPath);
    logger.debug("rdseed工作目录'{}'", workDir.getAbsolutePath());
  }
  
  /**
   * 设置rdseed执行过程中的参数
   * @see EventExportAction#prepare()
   */
  @Override
  public void prepare() throws Exception {
    //通道列表
    if(!ArrayUtils.isEmpty(channels)) {
      args[6] = StringUtils.arrayToCommaDelimitedString(channels) + "\n";
    }
    //输出格式
    if(StringUtils.hasText(format)) {
      args[9] = format + "\n";    
    }
    //时间输入的位置,缺省索引是12
    int tmIdx = 12;
    //1标示输出SAC格式
    if(!StringUtils.hasText(format) || "1".equals(format)) {
      tmIdx = 14;
    }
    //2标示输出AH格式
    if("2".equals(format)) {
      tmIdx = 13;
    }
    if(model.getStartTime() != null) {
      args[tmIdx] = DateUtil.getDateTime("yyyy,DDD,HH:mm:ss", model.getStartTime())
       + ".0001\n"; 
    }
    if(model.getEndTime() != null) {
      args[tmIdx + 1] = DateUtil.getDateTime("yyyy,DDD,HH:mm:ss", model.getEndTime())
       + ".0001\n"; 
    }
    
  }
  
  public String export() {
    Set<String> seedNames = getSeedNames();
    if(CollectionUtils.isEmpty(seedNames)) {
      render(getResponse(), "没有找到符合条件的seed文件.", "text/plain");
      return null;
    }
    logger.debug("需要处理的连续波形文件包括:{}", seedNames.toString());
    
    for(String seedName : seedNames) {
      exportSingleSeed(seedName);
    }
    try {
      rmRdseedLog(workDir);
      download(workDir, format);
    } finally {
      rmOutput(workDir);
    }
    return null;
  }
  
  
  /**
   * 查询符合条件的连续波形Seed文件名称
   * @return
   */
  private Set<String> getSeedNames() {
    StringBuffer hql = new StringBuffer(
        "select s.seed from StationSeed s where s.net=? and s.sta=?");
    List<Object> args = new ArrayList<Object>();
    args.add(model.getNet());
    args.add(model.getSta());
    
    if(!ArrayUtils.isEmpty(channels)) {
      hql.append(" and s.cha in ")
      //这里用IN查询，很土的办法...
      .append(toJson(channels).replace("\"", "'").replace("[", "(").replace("]", ")"));
      //args.add(channels);
    }
    
    if(model.getStartTime() != null) {
      hql.append(" and s.startTime >= ?");
      args.add(model.getStartTime());
    }
    
    if(model.getEndTime() != null) {
      hql.append(" and s.endTime <= ?");
      args.add(model.getEndTime());
    }
    
    List<String> list = dao.query(hql.toString(), args.toArray(new Object[]{}));
    Set<String> s = new HashSet<String>(list.size());
    for(String name : list) {
      s.add(name);
    }
    return s;
  }
  
  /**
   * 处理单个Seed文件
   * @param seedName
   */
  public void exportSingleSeed(String seedName) {
    //构建seed文件的完整路径
    String seed = seedPath + seedName + "\n";
    args[0] = seed; //Input  File (/dev/nrst0) or 'Quit' to Exit
    BufferedReader reader = null;
    BufferedWriter writer = null;
    
    try {
      File rdseed = new File(cmd);
      if(!rdseed.exists()) {
        logger.warn("rdseed 不存在。");
        return;
      }
      ProcessBuilder procBuilder = new ProcessBuilder();
      //在工作目录下为每一个seed建立工作目录
      File currWorkPath = new File(workDir.getAbsolutePath() + "/" 
        + seedName.substring(0, seedName.lastIndexOf(".")) + "/");
      currWorkPath.mkdirs();
      procBuilder.directory(currWorkPath); //设置执行目录
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
        logger.info("连续波形文件{}处理完毕", seedName);
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
    }
  }
  

  
  @Override
  public StationSeed getModel() {
    return model;
  }
  
  public void setModel(StationSeed model) {
    this.model = model;
  }
 
}
