package com.systop.cms.webview.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Articles;
import com.systop.cms.model.Catalogs;
import com.systop.cms.model.Templates;
import com.systop.cms.template.TemConstants;
import com.systop.cms.webview.URLUtil;
import com.systop.cms.webview.exception.ParseURLToTemplateException;
import com.systop.common.modules.template.freemarker.servlet.StringTemplateContext;
import com.systop.core.service.BaseGenericsManager;

import freemarker.cache.StringTemplateLoader;

/**
 * 用于解析模板，同时加入一些基本数据
 * 
 * @author lunch
 */
@Service
public class FreeMarkerViewManager extends BaseGenericsManager<Templates> {

  /**
   * 根据URL请求封装模板信息
   * 
   * @throws Exception
   */
  public StringTemplateContext urlToTmptContext(String url, StringTemplateLoader tmLder)
      throws ParseURLToTemplateException {

    // 从URL中获取访问目录
    String urlRoot = URLUtil.getRootForURL(url);
    // 从URL中获取访问文件
    String fileName = URLUtil.getFileNameForURL(url);

    // 当URL请求的文件名为INDEX的时候有两种可能，一种是网站主页，一种是栏目主页
    if (CmsConstants.INDEX.equalsIgnoreCase(fileName)) {
      return parseIndexFile(urlRoot, tmLder);
    } else { // 当请求为文章内容时
      return parseArticleFile(fileName, tmLder);
    }
  }

  /**
   * 解析index请求．包括网站主页和栏目主页．获得模板及相关数据
   * 
   * @param urlRoot
   * @param tmLder
   * @return
   * @throws ParseURLToTemplateException
   */
  @SuppressWarnings("unchecked")
  protected StringTemplateContext parseIndexFile(String urlRoot, StringTemplateLoader tmLder)
      throws ParseURLToTemplateException {

    String templateName = null; // 模板名称
    String templateContent = null; // 模板内容
    Templates t = null;

    Map model = null; // model内容

    // urlRoot为空的时候代表是网站主页，查找网站主页模板
    if (StringUtils.isBlank(urlRoot)) {
      templateName = CmsConstants.INDEX;

      if (tmLder.findTemplateSource(CmsConstants.INDEX) == null) {
        // 得到默认网站主页模板
        t = getDefaultTemplates(TemConstants.TEMPLATE_INDEX);
        if (t != null) {
          templateContent = t.getContent();
        } else {
          throw new ParseURLToTemplateException("未找到合适的网站主页模板");
        }

      } else {
        logger.debug("网站主页模板" + CmsConstants.INDEX + "已经存在，不再添加．");
      }

    } else { // urlRoot不为空的时候代表是栏目主页，查找栏目模板
      Catalogs cata = getCataByRoot(urlRoot);
      if (cata != null) {
        t = cata.getCataTemplate();
        if (t == null) { // 如果为空则查询默认的栏目模板
          t = getDefaultTemplates(TemConstants.TEMPLATE_CATALOG);
        }
        model = new HashMap();
        model.put("catalog", cata);
        // 得到文章所属栏目的顶级栏目
        model.put("topCatalog", getRoot(cata));
        templateName = t.getName();
        if (tmLder.findTemplateSource(templateName) == null) {
          templateContent = t.getContent();
        } else {
          logger.debug("栏目模板［" + templateName + "］已经存在，不添加");
        }

      } else {
        throw new ParseURLToTemplateException("未找到与栏目路径" + urlRoot + "对应的栏目");
      }
    }
    // 解析模板
    templateContent = presTemplate(templateContent);
    return createSTContext(templateName, templateContent, model);
  }

  /**
   * 根据访问路径获取栏目
   * 
   * @param root
   * @return
   */
  @SuppressWarnings("unchecked")
  protected Catalogs getCataByRoot(String root) {
    String hql = "from Catalogs c where c.rootPath=?";
    List<Catalogs> cataList = getDao().query(hql, root);

    Catalogs cata = null;
    if (cataList != null && !cataList.isEmpty()) {
      cata = cataList.get(0);
    }
    return cata;
  }

  /**
   * 解析文章请求，获得模板及相关数据
   * 
   * @param fileName
   * @param tmLder
   * @return
   * @throws ParseURLToTemplateException
   */
  @SuppressWarnings("unchecked")
  protected StringTemplateContext parseArticleFile(String fileName, StringTemplateLoader tmLder)
      throws ParseURLToTemplateException {

    String templateName = null; // 模板名称
    String templateContent = null; // 模板内容
    Map model = null; // model内容

    String hql = "from Articles a where a.id=? and "
        + "(a.available=? or a.available is null) and a.audited=?";

    List<Articles> artList = null;
    try {
      artList = getDao().query(hql,
          new Object[] { Integer.valueOf(fileName), CmsConstants.Y, CmsConstants.Y });
    } catch (NumberFormatException e) {
      throw new ParseURLToTemplateException(e.getMessage() + "文章'" + fileName + "."
          + CmsConstants.POSTFIX + "'未找到");
    }

    if (artList != null && !artList.isEmpty()) {
      Articles article = artList.get(0);
      Templates t = article.getCatalog().getArtTemplate();
      if (t == null) { // 如果为空则查询默认的文章模板
        t = getDefaultTemplates(TemConstants.TEMPLATE_ARTICLE);
      }
      model = new HashMap();
      model.put("article", article);
      // 得到文章所属栏目的顶级栏目
      model.put("catalog", getRoot(article.getCatalog()));
      // 得到文章所属栏目的路径，导航
      model.put("path", getRootPath(article.getCatalog()));
      templateName = t.getName();
      if (tmLder.findTemplateSource(templateName) == null) {
        templateContent = t.getContent();
      } else {
        logger.debug("文章模板'" + templateName + "'已经存在，不添加");
      }

    } else {
      throw new ParseURLToTemplateException("未找到与" + fileName + "." + CmsConstants.POSTFIX
          + "对应的文章");
    }
    // 解析模板
    templateContent = presTemplate(templateContent);
    return createSTContext(templateName, templateContent, model);
  }

  /** 创建StringTemplateContext */
  @SuppressWarnings("unchecked")
  protected StringTemplateContext createSTContext(String templateName, String templateContent,
      Map model) {
    StringTemplateContext strTmCtx = null;
    // templateContent为空不要紧，会在Servlet中添加模板的时候进行判断
    if (templateName != null) {
      strTmCtx = new StringTemplateContext();
      strTmCtx.setTemplateName(templateName);
      strTmCtx.setTemplateContent(templateContent);
      strTmCtx.setModel(model);
    }
    return strTmCtx;
  }

  /**
   * 模板类型 0:栏目模板 ,1:文章模板 ,2:公告模板 ,3:主页模板
   * 
   * @param templateType
   * @return
   */
  @SuppressWarnings("unchecked")
  protected Templates getDefaultTemplates(String templateType) {
    String hql = "from Templates t where t.type=? and t.isDef=?";
    List<Templates> tmptList = getDao().query(hql, new Object[] { templateType, CmsConstants.Y });
    Templates t = null;
    if (tmptList != null && !tmptList.isEmpty()) {
      t = tmptList.get(0);
    }
    return t;
  }

  /**
   * 解析模板，解析模板中的公共模板
   * 
   * @param tmptContent 模板内容
   * @return
   */
  protected String presTemplate(String tmptContent) {
    if (tmptContent == null || tmptContent.equals("")) {
      return tmptContent;
    }

    Pattern pattern = Pattern.compile(CmsConstants.FM_TMPT_REGEX);
    Matcher matcher = pattern.matcher(tmptContent);
    if (matcher.find()) {
      String tmptName = matcher.group(1);
      String fmTmpt = tmptContent.substring(matcher.start(), matcher.end());
      logger.debug("加载模板：" + tmptName);
      // 得到模板内容
      String tmptCont = getTmptCont(tmptName);
      // 模板内容替换模板表达示
      tmptContent = tmptContent.replace(fmTmpt, tmptCont);
      tmptContent = presTemplate(tmptContent);
    }
    return tmptContent;
  }

  /**
   * 根据模板名称得到模板内容
   * 
   * @param tmptName 模板名称
   * @return
   */
  @SuppressWarnings("unchecked")
  private String getTmptCont(String tmptName) {
    String hql = "from Templates t where t.name = ?";
    List<Templates> tmptList = getDao().query(hql, tmptName);
    if (tmptList != null && !tmptList.isEmpty()) {
      return tmptList.get(0).getContent();
    } else {
      return "";
    }
  }

  /**
   * 根据子栏目得到根栏目
   * 
   * @param catalog 子栏目
   * @return
   */
  private Catalogs getRoot(Catalogs catalog) {
    if (catalog != null && catalog.getParentCatalog() != null) {
      catalog = getRoot(catalog.getParentCatalog());
    }
    return catalog;
  }

  /**
   * 根据子栏目得到根栏目
   * 
   * @param catalog 子栏目
   * @return
   */
  @SuppressWarnings("unchecked")
  private String getRootPath(Catalogs catalog) {
    /** 导航路径 */
    StringBuffer path = new StringBuffer();
    List pathList = new ArrayList();

    String subPath = CmsConstants.START_ROOT + catalog.getRootPath() + "/index.shtml";
    String subName = catalog.getName();
    path.append("<a class='normal' href='" + subPath + "'>");
    path.append(subName + "</a>");
    pathList.add(path.toString());
    logger.debug("path is:" + path.toString());

    while (catalog != null && catalog.getParentCatalog() != null) {
      StringBuffer rootPath = new StringBuffer();
      catalog = getRoot(catalog.getParentCatalog());
      String subPathTemp = CmsConstants.START_ROOT + catalog.getRootPath() + "/index.shtml";
      String subNameTemp = catalog.getName();
      rootPath.append("<a class='normal' href='" + subPathTemp + "'>");
      rootPath.append(subNameTemp + "</a>");
      pathList.add(rootPath.toString());
      logger.debug("path is:" + rootPath.toString());
    }
    StringBuffer newPath = new StringBuffer();
    logger.debug("list size:" + pathList.size());
    for (int i = pathList.size(); i > 0; i--) {
      newPath.append(pathList.get(i - 1));
      newPath.append(" -> ");
    }
    logger.debug("newPAHT:" + newPath.toString());
    return newPath.toString();
  }
}
