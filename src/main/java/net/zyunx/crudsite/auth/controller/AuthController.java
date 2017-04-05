package net.zyunx.crudsite.auth.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.zyunx.crudsite.auth.AuthService;
import net.zyunx.crudsite.auth.Group;
import net.zyunx.crudsite.auth.GroupPermission;
import net.zyunx.crudsite.auth.Permission;
import net.zyunx.crudsite.auth.User;
import net.zyunx.crudsite.auth.UserPermission;
import net.zyunx.crudsite.auth.bo.AuthBO;
import net.zyunx.crudsite.commons.dao.exception.DAOException;
import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;
import net.zyunx.crudsite.commons.dao.jdbc.SqlTemplate;
import net.zyunx.crudsite.message.controller.MessageController;

@Controller
@RequestMapping("/auth")
public class AuthController {
	protected Log logger = LogFactory.getLog(AuthController.class);
	
	@Autowired
	AuthService authService;
	
	@Autowired
	SqlTemplate sqlTemplate;
	
	@Autowired
	AuthBO authBO;
	
	private int redirectTime = 3;
	
	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	public SqlTemplate getSqlTemplate() {
		return sqlTemplate;
	}

	public void setSqlTemplate(SqlTemplate sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}

	public int getRedirectTime() {
		return redirectTime;
	}

	public void setRedirectTime(int redirectTime) {
		this.redirectTime = redirectTime;
	}
	
	
	private String urlOfListUsers() {
		return "auth/users";
	}
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView listUsers() {
		final List<User> userList = new ArrayList<User>(100);
		this.sqlTemplate.query("select user_name from users", 
				new Object[]{}, new ResultSetCallback<Object>() {
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
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("users", userList);
		mv.setViewName("auth/users");
		return mv;
	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ModelAndView userForm(HttpServletRequest request) throws Exception {
		String userName = request.getParameter("userName");
		final List<User> userList = new ArrayList<User>(100);
		this.sqlTemplate.query("select user_name from users where user_name = ?", 
				new Object[]{ userName }, 
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
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", userList.size() > 0 ? userList.get(0) : null);
		mv.setViewName("auth/user");
		return mv;
	}
	
	@RequestMapping(value="/user/create", method=RequestMethod.POST)
	public ModelAndView addUser(User user, RedirectAttributes redirectAttributes) throws Exception {
		logger.info("add user " + user.getUserName());
		
		this.sqlTemplate.execute("insert into users (user_name, password) values (?, ?)",
				new Object[] {user.getUserName(), user.getPassword()});
		
		return MessageController.redirectView(redirectAttributes, 
				"auth/users", "创建用户成功", this.getRedirectTime());
	}
	
	@RequestMapping(value="/changeUserPassword", method=RequestMethod.GET)
	public ModelAndView changeUserPasswordForm(String userName, RedirectAttributes redirectAttributes) throws Exception {
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes, 
					"auth/users", userName + "用户不存在");
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject(userName);
		mv.setViewName("/auth/user/changeUserPassword");
		return mv;
	}
	@RequestMapping(value="/changeUserPassword", method=RequestMethod.POST)
	public ModelAndView changeUserPassword(User user, RedirectAttributes redirectAttributes) throws Exception {
		logger.info("change user " + user.getUserName() + "'s password");
		this.sqlTemplate.execute("update users set password = ? where user_name = ?",
				new Object[] {user.getPassword(), user.getUserName()});
		
		return MessageController.redirectView(redirectAttributes, 
				"auth/users", "修改" + user.getUserName() + "用户密码成功");
	}
	
	@RequestMapping(value="/user/delete", method=RequestMethod.GET)
	public ModelAndView deleteUser(String userName, RedirectAttributes redirectAttributes) {
		logger.info("delete user " + userName);
		this.sqlTemplate.execute("delete from users where user_name = ?",
				new Object[] {userName});
		
		return MessageController.redirectView(redirectAttributes, 
				"auth/users", "删除用户成功", this.getRedirectTime());
	}
	
	
	@RequestMapping(value="/groups", method=RequestMethod.GET)
	public ModelAndView listGroups() {
		final List<Group> groupList = new ArrayList<Group>(100);
		this.sqlTemplate.query("select group_name from groups", 
				new Object[]{},
				new ResultSetCallback<Void>() {

					public Void doWithResultSet(ResultSet rs) {
						try {
							while (rs.next()) {
								Group g = new Group();
								g.setGroupName(rs.getString("group_name"));
								groupList.add(g);
							}
						} catch (SQLException e) {
							logger.warn("error get database data", e);
						}
						return null;
					}
			
		});
		ModelAndView mv = new ModelAndView();
		mv.addObject("groups", groupList);
		mv.setViewName("/auth/group/groups");
		return mv;
	}
	
	@RequestMapping(value="/group/create", method=RequestMethod.POST)
	public ModelAndView addGroup(Group group, RedirectAttributes redirectAttributes) throws Exception {
		logger.info("add group " + group.getGroupName());
		
		this.sqlTemplate.execute("insert into groups (group_name) values (?)",
				new Object[] {group.getGroupName()});
		
		return MessageController.redirectView(redirectAttributes, 
				"auth/groups", "创建组成功", this.getRedirectTime());
	}
	
	@RequestMapping(value="/group/delete", method=RequestMethod.GET)
	public ModelAndView deleteGroup(String groupName, RedirectAttributes redirectAttributes) {
		logger.info("delete group " + groupName);
		this.sqlTemplate.execute("delete from groups where group_name = ?",
				new Object[] {groupName});
		
		return MessageController.redirectView(redirectAttributes, 
				"auth/groups", "删除组成功", this.getRedirectTime());
	}
	
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public ModelAndView listPermissions() {
		ModelAndView mv = new ModelAndView();
		final List<Permission> permissionList = new ArrayList<Permission>(100);
		this.sqlTemplate.query("select permission_name from permissions",
				new Object[] {},
				new ResultSetCallback<Void>() {

					public Void doWithResultSet(ResultSet rs) {
						try {
							while (rs.next()) {
								Permission p = new Permission();
								p.setPermissionName(rs.getString("permission_name"));
								permissionList.add(p);
							}
						} catch (SQLException e) {
							logger.warn("error get database data", e);
						}
						return null;
					}
		});
		mv.addObject("permissions", permissionList);
		mv.setViewName("/auth/permission/permissions");
		return mv;
	}
	
	@RequestMapping(value="/permission/create", method=RequestMethod.POST)
	public ModelAndView createPermission(String permissionName,
			RedirectAttributes redirectAttributes) {
		try {
			this.sqlTemplate.execute("insert into permissions (permission_name) values (?)",
					new Object[] {permissionName});
		} catch (DAOException e) {
			return MessageController.redirectView(redirectAttributes, "auth/permissions", 
					"创建权限失败", this.getRedirectTime());
		}

		return MessageController.redirectView(redirectAttributes, "auth/permissions", 
				"创建权限成功", this.getRedirectTime());
	}
	
	@RequestMapping(value="/permission/delete")
	public ModelAndView deletePermission(String permissionName,
			RedirectAttributes redirectAttributes) {
		try {
			this.sqlTemplate.execute("delete from permissions where permission_name = ?",
					new Object[] {permissionName});
		} catch (DAOException e) {
			return MessageController.redirectView(redirectAttributes, "auth/permissions", 
					"删除权限失败", this.getRedirectTime());
		}

		return MessageController.redirectView(redirectAttributes, "auth/permissions", 
				"删除权限成功", this.getRedirectTime());
	}
	
	
	private String urlOfListGroupsOfUser(String userName) {
		return "auth/listGroupsOfUser?userName=" + userName;
	}
	
	@RequestMapping(value="/userLeaveGroup", method=RequestMethod.POST)
	public ModelAndView userLeaveGroup(String userName, String groupName,
			RedirectAttributes redirectAttributes) {
		
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					"auth/users", 
					"用户" + userName + "不存在");
		}
		
		if (!this.authBO.doesGroupExist(groupName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListGroupsOfUser(userName), 
					groupName + "组不存在");
		}
		
		if (!this.authBO.isUserInGroup(userName, groupName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListGroupsOfUser(userName), 
					userName + "不在" + groupName + "组中");
		}
		
		this.sqlTemplate.execute("delete from user_groups where user_name = ? and group_name = ?",
				new Object[] {userName, groupName});
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListGroupsOfUser(userName),
				userName + "离开" + groupName + "组");
	}
	@RequestMapping(value="/userJoinGroup", method=RequestMethod.POST)
	public ModelAndView userJoinGroup(String userName, String groupName,
			RedirectAttributes redirectAttributes) {
		
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					"auth/users", 
					"用户" + userName + "不存在");
		}
		
		if (!this.authBO.doesGroupExist(groupName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListGroupsOfUser(userName), 
					groupName + "组不存在");
		}
		
		if (this.authBO.isUserInGroup(userName, groupName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListGroupsOfUser(userName), 
					userName + "已经在" + groupName + "中", this.getRedirectTime());
		}
		
		this.sqlTemplate.execute("insert into user_groups (user_name, group_name) values (?, ?)"
				, new Object[] {userName, groupName});
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListGroupsOfUser(userName),
				userName + "加入" + groupName + "组成功",
				redirectTime);
	}
	
	@RequestMapping(value="/listGroupsOfUser", method=RequestMethod.GET)
	public ModelAndView listGroupsOfUser(String userName,
			RedirectAttributes redirectAttributes) {
		
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					"auth/users", 
					"用户" + userName + "不存在");
		}
		
		final List<Group> groupList = new ArrayList<Group>(100);
		this.sqlTemplate.query("select group_name from user_groups where user_name = ?",
				new Object[] {userName},
				new ResultSetCallback<Void>() {
					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							Group g = new Group();
							g.setGroupName(rs.getString("group_name"));
							groupList.add(g);
						}
						return null;
					}
			
		});
		ModelAndView mv = new ModelAndView();
		mv.addObject("userName", userName);
		mv.addObject("groups", groupList);
		mv.setViewName("/auth/listGroupsOfUser");
		return mv;
	}
	
	private String urlOfListGroups() {
		return "auth/groups";
	}
	private String urlOfListPermissionsOfGroup(String groupName) {
		return "auth/listPermissionsOfGroup?groupName=" + groupName;
	}
	
	@RequestMapping(value="/listPermissionsOfGroup", method=RequestMethod.GET)
	public ModelAndView listPermissionsOfGroup(String groupName,
			RedirectAttributes redirectAttributes) {
		if (!this.authBO.doesGroupExist(groupName)) {
			return MessageController.redirectView(redirectAttributes,
					this.urlOfListGroups(), 
					groupName + "组不存在");
		}
		
		final List<GroupPermission> groupPermissionList = new ArrayList<GroupPermission>();
		this.sqlTemplate.query("select group_name, permission_name from group_permissions where group_name = ?",
				new Object[]{groupName},
				new ResultSetCallback<Void>() {

					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							GroupPermission gp = new GroupPermission();
							gp.setGroupName(rs.getString("group_name"));
							gp.setPermissionName(rs.getString("permission_name"));
							groupPermissionList.add(gp);
						}
						
						return null;
					}
			
		});
		ModelAndView mv = new ModelAndView();
		mv.addObject("groupName", groupName);
		mv.addObject("groupPermissions", groupPermissionList);
		mv.setViewName("/auth/listPermissionsOfGroup");
		return mv;
	}
	
	@RequestMapping(value="/grantPermissionToGroup", method=RequestMethod.POST)
	public ModelAndView grantPermissionToGroup(String permissionName, String groupName,
			RedirectAttributes redirectAttributes) {
		if (!this.authBO.doesGroupExist(groupName)) {
			return MessageController.redirectView(redirectAttributes,
					this.urlOfListGroups(), 
					groupName + "组不存在");
		}
		
		if (!this.authBO.doesPermissionExist(permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					"权限" + permissionName + "不存在");
		}
		
		if (this.authBO.doesGroupHavePermission(groupName, permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					groupName + "组已拥有" + permissionName + "权限");
		}
		
		try {
			this.sqlTemplate.execute("insert into group_permissions (group_name, permission_name)"
					+ " values (?, ?)", new Object[] {groupName, permissionName});
		} catch (DAOException e) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					"授予" + groupName + "组权限" + permissionName + "失败");
		}
		
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListPermissionsOfGroup(groupName),
				"授予" + groupName + "组权限" + permissionName + "成功");
	}
	@RequestMapping(value="/revokePermissionFromGroup", method=RequestMethod.POST)
	public ModelAndView revokePermissionFromGroup(String permissionName, String groupName,
			RedirectAttributes redirectAttributes) {
		
		if (!this.authBO.doesGroupExist(groupName)) {
			return MessageController.redirectView(redirectAttributes,
					this.urlOfListGroups(), 
					groupName + "组不存在");
		}
		
		if (!this.authBO.doesPermissionExist(permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					"权限" + permissionName + "不存在");
		}
		
		if (!this.authBO.doesGroupHavePermission(groupName, permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					groupName + "组没有" + permissionName + "权限");
		}
		
		try {
			this.sqlTemplate.execute("delete from group_permissions where"
					+ " group_name = ? and permission_name = ?)",
					new Object[] {groupName, permissionName});
		} catch (DAOException e) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfGroup(groupName),
					"取回" + groupName + "组权限" + permissionName + "失败");
		}
		
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListPermissionsOfGroup(groupName),
				"取回" + groupName + "组权限" + permissionName + "成功");
	}
	
	
	private String urlOfListPermissionsOfUser(String userName) {
		return "auth/listPermissionsOfUser?userName=" + userName;
	}
	@RequestMapping(value="/listPermissionsOfUser", method=RequestMethod.GET)
	public ModelAndView listPermissionsOfUser(String userName,
			RedirectAttributes redirectAttributes) {
		
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					"auth/users", 
					"用户" + userName + "不存在");
		}
		
		final List<UserPermission> userPermissionListOfHimself = new ArrayList<UserPermission>(100);
		this.sqlTemplate.query("select user_name, permission_name from user_permissions"
				+ " where user_name = ?",
				new Object[]{userName},
				new ResultSetCallback<Void>() {
					
					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							UserPermission up = new UserPermission();
							up.setPermissionName(rs.getString("permission_name"));
							up.setUserName(rs.getString("user_name"));
							userPermissionListOfHimself.add(up);
						}
						return null;
					}
					
				});
		
		final List<UserPermission> userPermissionListOfGroups = new ArrayList<UserPermission>(100);
		this.sqlTemplate.query("select"
				+ " ug.user_name as user_name,"
				+ " gp.permission_name as permission_name"
				+ " from user_groups ug, group_permissions gp"
				+ " where ug.group_name = gp.group_name and user_name = ?",
				new Object[]{userName},
				new ResultSetCallback<Void>() {
					
					public Void doWithResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							UserPermission up = new UserPermission();
							up.setPermissionName(rs.getString("permission_name"));
							up.setUserName(rs.getString("user_name"));
							userPermissionListOfGroups.add(up);
						}
						return null;
					}
					
				});
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("userPermissionsOfHimself", userPermissionListOfHimself);
		mv.addObject("userPermissionsOfGroups", userPermissionListOfGroups);
		mv.addObject("userName", userName);
		mv.setViewName("/auth/listPermissionsOfUser");
		return mv;
	}
	
	@RequestMapping(value="/grantPermissionToUser", method=RequestMethod.POST)
	public ModelAndView grantPermissionToUser(String permissionName, String userName,
			RedirectAttributes redirectAttributes) {
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListUsers(),
					userName + "用户不存在");
		}
		
		if (!this.authBO.doesPermissionExist(permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					permissionName + "权限不存在");
		}
		
		if (this.authBO.doesUserHimselfHavePermission(userName, permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					userName + "用户自身已拥有" + permissionName + "权限");
		}
		
		try {
			this.sqlTemplate.execute("insert into user_permissions (user_name, permission_name) values (?, ?)",
					new Object[] {userName, permissionName});
		} catch (DAOException e) {
			logger.warn("grant permission to user failed", e);
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					"授予" + permissionName + "权限给" + userName + "用户失败");
		}
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListPermissionsOfUser(userName),
				"授予" + permissionName + "权限给" + userName + "用户成功");
	}
	
	@RequestMapping(value="revokePermissionFromUser", method=RequestMethod.POST)
	public ModelAndView revokePermissionFromUser(String permissionName, String userName,
			RedirectAttributes redirectAttributes) {
		if (!this.authBO.doesUserExist(userName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListUsers(),
					userName + "用户不存在");
		}
		
		if (!this.authBO.doesPermissionExist(permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					permissionName + "权限不存在");
		}
		
		if (!this.authBO.doesUserHimselfHavePermission(userName, permissionName)) {
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					userName + "用户自身没有" + permissionName + "权限");
		}
		
		try {
			this.sqlTemplate.execute("delete from user_permissions where user_name = ? and permission_name = ?",
					new Object[] {userName, permissionName});
		} catch (DAOException e) {
			logger.warn("grant permission to user failed", e);
			return MessageController.redirectView(redirectAttributes,
					urlOfListPermissionsOfUser(userName),
					"从" + userName + "用户剥夺" + permissionName + "权限失败");
		}
		
		return MessageController.redirectView(redirectAttributes,
				urlOfListPermissionsOfUser(userName),
				"从" + userName + "用户剥夺" + permissionName + "权限成功");
	}
	
}
