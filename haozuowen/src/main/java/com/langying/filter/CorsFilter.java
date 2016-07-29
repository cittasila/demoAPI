package com.langying.filter;

import com.langying.config.DomainConfig;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@CompileStatic
@TypeChecked

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

class CorsFilter implements Filter {
	private final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(this.getClass());


	@Autowired
	private Environment env;

	@Autowired
	private DomainConfig domainConfig;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		/*String referer=request.getHeader("Referer");
		String origin=request.getHeader("Origin");
		if(referer!=null){
			domainConfig.domain.each {String it->
				if(referer.indexOf(it+"/")!=-1)
					response.setHeader("Access-Control-Allow-Origin",it)
			}
		}else if(origin!=null){
			domainConfig.domain.each {String it->
				if(origin.indexOf(it)!=-1)
					response.setHeader("Access-Control-Allow-Origin",it)
			}
		}*/
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Origin, Accept, Content-Type,x-auth-token,x-xsrf-token,token");
		if (!"OPTIONS".equals(request.getMethod())) {
			if(request.getHeader("Cookie")!=null){
				request.setAttribute("sessionid",request.getSession().getId());
			}
			if(request.getHeader("uuid")!=null){
				request.setAttribute("sessionid",request.getHeader("uuid"));
			}
			if(request.getParameter("uuid")!=null){
				request.setAttribute("sessionid",request.getParameter("uuid").toString());
			}else {
				request.setAttribute("sessionid",request.getSession().getId());
			}
			String token=null;
			if(request.getParameter("token")!=null){
				token=request.getParameter(token);
			}else if(request.getHeader("token")!=null){
				token=request.getHeader("token");
			}
			if(request.getAttribute("path")==null) {
				request.setAttribute("path", request.getRequestURI().substring(request.getContextPath().length()));
			}
			if(token!=null){
				HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
				requestWrapper.addHeader("x-auth-token",token);

				chain.doFilter(requestWrapper, res);
			}else {
				chain.doFilter(req, res);
			}
		} else {
		}
	}

	public void init(FilterConfig filterConfig) {}
	public void destroy() {}

	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
		/**
		 * construct a wrapper for this request
		 *
		 * @param request
		 */
		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<String, String>();

		/**
		 * add a header with given name and value
		 *
		 * @param name
		 * @param value
		 */
		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		/**
		 * get the Header names
		 */
		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			for (String name : headerMap.keySet()) {
				names.add(name);
			}
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = Collections.list(super.getHeaders(name));
			if (headerMap.containsKey(name)) {
				values.add(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}

	}
}
