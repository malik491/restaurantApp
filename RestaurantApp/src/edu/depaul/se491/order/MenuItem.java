package edu.depaul.se491.order;

/**
 * an interface for an item on a menu
 * @author Malik
 */
public interface MenuItem {
	
	/**
	 * return the menu item id
	 * @return
	 */
	public String getId();
	
	/**
	 * return the menu item name
	 * @return
	 */
	public String getName();
	
	/**
	 * return the menu item description
	 * @return
	 */
	public String getDescription();
	
	/**
	 * return a menu item recipe
	 * @return
	 */
	public Recipe getRecipe();
	
	/**
	 * return the menu item price
	 * @return
	 */
	public double getPrice();

	/**
	 * set the menu item id
	 * @param id
	 */
	public void setId(String id);
	
	/**
	 * set the menu item name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * set the menu item description
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * set menu item recipe
	 * @param recipe
	 */
	public void setRecipe(Recipe recipe);
	
	/**
	 * set the menu item price
	 * @param price
	 */
	public void setPrice(double price);

}
