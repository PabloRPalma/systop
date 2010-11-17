package quake.seismic.data.seed.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
  /**
   * rdseed的参数
   */
  private String[] args = new String[] {"quit","\n","\n","d\n",
      "\n","\n","\n","\n","\n","\n","\n","\n",
      "\n","\n","\n","N\n","quit\n"};
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
      //10-Strip out records with zero sample count? [Y/N]? [Y/N]
      //11-Select Data Type [(E=Everything), D=Data of Undetermined State, R=Raw waveform Data, Q=QC'd data] :
      //12-Start Time(s) YYYY,DDD,HH:MM:SS.FFFF :
      //13-End Time(s)   YYYY,DDD,HH:MM:SS.FFFF :
      //14 Sample Buffer Length [2000000]:
      //15-Extract Responses [Y/(N)]
      //16-Input  File (/dev/nrst0) or 'Quit' to Exit: QUit      
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
    if(ArrayUtils.isNotEmpty(stations)) {
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
      readString(reader, writer);
      for(String arg : args) {
        logger.debug(arg);
        writer.write(arg);
        writer.flush();
      }
      int exit = process.waitFor();
      logger.info("rdseed 执行完毕 [{}]", exit);
      
      if(exit == 0) {
        download(); //下载数据  
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
      
      rmOutput();
    }
    return null;
  }  
  
  /**
   * 删除输出目录
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
  
  private void download() {
    File expFile = new File(exeDir.getAbsolutePath() + "/seed.rdseed"); 
    if(!expFile.exists()) {
      render(getResponse(), "暂无数据", "text/html");
      return;
    }
    
    getResponse().setContentType("application/x-download");
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + seed + "\"");
    FileChannelUtil fcu = new FileChannelUtil();
    try {
      fcu.read(expFile, getResponse().getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void readString(final Reader reader, final Writer writer) throws Exception {
    new Thread(new Runnable() {
      public void run() {
        try {
          char [] buf = new char[300];
          while (reader.read(buf) >= 0) {
            System.out.println(buf);
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
}
