package net.zyunx.crudsite.menu;

import java.util.List;

public interface MenuBO {
	Boolean doesMenuItemExist(String itemName);
	MenuItem findMenuItem(String itemName);
	List<MenuItem> listAllMenuItems();
}
