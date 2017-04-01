package net.zyunx.crudsite.commons.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceUtil {
	public static Connection getConnection(DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}
}
