/**
 * 
 */
package edu.depaul.se491.util;

/**
 * a class for generic query generating methods
 * @author Malik
 */
public abstract class GenericQuery {

	
	/**
	 * Usage: PreparedStatement or Statement
	 * Query: SELECT * FROM tableName
	 * @param tableName
	 * @return
	 */
	public static String selectAll(String tableName) {
		return String.format("SELECT * FROM %s", tableName);
	}

	/** 
	 * Usage: PrepareStatement or Statement
	 * Query: SELECT * FROM tableName ORDER BY colName ASC | DESC
	 * 
	 * @param tableName
	 * @param colName
	 * @param isDesc sort the result in a descending order
	 * @return
	 */
	public static String selectAllOrdered(String tableName, String colName, boolean isDesc) {
		return String.format("SELECT * FROM %s ORDER BY %s %s", tableName, colName, isDesc? "DESC" : "ASC" );
	}
	
	/**
	 * return a query with a placeholder for parameter
	 * 
	 * Usage: PreparedStatement ONLY
	 * Query: SELECT * FROM tableName WHERE (colName = ?)
	 * 
	 * @param tableName
	 * @param colName
	 * @return
	 */
	public static String selectAllById(String tableName, String colName) {
		return String.format("SELECT * FROM %s WHERE (%s = ?)", tableName, colName);
	}
	
	/**
	 * Usage: PreparedStatement ONLY
	 * Query: INSERT INTO tableName (col1, col2, ..) VALUES (?, ?, ..)
	 * @param tableName
	 * @param columns column names
	 * @return
	 */
	public static String insertWithColQuery(String tableName, String[] columns) {
		String comma = " , ";
		String questionMark = " ? ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "); 
		sb.append(tableName);
		sb.append(" ( ");
		
		StringBuilder values = new StringBuilder();
		values.append(" ) VALUES ( ");
		
		int i = 1;
		int length = columns.length;

		for (String col: columns) {
			sb.append(col);
			values.append(questionMark);
			
			if (i < length) {
				sb.append(comma);
				values.append(comma);
			}
			i++;
		}	
		values.append(" ) ");
		sb.append(values);
		return sb.toString();
	}
	
	/**
	 * Usage: PreparedStatement ONLY
	 * Query: INSERT INTO tableName VALUES (?, ?, ..)
	 * @param tableName
	 * @param placeHolders number of placeholders to add
	 * @return
	 */
	public static String insertQuery(String tableName, int placeHolders) {
		String comma = " , ";
		String questionMark = " ? ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "); 
		sb.append(tableName);
		sb.append(" VALUES ( ");
		
		for (int i=0; i < placeHolders; i++) {
			sb.append(questionMark);
			if (i+1 < placeHolders)
				sb.append(comma);
		}
		sb.append(" ) ");
		return sb.toString();
	}
	
	/**
	 * Usage: PreparedStatement ONLY
	 * Query: UPDATE TABLE tableName SET (col1=?, col2=?, ..) WHERE (col = ? ) 
	 * @param tableName
	 * @param columns column names
	 * @param conditionCol condition column for the where clause
	 * @return
	 */
	public static String updateQuery(String tableName, String[] columns, String conditionCol) {
		String comma = " , ";
		String equalQM = " = ? ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE "); 
		sb.append(tableName);
		sb.append(" SET ");
				
		int i = 1;
		int length = columns.length;
		for (String col: columns) {
			sb.append(col);
			sb.append(equalQM);
			if (i < length) {
				sb.append(comma);
			}
			i++;
		}
		sb.append(" WHERE ( ");
		sb.append(conditionCol);
		sb.append(" = ? )");
		
		return sb.toString();
	}
	
	
}
