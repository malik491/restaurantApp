/**
 * 
 */
package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.bean.loader.OrderItemBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.Values;

/**
 * OrderItemDAO access/modify orderItem data in the database
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
	 * @param orderId the id order a given order
	 * @return
	 * @throws SQLException
	 */
	public List<OrderItemBean> getItemsForOrder(long orderId) throws SQLException  {
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
		}
		return orderItems;
	}
	
	/**
	 * Insert a list of order items for the given order as a part of a database transaction
	 * @param conn connection for this transaction
	 * @param order order with orderItem data
	 * @return
	 * @throws SQLException
	 */
	public boolean transactionAdd(Connection conn, OrderBean order) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_ORDER_ITEM_QUERY);
			
			long orderId = order.getId();
			for(OrderItemBean oItem: order.getItems()) {
				loader.loadParameters(ps, oItem, orderId);
				if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
					throw new SQLException("OrderItemDAO.transactionAdd(): multiple (or 0) rows affected by transactionAdd(order).");
			}		
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
		return true;
	}
	
	private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
	private static final String SELECT_BY_ORDER_ID_QUERY = "SELECT * FROM order_items NATURAL JOIN menu_items WHERE (order_id = ?)";
	
	
}
