package edu.depaul.se491.order;

/**
 * @author Malik
 */
public interface RecipeItem {
	
	/**
	 * return an ingredient associated with this recipe item
	 * @return
	 */
	public Ingredient getIngredient();
	
	/**
	 * set a ingredient for this recipe item
	 * @param mItem a menu Item
	 */
	public void setIngredient(Ingredient ingredient);

	/**
	 * return the quantity for the ingredient
	 * @return
	 */
	public int getQuantity();
	
	/**
	 * set the quantity for the ingredient
	 * @param quantity
	 */
	public void setQuantity(int quantity);

}
