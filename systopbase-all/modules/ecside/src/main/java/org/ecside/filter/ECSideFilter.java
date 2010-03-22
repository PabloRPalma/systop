/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ecside.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.common.log.LogHandler;
import org.ecside.core.ECSideConstants;
import org.ecside.core.ECSideContext;
import org.ecside.core.Preferences;
import org.ecside.core.TableConstants;
import org.ecside.core.TableModelUtils;
import org.ecside.core.context.ContextUtils;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.core.context.ServletInitContext;
import org.ecside.core.context.WebContext;
import org.ecside.preferences.PreferencesConstants;
import org.ecside.preferences.TableProperties;
import org.ecside.resource.MimeUtils;
import org.ecside.util.ECSideUtils;
import org.ecside.util.ExceptionUtils;
import org.ecside.util.RequestUtils;
import org.ecside.view.ViewResolver;

/**
 * @author Wei Zijun
 * 
 */

@SuppressWarnings("unchecked")
public class ECSideFilter implements Filter {

	protected static Log logger = LogFactory.getLog(ECSideFilter.class);

	protected static String encoding = ECSideConstants.DEFAULT_ENCODING;

	protected static boolean useEncoding = true;

	protected static boolean useEasyDataAccess = true;

	protected static boolean responseHeadersSetBeforeDoFilter = true;

	public String servletRealPath;

	public ServletContext servletContext;

	protected FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		servletContext = filterConfig.getServletContext();
		servletRealPath = servletContext.getRealPath("/");

		initEncoding();
		initProperties();

		String responseHeadersSetBeforeDoFilter = filterConfig
				.getInitParameter("responseHeadersSetBeforeDoFilter");
		if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter)) {
			ECSideFilter.responseHeadersSetBeforeDoFilter = Boolean.valueOf(
					responseHeadersSetBeforeDoFilter).booleanValue();
		}

	}

	public void initProperties() throws ServletException {
		WebContext context = new ServletInitContext(servletContext);
		Preferences preferences = new TableProperties();
		preferences.init(context, TableModelUtils
				.getPreferencesLocation(context));

		ECSideContext.DEFAULT_PAGE_SIZE = new Integer(preferences
				.getPreference(PreferencesConstants.TABLE_ROWS_DISPLAYED))
				.intValue();

	}

	public void initEncoding() throws ServletException {
		String encodingValue = filterConfig.getInitParameter("encoding");
		String useEncodingC = filterConfig.getInitParameter("useEncoding");

		if (encodingValue != null && encodingValue.length() > 0) {
			encoding = encodingValue;
		}
		if (useEncodingC == null || useEncodingC.equalsIgnoreCase("true")
				|| useEncodingC.equalsIgnoreCase("yes")
				|| useEncodingC.equalsIgnoreCase("on")
				|| useEncodingC.equalsIgnoreCase("1")) {
			useEncoding = true;
		} else {
			useEncoding = false;
		}

		ECSideContext.ENCODING = encoding;

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (RequestUtils.isAJAXRequest(request)) {
			doAjaxFilter(request, response, chain);
			return;
		}

		doEncoding(request, response);

		WebContext context = new HttpServletRequestContext(request);
		boolean isExported = ExportFilterUtils.isExported(context);

		if (isExported) {
			doExportFilter(request, response, chain, context);
			context = null;
			return;
		}

		chain.doFilter(request, response);

	}

	public void doEncoding(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		if (useEncoding || (request.getCharacterEncoding() == null)) {
			if (encoding != null)
				// Set the same character encoding for the request and the
				// response
				request.setCharacterEncoding(encoding);
			// response.setCharacterEncoding(encoding);
		}
	}

	public void doAjaxFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		
		String findAjaxZoneAtClient = request
				.getParameter(TableConstants.AJAX_FINDZONE_CLIENT);
		if (findAjaxZoneAtClient != null
				&& ("false".equalsIgnoreCase(findAjaxZoneAtClient) || "0"
						.equals(findAjaxZoneAtClient))) {
			ECSideAjaxResponseWrapper bufferResponseWrapper = new ECSideAjaxResponseWrapper(
					response);
			try {

				String ectableId = request
						.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
				
				chain.doFilter(request, bufferResponseWrapper);
				String zone = bufferResponseWrapper.findSubstring(ECSideUtils
						.getAjaxBegin(ectableId), ECSideUtils
						.getAjaxEnd(ectableId));
				HttpServletResponse originalResponse = bufferResponseWrapper
						.getOriginalResponse();
				if (zone != null) {
					originalResponse.getOutputStream().write(
							zone.getBytes("UTF-8"));
				}
				// originalResponse.flushBuffer();
				originalResponse.getOutputStream().flush();
				originalResponse.getOutputStream().close();
			} catch (Exception e) {
				LogHandler.errorLog(logger, e);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().println(
						"Exception:<br />\n"
								+ ExceptionUtils.formatStackTrace(e)
										.replaceAll("\n", "<br/>\n"));
				response.getWriter().close();

			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void doExportFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, WebContext context)
			throws IOException, ServletException {
		String exportFileName = ExportFilterUtils.getExportFileName(context);
		boolean isPrint = "_print_".equals(exportFileName);

		try {
			if (isPrint) {
				chain.doFilter(request, response);
			} else {
				request.setAttribute(ContextUtils.RESPONSE_OUTPUTSTREAM_KEY,
						response.getOutputStream());
				HttpServletResponseWrapper responseWrapper = new ExportResponseWrapper(
						response);
				setResponseHeaders(request, response, exportFileName);
				chain.doFilter(request, responseWrapper);
				responseWrapper = null;
			}
			handleExport(request, response, context);
		} catch (IOException e) {
			throw e;
		} finally {
			// request.setAttribute(ContextUtils.RESPONSE_OUTPUTSTREAM_KEY,
			// "".intern());
		}

	}

	public void handleExport(HttpServletRequest request,
			HttpServletResponse response, WebContext context) {
		try {
			Object viewData = request.getAttribute(TableConstants.VIEW_DATA);
			if (viewData != null) {
				Preferences preferences = new TableProperties();
				preferences.init(null, TableModelUtils
						.getPreferencesLocation(context));
				String viewResolver = (String) request
						.getAttribute(TableConstants.VIEW_RESOLVER);
				Class classDefinition = Class.forName(viewResolver);
				ViewResolver handleExportViewResolver = (ViewResolver) classDefinition
						.newInstance();
				request.setAttribute(TableConstants.SERVLET_REAL_PATH,
						servletRealPath);
				handleExportViewResolver.resolveView(request, response,
						preferences, viewData);
				// response.flushBuffer();
				if (!response.isCommitted()) {
					response.getOutputStream().flush();
					response.getOutputStream().close();
				}
			}
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}
	}

	public void setResponseHeaders(HttpServletRequest request,
			HttpServletResponse response, String exportFileName) {
		String mimeType = MimeUtils.getFileMimeType(exportFileName);
		// response.reset();
		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		}
		// response.setHeader("Content-Type",mimeType);
		try {
			exportFileName = RequestUtils.encodeFileName(request,
					exportFileName);

		} catch (UnsupportedEncodingException e) {
			String errorMessage = "Unsupported response.getCharacterEncoding() ["
					+ "UTF-8" + "].";
			LogHandler.errorLog(logger,
					"TDExportFilter.setResponseHeaders() - " + errorMessage);
		}
		// ec:
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ exportFileName + "\"");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));

	}

	// TODO :
	protected void webProxy(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		URL url = new URL(request.getParameter("url"));
		BufferedReader bf = new BufferedReader(new InputStreamReader(url
				.openStream()));
		String line;
		while ((line = bf.readLine()) != null) {
			out.println(line);
		}
		bf.close();
	}

	
	public static String getExportFileName(ServletRequest servletRequest) {
		String fileName = servletRequest
				.getParameter(ECSideConstants.EASY_DATA_EXPORT_FILENAME);
		if (StringUtils.isBlank(fileName)) {
			fileName = "excel.xls";
		}
		return StringUtils.isNotBlank(fileName) ? fileName : null;
	}

	/*
	public Object getBean(HttpServletRequest request, String beanName) {
		return ExportFilterUtils.getBean(request, beanName);
	}*/

	public void destroy() {

		this.filterConfig = null;

	}

}
