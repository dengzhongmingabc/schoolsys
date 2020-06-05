package com.honorfly.schoolsys.utils.web;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedFilter implements Filter {
	/** ������Ϣ */
	private static final String ERROR_MESSAGE = "Access denied!";

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader(new String(Base64.decodeBase64("UG93ZXJlZEJ5"),
				"utf-8"), new String(Base64.decodeBase64("bXNuNi5jb20=\n"),
				"utf-8"));
		response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
	}
}
