package edu.depaul.se491.order;

/**
 * a class for an order item
 * @author Lamont
 */
public class OrderItemImp implements OrderItem {
	private MenuItem mItem;
	int quantity;
		
	/**
	 * construct a new order item
	 * @param mItem
	 * @param quantity
	 */
	public OrderItemImp(MenuItem mItem, int quantity) {
		this.mItem = mItem;
		this.quantity = quantity;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.OrderItem#getMenuItem()
	 */
	public MenuItem getMenuItem() {
		return mItem;		
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.OrderItem#setMenuItem( edu.depaul.se491.order.MenuItem)
	 */
	public void setMenuItem(MenuItem mItem) {
		this.mItem = mItem;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.OrderItem#getQuantity()
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.OrderItem#setQuantity(int)
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
