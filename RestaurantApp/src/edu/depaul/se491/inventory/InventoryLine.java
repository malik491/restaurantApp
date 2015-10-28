package edu.depaul.se491.inventory;

/**
 * @author Malik
 */
public interface InventoryLine {
	
	/**
	 * return inventory item
	 * @return
	 */
	public InventoryItem getItem();
	
	/**
	 * set inventory item
	 * @param item
	 */
	public void setItem(InventoryItem item);
	
	/**
	 * return current inventory item quantity
	 * @return
	 */
	public int getQuantity();
	
	/**
	 * set inventory item quantity
	 * @param quantity
	 */
	public void setQuantity(int quantity);

}
