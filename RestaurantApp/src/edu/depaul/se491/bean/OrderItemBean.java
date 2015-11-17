package edu.depaul.se491.bean;

import java.io.Serializable;

/**
 * Order Item Bean to store an order item data (menu item & quantity)
 * @author Malik
 */
public class OrderItemBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MenuItemBean menuItem;
	private int quantity;
	
	/**
	 * construct an empty order item
	 */
	public OrderItemBean() {}
		
	/**
	 * return the Menu Item associated with this Order Item
	 * @return
	 */
	public MenuItemBean getMenuItem() {
		return menuItem;
	}

	/**
	 * set the Menu Item associated with this Order Item
	 * @param menuItem
	 */
	public void setMenuItem(MenuItemBean menuItem) {
		this.menuItem = menuItem;
	}
	
	/**
	 * return quantity for the menu item
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * set the quantity for the menu item
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
