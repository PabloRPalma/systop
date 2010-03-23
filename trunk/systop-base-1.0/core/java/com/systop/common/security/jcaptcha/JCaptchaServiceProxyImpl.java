package com.systop.common.security.jcaptcha;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 实现 CaptchaServiceProxy 用于acegi来校验，由spring注入jcaptchaService
 * 
 * @author sshwsfc@gmail.com
 */
public class JCaptchaServiceProxyImpl implements CaptchaServiceProxy {
  /**
   * Log for the class
   */
  protected static Log log = LogFactory.getLog(JCaptchaServiceProxyImpl.class);
  /**
   * instance of CaptchaService.
   */
  private CaptchaService jcaptchaService;
  
  /**
   * @see {@link CaptchaServiceProxy#validateReponseForId(String, Object)}
   */
  public boolean validateReponseForId(String id, Object response) {
    log.debug("validating captcha response");

    try {
      boolean isHuman = jcaptchaService.validateResponseForID(id, response)
          .booleanValue();
      if (isHuman) {
        log.debug("captcha passed");
      } else {
        log.warn("captcha failed");
      }
      return isHuman;

    } catch (CaptchaServiceException cse) {
      // fixes known bug in JCaptcha
      log.warn("captcha validation failed due to exception", cse);
      return false;
    }
  }

  public void setJcaptchaService(CaptchaService jcaptchaService) {
    this.jcaptchaService = jcaptchaService;
  }
}
