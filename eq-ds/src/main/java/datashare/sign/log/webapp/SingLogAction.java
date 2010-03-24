package datashare.sign.log.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import datashare.admin.ds.service.DataSourceManager;
import datashare.base.webapp.AbstractQueryAction;
import datashare.sign.log.dao.SignLogDao;
import datashare.sign.log.model.LogCriteria;
import datashare.sign.support.SignCommonDao;

@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SingLogAction extends AbstractQueryAction<LogCriteria> {

  @Autowired(required = true)
  private SignLogDao signLogDao;

  //查询名称
  @Autowired(required = true)
  private SignCommonDao signCommonDao;

  //获得SCHEMA
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /**
   * 模型驱动，查询条件
   */
  private LogCriteria model = new LogCriteria();

  /**
   * 查看日志信息列表
   * 
   * @return
   */
  public String list() {
    model.setSchema(dataSourceManager.getQzSchema());
    model.setMethodId(model.getItemId().substring(0, 3));
    List items = signLogDao.queryLog(model);
    if (items == null) {
      items = java.util.Collections.EMPTY_LIST;
    }
    getRequest().setAttribute("items", items);

    //设置stationName
    String stationName = signCommonDao.getStationName(model.getStationId());
    getRequest().setAttribute("stationName", "(" + model.getStationId() + ")" + stationName);
    //设置测点名称
    String pointName = signCommonDao.getPointName(model.getStationId(), model.getPointId());
    getRequest().setAttribute("pointName", "(" + model.getPointId() + ")" + pointName);
    //设置测项分量名称
    String itemName = signCommonDao.getItemName(model.getItemId());
    getRequest().setAttribute("itemName", "(" + model.getItemId() + ")" + itemName);
    return "list";
  }

  @Override
  public LogCriteria getModel() {
    return model;
  }

  /**
   * @return the signLogDao
   */
  public SignLogDao getSignLogDao() {
    return signLogDao;
  }

  /**
   * @param signLogDao the signLogDao to set
   */
  public void setSignLogDao(SignLogDao signLogDao) {
    this.signLogDao = signLogDao;
  }

  /**
   * @return the dataSourceManager
   */
  public DataSourceManager getDataSourceManager() {
    return dataSourceManager;
  }

  /**
   * @param dataSourceManager the dataSourceManager to set
   */
  public void setDataSourceManager(DataSourceManager dataSourceManager) {
    this.dataSourceManager = dataSourceManager;
  }

}
