package com.systop.cms.fulltext;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextSession;

/**
 * 全文检索接口
 * @author lunch
 */
@SuppressWarnings("unchecked")
public interface FullTextManager {

	/**
	 * 返回全文检索的条件解析器
	 * 
	 * @param defField
	 * @param analyzer
	 * @return
	 */
	public abstract QueryParser createParser(String defField,
			Analyzer analyzer);

	/**
	 * 返回中文分词的解析器
	 * 
	 * @param defField
	 * @return
	 */
	public abstract QueryParser createChineseParser(String defField);

	/**
	 * 返回全文检索用的Session
	 * 
	 * @return
	 */
	public abstract FullTextSession getFullTextSession();

	/**
	 * 执行全文检索的方法
	 * @param query
	 * @param entClass
	 * @return
	 */
  public abstract List fullTextQuery(Query query, Class entClass);

	/**
	 * 执行全文检索的方法
	 * @param query
	 * @param entClass
	 * @param maxResults
	 * @return
	 */
  public abstract List fullTextQuery(Query query, Class entClass,
			int maxResults);

	/**
	 * 执行全文检索
	 * @param defField 构建QueryParser的默认查询字段
	 * @param parStr the query string to be parsed
	 * @param entClass 针对的实体类
	 * @return
	 * @throws ParseException 
	 */
	public abstract List fullTextQuery(String defField, String parStr,
			Class entClass) throws ParseException;

	/**
	 * 执行全文检索
	 * @param defField 构建QueryParser的默认查询字段
	 * @param parStr the query string to be parsed
	 * @param entClass 针对的实体类
	 * @param maxResults 返回最大行数
	 * @return
	 * @throws ParseException 
	 */
	public abstract List fullTextQuery(String defField, String parStr,
			Class entClass, int maxResults) throws ParseException;

}