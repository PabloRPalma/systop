package com.systop.common.modules.security.user.webapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.dept.webapp.DeptAction;
import com.systop.common.modules.security.user.UserConstants;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.core.dao.support.Page;
import com.systop.core.util.RequestUtil;
import com.systop.core.webapp.struts2.action.ExtJsCrudAction;

/**
 * <code>User</code>对象的struts2 action。
 * @author Sam Lee
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends ExtJsCrudAction<User, UserManager> {
  /**
   * 对应页面查询条件
   */
  private String queryUsername;

  /**
   * 修改密码的时候对应输入的旧密码
   */
  private String oldPassword;
  
  /**
   * 标识是否是用户自行修改个人信息，如果为<code>null</code>则表示
   * 由管理员修改，否则，表示由用户自己修改
   */
  private String selfEdit;
  
  /**
   * 当前登录用户
   */
  private User user;
  
  @Autowired
  private DeptAction deptAction;
  
  /**
   * 保存用户树形列表
   */
  private List<Map> userTree;
  /**
   * 上级部门ID，用于列出树形列表
   */
  private Integer parentDeptId;
  
  /**
   * 角色名称，用于员工选择器
   */
  private String roleName;

  /**
   * 对应上传图片的原始文件名，新文件名在getModel().getPhoto()
   */
  private String photoFileName;

  public String getPhotoFileName() {
    return photoFileName;
  }

  public void setPhotoFileName(String photoFileName) {
    this.photoFileName = photoFileName;
  }

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
    Example example = Example.create(getModel()).ignoreCase().setPropertySelector(
        /**
         * 选择不为null的属性作为查询条件
         */
        new PropertySelector() {
          public boolean include(Object propertyValue, String propertyName,
              Type type) {
            return propertyValue != null && StringUtils.isNotBlank(propertyValue.toString());
          }
        }).enableLike(MatchMode.ANYWHERE);
    criteria.add(example);
    logger.info(criteria.toString());
    return setupSort(criteria);
  }

  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "model.loginId", message = "登录名是必须的."),
      @RequiredStringValidator(fieldName = "model.password", message = "密码是必须的."),
      @RequiredStringValidator(fieldName = "model.confirmPwd", message = "请两次输入密码.") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "password", minLength = "3", maxLength = "32", message = "密码应多于3字符", trim = true) }, emails = { @EmailValidator(fieldName = "model.email", message = "请输入正确的e-Mail.") }, expressions = { @ExpressionValidator(message = "两次输入的密码必须相同.", expression = "model.password==model.confirmPwd") })
  @Override
  public String save() {
    Assert.notNull(getModel());
    if (getModel().getId() == null) {
      //不区分内部用户和外部用户
      //getModel().setUserType(UserConstants.USER_TYPE_SYS);
      getModel().setStatus(UserConstants.USER_STATUS_USABLE);
    }
    String result = super.save();
    if(isSelfEdit()) {
      logger.debug("用户自己修改个人信息.");
      addActionMessage("用户信息已经成功修改。");
      return INPUT;
    } else {
      return result;
    }
    
  }

  /**
   * 编辑用户
   */
  @SkipValidation
  @Override
  public String editNew() {
    getModel().setSex(UserConstants.GENT);
    return INPUT;
  }
  
  /**
   * 修改密码
   */
  public String changePassword() {
    if (StringUtils.isBlank(oldPassword) || getModel() == null
        || getModel().getId() == null) {
      return "changePassword";
    }
    String pwdToShow = getModel().getPassword();
    try {
      getManager().changePassword(getModel(), oldPassword);
      addActionMessage("修改密码成功，新密码是：" + pwdToShow);
    } catch (Exception e) {
      addActionError(e.getMessage());
    }
    return "changePassword";
  }

  /**
   * 启用用户
   * @return
   */
  public String unsealUser() {
    if (ArrayUtils.isEmpty(selectedItems)) {
      if (getModel() != null) {
        Serializable id = extractId(getModel());
        if (id != null) {
          selectedItems = new Serializable[] { id };
        }
      }
    }
    if (selectedItems != null) {
      for (Serializable id : selectedItems) {
        if (id != null) {
          User user = getManager().get(convertId(id));
          if (user != null) {
            getManager().unsealUser(user);
          } else {
            logger.debug("用户信息不存在.");
          }
        }
      }
      logger.debug("{} items usable user.", selectedItems.length);
    }
    return SUCCESS;
  }
  
  public String showSelf(){
    if(getModel().getId() != null){
      setModel(getManager().get(getModel().getId()));
    }
    return "bingo";
  }
  /**
   * 前台注册用户修改个人信息
   * @return
   */
  public String editInfo(){
    user = UserUtil.getPrincipal(getRequest());
    getModel().setLoginId(user.getLoginId());
    getModel().setPassword(user.getPassword());
    if(isSelfEdit()){
      getManager().update(getModel());
      addActionMessage("个人信息修改成功!");
    }
    return "bingo";
  }
  
  /**
   * Build a tree as json format.
   */
  @SkipValidation
  public String userTree() {
    if (RequestUtil.isJsonRequest(getRequest())) {
      Dept parent = null;
      if (parentDeptId != null) {
        parent = getManager().getDao().get(Dept.class, parentDeptId);
      } else {
        parent = (Dept) getManager().getDao().findObject("from Dept d where d.parentDept is null");
      }
      Map<String, Object> parentMap = null;
      if (parent != null) {
        parentMap = new HashMap<String, Object>();
        parentMap.put("id", parent.getId());
        parentMap.put("text", parent.getName());
        parentMap.put("type", parent.getType());
      }
      Map deptTree = deptAction.getDeptTree(parentMap, true);
      Map tree = getManager().getUserTree(deptTree, roleName);
      if (!tree.isEmpty()) {
        userTree = new ArrayList<Map>();
        userTree.add(tree);
      }
      return "tree";
    }
    return INDEX;
  }
  
  
  /**
   * 取得用户登陆后的信息
   */
  public String userInfo() {
    user = UserUtil.getPrincipal(getRequest());
    return "userInfo";
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
   * 是否用户自己修改信息
   */
  private boolean isSelfEdit() {
    return StringUtils.isNotBlank(selfEdit);
  }

  /**
   * @return the queryUsername
   */
  public String getQueryUsername() {
    return queryUsername;
  }

  /**
   * @param queryUsername the queryUsername to set
   */
  public void setQueryUsername(String queryUsername) {
    this.queryUsername = queryUsername;
  }

  /**
   * @return the oldPassword
   */
  public String getOldPassword() {
    return oldPassword;
  }

  /**
   * @param oldPassword the oldPassword to set
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * @return the selfEdit
   */
  public String getSelfEdit() {
    return selfEdit;
  }

  /**
   * @param selfEdit the selfEdit to set
   */
  public void setSelfEdit(String selfEdit) {
    this.selfEdit = selfEdit;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  public List<Map> getUserTree() {
    return userTree;
  }

  public void setUserTree(List<Map> userTree) {
    this.userTree = userTree;
  }

  public Integer getParentDeptId() {
    return parentDeptId;
  }

  public void setParentDeptId(Integer parentDeptId) {
    this.parentDeptId = parentDeptId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
}
