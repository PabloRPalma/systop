package com.systop.fsmis.fscase.task.taskdetail.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.cms.utils.PageUtil;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.dao.support.Page;
import com.systop.core.util.ReflectUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;
import com.systop.fsmis.FsConstants;
import com.systop.fsmis.fscase.task.TaskConstants;
import com.systop.fsmis.fscase.task.taskdetail.service.TaskDetailManager;
import com.systop.fsmis.model.Corp;
import com.systop.fsmis.model.TaskDetail;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
@SuppressWarnings( { "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskDetailAction extends
    ExtJsCrudAction<TaskDetail, TaskDetailManager> {

  // 是否为综合案件
  private String isMultipleCase;
  // 默认显示的Tab序号,用于在view页面默认显示哪个Tab
  private String modelId;

  // 查询起始时间
  private Date taskBeginTime;
  // 查询截至时间
  private Date taskEndTime;
  // 在页面中作为默认人员输入项
  private User user;

  // 用户选择的企业
  private Integer corpId;

  private String taskId;

  private Map<String, String> jsonResult;

  private String[] corpNames;

  private String[] corpArry;

  @Autowired
  private LoginUserService loginUserService;

  /**
   * 根据当前登录人员所在的区县和部门列出任务明细
   */
  @Override
  public String index() {
    if (!checkLogin()) {
      return INDEX;
    }
    StringBuffer buf = new StringBuffer("from TaskDetail detail where 1=1 ");
    List<Object> args = new ArrayList<Object>();
    // 根据任务title查询
    if (getModel() != null && getModel().getTask() != null
        && StringUtils.isNotBlank(getModel().getTask().getTitle())) {
      buf.append("and detail.task.title like ? ");
      args.add(MatchMode.ANYWHERE
          .toMatchString(getModel().getTask().getTitle()));
    }
    if (StringUtils.isNotBlank(getModel().getStatus())) {
      buf.append("and detail.status = ? ");
      args.add(getModel().getStatus());
    }
    builddDispatchTimeCondition(buf, args);

    Dept dept = loginUserService.getLoginUserDept(getRequest());
    if (dept != null) {
      buf.append("and detail.dept.id = ? ");
      args.add(dept.getId());
    }
    // 区分一般案件和综合案件
    buf.append("and detail.task.fsCase.isMultiple = ? ");
    args.add(isMultipleCase);
    getRequest().setAttribute("userId", user.getId());
    getRequest().setAttribute("userName", user.getName());
    
    Page page = PageUtil.getPage(getPageNo(), getPageSize());
    page = getManager().pageQuery(page, buf.toString(), args.toArray());
    restorePageData(page);

    return INDEX;
  }

  /**
   * 根据任务id得到任务明细信息,<br>
   * 用于在查看页面中列出当前任务的任务明细
   * 
   * @return
   */
  public String getTaskDetailsByTaskId() {
    page = PageUtil.getPage(getPageNo(), getPageSize());
    page = getManager().pageQuery(page,
        "from TaskDetail detail where detail.task.id = ?",
        Integer.parseInt(taskId));

    List taskDetails = page.getData();
    List mapTaskDetails = new ArrayList(taskDetails.size());
    for (Iterator itr = taskDetails.iterator(); itr.hasNext();) {
      TaskDetail td = (TaskDetail) itr.next();
      Map mapTaskDetail = ReflectUtil.toMap(td, new String[] { "id",
          "completionTime", "remainDays", "status" }, true);
      mapTaskDetail.put("deptName", td.getDept().getName());
      mapTaskDetail.put("taskTitle", td.getTask().getTitle());
      mapTaskDetails.add(mapTaskDetail);
    }
    page.setData(mapTaskDetails);

    return JSON;
  }

  /**
   * 得到当前登录人员的部门的新任务数量,被客户端Ajax访问,形成提示信息
   * 
   * @return
   */
  public String getDeptTaskDetailMes() {
    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());
    // 得到当前登录人员的部门
    Dept dept = loginUserService.getLoginUserDept(getRequest());
    if (dept != null && dept.getId() != null) {
      // 单体任务
      jsonResult.put("single", String.valueOf(getManager().getNewTasks(dept,
          FsConstants.N).size()));
      // 多体任务
      jsonResult.put("multiple", String.valueOf(getManager().getNewTasks(dept,
          FsConstants.Y).size()));
    }

    return "jsonResult";
  }

  /**
   * 构建根据派发起至时间查询条件
   * 
   * @param buf hql的StringBuffer
   * @param args 参数数组
   */
  private void builddDispatchTimeCondition(StringBuffer buf, List<Object> args) {
    if (taskBeginTime != null && taskEndTime != null) {
      buf
          .append("and detail.task.dispatchTime >=? and detail.task.dispatchTime <=? ");
      args.add(taskBeginTime);
      args.add(taskEndTime);
    }

  }

  /**
   * 查看派遣给当前登录人员部门的任务明细方法<br>
   * 由于任务明细的查看操作需要置状态为"已查看",所以需要有本方法,而不是直接访问FsCaseAction的view方法.
   * 由于查看功能使用的是在FsCase模块中统一的Tab页方式,<br>
   * 各个子模块间就不能用"model.id"方式来传递当前实体id了,否则会引起冲突<br>
   * 所以在各个子模块中需要通过getParameter("taskDetailId")方式来获得当前实体实例id
   */
  @Override
  public String view() {
    String id = getRequest().getParameter("taskDetailId");
    if (StringUtils.isNotBlank(id)) {
      Integer taskDetailId = Integer.parseInt(id);
      setModel(getManager().get(taskDetailId));
      // 只有任务明细状态为"未接收"时,才涉及到改状态为"已查看"
      if (getModel().getStatus().equals(TaskConstants.TASK_DETAIL_UN_RECEIVE)) {
        // 置任务明细状态为"已查看"
        getModel().setStatus(TaskConstants.TASK_DETAIL_LOOKED);
        getManager().save(getModel());
      }
    }

    return VIEW;
  }

  /**
   * 接收任务方法
   * 
   * @return
   */
  public String receiveTask() {
	// 任务状态为：处理中状态
	getModel().getTask().setStatus(TaskConstants.TASK_PROCESSING);
    // 任务详细为：处理中状态
    getModel().setStatus(TaskConstants.TASK_DETAIL_RECEIVED);
    getManager().save(getModel());

    return SUCCESS;
  }

  /**
   * 转发到填写退回人员/退回原因页面
   * 
   * @return
   */
  public String toReturnTaskDetail() {

    return "returnTaskDetail";
  }

  /**
   * 完成退回操作方法
   * 
   * @return
   */
  public String doReturnTaskDetail() {

    getManager().doReturnTaskDetail(getModel());
    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());
    jsonResult.put("result", "success");

    return "jsonResult";
  }

  /**
   * 转到任务明细处理页面
   * 
   * @return
   */
  public String toDealWithTaskDetail() {
    // 设定默认的任务处理结果填写人为当前登录人员
    getModel()
        .setInputer(loginUserService.getLoginUser(getRequest()).getName());
    return "toDealWithTaskDetail";
  }

  /**
   * 完成事件明细处理方法
   * 
   * @return
   */
  public String doDealWithTaskDetail() {
    // 如果没有指定企业,则不设定企业关联---待修改后 的getManager().doCommitTaskDetail
    // 测试通过后删除此注释
    /*
     * if (getModel().getTask().getFsCase().getCorp().getId() == null) {
     * getModel().getTask().getFsCase().setCorp(null); }
     */
    String isNewCorp = getRequest().getParameter("isNewCorp");
    getManager().doCommitTaskDetail(getModel(),isNewCorp);

    return SUCCESS;

  }

  /**
   * 得到当前登录用户所在区县下的所有企业,以在页面中自动补全
   * 
   * @return
   */
  public String getCorps() {
    List<Corp> list = getManager().getDao().query(
        "from Corp c where c.dept.id = ?",
        loginUserService.getLoginUserCounty(getRequest()).getId());
    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());

    corpNames = new String[list.size()];

    /*
     * 由于数据特殊,map类型在JQuery端不好实现,找到好的解决办法后修改本段注释并启用 for (Corp corp : list) { if
     * (corp.getId() != null && StringUtils.isNotBlank(corp.getName())) { Map
     * map = ReflectUtil.toMap(corp, new String[] { "code", "name" }, false); if
     * (MapUtils.isNotEmpty(map)) { if (map.get("code") != null &&
     * map.get("name") != null) { jsonResult.put(map.get("code").toString(),
     * map.get("name") .toString());
     * corpNameList.add(map.get("name").toString()); } } }
     * 
     * }
     */

    for (int i = 0; i < list.size(); i++) {
      StringBuffer buf = new StringBuffer(list.get(i).getCode()).append(":")
          .append(list.get(i).getName());
      corpNames[i] = buf.toString();
    }

    return "jsonCorpNames";
  }

  /**
   * 单体任务详细状态列表返回页面:不带颜色
   */
  public Map<String, String> getStateMap() {

    Map<String, String> StateMap = new LinkedHashMap<String, String>();
    StateMap.putAll(TaskConstants.TASK_DETAIL_MAP);

    return StateMap;
  }

  /**
   * 单体任务详细状态列表返回页面:带颜色
   */
  public Map<String, String> getStateColorMap() {

    Map<String, String> StateColorMap = new LinkedHashMap<String, String>();
    StateColorMap.putAll(TaskConstants.TASK_DETAIL_COLOR_MAP);

    return StateColorMap;
  }
  /**
   * 查询所有当前登录人员所在区县辖区的企业,以在页面选择
   * 
   * @return
   */
  public String getCurrentCuntyCorps() {

    @SuppressWarnings("unused")
    List<Corp> corpList = getManager().getDao().query(
        "from Corp c where c.dept.id = ?",
        loginUserService.getLoginUserCounty(getRequest()).getId());
    // 待本阶段结束后,将企业信息自动补全功能改为JQuery来实现,再启用本段代码
    // ReflectUtil.toMap(bean, propertyNames, containsNull)

    return JSON;
  }

  /**
   * 根据企业编号得到企业信息
   * 
   * @param code
   * @return
   */
  public String getCorpByCode() {
    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());
    Corp corp = (Corp) getManager().getDao().findObject(
        "from Corp c where c.code = ?", String.valueOf(corpId));
    if (corp != null) {
      jsonResult = ReflectUtil.toMap(corp, new String[] { "id", "name", "code",
          "address", "legalPerson", "produceLicense", "sanitationLicense",
          "operateDetails" }, false);
    }

    return "jsonResult";
  }

  /**
   * <pre>
   * 根据id得到任务明细信息,用于:
   * 1.根据id得到任务明细处理结果信息,以在Ext弹出界面中显示
   * 2.根据id得到任务退回人员/退回原因信息在Ext弹出界面中显示
   * </pre>
   * 
   * @return
   */
  public String viewTaskDetailById() {
    jsonResult = Collections.synchronizedMap(new HashMap<String, String>());
    String idStr = getRequest().getParameter("taskDetailId");

    if (StringUtils.isNotBlank(idStr) && StringUtils.isNumeric(idStr)) {

      TaskDetail td = getManager().get(Integer.valueOf(idStr));
      jsonResult = ReflectUtil.toMap(td, new String[] { "inputer", "processor",
          "process", "basis", "result", "returnPeople", "returnReason" }, true);
      jsonResult.put("taskTitle", td.getTask().getTitle());
      jsonResult.put("deptName", td.getDept().getName());
    }

    return "jsonResult";
  }

  /**
   * 打印任务明细
   * 
   * @return
   */
  public String printTaskDetail() {

    return "printTaskDetail";
  }

  private boolean checkLogin() {
    user = loginUserService.getLoginUser(getRequest());
    if (user == null) {
      addActionError("请先登录!");
      return false;
    }

    return true;
  }

  public Date getTaskEndTime() {
    return taskEndTime;
  }

  public void setTaskEndTime(Date taskEndTime) {
    this.taskEndTime = taskEndTime;
  }

  public Date getTaskBeginTime() {
    return taskBeginTime;
  }

  public void setTaskBeginTime(Date taskBeginTime) {
    this.taskBeginTime = taskBeginTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public String getIsMultipleCase() {
    return isMultipleCase;
  }

  public void setIsMultipleCase(String isMultipleCase) {
    this.isMultipleCase = isMultipleCase;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public Map<String, String> getJsonResult() {
    return jsonResult;
  }

  public void setJsonResult(Map<String, String> jsonResult) {
    this.jsonResult = jsonResult;
  }

  public String[] getCorpArry() {
    return corpArry;
  }

  public void setCorpArry(String[] corpArry) {
    this.corpArry = corpArry;
  }

  public String[] getCorpNames() {
    return corpNames;
  }

  public void setCorpNames(String[] corpNames) {
    this.corpNames = corpNames;
  }

  public Integer getCorpId() {
    return corpId;
  }

  public void setCorpId(Integer corpId) {
    this.corpId = corpId;
  }

}
