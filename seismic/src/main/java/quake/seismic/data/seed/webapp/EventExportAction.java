package quake.seismic.data.seed.webapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.seedpath.service.SeedpathManager;

import com.systop.core.webapp.struts2.action.BaseAction;

/**
 * 事件波形导出Action
 * @author sam
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EventExportAction extends BaseAction {
  /**
   * <code>Seedpath</code>管理类
   */
  @Autowired(required = true)
  private SeedpathManager manager;
  
  public String export() {
    /*
    Seedpath seedPath = manager.get();
    if (seedPath == null) {
      logger.warn("Seed 路径未设置！");
      return null;
    }*/
    
    //String cmd = seedPath.getPath() + "appsoft/rdseed";
    String cmd = "/home/sam/dev/seed/appsoft/rdseed";
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      File rdseed = new File(cmd);
      if(!rdseed.exists()) {
        logger.warn("rdseed 不存在。");
        return null;
      }
      ProcessBuilder procBuilder = new ProcessBuilder();
      procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
      procBuilder.command(rdseed.getAbsolutePath());
      
      Process process = procBuilder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
      readString(reader, writer);
      writer.write("/home/sam/dev/seed/data/seed/HE.200912312225.0001.seed\n");
      writer.flush();
      writer.write("/home/sam/dev/seed/xx.seed\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("d\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("5\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("\n");
      writer.flush();
      writer.write("Quit\n");
      writer.flush();
      //CharBuffer buf = CharBuffer.allocate(300);
      //int bytes = reader.read(buf);
      //System.out.println(buf.toString());
      //writer.write("/home/sam/dev/seed/data/seed/HE.200912312225.0001.seed");
      //writer.flush();
      //bytes = reader.read(buf);
      //System.out.println(buf.toString());
      
      int exit = process.waitFor();
      System.out.println(exit);
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
    return null;
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
  
  public static void main(String args[]) {
    EventExportAction eea = new EventExportAction();
    eea.export();
  }
}
