package net.zyunx.crudsite.login;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginFilter extends OncePerRequestFilter {
	protected Log logger = LogFactory.getLog(LoginFilter.class);
	
	private Set<String> excludes = new HashSet<String>();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("do filter");
		if (isExcluded(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if (!LoginUtil.isLogined(request)) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/login");
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected void initFilterBean() {
		FilterConfig filterConfig = getFilterConfig();
		String excludesValue = filterConfig.getInitParameter("excludes");
		this.excludes.addAll(Arrays.asList(excludesValue.split(",")));
	}

	protected boolean isExcluded(HttpServletRequest request) {
		return this.excludes.contains(request.getServletPath());
	}
}
