package com.systop.cms.fulltext;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.systop.core.dao.hibernate.BaseHibernateDao;

/**
 * 全文检索基类
 * 
 * @author lunch
 */
public class BaseFullTextManager implements FullTextManager {

  /**
   * Log,子类可以直接使用，不必重新声明
   */
  protected Log log = LogFactory.getLog(getClass());

  /**
   * 中文分次，通过Spring配置。默认使用ChineseAnalyzer
   */
  @Autowired(required = true)
  @Qualifier("chineseAnalyzer")
  private Analyzer chineseAnalyzer;

  /**
   * Common Data Access Object
   */
  @Autowired(required = true)
  @Qualifier("baseHibernateDao")
  private BaseHibernateDao dao;

  /**
   * @see com.systop.common.core.service.FullTextManager1 #createParser(String,
   *      org.apache.lucene.analysis.Analyzer)
   */
  public QueryParser createParser(String defField, Analyzer analyzer) {

    return new QueryParser(defField, analyzer);
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1 #createChineseParser(java.lang.String)
   */
  public QueryParser createChineseParser(String defField) {
    return createParser(defField, getChineseAnalyzer());
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1#getFullTextSession()
   */
  public FullTextSession getFullTextSession() {
    return Search.getFullTextSession(getDao().getHibernateTemplate().getSessionFactory()
        .openSession());
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1
   *      #fullTextQuery(org.apache.lucene.search.Query, Class)
   */
  @SuppressWarnings("unchecked")
  public List fullTextQuery(Query query, Class entClass) {
    FullTextQuery fullTextQuery = this.getFullTextSession().createFullTextQuery(query, entClass);
    return fullTextQuery.list();
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1
   *      #fullTextQuery(org.apache.lucene.search.Query, Class, int)
   */
  @SuppressWarnings("unchecked")
  public List fullTextQuery(Query query, Class entClass, int maxResults) {
    FullTextQuery fullTextQuery = this.getFullTextSession().createFullTextQuery(query, entClass);
    fullTextQuery.setMaxResults(maxResults);
    return fullTextQuery.list();
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1 #fullTextQuery(java.lang.String, String,
   *      Class)
   */
  @SuppressWarnings("unchecked")
  public List fullTextQuery(String defField, String parStr, Class entClass) throws ParseException {
    return fullTextQuery(createChineseParser(defField).parse(parStr), entClass);
  }

  /**
   * @see com.systop.common.core.service.FullTextManager1 #fullTextQuery(String, String, Class, int)
   */
  @SuppressWarnings("unchecked")
  public List fullTextQuery(String defField, String parStr, Class entClass, int maxResults)
      throws ParseException {
    return fullTextQuery(createChineseParser(defField).parse(parStr), entClass, maxResults);
  }

  /**
   * getter for dao
   * 
   * @return
   */
  public BaseHibernateDao getDao() {
    return dao;
  }

  /**
   * setter for dao
   * 
   * @param dao
   */
  public void setDao(BaseHibernateDao dao) {
    this.dao = dao;
  }

  /**
   * getter for chineseAnalyzer
   * 
   * @return Analyzer
   */
  public Analyzer getChineseAnalyzer() {
    return chineseAnalyzer;
  }

  /**
   * setter for chineseAnalyzer
   * 
   * @param chineseAnalyzer
   */
  public void setChineseAnalyzer(Analyzer chineseAnalyzer) {
    this.chineseAnalyzer = chineseAnalyzer;
  }

}
