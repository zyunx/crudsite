package net.zyunx.crudsite.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginFilter extends OncePerRequestFilter {
	protected Log logger = LogFactory.getLog(LoginFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("do filter");
		if (!LoginUtil.isLogined(request)) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/login");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
