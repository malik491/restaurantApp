package edu.depaul.se491.util;

/**
 * a class to hold column names for tables in the database
 * use these labels to get the column data from a row in a ResultSet
 * Like this:
 * 
 * ResultSet rs = .... 
 * int id   = rs.getInt(columnLabel)
 * String s = rs.getString(columnLabel) 
 *
 * @author Malik
 */
public abstract class DBLabels {

	private DBLabels() {}
	
	
	/* shared labels (e.g., foreign keys)*/
	public static final String ITEM_ID_LABEL = "menu_item_id";
	public static final String ORDER_ID_LABEL = "order_id";
	public static final String USER_ID_LABEL = "user_id";
	public static final String ADDRESS_ID_LABEL = "address_id";
	
	/* column names for ACCOUNTS table*/
	public static final String ACCOUNT_ID_LABEL = "account_id";
	public static final String USERNAME_LABEL = "username";
	public static final String PASSOWRD_LABEL = "password";
	public static final String ACCOUNT_ROLE_LABEL = "account_role";

	/* column names for ADDRESSES table*/
	public static final String ADDRESS_LINE1_LABEL = "line1";
	public static final String ADDRESS_LINE2_LABEL = "line2";
	public static final String ADDRESS_CITY_LABEL = "city";
	public static final String ADDRESS_ZIPCODE_LABEL = "zipcode";
	public static final String ADDRESS_STATE_LABEL = "state";
	
	/* unique column names for MENU_ITEMS table*/
	public static final String ITEM_NAME_LABEL = "item_name";
	public static final String ITEM_DESC_LABEL = "item_description";
	public static final String ITEM_PRICE_LABEL = "item_price";
	
	/* unique column names for ORDERS table*/
	public static final String ORDER_STATUS_LABEL = "order_status";
	public static final String ORDER_TYPE_LABEL = "order_type";
	public static final String ORDER_DATE_LABEL = "order_date";
	public static final String ORDER_TOTAL_LABEL = "order_total";
	public static final String ORDER_CONF_LABEL = "order_confirmation";
	
	
	/* unique names for ORDER_ITEMS table */
	public static final String QUANTITY_LABEL = "quantity";
	
	
	/* unique column names for USERS table */
	public static final String USER_FIRST_NAME_LABEL = "first_name";
	public static final String USER_LAST_NAME_LABEL = "last_name";
	public static final String USER_EMAIL_LABEL = "email";
	public static final String USER_PHONE_LABEL = "phone";
	
	
	public static final String TABLE_ACCOUNTS = "accounts";
	public static final String TABLE_ADDRESSES = "addresses";
	public static final String TABLE_ORDERS = "orders";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_ORDER_ITEMS = "order_items";
	public static final String TABLE_MENU_ITEMS = "menu_items";
	
	
	
	
}
