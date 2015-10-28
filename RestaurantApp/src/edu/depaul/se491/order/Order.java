package edu.depaul.se491.order;

import java.util.List;

/**
 * an interface for a food order
 * @author Malik
 */
public interface Order {
	
	
	/**
	 * return order id
	 * @return
	 */
	public String getId();
	
	/**
	 * set order id
	 * @param id
	 */
	public void setId(String id);
	
	/**
	 * add a menu item to this order
	 * @param mIem menu item to add
	 * @return
	 */
	public boolean addItem(MenuItem mIem);
	
	/**
	 * remove a menu item from this order
	 * @param mItemId id of the menu item to remove
	 * @return
	 */
	public boolean removeItem(String mItemId);

	/**
	 * return order item associated with the given menu item.
	 * null is returned if this order does not have the given menu item
	 * @param mItemId id of a menu item
	 * @return orderItem or null
	 */
	public OrderItem getItem(String mItemId);
	
	/**
	 * return all items in this order
	 * @return
	 */
	public List<OrderItem> getItems();
	
	/**
	 * return true if this order has a menu item	
	 * @param mItemId id of a menu item
	 * @return
	 */
	public boolean hasItem(String mItemId);
		
	/**
	 * return total price for this order
	 * @return
	 */
	public double getTotalPrice();
	
	/**
	 * set order status
	 * @param status
	 */
	public void setStatus(OrderStatus status);
	
	/**
	 * return order status
	 * @return
	 */
	public OrderStatus getStatus();
}
