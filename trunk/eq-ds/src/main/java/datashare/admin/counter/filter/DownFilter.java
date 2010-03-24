package datashare.admin.counter.filter;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import datashare.admin.counter.model.DownCounter;
import datashare.admin.counter.service.DownCounterManager;

public class DownFilter implements Filter {


  @Autowired(required = true)
  private DownCounterManager downCounterManager;

  private DownCounter[] downCounters;

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String url = ((HttpServletRequest) request).getServletPath();
    save(url);   
    try {
      chain.doFilter(request, response);
    }finally{
      String contentType = ((HttpServletResponse)response).getContentType();
      if(!"".equals(contentType) && contentType != null){
        save(contentType.substring(0,contentType.indexOf(";")));
      }
    }    
  }

  
  /**
   * 保存数据下载次数
   * @param url
   */
  private void save(String url){
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    for (DownCounter model : downCounters) {
      if (antPathMatcher.match(model.getPattern(), url)) {
        DownCounter downCounter = new DownCounter();
        BeanUtils.copyProperties(model, downCounter);
        downCounter.setTime(new Date(new java.util.Date().getTime()));
        downCounterManager.create(downCounter);
      }
    }
  }
  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }

  

  public DownCounter[] getDownCounters() {
    return downCounters;
  }

  public void setDownCounters(DownCounter[] downCounters) {
    this.downCounters = downCounters;
  }

}
