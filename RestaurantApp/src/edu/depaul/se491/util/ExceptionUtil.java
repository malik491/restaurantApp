/**
 * 
 */
package edu.depaul.se491.util;

import java.sql.SQLException;

/**
 * @author Malik
 *
 */
public abstract class ExceptionUtil {
	
	public static synchronized void printException(Exception e, String servletName) {
		System.err.println(e.getClass().getTypeName() + " Occured (servlet: " + servletName + ")");
		System.err.println(e.getMessage());
		if (e instanceof SQLException) {
			System.err.println(((SQLException)e).getSQLState());
			System.err.println(((SQLException)e).getErrorCode());			
		}
		System.err.flush();
		e.printStackTrace();
	}

}
