package datashare.admin.counter.webapp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.counter.model.SetCounter;
import datashare.admin.counter.service.SetCounterManager;

@SuppressWarnings({"unchecked","serial"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SetCounterAction extends DefaultCrudAction<SetCounter, SetCounterManager> {

  Logger logger = LoggerFactory.getLogger(SetCounterAction.class);
  @Autowired(required = true)
  private JdbcTemplate jdbcTemplate;
  private String beginDate;

  private String endDate;

  /**
   * 统计网站点击率
   */
  public String index() {
    StringBuffer sql = new StringBuffer("select DATE_FORMAT(VISITE_TIME,'%Y-%m') month, count(ID) hits from set_counter where 1=1");
    if (beginDate != null && endDate != null) {
      sql.append(" and DATE_FORMAT(VISITE_TIME,'%Y-%m')>=? and DATE_FORMAT(VISITE_TIME,'%Y-%m')<=?");
    }
    sql.append(" group by month order by DATE_FORMAT(VISITE_TIME,'%Y-%m')");
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
