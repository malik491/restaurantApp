/**
 * 
 */
package edu.depaul.se491.bean.order;

import java.io.Serializable;
import java.util.Map;

/**
 * Order Bean
 * @author Malik
 */

public class OrderBean implements Serializable {
	private Map<String, OrderItemBean> items;
	private OrderStatus status;
	private OrderType type;
	
	public OrderBean() {
	
	}

	public Map<String, OrderItemBean> getItems() {
		return items;
	}

	public void setItems(Map<String, OrderItemBean> items) {
		this.items = items;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}
	
}
