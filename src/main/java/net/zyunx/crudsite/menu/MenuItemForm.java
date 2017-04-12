package net.zyunx.crudsite.menu;

public class MenuItemForm {
	/**
	 * Html form action, default to current location
	 */
	private String action = "?";
	
	private String method = "post";
	
	private MenuItem menuItem;
	
	public String getAction() {
		return this.action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMethod() {
		return this.method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public MenuItem getMenuItem() {
		return this.menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
