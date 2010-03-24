package datashare.sign.prequery.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.ApplicationException;
import com.systop.core.dao.hibernate.BaseHibernateDao;

import datashare.admin.ds.service.DataSourceManager;
import datashare.admin.method.model.Method;
import datashare.admin.samplerate.service.SampleRateManager;
import datashare.admin.subject.model.Subject;
import datashare.base.webapp.AbstractQueryAction;
import datashare.sign.prequery.dao.PrepareQueryDao;
import datashare.sign.prequery.model.Item;
import datashare.sign.prequery.model.Point;
import datashare.sign.prequery.model.PrequeryCriteria;
import datashare.sign.prequery.model.Station;

/**
 * 通过测项代码，数据类型，采样率并结合权限，进行筛选。主要生产进行下一步查询数据用的树型列表
 * 
 * @author Lunch
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PreQueryAction extends AbstractQueryAction<PrequeryCriteria> {
  /**
   * 用于查询MySQL数据库中学科表
   */
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao baseHibernateDao;

  /** 用于数据查询 */
  @Autowired
  private PrepareQueryDao prepareQueryDao;

  /** 用于获取Schema */
  @Autowired(required = true)
  private DataSourceManager dataSourceManager;

  /** 对应页面传来的查询条件，即model */
  private PrequeryCriteria model = new PrequeryCriteria();
  /**
   * 学科,用于过滤测项分量
   */
  private Subject subject = new Subject();

  /** JSON param 返回页面的json数据，在配置文件中使用 */
  @SuppressWarnings("unchecked")
  private List jsonData;

  @Autowired(required = true)
  private SampleRateManager srManager;

  /**
   * 根据学科ID，列出该学科对应测项。并将所有测项ID对应methodIds存入request
   */
  public String index() {
    subject = baseHibernateDao.get(Subject.class, subject.getId());
    String methodIds = subject.getMethodIdsStr();
    // 设置 methIds参数，前台jsp页面使用.
    getRequest().setAttribute("methodIds", methodIds);
    getRequest().setAttribute("sampleRateList", srManager.getEnabled());
    logger.debug("Subject:" + subject.getId() + "对应MethodId：" + methodIds);
    return SUCCESS;
  }

  /**
   * 返回构建树型列表的JSON数据.此时查询条件model[PrequeryCriteria]中的属性methodId可能包含的不止一个测项id，有可能是用逗号隔开的多个测项ID.
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public String staionsTree() {
    // 验证参数
    validateParams();
    // 当前登录用户
    User loginUser = UserUtil.getPrincipal(this.getRequest());
    if (loginUser == null) {
      throw new ApplicationException("用户未登录,访问被拒绝!");
    }
    logger.debug("角色验证前的methodIds:" + getModel().getMethodId());

    /*
     * 在查询前应考虑登录用户角色和测项的关联关系。此时应从当前登录用户角色中取得可访问的测项代码[methodId],
     * 然后和前台传递的methodId序列作比较，最后将没有权限访问的测项去除。
     */
    if (validateMethodIdByRole(loginUser)) {// 至少有一项符合权限要求
      getModel().setSchema(dataSourceManager.getQzSchema());
      // 此参数用于模糊匹配表名
      getModel().setLikeTableName(
          new StringBuffer("%").append(getModel().getTableCategory())
          .append("_").append(getModel().getSampleRate()).append("%").toString());
      // 为Json返回参数赋值
      jsonData = convertStation(prepareQueryDao.buildStations(getModel()));
    } else {// 没有任何权限的时候返回无权限提示
      jsonData = emptyNodes("无权限访问该类数据!");
    }
    logger.debug("角色验证后符合条件的methodIds:" + getModel().getMethodId());
    return SUCCESS;
  }

  /**
   * 根据登录用户角色判断要查询的测项ID是否符合权限要求，不符合的去除.只要有一项符合权限即返回true
   * 
   * @param user
   * @return
   */
  private boolean validateMethodIdByRole(User user) {
    user = baseHibernateDao.get(User.class, user.getId());
    Set<Role> roles = user.getRoles();
    Set<Method> methods = new HashSet<Method>();
    for (Role r : roles) {
      methods.addAll(r.getMethods());
    }
    List<String> midList = getMethodIdList(getModel());
    // 定义list变量，用于存放符合权限要求的测项ID
    List<String> newMidList = new ArrayList<String>();
    for (String id : midList) {// 遍历所有的测项id，检查他们是不是符合权限要求。
      for (Method m : methods) {
        // 当前登录用户拥有的所有角色对应的所有测项有可能存在重复。所以当只要有一次判断id存在的话就跳出本次循环。
        if (m.getId().equals(id)) {
          newMidList.add(id);
          break;
        }
      }
    }
    getModel().setMethodId(idListToString(newMidList));
    return !newMidList.isEmpty();
  }

  /**
   * 将PrequeryCriteria中的methodId分割，并存储到List中.
   * 
   * @param prec
   * @return
   */
  private List<String> getMethodIdList(PrequeryCriteria prec) {
    String mids = getModel().getMethodId();
    if (mids.indexOf(",") == -1) {
      mids = mids + ",";
    }
    // 分割mids,取得测项代码数组,mids由多个测项代码组成，彼此间有[,]隔开。
    String[] midArray = mids.split(",");
    List<String> midList = new ArrayList<String>();
    for (String id : midArray) {
      midList.add(id);
    }
    return midList;
  }

  /**
   * 将存有methodId的list转换成用逗号分割的字符串
   * 
   * @param midList
   * @return
   */
  private String idListToString(List<String> midList) {
    String mids = "";
    if (!midList.isEmpty()) {
      for (String id : midList) {
        mids = mids + id + ",";
      }
    }
    // 这样转换后mids的最后一位可能是','在此不用处理，并不影响后面程序执行。
    return mids;
  }

  /**
   * 查询参数验证，因为是返回JSON数据，没有具体显示页面，所以直接抛出异常
   */
  private void validateParams() {
    if (StringUtils.isBlank(getModel().getTableCategory())) {
      throw new ApplicationException("非法操作，缺少参数：数据类型。");
    }
    if (StringUtils.isBlank(getModel().getSampleRate())) {
      throw new ApplicationException("非法操作，缺少参数：采样率。");
    }
    if (StringUtils.isBlank(getModel().getMethodId())) {
      throw new ApplicationException("非法操作，缺少参数：测项代码。");
    }
  }

  /**
   * 把查询出的台站列表转换成符合TreeView控件要求的数据格式。
   * 
   * @param stations
   * @return
   */
  @SuppressWarnings("unchecked")
  private List convertStation(List<Station> stations) {
    if (stations.isEmpty()) {// 如果数据为空
      return emptyNodes("没有可以访问的数据!");
    }

    List sData = new ArrayList();
    for (Station s : stations) { // 遍历台站信息
      Map sM = new HashMap();
      sM.put("text", s.getName()); // 设置台站级节点名称
      sM.put("expanded", false); // 设置节点是否打开
      if (!s.getPoints().isEmpty()) {// 如果测点信息不为空
        List pData = new ArrayList();
        for (Point p : s.getPoints()) {// 遍历测点信息
          Map pM = new HashMap();
          pM.put("text",  p.getId() + "号测点_" + p.getName()); // 设置测点级节点名称{不在使用节点名称，而使用测点1、测点2代替。1、2为pointId}
          List iData = new ArrayList();
          if (!p.getItems().isEmpty()) {// 如果测项分量不为空
            for (Item i : p.getItems()) {// 遍历测项分量
              Map iM = new HashMap();
              // 设置测项分量级的节点信息
              iM.put("text", "<input type='checkbox' name='selectItemIds' value='" + i.getId()
                  + "'> <a href='#' title='" + " 开始时间:" + i.getStartDate() + " 截止时间:"
                  + i.getEndDate() + "'>" + i.getName() + "</a>");
              iData.add(iM);// 测项分量列表增加测项分量信息
            }
            pM.put("children", iData); // 为测点级节点设置节点：测项分量列表
          }
          pData.add(pM); // 测点列表增加测点信息
        }
        sM.put("children", pData); // 为台站级节点设置子节点：测点列表
      }
      sData.add(sM);// 台站列表增加台站信息
    }
    return sData;
  }

 /**
   * 返回提示没有可用数据的树型列表,
   * 
  * @param message 提示信息
  * @return
  */
  @SuppressWarnings("unchecked")
  private List emptyNodes(String message) {
    List temps = new ArrayList();
    Map map = new HashMap();
    map.put("text", message);
    map.put("expanded", false);
    temps.add(map);
    return temps;
  }

  /**
   * @see com.opensymphony.xwork2.ModelDriven#getModel()
   */
  @Override
  public PrequeryCriteria getModel() {
    return model;
  }

  public void setModel(PrequeryCriteria model) {
    this.model = model;
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

  /**
   * @return the stations
   */
  @SuppressWarnings("unchecked")
  public List getJsonData() {
    return jsonData;
  }

  /**
   * @return the prepareQueryDao
   */
  public PrepareQueryDao getPrepareQueryDao() {
    return prepareQueryDao;
  }

  /**
   * @param prepareQueryDao the prepareQueryDao to set
   */
  public void setPrepareQueryDao(PrepareQueryDao prepareQueryDao) {
    this.prepareQueryDao = prepareQueryDao;
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }

}
