package edu.depaul.se491.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * a class representing a menu
 * @author Mirth (modified by Malik)
 *
 */
public class MenuImp implements Menu {
	private List<MenuItem> items;
	
	/**
	 * construct a new empty menu
	 */
	public MenuImp() {
		items = new ArrayList<>();
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Menu#addItem(edu.depaul.se491.order.MenuItem)
	 */
	public boolean addItem(MenuItem mItem) {
		boolean added = false;
		if (isValidMenuItem(mItem))
			added = items.add(mItem);
		return added;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Menu#removeItem(java.lang.String)
	 */
	public boolean removeItem(String mItemId) {
		MenuItem oldItem = null;
		if (isValidMenuItemId(mItemId)) {
			for(MenuItem mItem: items) {
				if (mItem.getId().equals(mItemId)){
					oldItem = mItem;
					break;
				}
			}
		}
		return (oldItem != null)? items.remove(oldItem) : false;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Menu#getItem(java.lang.String)
	 */
	public MenuItem getItem(String mItemId) {
		MenuItem result = null;
		if (isValidMenuItemId(mItemId)) {
			for(MenuItem mItem: items){
				if (mItem.getId().equals(mItemId)) {
					result = mItem;
					break;
				}
			}			
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Menu#getItems()
	 */
	public List<MenuItem> getItems() {
		List<MenuItem> copy = new ArrayList<>();
		copy.addAll(items);
		return Collections.unmodifiableList(copy);
	}	
	
	/**
	 * return true if id is valid
	 * Id is valid if it is not null AND not empty string 
	 * @param menuItemId
	 * @return
	 */
	private boolean isValidMenuItemId(String menuItemId) {
		return menuItemId != null && !menuItemId.isEmpty();
	}
	
	/**
	 * return true if menu item is valid
	 * menu item is valid if it is not null and has valid id
	 * @param mItem
	 * @return
	 */
	private boolean isValidMenuItem(MenuItem mItem) {
		return mItem != null && isValidMenuItemId(mItem.getId());
	}
	
	
}
