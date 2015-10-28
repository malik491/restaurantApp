package edu.depaul.se491.order;

/**
 * a class representing a menu item
 * @author Mirtha (modified by Malik)
 *
 */
public class MenuItemImp implements MenuItem {
	
	private int id;
	private String name;
	private String description;
	private Recipe recipe;
	private double price;
	
	/**
	 * construct a new menu item
	 * @param name
	 * @param price
	 */
	public MenuItemImp(String name, double price) {
		this.id = UNIQUE_ID++;
		this.name = name;
		this.price = price;
	}
	
	
	/**
	 * construct a new menu item
	 * @param name
	 * @param desc
	 * @param price
	 */
	public MenuItemImp(String name, String desc, double price) {
		this(name, price);
		this.description = desc;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#getId()
	 */
	public String getId()
	{
		return Integer.toString(id);
	}
	

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#getName()
	 */
	public String getName()
	{
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#getDescription()
	 */
	public String getDescription()
	{
		return this.description;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#getRecipe()
	 */
	public Recipe getRecipe() {
		return recipe;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#getPrice()
	 */
	public double getPrice()
	{
		return this.price;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#setId(java.lang.String)
	 */
	public void setId(String id)
	{
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.err.println(String.format("Invalid input. MenuItemImp.setId(%s)", id));
		}
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#setDescription(java.lang.String)
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#setPrice(java.lang.String)
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.MenuItem#setRecipe(edu.depaul.se491.order.Recipe)
	 */
	public void setRecipe(Recipe recipe) {
		if (isValidRecipe(recipe))
			this.recipe = recipe;
	}
	
	/**
	 * return true if recipe is valid
	 * a valid recipe is not null and has at least one recipe item
	 * @param recipe
	 * @return
	 */
	private boolean isValidRecipe(Recipe recipe) {
		return recipe != null && recipe.getIngredients() != null && recipe.getIngredients().size() > 0;
	}
	
	
	private boolean isValidString(String s) {
		return s != null && !s.isEmpty();
	}
	
	private static int UNIQUE_ID = 1;
}
