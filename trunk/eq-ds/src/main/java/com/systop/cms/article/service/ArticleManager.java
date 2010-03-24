package com.systop.cms.article.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systop.cms.CmsConstants;
import com.systop.cms.model.Articles;
import com.systop.cms.model.Attachments;
import com.systop.cms.model.Catalogs;
import com.systop.core.service.BaseGenericsManager;

/**
 * 文章管理
 * 
 * @author zhangwwei
 */
@Service
public class ArticleManager extends BaseGenericsManager<Articles> {

  /**
   * 根据文章的ID查询文章
   * 
   * @param articleId 文章ID
   * @return List
   */
  @SuppressWarnings("unchecked")
  public List getArticle(Integer articleId) {
    List<Articles> articleList = query("from Articles a where a.id =?", articleId);
    return articleList;
  }

  /**
   * 根据ID查询审核通过的文章列表
   * 
   * @param articleId 文章ID
   * @return List
   */
  @SuppressWarnings("unchecked")
  public List articlesExists(Integer articleId) {
    List<Articles> articlesList = query("from Articles a where a.id=? and a.audited=?",
        new Object[] { articleId, CmsConstants.Y });
    return articlesList;
  }

  /**
   * 保存文章
   * 
   * @param articles 文章
   * @param attachment 附件
   * @param catalogId 栏目ID
   * @see BaseManager#save(Object)
   */
  @SuppressWarnings("unchecked")
  @Transactional
  public void saveAtricle(Articles article, List<Map> attachment, Integer catalogId) {
    logger.debug("artcle..." + article);

    Catalogs cata = null;
    if (catalogId != null) {
      cata = getDao().get(Catalogs.class, catalogId);
    }
    article.setCatalog(cata);

    // 循环保存附件,用于多附件上传
    if (attachment != null) {
      saveAttachments(attachment, article);
    }
    // 保存文章
    if (article.getId() == null) {
      save(article);
      // 为了获取文章ID
      getDao().getHibernateTemplate().flush();
      article.setPath(cata.getRootPath() + "/" + article.getId() + "." + CmsConstants.POSTFIX);
    } else {
      article.setPath(cata.getRootPath() + "/" + article.getId() + "." + CmsConstants.POSTFIX);
    }
    save(article);
  }

  /**
   * 保存多个附件
   * 
   * @param attachment 附件list
   * @param article 附件所属文章对象
   */
  @SuppressWarnings("unchecked")
  @Transactional
  private void saveAttachments(List<Map> attachment, Articles article) {
    for (Map<String, String> attmap : attachment) {
      Attachments attachments = new Attachments();
      attachments.setArticle(article);
      attachments.setName(attmap.get("attachmentName"));
      attachments.setPath(attmap.get("attachmentUrl"));
      getDao().save(attachments);
      article.getAttachmentses().add(attachments);
    }
  }

  /**
   * 删除附件
   * 
   * @param attachments 要删除的文章附件
   */
  @Transactional
  public void removeAttachments(Attachments attachments) {
    getDao().delete(attachments);
  }
  
  /**
   * 领导信息排序
   * 
   * @param name
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Articles> orderLeadAtl(String name) {
    String hql = "from Articles atl where atl.catalog.name = ? order by atl" + ".serialNo";
    List<Articles> list = query(hql, name);
    return list;
  }

  /**
   * 文章信息排序
   * 
   * @param catalogId
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Articles> orderCatalogArt(Integer catalogId) {
    String hql = "from Articles a where a.catalog.id = ?" + "and a.audited=?"
        + "order by a.serialNo";
    List<Articles> list = query(hql, new Object[] { catalogId, CmsConstants.Y });
    return list;
  }

  /**
   * 文章信息排序
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<Articles> orderCatalogArt_temp() {
    // 待删除方法
    // String hql = "from Articles a where" + " a.audited=? order by a.serialNo";
    // List<Articles> list = query(hql, new Object[] { CmsConstants.Y });
    return Collections.EMPTY_LIST;
  }

  /**
   * 根据栏目名称得到文章列表
   * 
   * @param name 栏目名称
   * @return
   */
  @SuppressWarnings( { "unchecked", "unchecked" })
  public List<Articles> queryArticleByCatalogName(String name) {
    Catalogs catalog = this.getCatalogs(name);
    if (catalog == null || catalog.getArticles().isEmpty()) {
      return Collections.EMPTY_LIST;
    }
    List<Articles> list = Arrays.asList(catalog.getArticles().toArray(
        new Articles[catalog.getArticles().size()]));
    return list;

  }

  /**
   * 根据栏目名称得到栏目信息
   * 
   * @param name 栏目名称
   * @return
   */
  @SuppressWarnings("unchecked")
  public Catalogs getCatalogs(String name) {
    String hql = "from Catalogs c where c.name = ?";
    List<Catalogs> list = getDao().query(hql, name);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

}
