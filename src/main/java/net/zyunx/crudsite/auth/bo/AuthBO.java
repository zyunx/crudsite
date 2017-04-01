package net.zyunx.crudsite.auth.bo;

public interface AuthBO {
	boolean doesUserExist(String userName);
	boolean doesGroupExist(String groupName);
	boolean doesPermissionExist(String permissionName);
	
	boolean doesGroupHavePermission(String groupName, String permissionName);
	
	boolean isUserInGroup(String userName, String groupName);
	
	boolean checkPermission(String userName, String permissionName);
}
