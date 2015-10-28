/**
 * 
 */
package edu.depaul.se491.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.depaul.se491.order.Order;

/**
 * @author Mirtha (modified by Malik)
 *
 */
public class OrderDBImp implements OrderDB {
	// same database for all clients
	private static OrderDB instance = null;
	private static HashMap<String, Order> orders = new HashMap<>();
	
	// hidden constructor to enforce a single instance
	private OrderDBImp() {}
	
	/**
	 * return an account database
	 * @return
	 */
	public static OrderDB getInstance() {
		if (instance == null)
			instance = new OrderDBImp();
		return instance;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.OrderDB#addOrder(edu.depaul.se491.order.Order)
	 */
	@Override
	public boolean addOrder(Order o) {
		boolean added = false;
		if (validOrder(o)) {
			orders.put(o.getId(), o);
			added = true;
		}
		return added;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.OrderDB#removeOrder(java.lang.String)
	 */
	@Override
	public boolean removeOrder(String orderId) {
		return validOrderId(orderId)? (orders.remove(orderId) != null) : false;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.OrderDB#getOrder(java.lang.String)
	 */
	@Override
	public Order getOrder(String orderId) {
		return validOrderId(orderId)? orders.get(orderId) : null;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.OrderDB#getOrders()
	 */
	@Override
	public List<Order> getOrders() {
		List<Order> copy = new ArrayList<>();
		copy.addAll(orders.values());
		return Collections.unmodifiableList(copy);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.OrderDB#hasOrder(java.lang.String)
	 */
	@Override
	public boolean hasOrder(String orderId) {
		return validOrderId(orderId)? orders.containsKey(orderId) : false;
	}
	
	private boolean validOrderId(String orderId) {
		return (orderId != null && !orderId.isEmpty());
	}
	
	private boolean validOrder(Order o) {
		return (o != null && validOrderId(o.getId()));
	}

}
