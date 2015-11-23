package edu.depaul.se491.util;

/**
 * Values with names (meaning) instead of using hard coded (no names) values
 * @author Malik
 *
 */
public abstract class Values {

	private Values() {}
	
	public static final long UNKNOWN = -1;
	public static final int  SQL_NULL = java.sql.Types.NULL;
	
	public static final int ONE_ROW_AFFECTED = 1;
	public static final int ZERO_ROW_AFFECTED = 0;
	

	public static final String EMPTY_STRING = "";
	
	public static final String AJAX_REQUEST = "ajax";
	
	public static boolean validUpdate(int affectedRows) {
		return affectedRows == ZERO_ROW_AFFECTED || affectedRows == ONE_ROW_AFFECTED;
	}
	
	public static boolean validInsert(int affectedRows) {
		return affectedRows == ONE_ROW_AFFECTED;
	}
	
	public static boolean validDelete(int affectedRows) {
		return affectedRows == ONE_ROW_AFFECTED;
	}
	
}
