package com.systop.common.modules.template.freemarker.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 同{@link freemarker.ext.servlet.AllHttpScopesHashModel}
 * @author Sam
 *
 */
@SuppressWarnings("unchecked")
public class WebParametersHashModel extends SimpleHash {
  private final ObjectWrapper wrapper;
  private final ServletContext context;
  private final HttpServletRequest request;
  
  private final Map unlistedModels = new HashMap();
   
  WebParametersHashModel(ObjectWrapper wrapper, ServletContext context, HttpServletRequest request) {
      this.wrapper = wrapper;
      this.context = context;
      this.request = request;
  }
  
  /**
   * Stores a model in the hash so that it doesn't show up in <tt>keys()</tt>
   * and <tt>values()</tt> methods. Used to put the Application, Session,
   * Request, RequestParameters and JspTaglibs objects.
   * @param key the key under which the model is stored
   * @param model the stored model
   */
  public void putUnlistedModel(String key, TemplateModel model)
  {
      unlistedModels.put(key, model);
  }

  public TemplateModel get(String key) throws TemplateModelException {
      // Lookup in page scope
      TemplateModel model = super.get(key);
      if(model != null) {
          return model;
      }

      // Look in unlisted models
      model = (TemplateModel)unlistedModels.get(key);
      if(model != null) {
          return model;
      }
      
      // Lookup in request scope
      Object obj = request.getAttribute(key);
      if(obj != null) {
          return wrapper.wrap(obj);
      }

      // Lookup in session scope
      HttpSession session = request.getSession(false);
      if(session != null) {
          obj = session.getAttribute(key);
          if(obj != null) {
              return wrapper.wrap(obj);
          }
      }

      // Lookup in application scope
      obj = context.getAttribute(key);
      if(obj != null) {
          return wrapper.wrap(obj);
      }

      // return wrapper's null object (probably null).        
      return wrapper.wrap(null);
  }
}
