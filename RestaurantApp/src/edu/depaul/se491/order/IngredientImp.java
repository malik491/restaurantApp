package edu.depaul.se491.order;

/**
 * 
 * @author Lamont
 *
 */
public class IngredientImp implements Ingredient{

	private int id;
	private String name;
	private String description;
	private static int UNIQUE_ID = 1;
	
	/**
	 * construct a new ingredient
	 * @param name ingredient name
	 */
	public IngredientImp(String name)
	{
		this.id = UNIQUE_ID++;
		this.name = name;		
	}
	
	/**
	 * construct a new ingredient
	 * @param name
	 * @param description
	 */
	public IngredientImp(String name, String description) {
		this(name);
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#getId()
	 */
	public String getId() 
	{
		return Integer.toString(id);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#getName()
	 */
	public String getName() 
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#getDescription()
	 */
	public String getDescription() 
	{
		return description;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#setId(java.lang.String)
	 */
	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.err.println(String.format("Invalid input. IngredientImp.setId(%s)", id));
		}
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#setName(java.lang.String)
	 */
	public void setName(String name) 
	{
		this.name  = name;		
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.order.Ingredient#setDescription(java.lang.String)
	 */
	public void setDescription(String description) 
	{
		this.description = description;		
	}

}
