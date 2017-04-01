package net.zyunx.crudsite.commons.dao.jdbc;

import java.sql.Connection;

public interface ConnectionCallback<T> {
	T doWithConnection(Connection conn);
}
