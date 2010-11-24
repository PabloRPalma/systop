package quake.seismic.data.seed.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import quake.admin.seedpath.model.Seedpath;
import quake.admin.seedpath.service.SeedpathManager;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.ArrayUtils;
import com.systop.core.util.FileChannelUtil;
import com.systop.core.webapp.struts2.action.BaseAction;

/**
 * 事件波形导出Action
 * @author sam
 *
 */

@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EventExportAction extends BaseAction implements Preparable{
  /**
   * <code>Seedpath</code>管理类
   */
  @Autowired(required = true)
  private SeedpathManager manager;
  
  /**
   * 全部台站
   */
  private String allStations;
  
  /**
   * 台站列表，用逗号分隔
   */
  private String[] stations;
  
  /**
   *通道列表， 用逗号分隔
   */
  private String[] channels;
  
  /**
   * Seed文件
   */
  private String seed;
  
  /**
   * rdseed执行路径
   */
  private File exeDir;
  
  /**
   * 输出格式
   */
  private String format;
  
  public static final Map OUTPUT_FORMAT = new LinkedHashMap(8);
  
  static {
    //(1=SAC), 2=AH, 3=CSS, 4=mini seed, 5=seed, 6=sac ascii, 7=SEGY
    OUTPUT_FORMAT.put("1", "SAC");
    OUTPUT_FORMAT.put("2", "AH");
    OUTPUT_FORMAT.put("3", "CSS");
    OUTPUT_FORMAT.put("4", "mini seed");
    OUTPUT_FORMAT.put("5", "seed");
    OUTPUT_FORMAT.put("6", "sac ascii");
    OUTPUT_FORMAT.put("7", "SEGY");
  }
  
 public static final Map OUTPUT_POSTFIX = new LinkedHashMap(8);
  
  static {
    //(1=SAC), 2=AH, 3=CSS, 4=mini seed, 5=seed, 6=sac ascii, 7=SEGY
    OUTPUT_POSTFIX.put("1", "*.SAC");
    OUTPUT_POSTFIX.put("2", "*.AH");
    OUTPUT_POSTFIX.put("3", "*");
    OUTPUT_POSTFIX.put("4", "*");
    OUTPUT_POSTFIX.put("5", "seed.rdseed");
    OUTPUT_POSTFIX.put("6", "*.SAC_ASC");
    OUTPUT_POSTFIX.put("7", "*.SEGY");
  }
  /**
   * rdseed的参数，第9步之后多输入几个，以适应不同的输出格式
   */
  private String[] args = new String[] {"quit","\n","\n","d\n",
      "\n","\n","\n","\n","\n","\n","\n","\n",
      "\n","\n","\n","\n","\n","N\n","quit\n"};
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
    Seedpath seedPath = manager.get();
    if (seedPath == null) {
      logger.warn("Seed 路径未设置！");
      return;
    }
    //构建rdseed执行路径，用于存放执行结果
    String dir = seedPath.getPath() + "data/seed/" + RandomStringUtils.randomNumeric(10);
    exeDir = new File(dir);
    exeDir.mkdirs();
    
    //构建seed文件的完整路径
    seed = seedPath.getPath() + "data/seed/" + seed + "\n";
    args[0] = seed; //Input  File (/dev/nrst0) or 'Quit' to Exit
    //台站列表
    logger.debug("Export all stations {}", StringUtils.hasText(allStations));
    if(ArrayUtils.isNotEmpty(stations) && !StringUtils.hasText(allStations)) {
      args[5] = StringUtils.arrayToCommaDelimitedString(stations) + "\n";
    }
    //通道列表
    if(ArrayUtils.isNotEmpty(channels)) {
      args[6] = StringUtils.arrayToCommaDelimitedString(channels) + "\n";
    }
    //输出格式
    if(StringUtils.hasText(format)) {
      args[9] = format + "\n";    
    }
    logger.info("rdseed args {}", Arrays.toString(args));
  }
  
  /**
   * 导出地震数据并下载
   * @return
   */
  public String export() {   
    Seedpath seedPath = manager.get();
    if (seedPath == null) {
      logger.warn("Seed 路径未设置！");
      return null;
    }
    
    String cmd = seedPath.getPath() + "appsoft/rdseed";
    
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      File rdseed = new File(cmd);
      if(!rdseed.exists()) {
        logger.warn("rdseed 不存在。");
        return null;
      }
      ProcessBuilder procBuilder = new ProcessBuilder();
      procBuilder.directory(exeDir); //设置执行目录
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
        download(); //打包下载数据  
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
      
      rmOutput(); //删除输出目录，包括rdseed生成的文件和打包的文件
    }
    return null;
  }  
  
  /**
   * 删除输出目录，包括rdseed生成的文件和打包的文件
   */
  private void rmOutput() {
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.redirectErrorStream(true);
    procBuilder.command("rm", "-r", exeDir.getAbsolutePath());
    try {
      Process process = procBuilder.start();
      process.waitFor();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 打包生成的文件
   */
  public File tar(File target, String output, String input) {
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.directory(target.getParentFile());
    procBuilder.redirectErrorStream(true);
    
    procBuilder.command("tar", "-czf", output, target.getName(), input);
    
    try {
      Process process = procBuilder.start();
      process.waitFor();
      if(true) {
        return new File(target.getParentFile().getAbsolutePath() + "/" + output);
      }  
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * 打包生成的文件，并下载
   */
  private void download() {
    if(!OUTPUT_FORMAT.containsKey(format)) {
      render(getResponse(), "输出格式不支持" + format, "text/html");
      return;
    }
    String filename = OUTPUT_FORMAT.get(format).toString() + ".tar.gz";
    //将生成的文件进行打包
    File expFile = tar(exeDir, filename, 
        OUTPUT_POSTFIX.get(format).toString());
    
    if(expFile == null || !expFile.exists()) {
      render(getResponse(), "暂无数据", "text/html");
      return;
    }
    
    getResponse().setContentType("application/x-download");
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
    FileChannelUtil fcu = new FileChannelUtil();
    try {
      fcu.read(expFile, getResponse().getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      expFile.delete();
    }
  }
  
  /**
   * 多线程读取rdseed输出的信息，使得rdseed可以正确运行
   */
  private void readString(final Reader reader, final boolean print) throws Exception {
    new Thread(new Runnable() {
      public void run() {
        try {
          char [] buf = new char[300];
          while (reader.read(buf) >= 0) {
            if(print)  System.out.println(buf);
          }
        } catch (IOException e) {
          // return on IOException
        }
      }
    }).start();
  }
  
 

  public String[] getStations() {
    return stations;
  }



  public void setStations(String[] stations) {
    this.stations = stations;
  }



  public String[] getChannels() {
    return channels;
  }



  public void setChannels(String[] channels) {
    this.channels = channels;
  }

  public String getSeed() {
    return seed;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
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
}
