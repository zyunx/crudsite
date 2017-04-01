package net.zyunx.crudsite.auth;

import java.util.List;

/**
 * 认证服务
 * @author zyunx
 *
 */
public interface AuthService {
	/**
	 * 认证
	 * @param username
	 * @param password
	 */
	void authenticate(String userName, String password) throws AuthException;
	User findUser(String userName) throws AuthException;
	void addUser(User user) throws AuthException;
	void removeUser(User user) throws AuthException;
	void changePassword(String userName, String newPassword) throws AuthException;
	List<User> listAllUsers();
	
	boolean isUserInGroup(String userName, String groupName);
	
}
