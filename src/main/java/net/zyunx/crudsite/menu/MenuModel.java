package net.zyunx.crudsite.menu;

import java.util.List;

public class MenuModel {
	private String itemName;
	private String itemText;
	private String itemUrl;
	private List<MenuModel> submenu;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public List<MenuModel> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<MenuModel> submenu) {
		this.submenu = submenu;
	}
}