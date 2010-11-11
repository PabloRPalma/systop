package quake.admin.seedpath.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.seedpath.model.Seedpath;
import quake.admin.seedpath.service.SeedpathManager;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.util.ResourceBundleUtil;
import com.systop.core.webapp.struts2.action.BaseAction;


/**
 * 波形文件存储路径及seed程序配置文件管理Action类
 * @author DU
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SeedpathAction extends BaseAction implements ModelDriven<Seedpath> {

  private Seedpath model = new Seedpath();
  
  /**
   * <code>Seedpath</code>管理类
   */
  @Autowired(required = true)
  private SeedpathManager manager;

  private String jsonResult;
  
  /**
   * 编辑或者新建Action
   */
  public String edit() {
    model = manager.get();
    if(model == null) {
      model = new Seedpath();
    }
    return INPUT;
  }
  
  /**
   * 保存波形文件路径，波形文件路径为seed程序所在路径
   * 波形文件路径保存成功后，修改seed程序运行所需要的配置文件
   * 根据系统中配置的文件的属性值进行修改，如：MySQL数据库的账号和密码，seed程序所在实际路径
   * 
   */
  public String save() {
    String path = model.getPath();
    if(!path.endsWith(File.separator)) {
      path = path + File.separator;
    }
    if(StringUtils.isBlank(path)) {
      addFieldError("czPath", "波形文件路径是必填项。");
      return INPUT;
    }
    File file = null;
    //如果不是绝对路径,则认为该目录在web目录下
    if(path.startsWith(File.separator) || path.charAt(1) == ':') { 
      file = new File(path);
      logger.debug("波形文件路径为绝对路径");
      model.setPath(path);
    } else {
      String realPath = getServletContext().getRealPath(path);
      file = new File(realPath);
      logger.debug("波形文件路径为相对路径");
      model.setPath(file.getAbsolutePath());
    }    
    if(!file.exists()) {
      addActionError("波形文件路径不存在，请输入已存在的路径。");
      return INPUT;
    }
    try {
      manager.save(model);
      addActionMessage("您已经成功设置波形文件存储路径.");
      manager.initConfigFile(model.getPath(), 
          getFromResourceBundle("jdbc.username", "root"),
          getFromResourceBundle("jdbc.password", "love125"));
      return SUCCESS;
    } catch (Exception e) {
      addActionError(e.getMessage());
    }
    return INPUT;
  }
  
  /**
   * 运行rd程序
   * 首先判断rd程序是否正在运行，如果正在运行则不再启动该程序，否则开始运行rd程序。
   */
  public String execSeedPro() {
    if(isRunning()) { //检查seed是否正在运行
      jsonResult = "seed程序正在运行,不可同时运行两个seed程序！";
      return "seedResult";
    }
    Seedpath seedpath = manager.get();
    if (seedpath != null) {
      String pro = seedpath.getPath() + "bin/rd.sh";
      try {
        File file = new File(pro);
        if (file.exists()) {
          logger.info("seed程序开始执行....");
          ProcessBuilder procBuilder = new ProcessBuilder(file.getAbsolutePath());
          procBuilder.redirectErrorStream(true); //合并输出子进程的standard和error inputstream
          logger.debug("合并子进程InputStream{}",procBuilder.redirectErrorStream());
          Process process = procBuilder.start();
          
          drainInBackground(process.getInputStream());
          /*
          BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String out = "";
          while((out = reader.readLine()) != null) {
            System.out.println(out);
          }
          */
          int exit = process.waitFor();
          logger.info("seed程序退出{}", exit);
          jsonResult = "seed解析程序运行成功！";
        } else {
          jsonResult = "seed程序未找到。";
        }
      } catch (IOException e) {
        e.printStackTrace();
        logger.error("程序：" + pro + " 执行错误...");
        jsonResult = "seed程序执行错误。" + e.getMessage();
      } catch (InterruptedException e) {
        e.printStackTrace();
        jsonResult = "seed程序非正常中断退出。" + e.getMessage();
      }
    } else {
      jsonResult = "未找到seed程序路径。";
    }
    return "seedResult";
  }
  
  /**
   * 判断rd.sh是否正在运行
   */
  static boolean isRunning() {
    ProcessBuilder procBuilder = new ProcessBuilder();
    procBuilder.redirectErrorStream(true);
    procBuilder.command("ps", "-C", "rd.sh");
    BufferedReader reader = null;
    try {
      Process process = procBuilder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String out = "";
      while((out = reader.readLine()) != null) {
        System.out.println(out);
        if(out.indexOf("rd.sh") >= 0) {
          return true;
        }
      }
      
      process.waitFor();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
    return false;
  }
  
  /**
   * 取得dao配置文件属性值
   * 
   * @param bundleName
   * @param defaultVale
   */
  private String getFromResourceBundle(String bundleName, String defaultVale) {
    return ResourceBundleUtil.getString(ResourceBundle.getBundle("dao"),  
        bundleName,  defaultVale);
  }
  
  /**
   * @return the model
   */
  public Seedpath getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(Seedpath model) {
    this.model = model;
  }

  public String getJosnResult() {
    return jsonResult;
  }

  public void setJosnResult(String josnResult) {
    this.jsonResult = josnResult;
  }
  
  /**
   * 启动一个线程，用于排空子进程的输出流，否则子进程会挂起
   */
  static void drainInBackground(final InputStream is) {
    new Thread(new Runnable() {
      public void run() {
        try {
          while (is.read() >= 0)
            ;
        } catch (IOException e) {
          // return on IOException
        }
      }
    }).start();
  }

}
