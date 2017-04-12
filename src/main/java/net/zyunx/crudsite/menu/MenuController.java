package net.zyunx.crudsite.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;
import net.zyunx.crudsite.commons.dao.jdbc.SqlTemplate;
import net.zyunx.crudsite.message.controller.MessageController;

@Controller
@RequestMapping("/menu")
public class MenuController implements ServletContextAware {
	Log logger = LogFactory.getLog(MenuController.class);
	
	@Autowired
	SqlTemplate sqlTemplate;
	
	@Autowired
	MenuBO menuBO;
	
	ServletContext servletContext;
	
	String urlOfListMenu() {
		return "menu/listMenu";
	}
	@RequestMapping(value="/listMenu", method=RequestMethod.GET)
	public ModelAndView listMenu() {
		MenuModel menu = null;
		final List<MenuItem> menuItemList = new ArrayList<MenuItem>(200);
		this.sqlTemplate.query("select item_name, item_text, item_url, item_parent from menu_items",
				new Object[] {}, new ResultSetCallback<Void>() {
			
					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							MenuItem mi = new MenuItem();
							mi.setItemName(rs.getString("item_name"));
							mi.setItemText(rs.getString("item_text"));
							mi.setItemUrl(rs.getString("item_url"));
							mi.setItemParent(rs.getString("item_parent"));
							
							menuItemList.add(mi);
						}
						return null;
					}
			
		});
		
		for (MenuItem i : menuItemList) {
			if (i.getItemName().equals("root")) {
				menu = new MenuModel();
				menu.setItemName(i.getItemName());
				menu.setItemText(i.getItemText());
				menu.setItemUrl(i.getItemUrl());
				break;
			}
		}
		logger.info("menu: " + menu);
		if (menu != null) {
			menu.setSubmenu(submenuOf(menu.getItemName(), menuItemList));
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("menu", menu);
		mv.setViewName("/menu/listMenu");
		return mv;
	}
	
	private List<MenuModel> submenuOf(String menuName, List<MenuItem> menuItemList) {
		List<MenuModel> menu = new ArrayList<MenuModel>();
		for (MenuItem mi : menuItemList) {
			if (!menuName.equals(mi.getItemParent())) {
				continue;
			}
			MenuModel submenu = new MenuModel();
			submenu.setItemName(mi.getItemName());
			submenu.setItemText(mi.getItemText());
			submenu.setItemUrl(mi.getItemUrl());
			logger.info(submenu.getItemName());
			submenu.setSubmenu(submenuOf(mi.getItemName(), menuItemList));
			menu.add(submenu);
		}
		return menu;
	}
	
	@RequestMapping(value="/addMenuItem", method=RequestMethod.GET)
	public ModelAndView addMenuItemForm(String itemParent,
			RedirectAttributes redirectAttributes) {
		if (!this.menuBO.doesMenuItemExist(itemParent)) {
			return MessageController.redirectView(redirectAttributes, 
					urlOfListMenu(), "父菜单" + itemParent + "不存在");
		}
		
		MenuItemForm form = new MenuItemForm();
		MenuItem menuItem = new MenuItem();
		menuItem.setItemParent(itemParent);
		form.setMenuItem(menuItem);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("form", form);
		mv.setViewName("/menu/menuForm");
		return mv;
	}
	@RequestMapping(value="/addMenuItem", method=RequestMethod.POST)
	public ModelAndView addMenuItem(MenuItem menuItem,
			RedirectAttributes redirectAttributes) {
		if (this.menuBO.doesMenuItemExist(menuItem.getItemName())) {
			return MessageController.redirectView(redirectAttributes, 
					urlOfListMenu(), "菜单" + menuItem.getItemName() + "已存在");
		}
		
		this.sqlTemplate.execute("insert into menu_items (item_name, item_text, item_url, item_parent)"
				+ " values (?, ?, ?, ?)",
				new Object[] {
						menuItem.getItemName(),
						menuItem.getItemText(), 
						menuItem.getItemUrl(),
						menuItem.getItemParent()});
		
		return MessageController.redirectView(redirectAttributes, 
				urlOfListMenu(), "菜单" + menuItem.getItemName() + "创建成功");
	}
	public ModelAndView updateMenuItemForm() {
		return null;
	}
	public ModelAndView updateMenuItem() {
		return null;
	}
	public ModelAndView deleteMenuItem() {
		return null;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
}
