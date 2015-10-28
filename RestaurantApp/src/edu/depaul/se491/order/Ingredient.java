package edu.depaul.se491.order;

/**
 * @author Malik
 *
 */
public interface Ingredient {
	
	/**
	 * return the ingredient id
	 * @return
	 */
	public String getId();
	
	/**
	 * return the ingredient name
	 * @return
	 */
	public String getName();
	
	/**
	 * return the ingredient description
	 * @return
	 */
	public String getDescription();
	
	/**
	 * set the ingredient id
	 * @param id
	 */
	public void setId(String id);
	
	/**
	 * set the ingredient name
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * set the ingredient description
	 * @param description
	 */
	public void setDescription(String description);


}
