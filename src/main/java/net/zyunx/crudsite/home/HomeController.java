package net.zyunx.crudsite.home;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.zyunx.crudsite.menu.MenuBO;
import net.zyunx.crudsite.menu.MenuController;
import net.zyunx.crudsite.menu.MenuModel;

@Controller
public class HomeController {
	Log logger = LogFactory.getLog(HomeController.class);
	
	@Autowired
	MenuController menuController;
	
	@Autowired
	MenuBO menuBO;
	
	@RequestMapping(value={"/home","/"})
	public ModelAndView play(String username, String password) {
		MenuModel menu = menuController.constructMenuModel(menuBO.listAllMenuItems());
		ModelAndView mv = new ModelAndView();
		mv.addObject("menu", menu);
		mv.setViewName("/home/home");
		return mv;
	}

}
