package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.bean.loader.OrderItemBeanLoader;
import edu.depaul.se491.dao.DAOFactory;

/**
 * OrderItemDAO access/modify order items data in the database
 * @author Malik
 *
 */
public class OrderItemDAO {
	private final DAOFactory factory;
	private final OrderItemBeanLoader loader;
	
	public OrderItemDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new OrderItemBeanLoader();
	}
	
	/**
	 * return a list of all order items for a given order (specified by orderId)
	 * Empty List is returned if there are no order items for the given order
	 * @param orderId order id associated with order items
	 * @return
	 * @throws SQLException
	 */
	public List<OrderItemBean> getOrderItems(long orderId) throws SQLException  {
		Connection conn = null;
		PreparedStatement ps = null;
		List<OrderItemBean> orderItems = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ORDER_ID_QUERY);
			
			ps.setLong(1, orderId);
			ResultSet rs = ps.executeQuery();
			
			orderItems = loader.loadList(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return orderItems;
	}
	
	/**
	 * Insert a list of order items for the given order as a part of a database transaction
	 * @param conn connection for this transaction
	 * @param orderId order id associated with items param
	 * @param items order items to add
	 * @return true if all items are added
	 * @throws SQLException
	 */
	public boolean transactionAdd(Connection conn, long orderId, List<OrderItemBean> items) throws SQLException {
		PreparedStatement ps = null;
		boolean added = false;
		try {
			int itemsSize = items.size();
			if (itemsSize < 1)
				return added;
			
			String multipleRowInsertQuery = getMultipleRowInsert(itemsSize);
			ps = conn.prepareStatement(multipleRowInsertQuery);
			
			int paramIndex = 1;
			for(OrderItemBean oItem: items) {
				loader.loadParameters(ps, oItem, paramIndex, orderId);
				paramIndex += INSERT_ORDER_ITEM_COLUMNS_COUNT;
			}
			
			added = ps.executeUpdate() == itemsSize;
					
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}

	/**
	 * update order items quantities as a part of a database transaction
	 * order item with quantity <= 0 will be deleted
	 * @param conn connection for this transaction
	 * @param orderId order id associated with items param
	 * @param items order items to update
	 * @return true if all items are updated
	 * @throws SQLException
	 */
	public boolean transactionUpdate(Connection conn, long orderId, List<OrderItemBean> items) throws SQLException {
		Statement updateS = null;
		Statement deleteS = null;
		boolean updated = false;
		try {
			int itemsCount = items.size();
			if (itemsCount < 1)
				return updated;
			
			// have you used Statement (instead of preparedStatement).
			// according to the docs, we can't get the number of rows
			// affected by each preparedStatement when using executeBatch 
			updateS = conn.createStatement();
			deleteS = conn.createStatement();
			
			boolean hasUpdate = false;
			boolean hasDelete = false;
			for(OrderItemBean oItem: items) {
				int newQuantity = oItem.getQuantity();
				long menuItemId = oItem.getMenuItem().getId();
				if (newQuantity > 0) {
					String updateQuery = String.format(
							"UPDATE order_items SET quantity=%d WHERE (order_id = %d AND menu_item_id= %d)",
							newQuantity, orderId, menuItemId);
					updateS.addBatch(updateQuery);
					hasUpdate = true;
				} else {
					String deleteQuery = String.format(
							"DELETE FROM order_items WHERE (order_id = %d AND menu_item_id= %d)",
							orderId, menuItemId);
					deleteS.addBatch(deleteQuery);
					hasDelete = true;	
				}
			}
			
			int updateCount = 0;
			if (hasUpdate)
				updateCount = executeBatch(updateS);
			
			int deleteCount = 0;
			if (hasDelete)
				deleteCount = executeBatch(deleteS);
			
			updated = updateCount + deleteCount == itemsCount;
					
		} catch (SQLException e) {
			throw e;
		} finally {
			if (updateS != null)
				updateS.close();
			if (deleteS != null)
				deleteS.close();
		}
		return true;
	}
	
	
	/**
	 * delete all order items for a given order as a part of a database transaction
	 * @param conn connection for this transaction
	 * @param orderId order id associated with the items
	 * @param itemsCount number of items for the order
	 * @return true if all items are deleted (deletedCount = itemsCount)
	 * @throws SQLException
	 */
	public boolean transactionDelete(Connection conn, long orderId, int itemsCount) throws SQLException {
		PreparedStatement ps = null;
		boolean deleted = false;
		try {
			ps = conn.prepareStatement(DELETE_ORDER_ITEMS_QUERY);
			ps.setLong(1, orderId);
			deleted = ps.executeUpdate() == itemsCount;
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return deleted;
	}
	
	/**
	 * execute a batched Statement (multiple queries)
	 * @param statement statement to batch-execute
	 * @return the number of affected row by executing the batch statement
	 * @throws SQLException
	 */
	private int executeBatch(Statement statement) throws SQLException {
		int numOfAffectedRows = 0;
		int[] batchUpdateCount = statement.executeBatch();
		for(int count: batchUpdateCount) {
			if (count == Statement.SUCCESS_NO_INFO) { 
				// (SUCCESS_NO_INFO = -2). Assume one row is affected (we can't know for sure without calling get).
				numOfAffectedRows++;
			} else if (count != Statement.EXECUTE_FAILED) {
				numOfAffectedRows += count;
			}
		}
		return numOfAffectedRows;
	}
	
	/**
	 * return a multiple row INSERT query for preparedStatement
	 * Example of 2 rows insert query (one column):INSERT ... VALUES (?), (?)
	 * @param numOfRows number of rows to be inserted
	 * @return
	 */
	private String getMultipleRowInsert(int numOfRows) {
		StringBuilder sb = new StringBuilder(INSERT_ORDER_ITEM_QUERY);
		for (int i=1; i < numOfRows; i++) {
			sb.append(MULTIPLE_INSERT_ROW);
		}
		return sb.toString();	
	}
	
	
	
	private static final String SELECT_BY_ORDER_ID_QUERY = "SELECT * FROM order_items NATURAL JOIN menu_items WHERE (order_id = ?)";
	private static final String INSERT_ORDER_ITEM_QUERY =  "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
	private static final String DELETE_ORDER_ITEMS_QUERY = "DELETE FROM order_items WHERE (order_id = ?)";

	
	private static final String MULTIPLE_INSERT_ROW = " ,(?,?,?)";
	private static final int INSERT_ORDER_ITEM_COLUMNS_COUNT = 3;
}
