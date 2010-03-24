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

import org.springframework.beans.factory.annotation.Autowired;

import datashare.admin.counter.model.SetCounter;
import datashare.admin.counter.service.SetCounterManager;

public class SiteFilter implements Filter {

  
 
  @Autowired(required=true)
  private SetCounterManager setCounterManager;

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
      String url = ((HttpServletRequest)request).getServletPath();
      SetCounter model = new SetCounter();
      model.setUrl(url);
      model.setVisiteTime(new Date(new java.util.Date().getTime()));
      setCounterManager.save(model);
      chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }

}
