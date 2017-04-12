package net.zyunx.crudsite.commons.dao.jdbc.callback;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zyunx.crudsite.commons.dao.jdbc.ResultSetCallback;

public class DoesRecordExistResultSetCallback implements ResultSetCallback<Boolean> {
	public Boolean doWithResultSet(ResultSet rs) throws SQLException {
		return rs.next();
	}
}