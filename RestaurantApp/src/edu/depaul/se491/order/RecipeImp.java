package edu.depaul.se491.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * a class representing a recipe
 * @author Malik
 */
public class RecipeImp implements Recipe {
	private HashMap<String, RecipeItem> ingredients;
	
	public RecipeImp() {
		ingredients = new HashMap<>();
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Recipe#addIngredient(edu.depaul.se491.order.Ingredient)
	 */
	@Override
	public boolean addIngredient(Ingredient ingredient) {
		boolean added = false;
		if (isValidIngredient(ingredient)) {
			RecipeItem old = ingredients.get(ingredient.getId());
			if (old != null)
				old.setQuantity(old.getQuantity() + 1);
			else
				ingredients.put(ingredient.getId(), new RecipeItemImp(ingredient, 1));
			added = true;
		}
		return added;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Recipe#removeIngredient(java.lang.String)
	 */
	@Override
	public boolean removeIngredient(String ingredientId) {
		boolean removed = false;
		if (isValidIngredientId(ingredientId)) {
			removed = ingredients.remove(ingredientId) != null;
		}
		return removed;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Recipe#getIngredient(java.lang.String)
	 */
	@Override
	public RecipeItem getIngredient(String ingredientId) {
		return isValidIngredientId(ingredientId)? ingredients.get(ingredientId) : null;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Recipe#getIngredients()
	 */
	@Override
	public List<RecipeItem> getIngredients() {
		List<RecipeItem> copy = new ArrayList<>();
		copy.addAll(ingredients.values());
		return Collections.unmodifiableList(copy);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Recipe#hasIngredient(java.lang.String)
	 */
	@Override
	public boolean hasIngredient(String ingredientId) {
		return isValidIngredientId(ingredientId)? ingredients.containsKey(ingredientId) : false;
	}
	
	/**
	 * return true if the id is not null AND the id is not an empty string
	 * @param ingredientId
	 * @return
	 */
	private boolean isValidIngredientId(String ingredientId) {
		return ingredientId != null && !ingredientId.isEmpty();
	}
	
	/**
	 * return true if ingredient is not null AND it has a valid id
	 * @param ingredient
	 * @return
	 */
	private boolean isValidIngredient(Ingredient ingredient) {
		return ingredient != null && isValidIngredientId(ingredient.getId());
	}

}
