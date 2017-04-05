package net.zyunx.crudsite.auth.bo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.zyunx.crudsite.auth.bo.AuthBO;
import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;
import net.zyunx.crudsite.commons.dao.jdbc.SqlTemplate;

@Component
public class AuthBOImpl implements AuthBO {

	@Autowired
	private SqlTemplate sqlTemplate;
	
	private DoesRecordExistResultSetCallback doesRecordExistResultSetCallback = 
			new DoesRecordExistResultSetCallback();
	
	public SqlTemplate getSqlTemplate() {
		return sqlTemplate;
	}

	public void setSqlTemplate(SqlTemplate sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}

	public boolean doesUserExist(String userName) {
		return this.sqlTemplate.query("select user_name from users where user_name = ?",
				new Object[] {userName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesGroupExist(String groupName) {
		return this.sqlTemplate.query("select group_name from groups where group_name = ?",
				new Object[] {groupName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean isUserInGroup(String userName, String groupName) {
		return this.sqlTemplate.query("select user_name, group_name from user_groups where user_name = ? and group_name = ?",
				new Object[] {userName, groupName},
				this.doesRecordExistResultSetCallback);
	}
	
	
	class DoesRecordExistResultSetCallback implements ResultSetCallback<Boolean> {
		public Boolean doWithResultSet(ResultSet rs) throws SQLException {
			return rs.next();
		}
	}

	public boolean doesPermissionExist(String permissionName) {
		return this.sqlTemplate.query("select permission_name from permissions where permission_name = ?",
				new Object[] {permissionName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesGroupHavePermission(String groupName, String permissionName) {
		return this.sqlTemplate.query("select group_name, permission_name from group_permissions"
				+ " where group_name = ? and permission_name = ?",
				new Object[] {groupName, permissionName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesUserHimselfHavePermission(String userName, String permissionName) {
		return this.sqlTemplate.query("select user_name, permission_name from user_permissions"
				+ " where user_name = ? and permission_name = ?",
				new Object[] {userName, permissionName},
				this.doesRecordExistResultSetCallback);
	}

}
