package edu.depaul.se491.bean;

import java.io.Serializable;

import edu.depaul.se491.util.Values;

/**
 * Menu Item Bean to store a menu item data
 * @author Malik
 */
public class MenuItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private double price;
	
	/**
	 * construct an empty menu item with no id
	 */
	public MenuItemBean() {
		this.id = Values.UNKNOWN;
		this.description = Values.EMPTY_STRING;
	}
	
	/**
	 * return item id
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * set item id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * return item name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * set item name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * return item description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set item description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * return item price
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * set item price
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
