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
	public static final String ADDRESS_ID_LABEL = "address_id";
	public static final String USER_EMAIL_LABEL = "user_email";
	
	/* unique column names for ACCOUNTS table*/
	public static final String ACCOUNT_ROLE_LABEL = "account_role";
	public static final String USERNAME_LABEL = "username";
	public static final String PASSOWRD_LABEL = "password";
	
	/* unique column names for USERS table*/
	public static final String USER_FIRST_NAME_LABEL = "first_name";
	public static final String USER_LAST_NAME_LABEL = "last_name";
	public static final String USER_PHONE_LABEL = "phone";
	
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
	public static final String ORDER_TIMESTAMP_LABEL = "order_timestamp";
	public static final String ORDER_TOTAL_LABEL = "order_total";
	public static final String ORDER_CONF_LABEL = "order_confirmation";
	
	
	/* unique column names for ORDER_ITEMS table */
	public static final String QUANTITY_LABEL = "quantity";
	
	
	
	public static final String TABLE_ACCOUNTS = "accounts";
	public static final String TABLE_ADDRESSES = "addresses";
	public static final String TABLE_ORDERS = "orders";
	public static final String TABLE_ORDER_ITEMS = "order_items";
	public static final String TABLE_MENU_ITEMS = "menu_items";
	
	
	
	public static final int ACCOUNT_USERNAME_LEN_MAX = 30; 
	public static final int ACCOUNT_PASSWORD_LEN_MAX = 60; 	
	public static final int USER_FIRST_NAME_LEN_MAX = 20;
	public static final int USER_LAST_NAME_LEN_MAX = 20; 
	public static final int USER_EMAIL_LEN_MAX = 30; 
	public static final int USER_PHONE_LEN_MAX = 15;
	public static final int MENU_ITEM_DESC_LEN_MAX = 300; 
	public static final int MENU_ITEM_NAME_LEN_MAX = 100; 
	public static final int ORDER_CONFIRMATION_LEN_MAX = 50; 
	public static final int ADDRS_LINE1_LEN_MAX = 100; 
	public static final int ADDRS_LINE2_LEN_MAX = 100;
	public static final int ADDRS_CITY_LEN_MAX = 100;
	public static final int ADDRS_ZIPCODE_LEN_MAX = 10;
	public static final double ORDER_TOTAL_MAX = 99999.99; /*MYSQL Decimal(7,2) --,---.--*/ 
	public static final int ORDER_ITEM_QUANTITY_MAX = 65535; /*MySQL unsigned smallInt*/ 
	public static final double MENU_ITEM_PRICE_MAX = 999.99; /*MySQL Decimal(5,2) ---.--*/

}
