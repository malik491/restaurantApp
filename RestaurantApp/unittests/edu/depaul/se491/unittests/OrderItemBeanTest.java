/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.OrderItemBean;

/**
 * @author Lamont
 *
 */
public class OrderItemBeanTest {
	
	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderItemBean#OrderItemBean()}.
	 */
	@Test
	public void testOrderItemBean() {
		OrderItemBean orderItem = new OrderItemBean();
		
		assertTrue(orderItem != null);
		assertEquals(null, orderItem.getMenuItem());
		assertEquals(0, orderItem.getQuantity());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderItemBean#getMenuItem()}.
	 */
	@Test
	public void testGetMenuItem() {
		OrderItemBean orderItem = new OrderItemBean();
		assertEquals(null, orderItem.getMenuItem());
		
		MenuItemBean menuItem1 = new MenuItemBean();
		MenuItemBean menuItem2 = new MenuItemBean();
		
		menuItem1.setId(1);
		menuItem2.setId(2);
		assertNotEquals(menuItem1.getId(), menuItem2.getId());

		orderItem.setMenuItem(menuItem1);
		assertEquals(menuItem1.getId(), orderItem.getMenuItem().getId());
		assertNotEquals(menuItem2.getId(), orderItem.getMenuItem().getId());

		orderItem.setMenuItem(menuItem2);		
		assertEquals(menuItem2.getId(), orderItem.getMenuItem().getId());
		assertNotEquals(menuItem1.getId(), orderItem.getMenuItem().getId());
		
		orderItem.setMenuItem(null);
		assertEquals(null, orderItem.getMenuItem());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderItemBean#setMenuItem(edu.depaul.se491.bean.MenuItemBean)}.
	 */
	@Test
	public void testSetMenuItem() {
		OrderItemBean orderItem = new OrderItemBean();
		assertEquals(null, orderItem.getMenuItem());
		
		MenuItemBean menuItem1 = new MenuItemBean();
		MenuItemBean menuItem2 = new MenuItemBean();
		
		menuItem1.setId(1);
		menuItem2.setId(2);
		assertNotEquals(menuItem1.getId(), menuItem2.getId());

		orderItem.setMenuItem(menuItem1);
		assertEquals(menuItem1.getId(), orderItem.getMenuItem().getId());
		assertNotEquals(menuItem2.getId(), orderItem.getMenuItem().getId());

		orderItem.setMenuItem(menuItem2);		
		assertEquals(menuItem2.getId(), orderItem.getMenuItem().getId());
		assertNotEquals(menuItem1.getId(), orderItem.getMenuItem().getId());
		
		orderItem.setMenuItem(null);
		assertEquals(null, orderItem.getMenuItem());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderItemBean#getQuantity()}.
	 */
	@Test
	public void testGetQuantity() {
		OrderItemBean orderItem = new OrderItemBean();
		assertEquals(0, orderItem.getQuantity());
		
		int quantity1 = 1;
		int quantity2 = 2;
		
		orderItem.setQuantity(quantity1);
		assertEquals(quantity1, orderItem.getQuantity());
		assertNotEquals(quantity2, orderItem.getQuantity());

		orderItem.setQuantity(quantity2);
		assertEquals(quantity2, orderItem.getQuantity());
		assertNotEquals(quantity1, orderItem.getQuantity());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderItemBean#setQuantity(int)}.
	 */
	@Test
	public void testSetQuantity() {
		OrderItemBean orderItem = new OrderItemBean();
		assertEquals(0, orderItem.getQuantity());
		
		int quantity1 = 1;
		int quantity2 = 2;
		
		orderItem.setQuantity(quantity1);
		assertEquals(quantity1, orderItem.getQuantity());
		assertNotEquals(quantity2, orderItem.getQuantity());

		orderItem.setQuantity(quantity2);
		assertEquals(quantity2, orderItem.getQuantity());
		assertNotEquals(quantity1, orderItem.getQuantity());
	}
}
