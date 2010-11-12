package quake.seismic.data.seed.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.admin.ds.service.DataSourceManager;
import quake.admin.seedpath.service.SeedpathManager;
import quake.base.webapp.AbstractQueryAction;
import quake.seismic.data.catalog.model.Criteria;
import quake.seismic.data.seed.dao.impl.SeedDao;
import quake.seismic.data.seed.model.StaCriteria;

/**
 * Seed事件波形Action
 * @author dhm
 *
 */
@SuppressWarnings( { "serial", "unchecked" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SeedAction extends AbstractQueryAction<Criteria> {
  
  private Criteria model = new Criteria();
  
  /**
   * 用于接收用户选择的需要查看的SEED图片ID
   */
  private String id;
  
  @Autowired
  @Qualifier("seedDao")
  private SeedDao seedDao;
  
  /**
   * 数据源Manager，用于获取测震schema
   */
  @Autowired
  private DataSourceManager dataSourceManager;

  /**
   * seed存储路径Manager
   */
  @Autowired
  private SeedpathManager seedpathManager;
  
  /**
   * 获取系统路径分隔符(Windows "\"，Linux "/")
   */
  private String separator = System.getProperties().getProperty("file.separator");

  /**
   * 显示Seed文件解析内容,各台站监测事件数据
   */
  public String showSeed() {
    String seedName = getRequest().getParameter("seedname");
    StringBuffer seedFile = new StringBuffer("");
    //获取Seed文件存储路径
    if (seedpathManager.get()!= null && seedpathManager.get().getPath() != null) {
      seedFile.append(seedpathManager.get().getPath()).append("data").append(separator).append(
          "seed").append(separator).append(seedName);
    }
    List<Map<String, Object>> items = seedDao.querySeedPlotsData(seedFile.toString());
    StaCriteria criteria = new StaCriteria();
    criteria.setSchema(dataSourceManager.getStationSchema());
    // 遍历所有的事件，取出对应Seed文件名，并将台站代码替换为台站中文名
    for (Map map : items) {
      String seedFileName = "";
      if(map.get("SeedFile") != null) {
        seedFileName = map.get("SeedFile").toString();
      }
      if (seedFileName.lastIndexOf(separator) >= 0) {
         map.put("SeedFile", seedFileName.substring(seedFileName.lastIndexOf(separator) + 1));// 截取文件名，页面只显示名称不显示路径
      } else {
         map.put("SeedFile", seedFileName);
      }
      
      //获取台站代码  台站代码由台网代码 + . + 台站代码构成  如 河北省无极  表示为 HB.WUJ
      String station = "";
      if(map.get("Station") != null){
        station = map.get("Station").toString();
      
        logger.debug("stationCode:" + station);      
        criteria.setNetCode(station.substring(0, station.lastIndexOf(".")));
        criteria.setStaCode(station.substring(station.lastIndexOf(".") + 1));
        logger.debug("criteria:" + criteria.getNetCode() + "." + criteria.getStaCode());
        station = seedDao.queryStaName(criteria);// 获取台站中文名
      }
      if(StringUtils.isNotBlank(station)) {
        map.put("Station", station);
      }
    }
    getRequest().setAttribute("items", items);
    return "show";
  }

  /**
   * 显示事件波形图，获取用户所选择的所有事件波形ID
   */
  public String view() {
    if (id != null) {
      String[] ids = id.split(",");
      List<Map<String, Object>> list = seedDao.queryMapFiles(ids);
      for (Map map : list) {
        String seedUrl = "";
        if(map.get("SeedFile") != null){
          seedUrl = map.get("SeedFile").toString();
        }
        seedUrl = seedUrl.substring(seedUrl.lastIndexOf(separator) + 1);
        map.put("SeedFile", seedUrl);
      }
      getRequest().setAttribute("items", list);
    }
    return "view";
  }

  /**
   * 输出图片流
   */
  public String jpgUrl() {
    String path = getRequest().getParameter("url");
    if(StringUtils.isNotBlank(path)){
      File jpg = new File(path);
      FileInputStream in = null;
      int len = 10 * 1024 * 1024;
      try {
        in = new FileInputStream(jpg);
        len = in.available();
        getResponse().setContentType("image/jpg");// 图片格式有待商榷
        byte[] by = new byte[len];
        int i;
        if ((i = in.read(by, 0, len)) != -1) {
          getResponse().getOutputStream().write(by, 0, i);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * SEED文件下载
   */
  public String down() {
    getResponse().setContentType("application/x-download");
    String seedname = getRequest().getParameter("seedname");
    getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + seedname + "\"");
    FileInputStream in = null;

    String path = "";
    if(seedpathManager.get() != null && seedpathManager.get().getPath() != null){
      path = new StringBuffer(seedpathManager.get().getPath()).append("data").append(separator)
        .append("seed").append(separator).toString();
      try {
        final int BUFFER = 4096;
        byte[] dataBlock = new byte[BUFFER];
        in = new FileInputStream(new File(path + seedname));
        int ch;
        while ((ch = in.read(dataBlock, 0, BUFFER)) != -1) {
          getResponse().getOutputStream().write(dataBlock, 0, ch);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public Criteria getModel() {
    return model;
  }

  public void setModel(Criteria model) {
    this.model = model;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
