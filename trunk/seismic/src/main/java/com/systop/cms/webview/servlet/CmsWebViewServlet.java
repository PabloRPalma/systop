package com.systop.cms.webview.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.systop.cms.CmsConstants;
import com.systop.cms.webview.exception.ParseURLToTemplateException;
import com.systop.cms.webview.service.AdvisoryFreeMarkerManager;
import com.systop.cms.webview.service.AnnounceFreeMarkerManager;
import com.systop.cms.webview.service.ArticleFreeMarkerManager;
import com.systop.cms.webview.service.CatalogFreeMarkerManager;
import com.systop.cms.webview.service.CzCatFreeMarkerManager;
import com.systop.cms.webview.service.FreeMarkerViewManager;
import com.systop.cms.webview.service.LinkFreeMarkerManager;
import com.systop.cms.webview.service.SoftwareFreeMarkerManager;
import com.systop.cms.webview.service.SpecialFreeMarkerManager;
import com.systop.cms.webview.servlet.base.BaseFreeMarkerServlet;
import com.systop.common.modules.template.freemarker.servlet.StringTemplateContext;

import freemarker.cache.StringTemplateLoader;

/**
 * 基于模板的页面浏览
 * 
 * @author lunch
 */
public class CmsWebViewServlet extends BaseFreeMarkerServlet {

  /**
   * @throws ParseURLToTemplateException
   * @throws Exception
   * @see freemarker.ext.servlet.FreeMarkerStringTemplateViewServlet
   *      #reqUrlToModelCtx(javax.servlet.http.HttpServletRequest)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected StringTemplateContext reqUrlToModelCtx(HttpServletRequest req,
      StringTemplateLoader tmLoader) throws ParseURLToTemplateException {

    String rul = req.getRequestURI();
    // 获得模板信息
    StringTemplateContext stc = getFmkViewMgr().urlToTmptContext(rul, tmLoader);
    if (stc != null) {
      Map model = null;
      if (stc.getModel() == null) {
        model = new HashMap();
        stc.setModel(model);
      }
      stc.getModel().put(CmsConstants.ADVISORY_BENA, getAdvisoryFmMgr());
      stc.getModel().put(CmsConstants.ANNOUNCE_BENA, getAnnounceFmMgr());
      stc.getModel().put(CmsConstants.ARTICLE_BENA, getArtFmMgr());
      stc.getModel().put(CmsConstants.CATALOG_BENA, getCatFmMgr());
      stc.getModel().put(CmsConstants.LINK_BENA, getLinkFmMgr());
      stc.getModel().put(CmsConstants.CZCAT_BENA, getCzCatFmMgr());
      stc.getModel().put(CmsConstants.SOFTWARE_BENA, getSoftFmMgr());
      stc.getModel().put(CmsConstants.SPECIAL_BEAN, getSpecialFmMgr());
      
      // 设置request对象
      stc.getModel().put("req", req);
      // 网站访问根目录
      stc.getModel().put("ctx", req.getContextPath());
      // 网站基本信息
      stc.getModel().put("siteCfg", quake.admin.sitecfg.SiteConstants.siteCfg);
    }
    return stc;
  }

  /** 通过Spring获得FreeMarkerViewManager */
  private FreeMarkerViewManager getFmkViewMgr() {
    return (FreeMarkerViewManager) getBean("freeMarkerViewManager");
  }

  /** 通过Spring获得ArticleFreeMarkerManager */
  private ArticleFreeMarkerManager getArtFmMgr() {
    return (ArticleFreeMarkerManager) getBean("articleFreeMarkerManager");
  }

  /** 通过Spring获得CatalogFreeMarkerManager */
  private CatalogFreeMarkerManager getCatFmMgr() {
    return (CatalogFreeMarkerManager) getBean("catalogFreeMarkerManager");
  }

  /** 通过Spring获得LinkFreeMarkerManager */
  private LinkFreeMarkerManager getLinkFmMgr() {
    return (LinkFreeMarkerManager) getBean("linkFreeMarkerManager");
  }

  /** 通过Spring获得AnnounceFreeMarkerManager */
  private AnnounceFreeMarkerManager getAnnounceFmMgr() {
    return (AnnounceFreeMarkerManager) getBean("announceFreeMarkerManager");
  }

  /** 通过Spring获得AdvisoryFreeMarkerManager */
  private AdvisoryFreeMarkerManager getAdvisoryFmMgr() {
    return (AdvisoryFreeMarkerManager) getBean("advisoryFreeMarkerManager");
  }

  /** 通过Spring获得CzCatFreeMarkerManager */
  private CzCatFreeMarkerManager getCzCatFmMgr() {
    return (CzCatFreeMarkerManager) getBean("czCatFreeMarkerManager");
  }

  /** 通过Spring获得SoftwareFreeMarkerManager */
  private SoftwareFreeMarkerManager getSoftFmMgr() {
    return (SoftwareFreeMarkerManager) getBean("softwareFreeMarkerManager");
  }
  
  /** 通过Spring获得SpecialFreeMarkerManager */
  private SpecialFreeMarkerManager getSpecialFmMgr() {
    return (SpecialFreeMarkerManager) getBean("specialFreeMarkerManager");
  }
}
