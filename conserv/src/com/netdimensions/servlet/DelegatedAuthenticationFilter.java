package com.netdimensions.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;

public class DelegatedAuthenticationFilter implements Filter {
	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		final String userId = (String) session.getAttribute("userId");
		
		if (userId == null) {
			String nonce = Hex.encodeHexString(randomBytes());
			session.setAttribute("relayState", new RelayState(nonce,
					httpRequest.getRequestURL().toString()));
			((HttpServletResponse) response)
					.sendRedirect(this.filterConfig
							.getInitParameter("serviceUrl")
							+ "?callback="
							+ URLEncoder.encode(
									URI.create(
											httpRequest.getRequestURL()
													.toString())
											.resolve(
													httpRequest
															.getContextPath()
															+ "/authenticationTokenVerifier")
											.toString(), "UTF-8")
							+ "&nonce="
							+ nonce);
		} else {
			chain.doFilter(new HttpServletRequestWrapper(httpRequest) {
				@Override
				public String getAuthType() {
					return "DELEGATED";
				}

				@Override
				public String getRemoteUser() {
					return userId;
				}
			}, response);
		}
	}

	private static byte[] randomBytes() {
		byte[] result = new byte[20];
		new SecureRandom().nextBytes(result);
		return result;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
