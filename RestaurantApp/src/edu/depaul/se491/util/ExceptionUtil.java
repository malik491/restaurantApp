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
		System.out.println(e.getClass().getTypeName() + " Occured (servlet: " + servletName + ")");
		System.out.println("Exception Message: " + e.getMessage() == null? " no meesage" : e.getMessage());
		
		if (e instanceof SQLException) {
			System.out.println("SQLState: " + ((SQLException)e).getSQLState());
			System.out.println("SQLErrorCode:" + ((SQLException)e).getErrorCode());			
		}
		System.out.flush();
		e.printStackTrace(System.out);
	}

}
