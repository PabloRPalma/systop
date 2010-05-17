package com.systop.fsmis.video.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.xwork.StringUtils;

import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.common.modules.security.user.service.UserManager;
import com.systop.core.taglibs.BaseSpringAwareBodyTagSupport;
import com.systop.fsmis.video.VideoConstants;


/**
 * 判断用户视频在线的tag
 * @author catstiger@gmail.com
 *
 */
@SuppressWarnings("serial")
public class VideoOnlineTag extends BaseSpringAwareBodyTagSupport {
  @Override
  public int doStartTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    User user = UserUtil.getPrincipal(request);
    if (user == null) {
      return SKIP_BODY;
    } else {
      UserManager userManager = (UserManager) getBean("userManager");
      user = userManager.get(user.getId());
      if(StringUtils.equals(VideoConstants.USER_TALKING, user.getVideoOnline()) ||
          StringUtils.equals(VideoConstants.USER_WAITING, user.getVideoOnline())) {
        return EVAL_BODY_INCLUDE;
      } else {
        return SKIP_BODY;
      }
    }
  }
}
