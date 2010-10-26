package quake.special.webapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import quake.special.dao.SpecialDao;
import quake.special.model.Criteria;
import quake.special.model.Special;
import quake.special.service.SpecialManager;

import com.systop.core.ApplicationException;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;


/**
 * 专题Action
 * 
 * @author yj
 * 
 */
@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SpecialAction extends ExtJsCrudAction<Special, SpecialManager> {
 
  private Criteria criteria = new Criteria();
  
  private static final String TEMPLATE_REMOVED_QC = "TEMPLATE_REMOVED_QC";

  private static final String TEMPLATE_ADDED_QC = "TEMPLATE_ADDED_QC";
 
  @Autowired(required = true)
  @Qualifier("specialDao")
  private SpecialDao specialDao;
  /**
   * 地震目录查询
   * 
   * @return
   */
  public String index() {

    return "index";
  }

  /**
   * 初始化Special实例
   */
  /*
  public void initSpecial() {
    if (getModel() == null || getModel().getId() == null) {
      throw new ApplicationException("Please select special to assign to");
    }
    getModel() = getManager().getSpecialById(special.getId());
    if (special == null) {
      throw new ApplicationException("Please select special to assign to");
    }
  }
*/
  /**
   * 客户端通过radio选择一个地震目录，提交服务器端，将这个存入Session
   * 
   * @return
   */
  public String selectQc() {

    if (getModel() == null || getModel().getId() == null || getCriteria() == null
        || getCriteria().getId() == null) {
      throw new ApplicationException("Please select a qc at least.");
    }
    selectQc(getModel().getId(), Integer.valueOf(getCriteria().getId()), true);

    return JSON;
  }

  /**
   * 页面通过AJAX方式把地震目录Id和专题地震Id传入服务器。
   * 
   * @param specialId 专题地震Id
   * @param qcId 地震目录Id
   * @param selected
   */
  private void selectQc(Integer specialId, Integer qcId, boolean selected) {
    if (specialId == null || qcId == null) {
      return;
    }
    if (getCriteria() == null || getModel() == null) {
      return;
    }
    if (selected) {
      temporaryAddQc(getModel(), getCriteria());
    } else {
      temporaryRemoveQc(getModel(), getCriteria());
    }
  }

  /**
   * 在Session中暂存一个已分配的地震目录 Id
   * 
   * @param special 专题地震
   * @param criteria 添加 地震目录
   */
  private void temporaryAddQc(Special special, Criteria criteria) {
    Set qcAdded = getTemporaryQc(special, TEMPLATE_ADDED_QC);
    qcAdded.add(criteria.getId());
    Set memberRemoved = getTemporaryQc(special, TEMPLATE_REMOVED_QC);
    memberRemoved.remove(criteria.getId());
  }

  /**
   * 在Session中暂存一个已分配的地震目录 Id
   * 
   * @param special 专题地震
   * @param criteria 删除 地震目录
   */
  public void temporaryRemoveQc(Special special, Criteria criteria) {
    Set qcRemoved = getTemporaryQc(special, TEMPLATE_REMOVED_QC);
    qcRemoved.add(criteria.getId());
    Set qcIdsAdded = getTemporaryQc(special, TEMPLATE_ADDED_QC);
    qcIdsAdded.remove(criteria.getId());
  }

  /**
   * 从Session中取得某个地震专题的临时QC，包括已指定和待指定的
   * 
   * @param special 专题地震
   * @param sessionName session的键名
   * @return
   */
  public Set getTemporaryQc(Special special, String sessionName) {
    Map specialQc = (Map) getRequest().getSession().getAttribute(sessionName);
    if (specialQc == null) {
      specialQc = Collections.synchronizedMap(new HashMap());
      getRequest().getSession().setAttribute(sessionName, specialQc);
    }
    Set qcIds = (Set) specialQc.get(special.getId());
    if (qcIds == null) {
      qcIds = Collections.synchronizedSet(new HashSet());
      specialQc.put(special.getId(), qcIds);
    }
    return qcIds;
  }

  /**
   * 列出所有地震目录，同时，根据当前{@link #special}的地震目录，将{@link #special} 的地震目录的changed属性设置为true
   * 
   * @return
   */
  public String qcOfSpecial() {
    List<Map> list = specialDao.queryQc(criteria);
    return JSON;
  }

  public Criteria getCriteria() {
    return criteria;
  }

  public void setCriteria(Criteria criteria) {
    this.criteria = criteria;
  }

}
