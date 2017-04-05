package net.zyunx.crudsite.message.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class MessageController {
	private static int DEFAULT_REDIRECT_TIME = 3;
	
	@RequestMapping(value="/message", method=RequestMethod.GET)
	public ModelAndView result(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		mv.addObject("message", map.get("message"));
		mv.addObject("timeout", map.get("timeout"));
		mv.addObject("gotoUrl", map.get("gotoUrl"));
		mv.setViewName("/message");
		return mv;
	}
	
	
	public static ModelAndView redirectView(RedirectAttributes redirectAttributes, 
			String gotoUrl, String message, int timeout) {
		ModelAndView mv = new ModelAndView();
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("gotoUrl", gotoUrl);
		redirectAttributes.addFlashAttribute("timeout", timeout);
		mv.setViewName("redirect:/message");
		return mv;
	}
	
	public static ModelAndView redirectView(RedirectAttributes redirectAttributes, 
			String gotoUrl, String message) {
		return redirectView(redirectAttributes, gotoUrl, message, DEFAULT_REDIRECT_TIME);
	}
}
