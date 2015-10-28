package edu.depaul.se491.order;

/**
 * an interface for an order item
 * @author Malik
 */
public interface OrderItem {

	/**
	 * return a menu item associated with this order item
	 * @return
	 */
	public MenuItem getMenuItem();
	
	/**
	 * set a menu item for this order item
	 * @param mItem a menu Item
	 */
	public void setMenuItem(MenuItem mItem);

	/**
	 * return the quantity for the menu item
	 * @return
	 */
	public int getQuantity();
	
	/**
	 * set the quantity for the menu item
	 * @param quantity
	 */
	public void setQuantity(int quantity);

}
