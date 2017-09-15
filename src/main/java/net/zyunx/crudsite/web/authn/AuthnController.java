package net.zyunx.crudsite.web.authn;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/authn")
public class AuthnController {
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/authn/add/form");
		return mv;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView add(String userName, String password, RedirectAttributes rattr) {
		ModelAndView mv = new ModelAndView();
		// TODO call service
		//rattr.addAttribute("userName", userName);
		rattr.addFlashAttribute("userName", userName);
		if (!"root".equals(userName)) {
			mv.setViewName("redirect:/authn/add/succ");
		} else {
			mv.setViewName("redirect:/authn/add/fail");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/add/succ", method=RequestMethod.GET)
	public ModelAndView addSucc(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addAllObjects(RequestContextUtils.getInputFlashMap(request));
		mv.setViewName("/authn/add/succ");
		return mv;
	}
	
	@RequestMapping(value="/add/fail", method=RequestMethod.GET)
	public ModelAndView addFail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addAllObjects(RequestContextUtils.getInputFlashMap(request));
		mv.setViewName("/authn/add/fail");
		return mv;
	}
	
	@RequestMapping(value="/lock", method=RequestMethod.GET)
	public ModelAndView lockForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addAllObjects(RequestContextUtils.getInputFlashMap(request));
		mv.setViewName("/authn/lock/form");
		return mv;
	}
	
	@RequestMapping(value="/lock", method=RequestMethod.POST)
	public ModelAndView lock(String userName, RedirectAttributes rattr) {
		ModelAndView mv = new ModelAndView();
		if ("root".equals(userName)) {
			rattr.addFlashAttribute("message", "失败");
			rattr.addFlashAttribute("userName", userName);
		} else {
			rattr.addFlashAttribute("message", "成功");
		}
		mv.setViewName("redirect:/authn/lock");
		return mv;
	}
	
	public ModelAndView unlock() {
		return null;
	}
	
	public ModelAndView list() {
		return null;
	}
}
