package edu.depaul.se491.inventory;

/**
 * @author Malik
 */
public interface InventoryItem {
	
	/**
	 * return item id
	 * @return
	 */
	public String getId();
	
	/**
	 * set item id
	 * @param id
	 */
	public void setId(String id);
	
	/**
	 * return item name
	 * @return
	 */
	public String getName();
	
	/**
	 * set item name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * return item description
	 * @return
	 */
	public String getDescription();
	
	/**
	 * set item description
	 * @param desc
	 */
	public void setDescription(String desc);
	
	/**
	 * return item price
	 * @return
	 */
	public double getPrice();
	
	/**
	 * set item price
	 * @param price
	 */
	public void setPrice(double price);

}
