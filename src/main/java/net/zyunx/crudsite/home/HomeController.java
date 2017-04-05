package net.zyunx.crudsite.home;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	Log logger = LogFactory.getLog(HomeController.class);
	
	@RequestMapping(value={"/home","/"})
	public ModelAndView play(String username, String password) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/home");
		return mv;
	}

}
