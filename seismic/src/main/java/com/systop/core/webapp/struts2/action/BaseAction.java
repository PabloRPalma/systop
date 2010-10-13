package com.systop.core.webapp.struts2.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ArrayUtils;
import com.systop.common.modules.security.user.UserUtil;
import com.systop.common.modules.security.user.model.User;
import com.systop.core.util.WebMockUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * BaseAction of the struts2
 * 
 * @author Sam
 * 
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseAction extends ActionSupport {

	/**
	 * 为子类提供Log功能，方便子类使用
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * struts2的ActionContext
	 */
	private ActionContext actionContext;

	/**
	 * 获得struts2的ActionContext
	 */
	protected final ActionContext getActionContext() {
		return (actionContext == null) ? actionContext = ActionContext
				.getContext() : actionContext;
	}

	/**
	 * 获得当前Action的名称
	 */
	protected final String getActionName() {
		return getActionContext().getName();
	}

	/**
	 * 获得当前Http Servlet Request.
	 * 如果在单元测试环境下，则每次调用都返回一个新的MockHttpServletRequest的实例
	 */
	protected final HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		if (getActionContext() != null) { // 从Struts2中取得Request
			request = (HttpServletRequest) getActionContext().get(
					StrutsStatics.HTTP_REQUEST);
		}

		if (request == null) {
			request = WebMockUtil.getHttpServletRequest();
		}

		return request;
	}

	/**
	 * 获得当前HttpServletResponse.
	 * 如果在单元测试环境下，则每次调用都返回一个新的MockHttpServletResponse的实例
	 */
	protected final HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		if (getActionContext() != null) { // 从Struts2中取得Response
			response = (HttpServletResponse) getActionContext().get(
					StrutsStatics.HTTP_RESPONSE);
		}

		if (response == null) {
			response = WebMockUtil.getHttpServletResponse();
		}

		return response;
	}

	/**
	 * 获得当前ServletContext. 如果在单元测试环境下，则每次调用都返回一个新的MockServletContext的实例.
	 */
	protected final ServletContext getServletContext() {
		ServletContext ctx = (ServletContext) getActionContext().get(
				StrutsStatics.SERVLET_CONTEXT);

		if (ctx == null) {
			ctx = WebMockUtil.getServletContext();
		}
		return ctx;
	}

	/**
	 * Return the real path of the given path within the web application, as
	 * provided by the servlet container.
	 * <p>
	 * Prepends a slash if the path does not already start with a slash.
	 * 
	 * @param path the path within the web application
	 * @return the corresponding real path or <code>null</code> if the path
	 *         cannot be resolved to a resource.
	 */
	protected String getRealPath(String path) {
		try {
			return WebUtils.getRealPath(getServletContext(), path);
		} catch (FileNotFoundException e) {
			logger.warn("Can't find real path '{}' in web context", path);
			e.printStackTrace();
			return null;
		}
	}

/**
	 * 直接输出数据，而不是通过struts重新定向.
	 * @param response The HttpServletResponse object to be write in
	 * @param text Content to be writed.
	 * @param contentType.
	 * @see {@link #renderJson(HttpServletResponse, String)
	 * @see {@link #renderXml(HttpServletResponse, String)
	 */
	protected void render(HttpServletResponse response, String text,
			String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出Json.
	 */
	protected void renderJson(HttpServletResponse response, String text) {
		render(response, text, "text/x-json;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXml(HttpServletResponse response, String text) {
		render(response, text, "text/xml;charset=UTF-8");
	}
	
	/**
	 * 防止内容被Browser缓存
	 */
	protected void preventFromCaching() {
	  getResponse().addHeader("Cache-Control", "no-cache");
    getResponse().addHeader("Pragma", "no-cache");
    getResponse().addHeader("Expires", "0");
	}

	/**
	 * 获取当前登录的用户信息
	 * @see {@link UserUtil#getPrincipal(HttpServletRequest)}
	 */
	 public User getPrincipal() {
	   return UserUtil.getPrincipal(getRequest());
	 }
	 
	  /**
	   * 使用XStream，将一个对象转变为json字符串，例如：<br>
	   * <pre>
	   * //输出{"user":{"loginId":"sss","isOpen":1,"createTime":"2010-02-06 22:29:44.156 CST","photos":[""]}}
	   * toJson("user", user, new String[]{"roles", "articles"});
	   * </pre>
	   * @param alias 别名，被转换的类型在json对象中的属性名称。
	   * @param target 被转换的对象
	   * @param omitFields 需要忽略的、位于<code>target</code>中的字段名称。也可以用@XStreamOmitField在字段上做标注。
	   * @return 如果<code>target</code>为<code>null</code>，返回'{"null":""}'字符串，否则返回json字符串。
	   */
	  protected String toJson(String alias, Object target, String...omitFields) {
	    XStream xstream = new XStream(new JettisonMappedXmlDriver());
	    xstream.setMode(XStream.NO_REFERENCES);
	    xstream.autodetectAnnotations(true); //自动处理XStream Annotations
	    if(target == null) {
	      xstream.toXML(null);
	    }
	    //别名
	    if(StringUtils.isNotBlank(alias)) {
	      xstream.alias(alias, target.getClass());
	    }
	    //忽略的字段
	    if(ArrayUtils.isNotEmpty(omitFields)) {
	      for(String omitField : omitFields) {
	        xstream.omitField(target.getClass(), omitField);
	      }
	    }
	    return xstream.toXML(target);
	  }
	  
	  /**
	   * 使用XStream，将一个对象转变为json字符串，例如：<br>
	   * <pre>
	   * //输出{"user":{"loginId":"sss","isOpen":1,"createTime":"2010-02-06 22:29:44.156 CST","photos":[""]}}
	   * toJson("user", user, new String[]{"roles", "articles"});
	   * </pre>
	   * @param aliasMap Class和别名Map，被转换的类型在json对象中的属性名称。
	   * @param target 被转换的对象
	   * @param omitFieldsMap 需要忽略的字段名称，Key值为字段所在的Class,
	   * value为该Class中被忽略的字段。也可以用@XStreamOmitField在字段上做标注。
	   * @return 如果<code>target</code>为<code>null</code>，返回'{"null":""}'字符串，否则返回json字符串。
	   */
	  protected String toJson(Map<Class, String> aliasMap, Object target, Map<Class, String[]> omitFieldsMap ) {
	    XStream xstream = new XStream(new JettisonMappedXmlDriver());
	    xstream.setMode(XStream.NO_REFERENCES);
	    xstream.autodetectAnnotations(true); //自动处理XStream Annotations
	    if(target == null) {
	      xstream.toXML(null);
	    }
	    //别名
	    if(MapUtils.isNotEmpty(aliasMap)) {
	      Set<Class> keys = aliasMap.keySet();
	      for(Class clazz : keys) {
	        xstream.alias(aliasMap.get(clazz), clazz);
	      }
	    }
	    //忽略的字段
	    if(MapUtils.isNotEmpty(omitFieldsMap)) {
	      Set<Class> keys = omitFieldsMap.keySet();
	      for(Class clazz : keys) {
	        String[] fields = omitFieldsMap.get(clazz);
	        for(String field : fields) {
	          xstream.omitField(clazz, field);  
	        }
	        
	      }
	    }
	    return xstream.toXML(target);
	  }
	  /**
	   * 使用XStream，将一个对象转变为json字符串，例如：<br>
	   * <pre>
	   * //输出{"loginId":"sss","isOpen":1,"createTime":"2010-02-06 22:29:44.156 CST","photos":[""]}
	   * toJson(user);
	   * </pre>
	   * @param target 被转换的对象
	   * @param omitFields 需要忽略的、位于<code>target</code>中的字段名称。也可以用@XStreamOmitField在字段上做标注。
	   * @return 如果<code>target</code>为<code>null</code>，返回'{}'字符串，否则返回json字符串。
	   */
	  protected String toJson(Object target, String...omitFields) {
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	      public HierarchicalStreamWriter createWriter(Writer writer) {
	        return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	      }
	    });
	    if(target == null) {
	      xstream.toXML(target);
	    }
	    xstream.autodetectAnnotations(true); //自动处理XStream Annotations
	    //忽略的字段
	    if(ArrayUtils.isNotEmpty(omitFields)) {
	      for(String omitField : omitFields) {
	        xstream.omitField(target.getClass(), omitField);
	      }
	    }
	    return xstream.toXML(target);
	  }
	  
	  /**
	   * 使用XStream，将一个对象转变为json字符串，例如：<br>
	   * <pre>
	   * //输出{"loginId":"sss","isOpen":1,"createTime":"2010-02-06 22:29:44.156 CST","photos":[""]}
	   * toJson(user);
	   * </pre>
	   * @param target 被转换的对象
	   * @param omitFields @param omitFieldsMap 需要忽略的字段名称，Key值为字段所在的Class,
	   * value为该Class中被忽略的字段。也可以用@XStreamOmitField在字段上做标注。
	   * @return 如果<code>target</code>为<code>null</code>，返回'{}'字符串，否则返回json字符串。
	   */
    protected String toJson(Object target,  Map<Class, String[]> omitFieldsMap) {
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	      public HierarchicalStreamWriter createWriter(Writer writer) {
	        return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	      }
	    });
	    if(target == null) {
	      xstream.toXML(target);
	    }
	    xstream.autodetectAnnotations(true); //自动处理XStream Annotations
	    //忽略的字段
	    if(MapUtils.isNotEmpty(omitFieldsMap)) {
	      Set<Class> keys = omitFieldsMap.keySet();
	      for(Class clazz : keys) {
	        String[] fields = omitFieldsMap.get(clazz);
	        for(String field : fields) {
	          xstream.omitField(clazz, field);  
	        }        
	      }
	    }
	    return xstream.toXML(target);
	  }
}
