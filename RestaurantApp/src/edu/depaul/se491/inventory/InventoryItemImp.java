package edu.depaul.se491.inventory;

/**
 * @author Malik
 *
 */
public class InventoryItemImp implements InventoryItem {
	private int id;
	private String name, description;
	private double price;
	
	public InventoryItemImp(String name, double price) {
		id = this.UNIQUE_ID++; // set id and increment unique id by 1
		this.name = name;
		this.price = price;
	}
	
	public InventoryItemImp(String name, double price, String desc) {
		this(name, price);
		this.description = desc;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#getId()
	 */
	@Override
	public String getId() {
		return Integer.toString(id);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		// id is auto generated to be unique so don't allow a reset
		// API should remove this method in the future
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String desc) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#getPrice()
	 */
	@Override
	public double getPrice() {
		return price;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryItem#setPrice(double)
	 */
	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	/* Global id associated with InventoryItemImp class*/
	private static Integer UNIQUE_ID = new Integer(1);
}
