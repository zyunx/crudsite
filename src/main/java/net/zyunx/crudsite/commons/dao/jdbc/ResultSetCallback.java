package net.zyunx.crudsite.commons.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetCallback<T> {
	T doWithResultSet(ResultSet rs) throws SQLException;
}
