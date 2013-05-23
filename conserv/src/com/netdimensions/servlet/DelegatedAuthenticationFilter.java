package com.netdimensions.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DelegatedAuthenticationFilter implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (new Random().nextBoolean()) {
			chain.doFilter(request, response);
		} else {
			response.setContentType("text/plain");
			response.getWriter().append("Not this time.");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}
}
