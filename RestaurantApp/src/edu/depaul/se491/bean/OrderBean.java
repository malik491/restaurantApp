package edu.depaul.se491.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.util.Values;

/**
 * Order Bean to store order data
 * @author Malik
 */

public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private AddressBean deliveryAddress;
	private OrderStatus status;
	private OrderType type;
	private Timestamp timestamp;
	private double total;
	private String confirmation;
	private final Map<String, OrderItemBean> orderItems;
	
	/**
	 * construct an empty order with no id
	 */
	public OrderBean() {
		id = Values.UNKNOWN;
		orderItems = new HashMap<String, OrderItemBean>();
	}

	/**
	 * return order id
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * set order id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * return all order items for this order
	 * @return
	 */
	public List<OrderItemBean> getItems() {
		List<OrderItemBean> result = new ArrayList<>();
		for(OrderItemBean oItem: orderItems.values()) {
			result.add(oItem);
		}
		return result;
	}
	
	/**
	 * set order items for this order
	 * @param items
	 */
	public void setItems(List<OrderItemBean> items) {
		for (OrderItemBean oItem: items) {
			String key = Long.toString(oItem.getMenuItem().getId());

			OrderItemBean oldItem = orderItems.get(key);
			if (oldItem != null)
				oldItem.setQuantity(oldItem.getQuantity() + oItem.getQuantity());	
			else
				orderItems.put(key, oItem);				
		}
	}

	/**
	 * return order status
	 * @return
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * set order status
	 * @param status
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/**
	 * return order type
	 * @return
	 */
	public OrderType getType() {
		return type;
	}

	/**
	 * set order type
	 * @param type
	 */
	public void setType(OrderType type) {
		this.type = type;
	}

	/**
	 * return the order timestamp (data & time)
	 * @return
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * set the order timestamp (data & time)
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * return total
	 * @return
	 */
	public double getTotal() {
		return total;
	}
	
	/**
	 * set total
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * return the delivery address
	 * null if getType() != OrderType.DELIVERY
	 * @return
	 */
	public AddressBean getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * set the delivery address
	 * @param deliveryAddress
	 */
	public void setDeliveryAddress(AddressBean deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * return order confirmation number
	 * @return
	 */
	public String getConfirmation() {
		return confirmation;
	}

	/**
	 * set the order confirmation number
	 * @param confirmation
	 */
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
}
