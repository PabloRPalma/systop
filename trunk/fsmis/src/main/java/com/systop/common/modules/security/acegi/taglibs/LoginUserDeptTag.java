package com.systop.common.modules.security.acegi.taglibs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.systop.common.modules.dept.model.Dept;
import com.systop.common.modules.security.user.LoginUserService;

@SuppressWarnings("serial")
public class LoginUserDeptTag extends BodyTagSupport {
  private boolean showPath = true;
  private boolean showTopDept = true;
  private String propertyName = null;

  public boolean getShowPath() {
    return showPath;
  }

  public void setShowPath(boolean showPath) {
    this.showPath = showPath;
  }

  @Override
  public int doEndTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    //得到Spring WebApplicationContext
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext
        .getServletContext());
    //得到Spring管理的LoginUserService
    LoginUserService loginUserService = (LoginUserService) ctx.getBean("loginUserService");
    Dept dept = loginUserService.getLoginUserDept(request);
    if(showTopDept) { //显示用户所在顶级部门（公司）的属性
      showPath = false; //肯定不会显示PATH了
      dept = loginUserService.getLoginUserCounty(request);
    }
    if (dept != null) {
      if(propertyName != null) { //显示当前部门的某个属性
        showPath = false; //肯定不会显示PATH了
      }
      String content = "";
      if (!showPath) {
        try {
          if(StringUtils.isBlank(propertyName)) {
            propertyName = "name";
          }
          content = BeanUtils.getProperty(dept, propertyName);
        } catch (IllegalAccessException e) {          
          e.printStackTrace();
        } catch (InvocationTargetException e) {          
          e.printStackTrace();
        } catch (NoSuchMethodException e) {          
          e.printStackTrace();
        }
      } else {
        content = loginUserService.getLoginUserPath(request);
      }
      try {
        pageContext.getOut().print(content);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return super.doEndTag();
  }

  @Override
  public void release() {
    showPath = true;
    propertyName = null;
    showTopDept = true;
    super.release();
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public boolean isShowTopDept() {
    return showTopDept;
  }

  public void setShowTopDept(boolean showTopDept) {
    this.showTopDept = showTopDept;
  }

}
