package edu.depaul.se491.db;

import java.util.List;

import edu.depaul.se491.order.Order;

/**
 * an interface representing a database for orders.
 * @author Malik
 */
public interface OrderDB {

	/**
	 * add order to the database
	 * @param o
	 * @return
	 */
	public boolean addOrder(Order o);
	
	/**
	 * remove order from the database
	 * @param orderId
	 * @return
	 */
	public boolean removeOrder(String orderId);
	
	/**
	 * return an order or null if no such order in the DB
	 * @param orderId
	 * @return
	 */
	public Order getOrder(String orderId);
	
	/**
	 * return all orders in the DB
	 * @return list of orders of an empty list
	 */
	public List<Order> getOrders();
	
	/**
	 * return true if the DB has the given order
	 * @param orderId
	 * @return
	 */
	public boolean hasOrder(String orderId);
	
}
