package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.bean.loader.OrderBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.util.DAOUtil;
import edu.depaul.se491.util.Values;

public class OrderDAO {
	private DAOFactory factory;
	private OrderBeanLoader loader;
	private AddressDAO addressDAO;
	private OrderItemDAO orderItemDAO;

	public OrderDAO(DAOFactory factory) {
		this.factory = factory;
		this.addressDAO = factory.getAddressDAO();
		this.orderItemDAO = factory.getOrderItemDAO();
		this.loader = new OrderBeanLoader();
	}
	
	/**
	 * return all orders in the database
	 * Empty list is returned if there are no orders in the database
	 * @return
	 * @throws SQLException
	 */
	public List<OrderBean> getAll() throws SQLException {
		return getMultiple(SELECT_ALL_WITH_ORDER_QUERY, null);
	}
	
	/**
	 * return all orders with the given order status in the database
	 * Empty list is returned if there are no orders for the given status in the database
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public List<OrderBean> getAllWithStatus(OrderStatus status) throws SQLException {
		return getMultiple(SELECT_BY_STATUS_QUERY, status.toString());
	}
	
	/**
	 * return all orders with the given order type in the database
	 * Empty list is returned if there are no orders for the given type in the database
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public List<OrderBean> getAllWithType(OrderType type) throws SQLException {
		return getMultiple(SELECT_BY_TYPE_QUERY, type.toString());
	}
	
	/**
	 * return order associated with the given id
	 * Null is returned if there are no order for the given id
	 * @param orderId
	 * @return
	 * @throws SQLException
	 */
	public OrderBean get(long orderId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		OrderBean order = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ID_QUERY);
			
			ps.setLong(1, orderId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				order = loader.loadSingle(rs);
			
			if (order != null)
				order.setItems(orderItemDAO.getOrderItems(order.getId()));
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return order;		
	}
	
	/**
	 * return order by its confirmation number
	 * Null is returned if there are no order with the given confirmation number
	 * @param orderConfirmation
	 * @return
	 * @throws SQLException
	 */
	public OrderBean get(String orderConfirmation) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		OrderBean order = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_CONFIRMATION_QUERY);
			
			ps.setString(1, orderConfirmation);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				order = loader.loadSingle(rs);
			
			if (order != null)
				order.setItems(orderItemDAO.getOrderItems(order.getId()));
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return order;		
	}
		
	/**
	 * add order to the database using the data in the orderBean
	 * @param order order data (excluding the id)
	 * @return true if order is added
	 * @throws SQLException
	 */
	public boolean add(OrderBean order) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean added = false;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			/* Transaction :
			 * - add address (if any)
			 * - add order
			 * - add order items
			 */
			
			// handle address
			AddressBean address = order.getDeliveryAddress();
			if (address != null) {
				address = addressDAO.transactionAdd(conn, address);
				added = (address != null);
				if (added)
					order.setDeliveryAddress(address);
			}
			
			if (added) {
				loader.loadParameters(ps, order, 1);
				added = Values.validInsert(ps.executeUpdate());
			}
			
			if (added) {
				order.setId(DAOUtil.getAutGeneratedKey(ps));
				long orderId = order.getId();
				List<OrderItemBean> oItems = order.getItems();
				added = (oItems.size() > 0)? orderItemDAO.transactionAdd(conn, orderId, oItems) : false;				
			}
			
			if (!added)
				throw new SQLException("OrderDAO.add(): failed to add order");
			
			// commit the transaction
			conn.commit();

		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return added;
	}
		
	/**
	 * Update an existing order with new data in the orderBean.
	 * It updates all order fields except orderId
	 * For order items, only quantity is updated.
	 * @param order updated order
	 * @return true if order is updated
	 * @throws SQLException
	 */
	public boolean update(final OrderBean updatedOrder) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;		
		boolean updated = false;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPDATE_ORDER_QUERY);

			/*
			 * transaction:
			 * - update order items
			 * - update order and order address
			 */
			final OrderBean oldOrder = get(updatedOrder.getId());
			final long orderId = oldOrder.getId();
			
			final AddressBean oldAddr = oldOrder.getDeliveryAddress();
			final AddressBean updatedAddr = updatedOrder.getDeliveryAddress();
			
			boolean hasNoAddr = (oldAddr == null && updatedAddr == null);
			boolean addNewAddr = (oldAddr == null && updatedAddr != null);
			boolean deletedAddr = (oldAddr != null && updatedAddr == null);
			boolean updatAddr = (oldAddr != null && updatedAddr != null) && (oldAddr.getId() == updatedAddr.getId());
			
			if (hasNoAddr || deletedAddr) {
				updated = true;
			} else if (addNewAddr) {
				AddressBean addedAddr = addressDAO.transactionAdd(conn, updatedAddr);
				updated = (addedAddr != null);
				if (updated)
					updatedOrder.setDeliveryAddress(addedAddr);
			} else if (updatAddr) {
				// SAME address ids, check for different data (id not used in equals)
				boolean hasDifferentData = !oldAddr.equals(updatedAddr);
				updated = hasDifferentData? addressDAO.transactionUpdate(conn, updatedAddr) : true;
			}
		
			// update order items
			if (updated)
				updated = updateOrderItem(conn, orderId, oldOrder.getItems(), updatedOrder.getItems());
						
			// update order
			if (updated) {
				int paramIndex = 1;
				loader.loadParameters(ps, updatedOrder, paramIndex);
				ps.setLong(paramIndex + ORDER_COLUMNS_COUNT, orderId);
				
				updated = Values.validUpdate(ps.executeUpdate());

				// if delete address, only delete it now; that is after updating/setting the order's address_id (foreign key) to NULL
				if (updated && deletedAddr)
					updated = addressDAO.transactionDelete(conn, oldAddr.getId());

			}
				
			if (!updated)
				throw new SQLException("OrderDAO.update(): failed to update order");
			
			// commit
			conn.commit();
			
		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return updated;
	}
	

	/**
	 * delete an existing order from the database
	 * @param order
	 * @return true if an order is deleted
	 * @throws SQLException
	 */
	public boolean delete(OrderBean order) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean deleted = false;
		
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			
			/*
			 * Transaction:
			 * - delete order items
			 * - delete order
			 * - delete address (if any)
			 */
			
			long orderId = order.getId();
			ps = conn.prepareStatement(DELETE_ORDER_QUERY);
			ps.setLong(1, orderId);

			// delete order items first (have foreign key to order)
			deleted = orderItemDAO.transactionDelete(conn, orderId, order.getItems().size());

			// delete order (has foreign key to address)
			if (deleted)
				deleted = Values.validDelete(ps.executeUpdate());

			// finally you can delete the address
			if (deleted) {
				AddressBean address = order.getDeliveryAddress();
				deleted = address != null? addressDAO.transactionDelete(conn, address.getId()) : true;
			}
			
			if (!deleted)
				throw new SQLException("OrderDAO.delete(): failed to delete order");

			// commit
			conn.commit();
			
		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;		
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return deleted;
	}
	
	/**
	 * return multiple orders from the database based on the select sqlQuery and parameter value
	 * @param sqlQuery select query with optional WHERE clause '... [WHERE columnName = ?]'
	 * @param paramValue param value for the condition in WHERE clause, if present in the sqlQuery
	 * @return
	 * @throws SQLException
	 */
	private List<OrderBean> getMultiple(String selectSQLQuery, String paramValue) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<OrderBean> orders = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(selectSQLQuery);
			
			if (paramValue != null)
				ps.setString(1, paramValue);

			ResultSet rs = ps.executeQuery();
			orders = loader.loadList(rs);
			
			for (OrderBean order: orders)
				order.setItems(orderItemDAO.getOrderItems(order.getId()));
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return orders;
	}
		
	/**
	 * update order items quantity for an order
	 * a quantity with value of 0 will delete the order item for this order. 
	 * If all order items have quantity = 0, this method will not update
	 * the order items and it will return false. Delete the order instead of
	 * updating all of its order items with zero quantity
	 * @param conn
	 * @param oldOrder old order in DB with order items
	 * @param updatedOrder up
	 * @return
	 * @throws SQLException
	 */
	private boolean updateOrderItem(Connection conn, final long orderId, final List<OrderItemBean> oldItems, final List<OrderItemBean> updatedItems) throws SQLException {
		if (oldItems.size() != updatedItems.size())
			throw new SQLException("OrderDAO.updateOrderItem(): failed to update order (old and updated items have different sizes)");
			
		List<OrderItemBean> changedItems = new ArrayList<>();
		for (OrderItemBean oldItem: oldItems) {
			boolean foundMatch = false;	
			for (OrderItemBean updatedItem: updatedItems) {
				if (oldItem.getMenuItem().getId() == updatedItem.getMenuItem().getId()) {
					foundMatch = true;
					if (oldItem.getQuantity() != updatedItem.getQuantity()) {
						changedItems.add(updatedItem);
						break; // break from the inner loop (process the next oldItem)
					}
				}
			}
			if (!foundMatch)
				throw new SQLException("OrderDAO.updateOrderItem(): failed to update order (old and updated items don't match on menu items)");
		}
		
		boolean updated = true;
		int changedItemsCount = changedItems.size();
		int oldItemsCount = oldItems.size();
		boolean changeAll = changedItemsCount == oldItemsCount;
		
		if (changedItemsCount > 0) {
			boolean allHaveZeroQty = false;
			
			if (changeAll) {
				allHaveZeroQty = true;
				for (OrderItemBean oItem: changedItems) {
					allHaveZeroQty &= oItem.getQuantity() == 0;
				}				
			}
			updated = allHaveZeroQty? false : orderItemDAO.transactionUpdate(conn, orderId, changedItems);			
		}
		
		return updated;
	}
	
	
	
	private static final String SELECT_ALL_QUERY = 
			"SELECT o.*, a.line1, a.line2, a.city, a.state, a.zipcode FROM orders as o LEFT JOIN addresses as a ON (o.address_id = a.address_id)";
	
	private static final String SELECT_ALL_WITH_ORDER_QUERY = 	SELECT_ALL_QUERY + " ORDER BY (o.order_id)";
	private static final String SELECT_BY_ID_QUERY =			SELECT_ALL_QUERY + " WHERE (o.order_id = ?)";
	private static final String SELECT_BY_STATUS_QUERY = 		SELECT_ALL_QUERY + " WHERE (o.order_status = ?) ORDER BY order_timestamp ASC";
	private static final String SELECT_BY_TYPE_QUERY = 	 		SELECT_ALL_QUERY + " WHERE (o.order_type = ?) ORDER BY order_type ASC";
	private static final String SELECT_BY_CONFIRMATION_QUERY = 	SELECT_ALL_QUERY + " WHERE (o.order_confirmation = ?)";
	
	private static final String INSERT_ORDER_QUERY = 
			"INSERT INTO orders (order_status, order_type, order_timestamp, order_total, order_confirmation, address_id) VALUES (?, ?, ?, ?, ?, ?)";	
	
	private static final String UPDATE_ORDER_QUERY = 
			"UPDATE orders SET order_status=?, order_type=?, order_timestamp=?, order_total=?, order_confirmation=?, address_id=? WHERE (order_id = ?)";
	
	private static final String DELETE_ORDER_QUERY = 
			"DELETE FROM orders WHERE (order_id = ?)";
	
	private static final int ORDER_COLUMNS_COUNT = 6;
	
}
