package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.OrderBean;
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
		return getMultiple(SELECT_ALL_QUERY, null);
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
				order.setItems(orderItemDAO.getItemsForOrder(order.getId()));
			
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
				order.setItems(orderItemDAO.getItemsForOrder(order.getId()));
			
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
	 * Delivery address (if any) and orderItems are also added (as a single transaction)
	 * @param order order data (excluding the id)
	 * @return
	 * @throws SQLException
	 */
	public boolean add(OrderBean order) throws SQLException {
		Connection conn = null;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			
			/* Transaction begin :
			 * - add address (if any)
			 * - add order
			 * - add order items
			 * - commit or roll back
			 */
			
			// handle address
			AddressBean deliveryAddress = order.getDeliveryAddress();
			if (deliveryAddress != null && deliveryAddress.getId() == Values.UNKNOWN) {
				addressDAO.transactionAdd(conn, deliveryAddress);
			}
			
			// handle the order
			transactionAdd(conn, order);
			
			// handle order items
			orderItemDAO.transactionAdd(conn, order);

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
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		
		return true;
	}
		
	/**
	 * update an existing order with new data in the orderBean
	 * orderItems data will not be updated for this order once 
	 * the order is added to the database
	 * @param order updated order data (status, tpye, total, confirmation, and delivery address)
	 * @return
	 * @throws SQLException
	 */
	public boolean update(OrderBean order) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;		

		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(UPDATE_ORDER_QUERY);
			
			loader.loadParameters(ps, order);
			ps.setLong(6, order.getId());
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("OrderDAO.update(): multiple (or 0) rows affected by a single update(order)");
		
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return true;
	}
	
	/**
	 * Insert a new order as a part of a database transaction
	 * Also, it will set the id in the passed object (the order parameter)
	 * @param conn connection for this transaction
	 * @param order order data (except the id). 
	 * @return if the order is inserted, its id will be set and return true
	 * @throws SQLException
	 */
	public boolean transactionAdd(Connection conn, OrderBean order) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			loader.loadParameters(ps, order);
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("OrderDAO.transactionAdd(): multiple (or 0) rows affected by transactionAdd(order)");
			
			// set the order id
			order.setId(DAOUtil.getAutGeneratedKey(ps));
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	
	/**
	 * return multiple orders from the database base on the select sqlQuery and parameter value
	 * @param sqlQuery select query with optional WHERE clause '... [WHERE columnName = ?]'
	 * @param paramValue condition value for the WHERE clause, if present in the sqlQuery
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
				order.setItems(orderItemDAO.getItemsForOrder(order.getId()));
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
	
	
	/* list of queries for PreparedStatement (pre-compiled objects)*/
	private static final String SELECT_ALL_QUERY = 
			  "SELECT o.*, a.line1, a.line2, a.city, a.state, a.zipcode FROM orders as o LEFT JOIN addresses as a ON (o.address_id = a.address_id)";
	private static final String SELECT_BY_ID_QUERY = 
			SELECT_ALL_QUERY + " WHERE (o.order_id = ?)";
	private static final String SELECT_BY_STATUS_QUERY = 
			SELECT_ALL_QUERY + " WHERE (o.order_status = ?)";
	private static final String SELECT_BY_TYPE_QUERY = 
			SELECT_ALL_QUERY + " WHERE (o.order_type = ?)";
	private static final String SELECT_BY_CONFIRMATION_QUERY = 
			SELECT_ALL_QUERY + " WHERE (o.order_confirmation = ?)";
	
			
	private static final String INSERT_ORDER_QUERY = 
			"INSERT INTO orders (address_id, order_status, order_type, order_total, order_confirmation) VALUES (?, ?, ?, ?, ?)";	
	private static final String UPDATE_ORDER_QUERY = 
			"UPDATE orders SET address_id=?, order_status=?, order_type=?, order_total=?, order_confirmation=? WHERE (order_id = ?)";
	

	
}
