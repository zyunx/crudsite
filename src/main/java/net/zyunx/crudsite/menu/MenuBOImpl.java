package net.zyunx.crudsite.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;
import net.zyunx.crudsite.commons.dao.jdbc.SqlTemplate;
import net.zyunx.crudsite.commons.dao.jdbc.callback.DoesRecordExistResultSetCallback;

@Component
public class MenuBOImpl implements MenuBO {
	@Autowired
	SqlTemplate sqlTemplate;
	
	public Boolean doesMenuItemExist(String itemName) {
		return this.sqlTemplate.query("select item_name from menu_items where item_name = ?",
				new Object[] {itemName},
				new DoesRecordExistResultSetCallback());
	}

	public MenuItem findMenuItem(String itemName) {
		return this.sqlTemplate.query("select item_name, item_text, item_url, item_parent, item_order"
				+ " from menu_items where item_name = ?",
				new Object[] {itemName},
				new ResultSetCallback<MenuItem>() {

					public MenuItem doWithResultSet(ResultSet rs) throws SQLException {
						if (rs.next()) {
							MenuItem item = new MenuItem();
							item.setItemName(rs.getString("item_name"));
							item.setItemText(rs.getString("item_text"));
							item.setItemUrl(rs.getString("item_url"));
							item.setItemParent(rs.getString("item_parent"));
							item.setItemOrder(rs.getInt("item_order"));
							return item;
						}
						
						return null;
					}
					
				});
	}
	
	public List<MenuItem> listAllMenuItems() {
		final List<MenuItem> menuItemList = new ArrayList<MenuItem>(200);
		this.sqlTemplate.query("select item_name, item_text, item_url, item_parent, item_order from menu_items",
				new Object[] {}, new ResultSetCallback<Void>() {
			
					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							MenuItem mi = new MenuItem();
							mi.setItemName(rs.getString("item_name"));
							mi.setItemText(rs.getString("item_text"));
							mi.setItemUrl(rs.getString("item_url"));
							mi.setItemParent(rs.getString("item_parent"));
							mi.setItemOrder(rs.getInt("item_order"));
							menuItemList.add(mi);
						}
						return null;
					}
			
		});
		return menuItemList;
	}

}
