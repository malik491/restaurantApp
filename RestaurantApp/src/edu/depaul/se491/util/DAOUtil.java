package edu.depaul.se491.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * utility class for DAO objects and SQLException
 * @author Malik
 */
public abstract class DAOUtil {
	
	/**
	 * return the auto generated key
	 * @param ps
	 * @return
	 * @throws SQLException
	 */
	public static long getAutGeneratedKey(PreparedStatement ps) throws SQLException {
		long newId = Values.UNKNOWN;
		ResultSet addressKey = ps.getGeneratedKeys();
		newId = addressKey.next()? addressKey.getLong(1) : Values.UNKNOWN;  					
		if (newId == Values.UNKNOWN)
			throw new SQLException("DAOUtil.getAutGeneratedKey(): can't get generated key for newly inserted item.");
		return newId;
	}
	
	public static synchronized void printException(SQLException e, String servletName) {
		System.err.println("SQLException Occured (servlet: " + servletName + ")");
		System.err.println(e.getMessage());
		System.err.println(e.getSQLState());
		System.err.println(e.getErrorCode());
		System.err.flush();
		e.printStackTrace();
	}	
}
