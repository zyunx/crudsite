package net.zyunx.crudsite.commons.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.zyunx.crudsite.commons.dao.exception.DAOException;

public class SqlTemplate {
	protected Log logger = LogFactory.getLog(SqlTemplate.class);
	
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public <T> T execute(ConnectionCallback<T> action) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return action.doWithConnection(conn);
		} catch (SQLException e) {
			logger.warn("error get connection", e);
			throw new DAOException("error get connection", e);
		} finally {
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
	
	public <T> T execute(final String sql, final Object[] params, final PreparedStatementCallback<T> action) {
		return this.execute(new ConnectionCallback<T>() {
			
			public T doWithConnection(Connection conn) {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(sql);
					for (int i = 0; i < params.length; i++) {
						stmt.setObject(i + 1, params[i]);
					}
					return action.doWithPreparedStatement(stmt);
				} catch (SQLException e) {
					logger.warn("error prepare statement", e);
					throw new DAOException("error prepare statement", e);
				} finally {
					if (stmt != null) {
						try {
							stmt.close();
							stmt = null;
						} catch (SQLException e) {
							logger.warn("close sql statement exeption");
						}
					}
				}
			}		
		});
	}
	public int execute(String sql, Object[] params) {
		return this.execute(sql, params, new PreparedStatementCallback<Integer>() {
			public Integer doWithPreparedStatement(PreparedStatement stmt) {
				try {
					return stmt.executeUpdate();
				} catch (SQLException e) {
					logger.warn("error execute update", e);
					throw new DAOException("error execute update", e);
				}
			}
		});
	}
	public <T> T query(String sql, Object[] params, final ResultSetCallback<T> action) {
		return this.execute(sql, params, new PreparedStatementCallback<T>() {
			public T doWithPreparedStatement(PreparedStatement stmt) {
				ResultSet rs = null;
				
				try {
					rs = stmt.executeQuery();
				} catch (SQLException e) {
					logger.warn("error execute query", e);
					throw new DAOException("error execute query", e);
				}
				
				try {
					return action.doWithResultSet(rs);
				} catch (SQLException e) {
					logger.warn("error process result set from database", e);
					throw new DAOException("error process result set from database", e);
				} finally {
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (SQLException e) {
							logger.warn("close sql result set exeption");
						}
					}
				}
			}
			
		});
	}
	
	
}
