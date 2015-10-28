package edu.depaul.se491.inventory;

import java.util.List;

/**
 * @author Malik
 *
 */
public interface Inventory {

	/**
	 * add inventory item
	 * @param item
	 * @return
	 */
	public boolean addItem(InventoryItem item);
	
	/**
	 * remove inventory item
	 * @param itemId inventory item id
	 * @return
	 */
	public boolean removeItem(String itemId);
	
	/**
	 * return inventory line associated with inventory item or null 
	 * @param itemId inventory item id
	 * @return
	 */
	public InventoryLine getItem(String itemId);
	
	/**
	 * return all items
	 * @return
	 */
	public List<InventoryLine> getItems();
	
	/**
	 * return true if inventory has this inventory item
	 * @param itemId inventory item id
	 * @return
	 */
	public boolean hasItem(String itemId);
}
