package com.systop.common.modules.security.user.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.model.RegMemo;
import com.systop.common.modules.security.user.model.Role;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.RegistManager;
import com.systop.common.modules.security.user.service.init.SysRolesProviderImpl;
import com.systop.core.ApplicationException;
import com.systop.core.dao.support.Page;
import com.systop.core.util.DateUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;

/**
 * <code>User</code>对象的struts2 action。
 * @author DU
 * 
 */
@Deprecated
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegistAction extends ExtJsCrudAction<User, RegistManager> {
  
  /** 
   * 查询条件_注册开始时间
   */
  private Date beginTime;
  
  /**
   * 查询条件_注册截止时间
   */
  private Date endTime;
  
  /**
   * 检测用户返回的结果
   */
  private Map<String, String> checkResult;
  
  /**
   * 注册用户登陆名
   */
  private String uName;
  
  /**
   * 注册用户ID
   */
  private String uId;
  
  private RegMemo regMemo;
  
  /**
   * 查询注册用户信息
   */
  @Override
  protected Page pageQuery() {
    Page page = new Page(Page.start(getPageNo(), getPageSize()), getPageSize());
    return getManager().pageQuery(page, setupDetachedCriteria());
  }
  
  /**
   * @return DetachedCriteria 查询条件
   */
  private DetachedCriteria setupDetachedCriteria() {
    DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
    criteria.add(Restrictions.eq("userType", UserConstants.USER_TYPE_REGIST));
    // 按用户名称查询
    if (StringUtils.isNotBlank(getModel().getLoginId())) {
      criteria.add(Restrictions.like("loginId", "%" + getModel().getLoginId() + "%"));
    }
    if (beginTime != null && endTime!= null) {
      criteria.add(Restrictions.between("registTime", 
          DateUtil.firstSecondOfDate(beginTime), DateUtil.lastSecondOfDate(endTime)));
    }
    return setupSort(criteria);
  }
  
  /**
   * 保存注册用户信息_在线注册
   */
  public String regist() {
    try {
      if (getModel().getId() == null) {
        getModel().setUserType(UserConstants.USER_TYPE_REGIST);
        getModel().setStatus(UserConstants.USER_STATUS_UNUSABLE);
        getModel().setRegistTime(new Date());
      }
      if (UserConstants.USER_LEVEL_NORMAL.equals(getModel().getLevel())) {
        getModel().getRoles().add(getRoles().get(0));
        getModel().getRoles().remove(getRoles().get(1));
      }
      if (UserConstants.USER_LEVEL_SENIOR.equals(getModel().getLevel())) {
        getModel().getRoles().add(getRoles().get(1));
        getModel().getRoles().remove(getRoles().get(0));
      }
      if (extractId(getModel()) != null) {
        getManager().getDao().evict(getModel());
      }
      getManager().save(getModel());
      return "ok";
    } catch (ApplicationException e) {
      addActionError(e.getMessage());
      return "regist";
    }
  }
  
  /**
   * 用户信息确认
   */
  public String confirm() {
    getModel();
    return "confirm";
  }
  
  /**
   * 保存注册用户信息_离线管理
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.loginId", message = "登录名是必须的."),
      @RequiredStringValidator(fieldName = "model.password", message = "密码是必须的."),
      @RequiredStringValidator(fieldName = "model.email", message = "电子邮件是必须的."),
      @RequiredStringValidator(fieldName = "model.confirmPwd", message = "请两次输入密码.") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "password", minLength = "3", maxLength = "32", message = "密码应多于3字符", trim = true) }, emails = { @EmailValidator(fieldName = "model.email", message = "请输入正确的e-Mail.") }, expressions = { @ExpressionValidator(message = "两次输入的密码必须相同.", expression = "model.password==model.confirmPwd") })
  @Override
  public String save() {
    try {
      if (getModel().getId() == null) {
        getModel().setUserType(UserConstants.USER_TYPE_REGIST);
        getModel().setStatus(UserConstants.USER_STATUS_UNUSABLE);
        getModel().setRegistTime(new Date());
      }
      if (UserConstants.USER_LEVEL_NORMAL.equals(getModel().getLevel())) {
        getModel().getRoles().add(getRoles().get(0));
        getModel().getRoles().remove(getRoles().get(1));
      }
      if (UserConstants.USER_LEVEL_SENIOR.equals(getModel().getLevel())) {
        getModel().getRoles().add(getRoles().get(1));
        getModel().getRoles().remove(getRoles().get(0));
      }
      if (extractId(getModel()) != null) {
        getManager().getDao().evict(getModel());
      }
      getManager().save(getModel());
      return SUCCESS;
    } catch (ApplicationException e) {
      addActionError(e.getMessage());
      return INPUT;
    }
  }
  
  /**
   * 重定向到新建页面_离线管理
   */
  @SkipValidation
  @Override
  public String editNew() {
    getModel().setSex(UserConstants.GENT);
    getModel().setLevel(UserConstants.USER_LEVEL_NORMAL);
    return INPUT;
  }
  
  /**
   * 重定向到注册页面_在线注册
   */
  @SkipValidation
  public String registNew() {
    getModel().setSex(UserConstants.GENT);
    getModel().setLevel(UserConstants.USER_LEVEL_NORMAL);
    return "regist";
  }
  
  /**
   * 用户注册须知
   */
  public String registMemo() {
    regMemo = getManager().getRegMemoInfo();
    return "regMemo";
  }

  /**
   * 批量修改注册用户审核状态
   * @return result of struts
   */
  @SkipValidation
  public String changeUserStatus() {
    getManager().changeUserStatus(selectedItems);
    return SUCCESS;
  }
  
  /**
   * 单个修改注册用户审核状态,并发送邮件通知用户
   * @return result of struts
   */
  @SkipValidation
  public String checkupUser() {
    StringBuffer actUrl = new StringBuffer("http://");
    actUrl.append(
        getRequest().getLocalAddr()).append(":").append(
            getRequest().getLocalPort()).append(
                getRequest().getContextPath()).append(
                    "/regist/activateUser.do?uId=");
    if (StringUtils.isNotBlank(uId)){
      try {
        getManager().checkupUser(uId, actUrl.toString());
      } catch (Exception e) {
        addActionError(e.getMessage());
        return INDEX; 
      }
    }
    return SUCCESS;
  }
  
  /**
   * 激活用户信息
   */
  public String activateUser() {
    if (StringUtils.isNotBlank(uId)){
      getManager().activateUser(uId);
    }
    return "activate";
  }
  
  /**
   * ajax请求，检测登录名是否已存在
   */
  public String checkName() {
    List<User> list = getManager().getUserByName(getUId(), getUName());
    checkResult = Collections.synchronizedMap(new HashMap<String, String>());
    if(list != null && list.size() > 0){
      checkResult.put("result", "exist");
    } else {
      checkResult.put("result", "notExist");
    }
    return "jsonRst";
  }
  
  /**
   * ajax请求，检测用户邮箱是否已存在
   */
  public String checkEmail() {
    List<User> list = getManager().getUserByEmail(getUId(), getUName());
    checkResult = Collections.synchronizedMap(new HashMap<String, String>());
    if(list != null && list.size() > 0){
      checkResult.put("result", "exist");
    } else {
      checkResult.put("result", "notExist");
    }
    return "jsonRst";
  }
  
  /**
   * 用户状态Map
   */
  public Map<String, String> getUserStatusMap() {
    return UserConstants.USER_STATUS;
  }
  
  /**
   * 返回性别Map
   */
  public Map<String, String> getSexMap() {
    return UserConstants.SEX_MAP;
  }
  
  /**
   * 返回学历Map
   */
  public Map<String, String> getDegreeMap() {
    return UserConstants.DEGREE_MAP;
  }
  
  /**
   * 返回用户单位Map
   */
  public Map<String, String> getUnitkindMap() {
    return UserConstants.USER_UNITKIND_MAP;
  }
  
  /**
   * 返回用户所在省份Map
   */
  public Map<String, String> getProvinceMap() {
    return UserConstants.PROVINCE_INFO_MAP;
  }
  
  /**
   * 返回用户级别Map
   */
  public Map<String, String> getUserLevelMap() {
    return UserConstants.USER_LEVEL_MAP;
  }
  
  /**
   * @return the checkResult
   */
  public Map<String, String> getCheckResult() {
    return checkResult;
  }
  
  /**
   * 返回“普通用户”，“高级用户”
   * @return
   */
  public List<Role> getRoles() {
    Role normal = (Role) getManager().getDao().findObject("from Role r where r.name=?", SysRolesProviderImpl.ROLE_NORMAL.getName());
    Role senior = (Role) getManager().getDao().findObject("from Role r where r.name=?", SysRolesProviderImpl.ROLE_SENIOR.getName());
    List<Role> roles = new ArrayList<Role>(2);
    roles.add(normal);
    roles.add(senior);
    return roles;
  }

  /**
   * @param checkResult the checkResult to set
   */
  public void setCheckResult(Map<String, String> checkResult) {
    this.checkResult = checkResult;
  }

  /**
   * @return the uName
   */
  public String getUName() {
    return uName;
  }

  /**
   * @param name the uName to set
   */
  public void setUName(String name) {
    uName = name;
  }

  /**
   * @return the uId
   */
  public String getUId() {
    return uId;
  }

  /**
   * @param id the uId to set
   */
  public void setUId(String id) {
    uId = id;
  }

  /**
   * @return the beginTime
   */
  public Date getBeginTime() {
    return beginTime;
  }

  /**
   * @param beginTime the beginTime to set
   */
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  
  /**
   * @return the regMemo
   */
  public RegMemo getRegMemo() {
    return regMemo;
  }

  /**
   * @param regMemo the regMemo to set
   */
  public void setRegMemo(RegMemo regMemo) {
    this.regMemo = regMemo;
  }
  
  /**
   * 返回行业列表
   * */
  public Map<String, String> getIndustryMap(){
    return UserConstants.INDUSTRY_MAP;
  }
}
