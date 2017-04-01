package net.zyunx.crudsite.commons.dao.exception;

public class DAOException extends RuntimeException {

	public DAOException(String msg) {
		super(msg);
	}
	
	public DAOException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
