package net.zyunx.crudsite.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.zyunx.crudsite.auth.AuthException;
import net.zyunx.crudsite.auth.AuthService;
import net.zyunx.crudsite.login.LoginUtil;
import net.zyunx.crudsite.message.controller.MessageController;

@Controller
public class LoginController {
	protected Log logger = LogFactory.getLog(LoginController.class);
	
	@Autowired
	AuthService authService;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView mv = new ModelAndView();		
		mv.setViewName("/login/login");
		return mv;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public ModelAndView login(String userName, String password,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info(userName + " attempt to login");
		try {
			this.authService.authenticate(userName, password);
			LoginUtil.login(request, userName);
			logger.info(userName + " login success");
			return MessageController.redirectView(redirectAttributes, "home", "登陆成功");
		} catch (AuthException e) {
			return MessageController.redirectView(redirectAttributes, "login", "登陆失败");
		}
	}
	
	@RequestMapping(value="logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		LoginUtil.logout(request);
		return MessageController.redirectView(redirectAttributes, "login", "登出成功");
	}
}
