package quake.seismic.data.seed.webapp;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import quake.admin.seedpath.service.SeedpathManager;

import com.systop.core.util.FileChannelUtil;
import com.systop.core.webapp.struts2.action.BaseAction;

public abstract class BaseSeedExpAction extends BaseAction {
  /**
   * <code>Seedpath</code>管理类
   */
  @Autowired(required = true)
  protected SeedpathManager seedpathManager;
  
  /**
   *通道列表， 用逗号分隔
   */
  protected String[] channels;
  
  /**
   * 输出格式
   */
  protected String format;
  
  /**
   * rdseed工作路径
   */
  protected File workDir;
  
  /**
   * rdseed命令
   */
  protected String cmd;
  
  
  /**
   * 波形文件目录
   */
  protected String seedPath;  
  /**
   * rdseed的参数，第9步之后多输入几个，以适应不同的输出格式
   */
  protected String[] args = new String[] {"quit","\n","\n","d\n",
        "\n","\n","\n","\n","\n","\n","\n","\n",
        "\n","\n","\n","\n","\n","N\n","quit\n"};
 
  
  public static final Map<String, String> OUTPUT_FORMAT 
    = new LinkedHashMap<String, String>(8);
    
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
  
 public static final Map<String, String> OUTPUT_POSTFIX 
   = new LinkedHashMap<String, String>(8);
  
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
   * 删除输出目录，包括rdseed生成的文件和打包的文件
   */
  protected void rmOutput(File dir) {
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.redirectErrorStream(true);
    procBuilder.command("rm", "-r", dir.getAbsolutePath());
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
  protected File tar(File target, String output, String input) {
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
  protected void download(File dir, String format) {
    if(!OUTPUT_FORMAT.containsKey(format)) {
      render(getResponse(), "输出格式不支持" + format, "text/html");
      return;
    }
    String filename = OUTPUT_FORMAT.get(format).toString() + ".tar.gz";
    //将生成的文件进行打包
    File expFile = tar(dir, filename, 
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
   * 删除rdseed产生的log文件
   */
  protected void rmRdseedLog(File dir) {
    if(!dir.exists()) {
      return;
    }
    File[] fileFirsts = dir.listFiles();
    Set<File> logs = new HashSet<File>();
    for(File file : fileFirsts) {
      if(file.isFile() && file.getName().startsWith("rdseed.err_log")) {
        logs.add(file);
      } else if(file.isDirectory()) {
        File[] fileSecs = file.listFiles();
        for(File f : fileSecs) {
          if(f.isFile() && f.getName().startsWith("rdseed.err_log")) {
            logs.add(f);
          }
        }
      }
    }
    for(File file : logs) {
      file.delete();
    }
  }

  /**
   * 多线程读取rdseed输出的信息，使得rdseed可以正确运行
   */
  protected void readString(final Reader reader, final boolean print) throws Exception {
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

  public String[] getChannels() {
    return channels;
  }

  public void setChannels(String[] channels) {
    this.channels = channels;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

}