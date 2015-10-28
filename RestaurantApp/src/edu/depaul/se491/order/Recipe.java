package edu.depaul.se491.order;

import java.util.List;

/**
 * @author Malik
 *
 */
public interface Recipe {
	

	/**
	 * add ingredient to this recipe
	 * @param ingredient
	 * @return
	 */
	public boolean addIngredient(Ingredient ingredient);
	
	/**
	 * remove an ingredient from this recipe
	 * @param ingredientId ingredient id
	 * @return
	 */
	public boolean removeIngredient(String ingredientId);

	/**
	 * return RecipeItem associated with the given ingredientId.
	 * otherwise return null
	 * @param ingredientId ingredient id
	 * @return RecipeItem or null
	 */
	public RecipeItem getIngredient(String ingredientId);
	
	/**
	 * return all ingredients in this recipe
	 * @return
	 */
	public List<RecipeItem> getIngredients();
	
	/**
	 * return true if this order has an ingredient	
	 * @param ingredientId ingredient id
	 * @return
	 */
	public boolean hasIngredient(String ingredientId);
}
