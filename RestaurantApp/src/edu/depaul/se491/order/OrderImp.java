package edu.depaul.se491.order;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * a class for an order
 * @author Lamont (modified by Malik)
 */
public class OrderImp implements Order{
	private int id;
	private OrderStatus status;
	private HashMap<String,OrderItem> orderItems;
		
	/**
	 * construct an empty order
	 */
	public OrderImp() {
		this.id = UNIQUE_ID++;
		orderItems = new HashMap<>();
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#getId()
	 */
	public String getId() {
		return Integer.toString(id);
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#setId(java.lang.String)
	 */
	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.err.println(String.format("Invalid input. OrderImp.setId(%s)", id));
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#addItem(edu.depaul.se491.order.MenuItem)
	 */
	public boolean addItem(MenuItem mItem) {
		String mItemId = mItem.getId();
		OrderItem oItem = orderItems.get(mItemId);
		
		if(oItem != null) {
			// just update the quantity for the menu item
			int oldQuantity = oItem.getQuantity();
			oItem.setQuantity(oldQuantity + 1);
		} else {
			// create a new order item
			OrderItem newOrderItem = new OrderItemImp(mItem, 1);
			orderItems.put(mItemId, newOrderItem);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#removeItem(java.lang.String)
	 */
	public boolean removeItem(String mItemId) {
		return (orderItems.remove(mItemId) != null);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#getItem(java.lang.String)
	 */
	public OrderItem getItem(String mItemId) {
		return orderItems.get(mItemId);
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#getItems()
	 */
	public List<OrderItem> getItems() {
		List<OrderItem> copy = new ArrayList<>();
		copy.addAll(orderItems.values());
		return Collections.unmodifiableList(copy);
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#addItems(java.util.List<edu.depaul.se491.order.MenuItem>)
	 */
	public void addItems(List<MenuItem> mItemList) {
		for(MenuItem mItem : mItemList){
			addItem(mItem);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#hasItem(java.lang.String)
	 */
	public boolean hasItem(String mItemId) {
		return orderItems.containsKey(mItemId);
	}
		
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#getTotalPrice()
	 */
	public double getTotalPrice() {
		double total = 0;
		for(OrderItem oItem: orderItems.values())
			total += (oItem.getMenuItem().getPrice() * oItem.getQuantity());
		return total;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#setStatus(edu.depaul.se491.order.OrderStatus)
	 */
	public void setStatus(OrderStatus status) {
		this.status=status;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Order#getStatus()
	 */
	public OrderStatus getStatus() {
		return status;
	}
	
	private static int UNIQUE_ID = 1;
}
