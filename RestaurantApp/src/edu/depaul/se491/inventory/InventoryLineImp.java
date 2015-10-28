package edu.depaul.se491.inventory;

/**
 * @author Malik
 *
 */
public class InventoryLineImp implements InventoryLine {
	
	private InventoryItem item;
	private int quantity;

	/**
	 * construct a new inventoryLine for an inventory item with a quantity
	 * @param item inventory item
	 * @param quantity
	 */
	public InventoryLineImp(InventoryItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryLine#getItem()
	 */
	@Override
	public InventoryItem getItem() {
		return item;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryLine#setItem(edu.depaul.se491.inventory.InventoryItem)
	 */
	@Override
	public void setItem(InventoryItem item) {
		this.item = item;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryLine#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return quantity;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.InventoryLine#setQuantity(int)
	 */
	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
