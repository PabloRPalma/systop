package quake.admin.seedpath.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import quake.admin.ds.service.DataSourceManager;
import quake.admin.seedpath.model.Seedpath;
import quake.base.service.Definable;

import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;


/**
 * 波形文件存储路径及seed程序配置文件管理类
 * @author DU
 *
 */
@SuppressWarnings("unchecked")
@Service
public class SeedpathManager implements Definable {

  private static Logger logger = LoggerFactory.getLogger(DataSourceManager.class);
  
  private BaseHibernateDao baseHibernateDao;
  
  /**
   * 数据源表主键（为了配合Hibernate）
   */
  public static final String PK = "seedpath";
  
  /**
   * 返回数据库中存储的波形文件存储路径信息
   */
  @Transactional(readOnly = true)
  public Seedpath get() {
    return baseHibernateDao.get(Seedpath.class, PK);
  }

  /**
   * 保存或者更新路径
   * @param dsInfo
   */
  @Transactional
  public void save(Seedpath seedpath) {
    Assert.notNull(seedpath);
    if (StringUtils.isBlank(seedpath.getId())) {
      seedpath.setId(PK);
    }
    if (get() != null) {
      baseHibernateDao.merge(seedpath);
    } else {
      baseHibernateDao.save(seedpath);
    }
  }
  
  /**
   * 修改seed目录下的dir配置文件
   * @param folderPath seed文件路径
   * @param userName 配置文件所用到的mysql用户名
   * @param password 配置文件所用到的mysql密码
   */
  public void initConfigFile(String folderPath,String userName,String password) {
    initDirFile(folderPath, userName, password);
    initRdFile(folderPath);
  }
  
  /**
   * 修改seed目录下的dir配置文件
   * @param folderPath seed文件路径
   * @param userName 配置文件所用到的mysql用户名
   * @param password 配置文件所用到的mysql密码
   */
  private void initDirFile(String folderPath,String userName,String password) {
    FileOutputStream dirFile = null;
    StringBuffer sbuf = new StringBuffer();
    try {
      dirFile = new FileOutputStream(folderPath + "bin/dir.cnf");
      sbuf.append(folderPath).append("appsoft/").append("\n")
      .append(folderPath).append("data/seed").append("\n")
      .append(folderPath).append("data/sac").append("\n")
      .append(folderPath).append("bin").append("\n")
      .append("host:localhost").append("\n")
      .append("database:eq").append("\n")
      .append("table:seed_plots").append("\n")
      .append("user:").append(userName).append("\n")
      .append("passwd:").append(password).append("\n");
      dirFile.write(sbuf.toString().getBytes());
    } catch (FileNotFoundException e) {
      logger.error("没有找到所要修改的配置文件...");
      //e.printStackTrace();
      throw new ApplicationException("没有找到所要修改的配置文件:" + folderPath + "bin/dir.cnf");
    } catch (IOException ex) {
      logger.error("读取配置文件出错，请重试!");
      //ex.printStackTrace();
      throw new ApplicationException("读取配置文件出错，请重试!");
    }finally {
      try {
        if (dirFile != null) {
          dirFile.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * 修改seed目录下的rd文件
   * @param folderPath seed文件路径
   */
  private void initRdFile(String folderPath) {
    BufferedReader reader = null;
    BufferedWriter writer = null;
    StringBuffer buffer = new StringBuffer();
    try {
      reader = new BufferedReader(new InputStreamReader (
          new FileInputStream(folderPath + "bin/rd.sh")));
      String line = reader.readLine();
      while(line != null) {
        if(!line.startsWith("#") && line.contains("set MYBIN")) {
          line = "set MYBIN="+"\""+folderPath +"bin\"";
        }
        buffer.append(line);
        buffer.append("\n");
        line = reader.readLine(); 
      }
      writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(folderPath + "bin/rd.sh")));
      writer.write(buffer.toString());
    } catch (FileNotFoundException e) {
      logger.error("没有找到所要修改的配置文件...");
      //e.printStackTrace();
      throw new ApplicationException("没有找到所要修改的配置文件:" + folderPath + "bin/rd.sh");
    } catch (IOException e) {
      logger.error("读取配置文件出错，请重试!");
      //e.printStackTrace();
      throw new ApplicationException("读取配置文件出错，请重试!");
    }finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * @param baseHibernateDao the baseHibernateDao to set
   */
  @Autowired(required = true)
  public void setBaseHibernateDao(BaseHibernateDao baseHibernateDao) {
    this.baseHibernateDao = baseHibernateDao;
  }
  
  @Override
  public boolean isDefined() {
    return get() != null;
  }
}
