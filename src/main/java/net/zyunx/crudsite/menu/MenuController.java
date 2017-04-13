package net.zyunx.crudsite.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public MenuModel constructMenuModel(List<MenuItem> menuItemList) {
		MenuModel menu = null;
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
		return menu;
	}
	
	String urlOfListMenu() {
		return "menu/listMenu";
	}
	@RequestMapping(value="/listMenu", method=RequestMethod.GET)
	public ModelAndView listMenu() {
		
		MenuModel menu = constructMenuModel(this.menuBO.listAllMenuItems());
		
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
			submenu.setItemOrder(mi.getItemOrder());
			submenu.setSubmenu(submenuOf(mi.getItemName(), menuItemList));
			menu.add(submenu);
		}
		
		Collections.sort(menu, new Comparator<MenuModel>() {

			public int compare(MenuModel o1, MenuModel o2) {
				return o1.getItemOrder() - o2.getItemOrder();
			}
			
		});
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
					urlOfListMenu(), "菜单项" + menuItem.getItemName() + "已存在");
		}
		
		this.sqlTemplate.execute("insert into menu_items (item_name, item_text, item_url, item_parent, item_order)"
				+ " values (?, ?, ?, ?, ?)",
				new Object[] {
						menuItem.getItemName(),
						menuItem.getItemText(), 
						menuItem.getItemUrl(),
						menuItem.getItemParent(),
						menuItem.getItemOrder()});
		
		return MessageController.redirectView(redirectAttributes, 
				urlOfListMenu(), "菜单" + menuItem.getItemName() + "创建成功");
	}
	
	@RequestMapping(value="/updateMenuItem", method=RequestMethod.GET)
	public ModelAndView updateMenuItemForm(String itemName,
			RedirectAttributes redirectAttributes) {
		MenuItem menuItem = this.menuBO.findMenuItem(itemName);
		if (menuItem == null) {
			return MessageController.redirectView(redirectAttributes, 
					urlOfListMenu(), "菜单项" + itemName + "不存在");
		}
		MenuItemForm form = new MenuItemForm();
		form.setMenuItem(menuItem);
		ModelAndView mv = new ModelAndView();
		mv.addObject("form", form);
		mv.setViewName("/menu/menuForm");
		return mv;
	}
	
	@RequestMapping(value="/updateMenuItem", method=RequestMethod.POST)
	public ModelAndView updateMenuItem(MenuItem menuItem,
			RedirectAttributes redirectAttributes) {
		if (!this.menuBO.doesMenuItemExist(menuItem.getItemName())) {
			return MessageController.redirectView(redirectAttributes, 
					urlOfListMenu(), "菜单项" + menuItem.getItemName() + "不存在");
		}
		
		this.sqlTemplate.execute("update menu_items set"
				+ " item_text = ?, item_url = ?, item_parent = ?, item_order = ?"
				+ " where item_name = ?",
				new Object[] {menuItem.getItemText(), menuItem.getItemUrl(),
						menuItem.getItemParent(), menuItem.getItemOrder(), menuItem.getItemName()});
		
		return MessageController.redirectView(redirectAttributes, 
				urlOfListMenu(), "菜单项" + menuItem.getItemName() + "更新成功");
	}
	
	@RequestMapping(value="/deleteMenuItem", method=RequestMethod.POST)
	public ModelAndView deleteMenuItem(String itemName,
			RedirectAttributes redirectAttributes) {
		if (!this.menuBO.doesMenuItemExist(itemName)) {
			return MessageController.redirectView(redirectAttributes, 
					urlOfListMenu(), "菜单项" + itemName + "不存在");
		}
		
		this.sqlTemplate.execute("delete from menu_items where item_name = ?",
				new Object[] {itemName});
		
		return MessageController.redirectView(redirectAttributes, 
				urlOfListMenu(), "菜单项" + itemName + "删除成功");
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
}
