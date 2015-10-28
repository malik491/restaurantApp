package edu.depaul.se491.order;

import java.util.List;

/**
 * an interface for restaurant menu
 * @author Malik
 */
public interface Menu {

	/**
	 * add item to this menu
	 * @param mItem
	 * @return
	 */
	public boolean addItem(MenuItem mItem);
	
	/**
	 * remove item from this menu
	 * @param mItemId
	 * @return
	 */
	public boolean removeItem(String mItemId);
	
	/**
	 * return a menu item from this menu
	 * @param mItemId menu item id
	 * @return
	 */
	public MenuItem getItem(String mItemId);
	
	/**
	 * return all items for this menu
	 * @return list of items or an empty list
	 */
	public List<MenuItem> getItems();
		
}
