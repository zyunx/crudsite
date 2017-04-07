package net.zyunx.crudsite.auth.bo;

import java.util.List;

import net.zyunx.crudsite.auth.User;
import net.zyunx.crudsite.commons.dao.Range;

public interface AuthBO {
	boolean doesUserExist(String userName);
	boolean doesGroupExist(String groupName);
	boolean doesPermissionExist(String permissionName);
	
	boolean isUserInGroup(String userName, String groupName);
	boolean isAnyoneInGroup(String groupName);
	boolean doesGroupHavePermission(String groupName, String permissionName);
	boolean doesAnyGroupHavePermission(String permissionName);
	boolean doesUserHimselfHavePermission(String userName, String permissionName);
	boolean doesAnyUserHimselfHavePermission(String permissionName);
	boolean doesUserHavePermission(String userName, String permissionName);
	
	int countUsers();
	List<User> listUsers(Range range);
}
