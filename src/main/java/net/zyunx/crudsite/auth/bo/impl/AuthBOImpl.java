package net.zyunx.crudsite.auth.bo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.zyunx.crudsite.auth.User;
import net.zyunx.crudsite.auth.bo.AuthBO;
import net.zyunx.crudsite.commons.dao.Range;
import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;
import net.zyunx.crudsite.commons.dao.jdbc.SqlTemplate;

@Component
public class AuthBOImpl implements AuthBO {
	protected Log logger = LogFactory.getLog(AuthBOImpl.class);
	
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

	public boolean isAnyoneInGroup(String groupName) {
		return this.sqlTemplate.query("select user_name from user_groups where group_name = ?",
				new Object[]{groupName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesAnyGroupHavePermission(String permissionName) {
		return this.sqlTemplate.query("select group_name from group_permissions where permission_name = ?",
				new Object[]{permissionName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesAnyUserHimselfHavePermission(String permissionName) {
		return this.sqlTemplate.query("select user_name from user_permissions where permission_name = ?",
				new Object[]{permissionName},
				this.doesRecordExistResultSetCallback);
	}

	public boolean doesUserHavePermission(String userName, String permissionName) {
		if (this.doesUserHimselfHavePermission(userName, permissionName)) {
			return true;
		}
		
		return this.sqlTemplate.query("select gp.permission_name"
				+ " from user_groups ug left join group_permissions gp on ug.group_name = gp.group_name"
				+ " where ug.user_name = ? and gp.permission_name = ?",
				new Object[]{userName, permissionName},
				this.doesRecordExistResultSetCallback);
	}

	public int countUsers() {
		return this.sqlTemplate.query("select count(*) as user_count from users", 
				new Object[]{},
				new ResultSetCallback<Integer>() {
					public Integer doWithResultSet(ResultSet rs) {
						try {
							rs.next();
							return rs.getInt("user_count");
						} catch (SQLException e) {
							logger.warn("error get user data from database", e);
							return 0;
						}
					}
		});
	}

	public List<User> listUsers(Range range) {
		final List<User> userList = new ArrayList<User>(range.getCount());
		this.sqlTemplate.query("select user_name from users limit ?, ?", 
				new Object[]{range.getStart(), range.getCount()},
				new ResultSetCallback<Object>() {
					public Object doWithResultSet(ResultSet rs) {
						try {
							while (rs.next()) {
								User user = new User();
								user.setUserName(rs.getString("user_name"));
								userList.add(user);
							}
						} catch (SQLException e) {
							logger.warn("error get user data from database", e);
						}
						return null;
					}
		});
		return userList;
	}

}
