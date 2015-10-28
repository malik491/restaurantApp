package edu.depaul.se491.order;

/**
 * 
 * @author Mirtha
 *
 */
public class RecipeItemImp implements RecipeItem {

	private Ingredient ingredient;
	private int quantity;
	
	/**
	 * construct a new recipe item
	 * @param ingredient
	 * @param quantity
	 */
	public RecipeItemImp(Ingredient ingredient, int quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.RecipeItem#getIngredient()
	 */
	public Ingredient getIngredient()
	{
		return ingredient;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.RecipeItem#setIngredient(edu.depaul.se491.order.Ingredient)
	 */
	public void setIngredient(Ingredient ingredient)
	{
		this.ingredient = ingredient;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.RecipeItem#getQuantity()
	 */
	public int getQuantity()
	{
		return quantity;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.RecipeItem#setQuantity(int)
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}


}
