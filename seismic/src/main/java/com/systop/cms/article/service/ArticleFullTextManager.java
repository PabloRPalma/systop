
package com.systop.cms.article.service;

import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.stereotype.Service;

import com.systop.cms.model.Articles;
import com.systop.cms.fulltext.BaseFullTextManager;

/**
 * 文章全文检索
 * @author guohongliang
 */
@Service
public class ArticleFullTextManager extends BaseFullTextManager {
  /**
   * 
   * @param defField
   * @param parStr
   * @return
   * @throws ParseException
   */
  @SuppressWarnings("unchecked")
  public List<Articles> queryContent(String defField, String parStr)
      throws ParseException {
    return this.fullTextQuery(defField, parStr, Articles.class);
  }

  /**
   * 
   * @param defField
   * @param parStr
   * @param maxResult
   * @return
   * @throws ParseException
   */
  @SuppressWarnings("unchecked")
  public List<Articles> queryContent(String defField, String parStr,
      int maxResult) throws ParseException {
    return this.fullTextQuery(defField, parStr, Articles.class, maxResult);
  }

}
