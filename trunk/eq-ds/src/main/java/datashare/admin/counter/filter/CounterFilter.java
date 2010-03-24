package datashare.admin.counter.filter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy;
import javax.persistence.Transient;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import datashare.admin.counter.model.UrlEntry;
import datashare.admin.counter.service.UrlEntryManager;

/**
 * 计数器Filter。因为用到了Spring管理的Bean，所以不能在web.xml中使用，应该 用{@link org.acegisecurity.util.FilterChainProxy}作为代理.
 * @author SAM
 */
@Deprecated
public class CounterFilter implements Filter {
  private static Logger logger = LoggerFactory.getLogger(CounterFilter.class);
  /**
   * 用于缓存命中过的URL，以免下一次仍然用AntPathMatcher进行匹配。每次有效命中，
   * 将实际的URL作为Key，将对应的UrlEntry作为Value保存到cache中.
   */
  private ConcurrentMap<String, UrlEntry> cache = new ConcurrentHashMap<String, UrlEntry>();
  
  @Autowired(required = true)
  private UrlEntryManager urlEntryManager;
  
  /**
   * 总点击量计数器，用于控制何时更新数据库。每次有效命中都会增加（无论命中那个UrlEntry）,
   * 当可以整除{@link #saveFreq}的时候，保存所有{{@link #urlEntries}
   */
  private AtomicInteger counter = new AtomicInteger();
  
  /**
   * 保存的频率
   */
  private Integer saveFreq = 20;
  /**
   * 需要过滤的URL
   */
  private UrlEntry[] urlEntries;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if(hit((HttpServletRequest) request)) {
      int c = counter.getAndIncrement();
      if(c % saveFreq == 0) {
        logger.debug("Save url entires.");
        save();
      }
    }
    chain.doFilter(request, response);
  }
  
  /**
   * 保存(新建或者合并){@link #urlEntries}中所有的数据，原有的hits加上{@link UrlEntry#getCounter()}
   * 的值，作为新的hits.
   */
  @Transient
  public void save() {
    for (UrlEntry model : urlEntries) {
      UrlEntry urlEntry = urlEntryManager.get(model.getPattern());
      if (urlEntry != null) {
        urlEntry.setHits(urlEntry.getHits() + model.getCounter());
        urlEntryManager.update(urlEntry);
      } else {
        urlEntry = new UrlEntry();
        BeanUtils.copyProperties(model, urlEntry);
        urlEntry.setHits(model.getCounter());
        urlEntryManager.create(urlEntry);
      }
      model.clear();
    }
  }

  /**
   * 销毁之前执行，将{@link #urlEntries}中的数据更新(merge)到数据库.
   */
  @PreDestroy
  public void preDestroy() {
    save();
  }

  /**
   * {@link #hit(HttpServletRequest)}用于操作一次成功的命中，它需要完成以下工作：
   * <br>
   * <ul>
   *    <li>首先将请求的Url与{@link #cache}中的数据进行匹配.</li>
   *    <li>如果{@link #cache}中没有，则与{@link #urlEntries}进行AntPath匹配，如果匹配成功，则存入{@link #cache}</li>
   *    <li>如果上述两步匹配成功，则执行{@link UrlEntry#inc()},并返回{@code true}</li>
   *    <li>否则返回{@code fasle}</li>
   * </ul>
   * 
   * @param request HttpServletRequest
   * @return Instance of UrlEntry, or <code>url</code>
   */
  private boolean hit(HttpServletRequest request) {
    String url = request.getServletPath();
    
    UrlEntry urlEntry = cache.get(url);
    
    if (urlEntry == null) {
      AntPathMatcher antPathMatcher = new AntPathMatcher();
      for (UrlEntry model :  urlEntries) {
        if (antPathMatcher.match(model.getPattern(), url)) {
          cache.put(url, model);
        }
      }
    }
    
    if (urlEntry != null) {
      urlEntry.inc();
      return true;
    }
    return false;
  }

  /**
   * Do Nothing
   */
  @Override
  public void destroy() {
  }

  /**
   * do nothing
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  /**
   * @param urlEntries the urlEntries to set
   */
  public void setUrlEntries(UrlEntry[] urlEntries) {
    this.urlEntries = urlEntries;
  }

  /**
   * @param saveFreq the saveFreq to set
   */
  public void setSaveFreq(Integer saveFreq) {
    this.saveFreq = saveFreq;
  }
}
