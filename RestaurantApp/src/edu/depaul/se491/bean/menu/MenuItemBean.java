/**
 * 
 */
package edu.depaul.se491.bean.menu;

import java.io.Serializable;

/**
 * Menu Item Bean
 * @author Malik
 */
public class MenuItemBean implements Serializable {
	private String id;
	private String name;
	private String description;
	private double price;
	
	public MenuItemBean() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
