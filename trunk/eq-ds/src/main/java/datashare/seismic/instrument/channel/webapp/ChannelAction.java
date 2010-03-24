package datashare.seismic.instrument.channel.webapp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.dao.support.Page;

import datashare.admin.ds.service.DataSourceManager;
import datashare.base.webapp.AbstractQueryAction;
import datashare.seismic.SeismicConstants;
import datashare.seismic.instrument.channel.dao.ChannelDao;
import datashare.seismic.instrument.channel.model.Channel;
import datashare.seismic.station.model.Criteria;

/**
 * 测震台站通道参数查询Action
 * @author du
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings({"unchecked","serial"})
public class ChannelAction extends AbstractQueryAction<Channel> {
  
  /**
   * 用于表格查询的通道参数DAO
   */
  @Autowired(required = true)
  @Qualifier("channelDao")
  private ChannelDao channelDao;
  
  /**
   * 用于获取Schema
   */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;
  
  /**
   * 测震通道参数查询条件类
   */
  private Channel model = new Channel();

  /**
   * 表格查询Action
   */
  public String list() {
    try {
      model.setPage(getPage());
      model.setSortProperty(getSortProperty());
      model.setSortDir(getSortDir());
      //测震SCHEMA
      model.setSchema(dataSourceManager.getCzSchema());
      /*不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
      model.setNetCode(SeismicConstants.NETWORK_INFO_HE);*/
      Page page = channelDao.queryChannelInfo(model);
      if (page != null) {
        getRequest().setAttribute("items", page.getData());
        restorePageData(page.getRows(), page.getPageSize());
      } else {
        clean(); //即使没有查到数据，也要设置页面上的分页信息，确保显示的结果正确
      }
    } catch (Exception e) {
      logger.error("数据查询错误{}", e.getMessage());
      e.printStackTrace();
      clean();
    }
    return "list";
  }
  
  /**
   * 台网代码名称Map
   */
  public Map<String, String> getNetworkInfoMap() {
    return SeismicConstants.NETWORK_INFO;
  }
  
  /**
   * 测震台站map,页面上显示的下拉列表
   */
  public List<Map> getStationInfoMap() {
    Criteria stationInfo = new Criteria();
    /*不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
    stationInfo.setNetCode(SeismicConstants.NETWORK_INFO_HE);*/
    stationInfo.setSchema(dataSourceManager.getCzSchema());
    return channelDao.getAllStationInfo(stationInfo);
    
  }
  
  /**
   * 测震台站Map数据
   */
  public Map<String, String> getStaNameAndCodeMap() {
    Criteria stationInfo = new Criteria();
    /*不同省份有不同的台网代码，暂时不考虑台网问题,即使考虑台网，如何取得有待确定....
    stationInfo.setNetCode(SeismicConstants.NETWORK_INFO_HE);*/
    stationInfo.setSchema(dataSourceManager.getCzSchema());
    return channelDao.getStationInfoMap(stationInfo);
  }
  
  /**
   * @return the model
   */
  public Channel getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(Channel model) {
    this.model = model;
  }

}
