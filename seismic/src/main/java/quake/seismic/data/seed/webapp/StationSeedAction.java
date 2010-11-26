package quake.seismic.data.seed.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.seismic.data.seed.model.StationSeed;

import com.opensymphony.xwork2.ModelDriven;
import com.systop.core.dao.hibernate.BaseHibernateDao;
import com.systop.core.webapp.struts2.action.BaseAction;
/**
 * 连续波形导出
 * @author sam
 *
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StationSeedAction extends BaseAction implements ModelDriven<StationSeed> {
  @Autowired
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;
  
  private StationSeed model = new StationSeed();
  
  private String[] channels; 
  /**
   * 输出格式
   */
  private String format;
  
  public String export() {
    List<String> seedNames = getSeedNames();
    if(CollectionUtils.isEmpty(seedNames)) {
      render(getResponse(), "没有找到符合条件的seed文件.", "text/plain");
      return null;
    }
    logger.debug("需要处理的连续波形文件包括:{}", seedNames.toString());
    return null;
  }
  
  /**
   * 查询符合条件的连续波形Seed文件名称
   * @return
   */
  private List<String> getSeedNames() {
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
    
    return dao.query(hql.toString(), args.toArray(new Object[]{}));
  }
  
  @Override
  public StationSeed getModel() {
    return model;
  }
  
  public void setModel(StationSeed model) {
    this.model = model;
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
