package net.zyunx.crudsite.commons.dao.jdbc;

import java.sql.PreparedStatement;

public interface PreparedStatementCallback<T> {
	T doWithPreparedStatement(PreparedStatement stmt);
}
