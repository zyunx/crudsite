package net.zyunx.crudsite.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
