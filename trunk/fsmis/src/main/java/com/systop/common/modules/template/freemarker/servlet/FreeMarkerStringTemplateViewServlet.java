package com.systop.common.modules.template.freemarker.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.systop.cms.CmsConstants;
import com.systop.cms.webview.exception.ParseURLToTemplateException;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StringUtil;

/**
 * 参考自freemarker.ext.servlet.FreemarkerServlet
 *  支持自定义标签的使用，支持自定义扩展名拦截．
 */
@SuppressWarnings("serial")
public class FreeMarkerStringTemplateViewServlet extends
		javax.servlet.http.HttpServlet {

	/**
	 * 为子类提供Log功能，方便子类使用
	 */
	protected Log log = LogFactory.getLog(getClass());

	/** TemplatePath */
	private static final String TEMPLATE_PATH = "TemplatePath";

	/** NoCache */
	private static final String NOCACHE = "NoCache";

	/** TemplateDelay */
	private static final String TEMPLATE_DELAY = "template_update_delay";

	/** DefaultEncoding */
	private static final String DEF_ENCODING = "default_encoding";

	/** Request */
	public static final String KEY_REQUEST = "Request";

	/** __FreeMarkerServlet.Request__ */
	public static final String KEY_REQUEST_PRIVATE =
										"__FreeMarkerServlet.Request__";

	/** RequestParameters */
	public static final String KEY_REQUEST_PARAMETERS = "RequestParameters";

	/** Session */
	public static final String KEY_SESSION = "Session";

	/** Application */
	public static final String KEY_APPLICATION = "Application";

	/** __FreeMarkerServlet.Application__ */
	public static final String KEY_APPLICATION_PRIVATE =
								"__FreeMarkerServlet.Application__";

	/** JspTaglibs */
	public static final String KEY_JSP_TAGLIBS = "JspTaglibs";

	/** .freemarker.Request */
	private static final String ATTR_REQUEST_MODEL = ".freemarker.Request";

	/** .freemarker.RequestParameters */
	private static final String ATTR_REQUEST_PARAMETERS_MODEL =
											".freemarker.RequestParameters";

	/** .freemarker.Session */
	private static final String ATTR_SESSION_MODEL = ".freemarker.Session";

	/** .freemarker.Application */
	private static final String ATTR_APPLICATION_MODEL =
													".freemarker.Application";

	/** .freemarker.JspTaglibs */
	private static final String ATTR_JSP_TAGLIBS_MODEL =
													".freemarker.JspTaglibs";

	/** 日期 */
	private static final String EXPIRATION_DATE;

	static {
		GregorianCalendar expiration = new GregorianCalendar();
		expiration.roll(Calendar.YEAR, -1);
		SimpleDateFormat httpDate = new SimpleDateFormat(
				"yyyy-MMM-dd HH:mm:ss", java.util.Locale.CHINA);
		EXPIRATION_DATE = httpDate.format(expiration.getTime());
	}

	// 暂时不使用此属性，如果需要其它方式加载Template才使用此属性
	//private String templatePath;

	/**
	 * response返回是否使用缓存
	 */
	private boolean nocache;

	/**
	 * 创新Freemarker模板的必要条件
	 */
	private Configuration config;

	/**
	 * 采用BEANS_WRAPPER
	 */
	private ObjectWrapper wrapper;

	/**
	 * text/html
	 */
	private String contentType;

	/**
	 * 采用StringLoader，从数据库中读取模板信息
	 */
	//private StringTemplateLoader strTmpt;

	/**
	 * Servlet 初始化
	 */
	@SuppressWarnings("unchecked")
  public void init() throws ServletException {

		try {
			config = new Configuration();

			config.setTemplateExceptionHandler(
					TemplateExceptionHandler.HTML_DEBUG_HANDLER);

			contentType = "text/html";

			// 采用BEANS_WRAPPER
			wrapper = ObjectWrapper.BEANS_WRAPPER;
			config.setObjectWrapper(wrapper);

			// 默认采用的是StringTemplateLoader，即从数据库中获得模板内容．
			//　如果需要其它的加载方式可以将这段代码注释取消．
			/*templatePath = getInitParameter(TEMPLATE_PATH);

			log.debug("templatePath value is:" + templatePath);

			if (templatePath == null) { //在配置Servlet的时候不去设置templatePath
				log.debug("TemplateLoader is StringTemplateLoader");
				//config.setTemplateLoader(strTmpt);

			} else {
				if (templatePath.startsWith("web://")) {
					config.setTemplateLoader(new WebappTemplateLoader(
							getServletContext(), templatePath.substring(5)));

				} else if (templatePath.startsWith("class://")) {
					// substring(7)取得真实的模板路径
					config.setClassForTemplateLoading(getClass(), templatePath
							.substring(7));

				} else {
					config.setTemplateLoader(new WebappTemplateLoader(
							getServletContext(), "templates"));

				}
			}*/

			// 初始化所有的Servlet参数
			Enumeration initpnames = getServletConfig().getInitParameterNames();
			while (initpnames.hasMoreElements()) {
				String name = (String) initpnames.nextElement();
				String value = getInitParameter(name);

				if (name == null) {
					throw new ServletException(this.getClass().toString()
							+ "需要一些初始化参数，web.xml可能尚未完成.");
				}
				if (value == null) {
					throw new ServletException(this.getClass().toString()
							+ "有部分初始化参数未被赋值，web.xml可能尚未完成.");
				}

				if (name.equals(TEMPLATE_PATH)) {
					// ignore: we have already processed these do nothing..
					log.debug("忽略" + TEMPLATE_PATH);

				} else if (name.equals(DEF_ENCODING)) { // set DefaultEncoding
					log.debug(DEF_ENCODING + " value is:" + value);
					config.setDefaultEncoding(value);

				} else if (name.equals(TEMPLATE_DELAY)) { // 模板延迟更新时间
					try {
						log.debug(TEMPLATE_DELAY + " value is:" + value);
						config.setTemplateUpdateDelay(Integer.parseInt(value));
					} catch (NumberFormatException e) {
						throw new ServletException(e.getMessage() + ". '"
								+ TEMPLATE_DELAY + "'必须是整数");
					}

				} else if (name.equals(NOCACHE)) { // 设置缓存
					log.debug(NOCACHE + " value is :" + value);
					nocache = StringUtil.getYesNo(value);

				} else {
					// 设置其它参数，嘿嘿，如果参数名称不符合Configuration要求肯定要Exception
					config.setSetting(name, value);
				}
			}
		} catch (ServletException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/** Get请求 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			process(request, response);
	}

	/** Post请求 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			process(request, response);
	}

	/**
	 * 向StringTemplateLoader中添加模板内容
	 * @throws ServletException 
	 */
	private StringTemplateLoader addStringTemplate(
			StringTemplateContext strTmpCtx) throws ServletException {
		if (strTmpCtx == null) {
			throw new ServletException("StringTemplateContext is null");
		}
		
		if (strTmpCtx.getTemplateContent() != null) {
			StringTemplateLoader stl = getStrTmpt();
			stl.putTemplate(strTmpCtx.getTemplateName(),
					strTmpCtx.getTemplateContent());
			setStrTmpt(stl);
			//重写添加TemplateLoader
			log.debug("StringTemplateLoader成功添加模板'"
					+ strTmpCtx.getTemplateName() + "'");
		}
		
		return getStrTmpt();

	}

	/**
	 * 模板解析过程
	 * @throws ParseURLToTemplateException 
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		StringTemplateLoader  strTmpt = getStrTmpt();
		if (strTmpt == null) {
			strTmpt = new StringTemplateLoader();
			setStrTmpt(strTmpt);
		}

		// 自定义类型StringTemplateContext,存放［模板名称］和［模板内容］
		StringTemplateContext strTmpCtx = null;
		try {
			strTmpCtx = reqUrlToModelCtx(req, strTmpt);
		} catch (ParseURLToTemplateException pe) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			pe.printStackTrace();
		}

		//添加模板
		strTmpt = addStringTemplate(strTmpCtx);
		config.setTemplateLoader(strTmpt);

		Template template = config.getTemplate(strTmpCtx.getTemplateName());
		Object attrContentType = template.getCustomAttribute("content_type");
		if (attrContentType != null) {
			resp.setContentType(attrContentType.toString());
		} else {
			resp.setContentType(contentType + "; charset="
					+ template.getEncoding());
		}

		setBrowserCaching(resp);

		ServletContext servletContext = getServletContext();

		try {
			TemplateModel model = createModel(wrapper, servletContext, req,
					resp, strTmpCtx.getModel());

			template.process(model, resp.getWriter());

		} catch (TemplateException te) {
			ServletException e = new ServletException(
					"Error executing FreeMarker template", te);
			try {
				e.getClass().getMethod("initCause",
						new Class[] { Throwable.class }).invoke(e,
						new Object[] { te });
			} catch (Exception ex) {
				// Can't set init cause, we're probably running on a pre-1.4
				// JDK, oh well...
			}
			throw e;
		}
	}

	/**
	 * 创建Freemarker模板的model
	 */
	@SuppressWarnings("unchecked")
  protected TemplateModel createModel(ObjectWrapper wrap,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Map model)
			throws TemplateModelException {

	  WebParametersHashModel params = new WebParametersHashModel(wrap,
				servletContext, request);
		

		// Create hash model wrapper for servlet context (the application)
		ServletContextHashModel servletContextModel =
					(ServletContextHashModel) servletContext
							.getAttribute(ATTR_APPLICATION_MODEL);

		if (servletContextModel == null) {
			servletContextModel = new ServletContextHashModel(this, wrap);

			servletContext.setAttribute(ATTR_APPLICATION_MODEL,
					servletContextModel);

			TaglibFactory taglibs = new TaglibFactory(servletContext);
			servletContext.setAttribute(ATTR_JSP_TAGLIBS_MODEL, taglibs);
		}
		params.putUnlistedModel(KEY_APPLICATION, servletContextModel);
		params.putUnlistedModel(KEY_APPLICATION_PRIVATE, servletContextModel);
		params.putUnlistedModel(KEY_JSP_TAGLIBS, (TemplateModel) servletContext
				.getAttribute(ATTR_JSP_TAGLIBS_MODEL));

		// Create hash model wrapper for session
		/*
		 * 不要从Session中获取了，直接覆盖即可，By Sam,2009-05-12 
		HttpSessionHashModel sessionModel;
		HttpSession session = request.getSession();
		sessionModel = (HttpSessionHashModel) session
				.getAttribute(ATTR_SESSION_MODEL);
		if (sessionModel == null || sessionModel.isZombie()) {
			sessionModel = new HttpSessionHashModel(session, wrap);
			session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
		}
		params.putUnlistedModel(KEY_SESSION, sessionModel);
    */
		//创建HttpSessionHashModel
		HttpSession session = request.getSession();
		HttpSessionHashModel sessionModel = new HttpSessionHashModel(session, wrap);
		session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
		params.putUnlistedModel(KEY_SESSION, sessionModel);
		
		// Create hash model wrapper for request
		HttpRequestHashModel requestModel = (HttpRequestHashModel) request
				.getAttribute(ATTR_REQUEST_MODEL);
		if (requestModel == null || requestModel.getRequest() != request) {
			requestModel = new HttpRequestHashModel(request, response, wrap);
			request.setAttribute(ATTR_REQUEST_MODEL, requestModel);
			request.setAttribute(ATTR_REQUEST_PARAMETERS_MODEL,
					new HttpRequestParametersHashModel(request));
		}
		params.putUnlistedModel(KEY_REQUEST, requestModel);
		params.putUnlistedModel(KEY_REQUEST_PRIVATE, requestModel);

		// Create hash model wrapper for request parameters
		HttpRequestParametersHashModel requestParametersModel = 
							(HttpRequestParametersHashModel) request
							.getAttribute(ATTR_REQUEST_PARAMETERS_MODEL);

		params.putUnlistedModel(KEY_REQUEST_PARAMETERS, requestParametersModel);

		params.putAll(model);

		return params;
	}

	/**
	 * 需要有子类重写，返回需要的TemplateModelContext[templateName Model]
	 * 
	 * @return
	 * @throws ParseURLToTemplateException 
	 * @throws Exception 
	 */
	protected StringTemplateContext reqUrlToModelCtx(HttpServletRequest req,
			StringTemplateLoader tmLoader) throws ParseURLToTemplateException {

		// 需要子类去重写，完成封装数据
		return null;
	}

	/**
	 * If the parameter "nocache" was set to true, generate a set of headers
	 * that will advise the HTTP client not to cache the returned page.
	 */
	private void setBrowserCaching(HttpServletResponse res) {
		if (nocache) {
			// HTTP/1.1 + IE extensions
			res.setHeader("Cache-Control",
					"no-store, no-cache, must-revalidate, "
							+ "post-check=0, pre-check=0");
			// HTTP/1.0
			res.setHeader("Pragma", "no-cache");
			// Last resort for those that ignore all of the above
			res.setHeader("Expires", EXPIRATION_DATE);
		}
	}

	/**
	 * getter strTmpt
	 * @return
	 */
	private StringTemplateLoader getStrTmpt() {
		return (StringTemplateLoader) getServletContext().getAttribute(
				CmsConstants.STRING_TEMPLATE_LOADER);
	}

	/**
	 * setter strTmpt
	 * @param strTmpt
	 */
	private void setStrTmpt(StringTemplateLoader strTmpt) {
		getServletContext().setAttribute(
				CmsConstants.STRING_TEMPLATE_LOADER, strTmpt);
	}
}