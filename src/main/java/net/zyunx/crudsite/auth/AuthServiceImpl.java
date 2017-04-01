package net.zyunx.crudsite.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {
	private Log logger = LogFactory.getLog(AuthServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	public void authenticate(String userName, String password) throws AuthException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			
			stmt = conn.createStatement();
			stmt.executeQuery("select user_name, password from users where user_name = '" + userName + "'");
			rs = stmt.getResultSet();
			if (!rs.next()) {
				throw new AuthException("user does not exist");
			}
			if (!StringUtils.hasText(password)) {
				throw new AuthException("password is empty");
			}
			if (!password.equals(rs.getString("password"))) {
				throw new AuthException("password is wrong");
			}
			logger.info(userName + " authenticate success");
			
		} catch (SQLException e) {
			throw new AuthException("database is not available");
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					logger.warn("close sql result set exeption");
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					logger.warn("close sql statement exeption");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					logger.warn("close sql connection exeption");
				}
			}
		}
		
		
	}

	public boolean isUserInGroup(String userName, String groupName) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<User> listAllUsers() {
		List<User> users = new ArrayList<User>(1);
		User u = new User();
		u.setUserName("xxxx");
		users.add(u);
		return users;
	}

	public void removeUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public void changePassword(String userName, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	public User findUser(String userName) throws AuthException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("select user_name, password from users where user_name = ?");
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			User user = new User();
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			return user;
			
		} catch (SQLException e) {
			throw new AuthException("database is not available");
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					logger.warn("close sql result set exeption");
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					logger.warn("close sql statement exeption");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					logger.warn("close sql connection exeption");
				}
			}
		}
	}

	public void addUser(User user) throws AuthException {
		// TODO Auto-generated method stub
		
	}

}
