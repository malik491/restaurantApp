/**
 * 
 */
package edu.depaul.se491.bean.order;

import java.io.Serializable;

import edu.depaul.se491.bean.menu.MenuItemBean;

/**
 * Order Item Bean
 * @author Malik
 */
public class OrderItemBean implements Serializable {
	private MenuItemBean menuItem;
	private int quantity;
	
	
	public OrderItemBean() {
		
	}


	public MenuItemBean getMenuItem() {
		return menuItem;
	}


	public void setMenuItem(MenuItemBean menuItem) {
		this.menuItem = menuItem;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
