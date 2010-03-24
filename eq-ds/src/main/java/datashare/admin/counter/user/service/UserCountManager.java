package datashare.admin.counter.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.service.BaseGenericsManager;

@SuppressWarnings({"unchecked"})
@Service
public class UserCountManager extends BaseGenericsManager<User> {
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * 获取用户角色类别
   * @return
   */
  public List<Map> queryRoles(){
    String hql = "select level,count(*) as c from users where login_id <> 'admin' and user_type =? group by level";
    
    List<Map> list = jdbcTemplate.queryForList(hql,new Object[]{UserConstants.USER_TYPE_REGIST});
   
    return list;
  }
}
