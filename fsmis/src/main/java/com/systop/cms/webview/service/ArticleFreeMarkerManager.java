package com.systop.cms.webview.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.stereotype.Service;

import com.systop.cms.CmsConstants;
import com.systop.cms.article.service.ArticleFullTextManager;
import com.systop.cms.model.Articles;
import com.systop.cms.model.Attachments;
import com.systop.core.dao.support.Page;
import com.systop.core.service.BaseGenericsManager;

/**
 * 文章模板解析
 * 
 * @author lunch
 */
@Service
public class ArticleFreeMarkerManager extends BaseGenericsManager<Articles> {

  private ArticleFullTextManager atlFullTextManager;

  /**
   * @param name 栏目名
   * @return List 相应栏目下的文章列表
   */
  public List<Articles> getArtsByCatName(String name) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    List<Articles> list = query(hql, new Object[] { name, CmsConstants.Y });
    return list;
  }

  /**
   * 根据栏目名称获得第一篇文章
   * 
   * @param name
   * @return
   */
  public Articles getFistArticleByCatName(String name) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    List<Articles> list = query(hql, new Object[] { name, CmsConstants.Y });
    return list.isEmpty() ? null : list.get(0);
  }

  /**
   * @param name 栏目名
   * @param size 个数
   * @return list
   */
  @SuppressWarnings("unchecked")
  public List<Articles> getArtsByCatName(String name, int size) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    Page page = new Page(Page.start(1, size), size);
    page = pageQuery(page, hql, new Object[] { name, CmsConstants.Y });
    return page.getData();
  }

  /**
   * 根据栏目id 查询文章列表.返回指定在条数
   * 
   * @param id 栏目主键ID
   * @return
   */
  public List<Articles> getArtsByCatID(Integer id, Integer count) {
    String hql = "from Articles a where a.catalog.id=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    List<Articles> artList = query(hql, new Object[] { id, CmsConstants.Y });
    if (artList.size() > count) {
      List<Articles> newList = new ArrayList<Articles>();
      for (int i = 0; i < count; i++) {
        newList.add(i, artList.get(i));
      }
      return newList;
    } else {
      return artList;
    }
  }

  /**
   * 根据栏目ID获得指定个数的推荐文章的信息，结合首页flash使用
   * 
   * @param catalogName 栏目名称
   * @param size 获得文章的个数
   * @return
   */
  @SuppressWarnings("unchecked")
  public Map getEliteArticlesInfo(String catalogName, Integer size, String ctx) {
    String hql = "from Articles a where a.catalog.name=? and a.audited=? and a.isElite=? order by serialNo, createTime DESC";
    Page page = new Page(Page.start(1, size), size);
    page = pageQuery(page, hql, new Object[] { catalogName, CmsConstants.Y, CmsConstants.Y });
    Map eliteMap = null;
    if (page.getData() != null) {
      eliteMap = new HashMap();
      String titles = "";
      String imgs = "";
      String urls = "";
      for (Object a : page.getData()) {
        // 前台不显示文章标题，用空格代替
        titles = titles + ((Articles) a).getShortTitle() + "|";
        if (StringUtils.isNotBlank(((Articles) a).getFlashImg())) {
          imgs = imgs + ctx + ((Articles) a).getFlashImg() + "|";
        } else {// 如果推荐文章的图片不存在用默认代替
          imgs = imgs + ctx + CmsConstants.ELITE_ART_IMG + "|";
        }
        urls = urls + ctx + ((Articles) a).getPath() + "|";
      }
      eliteMap.put("titles", cutLastChar(titles));
      eliteMap.put("imgs", cutLastChar(imgs));
      eliteMap.put("urls", cutLastChar(urls));
    }
    return eliteMap;
  }

  /**
   * 去除字符串最后一位
   * 
   * @param arg
   * @return
   */
  private String cutLastChar(String arg) {
    if (arg.endsWith("|")) {
      arg = arg.substring(0, arg.length() - 1);
    }
    return arg;
  }

  /**
   * @param name 栏目名
   * @param size 个数
   * @return list
   */
  public List<Articles> getArtsByChatNamePage(String name, int size) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    List<Articles> list = this.query(hql, 2, size, new Object[] { name, CmsConstants.Y });
    return list;
  }

  /**
   * @param articleId 文章ID
   * @return list
   */
  @SuppressWarnings("unchecked")
  public List<Attachments> getAttsByArtId(String articleId) {
    int id = Integer.parseInt(articleId);
    String hql = "from Attachments a where a.article.id=?";
    List<Attachments> list = getDao().query(hql, id);
    return list;
  }

  /**
   * 分页查询文章
   * 
   * @param cataName 栏目名
   * @param pageNo 页号
   * @param pageSize 一页记录数
   * @return Page
   */
  public Page queryArticles(String cataName, int pageNo, int pageSize) {
    String hql = "from Articles a " + "where a.catalog.name=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    Page page = new Page(Page.start(pageNo, pageSize), pageSize);
    return pageQuery(page, hql, new Object[] { cataName, CmsConstants.Y });
  }

  /**
   * 根据栏目id 查询文章列表
   * 
   * @param id
   * @param pageNo
   * @param pageSize
   * @return
   */
  public Page queryArticlesByID(Integer id, int pageNo, int pageSize) {
    String hql = "from Articles a " + "where a.catalog.id=? and a.audited=? "
        + "order by a.onTop DESC ,serialNo ,createTime DESC";
    Page page = new Page(pageNo, pageSize);
    return pageQuery(page, hql, pageNo, pageSize, new Object[] { id, CmsConstants.Y });
  }

  /**
   * 文章全文检索
   * 
   * @param search
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Articles> searchAtl(String search) {
    List<Articles> list = Collections.EMPTY_LIST;
    try {
      search = urlDecode(search);
      logger.info("全文检索内容：" + search);
      if (search != null && !search.equals("")) {
        list = atlFullTextManager.queryContent("title", getSearchParam(search));
      }
    } catch (ParseException e) {
      logger.error("全文检索失败：" + e.toString());
    }
    return list;
  }

  /**
   * 得到查旬条件
   * 
   * @param search
   * @return
   */
  public String getSearchParam(String search) {
    StringBuffer param = new StringBuffer().append("title:").append(search).append(" or ").append(
        "content:").append(search);
    return param.toString();
  }

  /**
   * 做url转换编码
   * 
   * @param str
   * @return
   */
  public String urlDecode(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void setAtlFullTextManager(ArticleFullTextManager atlFullTextManager) {
    this.atlFullTextManager = atlFullTextManager;
  }
}
