package com.iemr.common.identity.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlockingHttpMethodInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String method = request.getMethod();
		if (!("GET".equals(method) || "POST".equals(method))) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return false;
		}
		return true;
	}

}
