package eq.dataperm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Ehcache;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 根据角色和SQL id，得到该角色可访问的某个SQL
 * 
 * @author SAM
 * 
 */
@Component
public class SqlBuilder {
  private static Logger logger = LoggerFactory.getLogger(SqlBuilder.class);
  /**
   * 用于缓存SQL
   */
  private Ehcache cache;

  /**
   * The locations of the sql.xml files. They are often some of the string with Sping-style
   * resource. A ".class" subfix can make the scaning more precise.
   * <p>
   * example:
   * 
   * 
    &lt;list&gt;
                &lt;value&gt;classpath*:com/systop/** /sqls.xml&lt;/value&gt;
     &lt;/list>

   * 
   */

  private String[] sqlXmlLocations;

  
 
  /**
   * 从所有sql xml文件中，找到符合ID和角色要求的sql，如果没有找到，则返回<code>null</code>
   * 
   * @param sqlId 给出要找的SQL ID
   * @param role 给出角色名称
   * @return SQL
   * @throws IllegalArgumentException 如果没有找到符合要求的SQL
   */
  public String getSql(String sqlId, String role) {
    //从缓存中找到SQL
    String key = this.getKey(sqlId, role);
    net.sf.ehcache.Element e = cache.get(key);
    if(e != null) {
      return (String) e.getObjectValue();
    }
    //如果缓存中没有，则从xml中的查找
    String sql = null;
    Set<Resource> resources = scanSqlXmls(); //扫描classpath下的sql xml文件
    
    for(Resource resource : resources) {
      sql = findSql(resource, sqlId, role);
      if(StringUtils.hasLength(sql)) {
        //如果找到了sql，则存入缓存
        e = new net.sf.ehcache.Element(key, sql);
        cache.put(e);
        break;
      }
    }
    //如果没有找到需要的SQL，则抛出异常
    if(!StringUtils.hasLength(sql)) {
      throw new IllegalArgumentException("The sql [" + sqlId + "], can't be found.");
    }
    return sql;
  }
  
  /**
   * 返回缓存中的key
   */
  private String getKey(String sqlId, String role) {
    return new StringBuilder(100).append(sqlId).append("_").append(role).toString();
  }
  
  /**
   * 从一个sql xml中寻找合适的SQL
   * @param resource
   * @return
   */
  private String findSql(Resource resource, String sqlId, String role) {
    String sql = null;
    SAXReader reader = new SAXReader();
    try {
      Document doc = reader.read(resource.getInputStream());
      //找到属性id为<code>sqlId</code>的sql节点
      Element sqlEle = (Element) doc.selectSingleNode(sqlXPath(sqlId));
      if(sqlEle == null) {
        logger.debug("Can't find sql [{}] in [{}]", sqlId, resource.getFilename());
        return null;
      }
      //找到角色节点，其table属性为该角色可以访问的表名
      Element roleEle = (Element) doc.selectSingleNode(roleXPath(sqlId, role));
      String table = "";
      if(roleEle != null) {//如果角色节点存在，那么其table属性为表名，否则表示该SQL不涉及权限
        table = roleEle.attributeValue("table"); 
      }
      //找到statement节点
      Element stmtEle = (Element) doc.selectSingleNode(statementXPath(sqlId));
      //格式化，得到所需的SQL
      sql = MessageFormat.format(stmtEle.getText(), new Object[]{table});
      
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return sql;
  }
  
  private String sqlXPath(String sqlId) {
    return new StringBuilder(100).append("//sqls/sql[@id=\"")
      .append(sqlId).append("\"]").toString();
  }
  
  private String roleXPath(String sqlId, String role) {
    return new StringBuilder(100).append(sqlXPath(sqlId)).append("/")
    .append(role.toLowerCase()).toString();
  }
  
  private String statementXPath(String sqlId) {
    return new StringBuilder(100).append(sqlXPath(sqlId)).append("/statement").toString();
  }

  private Set<Resource> scanSqlXmls() {
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    Set<Resource> sqlXmlResources = new HashSet<Resource>();
    if (sqlXmlLocations != null) {
      try {
        for (String locations : sqlXmlLocations) {
          // Resolve the resources
          Resource[] resources = resourcePatternResolver.getResources(locations);
          for (Resource resource : resources) {
            sqlXmlResources.add(resource);
            logger.debug("A sql xml has been found. \n({})", resource.getFilename());
          }
        }

      } catch (IOException e) {
        logger.error("I/O failure during classpath scanning, ({})", e.getMessage());
        e.printStackTrace();
        throw new RuntimeException(e);
      } catch (LinkageError e) {
        logger.error("LinkageError ({})", e.getMessage());
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }

    return sqlXmlResources;
  }

  /**
   * @param cache the cache to set
   */
  @Autowired(required = true)
  public void setCache(Ehcache cache) {
    this.cache = cache;
  }

  /**
   * @param sqlXmlLocations the sqlXmlLocations to set
   */
  public void setSqlXmlLocations(String[] sqlXmlLocations) {
    this.sqlXmlLocations = sqlXmlLocations;
  }
}
