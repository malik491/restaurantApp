package edu.depaul.se491.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Malik
 *
 */
public class InventoryImp implements Inventory {
	private HashMap<String,InventoryLine> items;
	
	public InventoryImp() {
		items = new HashMap<>();
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.Inventory#addItem(edu.depaul.se491.inventory.InventoryItem)
	 */
	@Override
	public boolean addItem(InventoryItem item) {
		String itemId = item.getId();
		InventoryLine invLine = items.get(itemId);
		if (invLine != null) {
			invLine.setQuantity(invLine.getQuantity() + 1);
		} else {
			items.put(itemId ,new InventoryLineImp(item, 1));
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.Inventory#removeItem(java.lang.String)
	 */
	@Override
	public boolean removeItem(String itemId) {
		return items.remove(itemId) != null? true : false;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.Inventory#getItem(java.lang.String)
	 */
	@Override
	public InventoryLine getItem(String itemId) {
		return items.get(itemId);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.Inventory#getItems()
	 */
	@Override
	public List<InventoryLine> getItems() {
		List<InventoryLine> copy = new ArrayList<InventoryLine>();
		copy.addAll(items.values());
		return Collections.unmodifiableList(copy);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.inventory.Inventory#hasItem(java.lang.String)
	 */
	@Override
	public boolean hasItem(String itemId) {
		return items.containsKey(itemId);
	}

}
