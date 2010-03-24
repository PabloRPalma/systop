package datashare.admin.counter.webapp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.counter.model.DownCounter;
import datashare.admin.counter.service.DownCounterManager;

@SuppressWarnings({"unchecked","serial"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DownCounterAction extends DefaultCrudAction<DownCounter,DownCounterManager> {

  
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  private String beginDate;
  
  private String endDate;
  
  /**
   * 统计网站在线数据下载量
   */
  public String index(){
    StringBuffer sql = new StringBuffer("select DATE_FORMAT(TIME,'%Y-%m') month, count(id) hits from down_counter where 1=1");
    if (beginDate != null && endDate != null) {
      sql.append(" and DATE_FORMAT(TIME,'%Y-%m')>=? and DATE_FORMAT(TIME,'%Y-%m')<=?");
    }
    sql.append(" group by month order by DATE_FORMAT(TIME,'%Y-%m')");
    List list = null;
    
    if(beginDate != null && endDate != null){
      list = jdbcTemplate.queryForList(sql.toString(), new Object[] {beginDate,endDate});
    }else {
      list = jdbcTemplate.queryForList(sql.toString());
    }
    getRequest().setAttribute("items", list);

    return "index";
  }
  
  public String getBeginDate() {
    return beginDate;
  }
  
  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }
  
  public String getEndDate() {
    return endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
