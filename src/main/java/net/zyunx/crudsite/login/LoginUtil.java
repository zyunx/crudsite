package net.zyunx.crudsite.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
	public static final String PRINCIPAL_KEY = "login";
	
	public static boolean isLogined(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object login = session.getAttribute(PRINCIPAL_KEY);
		return (null != login);
	}
	
	public static void login(HttpServletRequest request, Object principal) {
		HttpSession session = request.getSession();
		session.setAttribute(PRINCIPAL_KEY, principal);
	}
	
	public static void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(PRINCIPAL_KEY);
	}
	
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(PRINCIPAL_KEY);
	}
	
}
