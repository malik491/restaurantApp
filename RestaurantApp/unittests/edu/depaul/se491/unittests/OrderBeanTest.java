/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.Values;

/**
 * @author Lamont
 *
 */
public class OrderBeanTest {


	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#OrderBean()}.
	 */
	@Test
	public void testOrderBean() {
		OrderBean order = new OrderBean(); 
		
		assertTrue (order != null);
		assertEquals(Values.UNKNOWN, order.getId());
		assertEquals(null, order.getStatus());
		assertEquals(null, order.getType());
		assertEquals(null, order.getTimestamp());
		assertEquals(null, order.getConfirmation());
		assertEquals(null, order.getDeliveryAddress());
		assertEquals(0, order.getItems().size());
		assertTrue(order.getTotal() == 0);
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getId()}.
	 */
	@Test
	public void testGetId() {
		OrderBean order = new OrderBean(); 
		assertEquals(Values.UNKNOWN, order.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		order.setId(id1);
		assertEquals(id1, order.getId());
		assertNotEquals(id2, order.getId());

		order.setId(id2);
		assertEquals(id2, order.getId());
		assertNotEquals(id1, order.getId());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setId(long)}.
	 */
	@Test
	public void testSetId() {
		OrderBean order = new OrderBean(); 
		assertEquals(Values.UNKNOWN, order.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		order.setId(id1);
		assertEquals(id1, order.getId());
		assertNotEquals(id2, order.getId());

		order.setId(id2);
		assertEquals(id2, order.getId());
		assertNotEquals(id1, order.getId());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getItems()}.
	 */
	@Test
	public void testGetItems() {
		OrderBean order = new OrderBean(); 
		
		assertEquals(0, order.getItems().size());
		
		List<OrderItemBean> items = new ArrayList<OrderItemBean>();
		OrderItemBean oItem1 = new OrderItemBean();
		OrderItemBean oItem2 = new OrderItemBean();

		MenuItemBean mItem1 = new MenuItemBean();
		long id1 = 1;
		mItem1.setId(id1);
		
		MenuItemBean mItem2 = new MenuItemBean();
		long id2 = 2;
		mItem2.setId(id2);
		
		int quantity1 = 10;
		oItem1.setMenuItem(mItem1);
		oItem1.setQuantity(quantity1);
		
		int quantity2 = 5;
		oItem2.setMenuItem(mItem2);
		oItem2.setQuantity(quantity2);
		
		items.add(oItem1);
		items.add(oItem2);
		
		order.setItems(items);

		assertEquals(2, order.getItems().size());
		
		for(OrderItemBean oItem: order.getItems()) {
			MenuItemBean mItem = oItem.getMenuItem();
			int quantity = oItem.getQuantity();
			
			assertEquals(mItem.getId() == id1? quantity1 : quantity2, quantity);
			assertEquals(quantity == quantity1? id1: id2, mItem.getId());
		}
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setItems(java.util.List)}.
	 */
	@Test
	public void testSetItems() {
		OrderBean order = new OrderBean(); 
		
		assertEquals(0, order.getItems().size());
		
		List<OrderItemBean> items = new ArrayList<OrderItemBean>();
		OrderItemBean oItem1 = new OrderItemBean();
		OrderItemBean oItem2 = new OrderItemBean();

		MenuItemBean mItem1 = new MenuItemBean();
		long id1 = 1;
		mItem1.setId(id1);
		
		MenuItemBean mItem2 = new MenuItemBean();
		long id2 = 2;
		mItem2.setId(id2);
		
		int quantity1 = 10;
		oItem1.setMenuItem(mItem1);
		oItem1.setQuantity(quantity1);
		
		int quantity2 = 5;
		oItem2.setMenuItem(mItem2);
		oItem2.setQuantity(quantity2);
		
		items.add(oItem1);
		items.add(oItem2);
		
		order.setItems(items);

		assertEquals(2, order.getItems().size());
		
		for(OrderItemBean oItem: order.getItems()) {
			MenuItemBean mItem = oItem.getMenuItem();
			int quantity = oItem.getQuantity();
			
			assertEquals(mItem.getId() == id1? quantity1 : quantity2, quantity);
			assertEquals(quantity == quantity1? id1: id2, mItem.getId());
		}
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getStatus());
		
		OrderStatus status1 = OrderStatus.SUBMITTED;
		OrderStatus status2 = OrderStatus.PREPARED;
		
		order.setStatus(status1);
		assertEquals(status1, order.getStatus());
		assertNotEquals(status2, order.getStatus());
		
		for(OrderStatus status: OrderStatus.values()) {
			order.setStatus(status);
			assertEquals(status, order.getStatus());
		}
		

	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setStatus(edu.depaul.se491.enums.OrderStatus)}.
	 */
	@Test
	public void testSetStatus() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getStatus());
		
		OrderStatus submitted = OrderStatus.SUBMITTED;
		OrderStatus prepared = OrderStatus.PREPARED;
		
		order.setStatus(submitted);
		assertEquals(submitted, order.getStatus());
		assertNotEquals(prepared, order.getStatus());
		
		for(OrderStatus status: OrderStatus.values()) {
			order.setStatus(status);
			assertEquals(status, order.getStatus());
		}
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getType()}.
	 */
	@Test
	public void testGetType() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getType());
		
		OrderType type1 = OrderType.DELIVERY;
		OrderType type2 =  OrderType.PICKUP;
		
		order.setType(type1);
		assertEquals(type1, order.getType());
		assertNotEquals(type2, order.getType());
		
		for(OrderType type: OrderType.values()) {
			order.setType(type);
			assertEquals(type, order.getType());
		}
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setType(edu.depaul.se491.enums.OrderType)}.
	 */
	@Test
	public void testSetType() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getType());
		
		OrderType type1 = OrderType.DELIVERY;
		OrderType type2 =  OrderType.PICKUP;
		
		order.setType(type1);
		assertEquals(type1, order.getType());
		assertNotEquals(type2, order.getType());
		
		for(OrderType type: OrderType.values()) {
			order.setType(type);
			assertEquals(type, order.getType());
		}
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getTimestamp()}.
	 */
	@Test
	public void testGetTimestamp() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getTimestamp());
		
		long time = System.currentTimeMillis();
		
		Timestamp timestamp1 = new Timestamp(time);
		Timestamp timestamp2 = new Timestamp(time + 10);
		
		order.setTimestamp(timestamp1);
		assertEquals(timestamp1.getTime(), order.getTimestamp().getTime());
		assertNotEquals(timestamp2.getTime(), order.getTimestamp().getTime());
		
		order.setTimestamp(timestamp2);
		assertEquals(timestamp2.getTime(), order.getTimestamp().getTime());
		assertNotEquals(timestamp1.getTime(), order.getTimestamp().getTime());
		
		order.setTimestamp(null);
		assertEquals(null, order.getTimestamp());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setTimestamp(java.sql.Timestamp)}.
	 */
	@Test
	public void testSetTimestamp() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getTimestamp());
		
		long time = System.currentTimeMillis();
		
		Timestamp timestamp1 = new Timestamp(time);
		Timestamp timestamp2 = new Timestamp(time + 10);
		
		order.setTimestamp(timestamp1);
		assertEquals(timestamp1.getTime(), order.getTimestamp().getTime());
		assertNotEquals(timestamp2.getTime(), order.getTimestamp().getTime());
		
		order.setTimestamp(timestamp2);
		assertEquals(timestamp2.getTime(), order.getTimestamp().getTime());
		assertNotEquals(timestamp1.getTime(), order.getTimestamp().getTime());
		
		order.setTimestamp(null);
		assertEquals(null, order.getTimestamp());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getTotal()}.
	 */
	@Test
	public void testGetTotal() {
		OrderBean order = new OrderBean();
		
		assertTrue(order.getTotal() == 0);
		
		double total1 = 12.99;
		double total2 = 12.9998;
		
		order.setTotal(total1);
		boolean sameTotal1 = Double.compare(total1, order.getTotal()) == 0;
		assertTrue(sameTotal1);
		
		boolean sameTotal2 = Double.compare(total2, order.getTotal()) == 0;
		assertFalse(sameTotal2);
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setTotal(double)}.
	 */
	@Test
	public void testSetTotal() {
		OrderBean order = new OrderBean();
		
		assertTrue(order.getTotal() == 0);
		
		double total1 = 12.99;
		double total2 = 12.9998;
		
		order.setTotal(total1);
		boolean sameTotal1 = Double.compare(total1, order.getTotal()) == 0;
		assertTrue(sameTotal1);
		
		boolean sameTotal2 = Double.compare(total2, order.getTotal()) == 0;
		assertFalse(sameTotal2);
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getDeliveryAddress()}.
	 */
	@Test
	public void testGetDeliveryAddress() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getDeliveryAddress());
		
		AddressBean address1 = new AddressBean();
		AddressBean address2 = new AddressBean();
		
		long id1 = 1;
		long id2 = 2;
		address1.setId(id1);
		address2.setId(id2);
		
		order.setDeliveryAddress(address1);
		assertTrue(order.getDeliveryAddress().equals(address1));
		assertFalse(order.getDeliveryAddress().equals(address2));

		order.setDeliveryAddress(address2);
		assertTrue(order.getDeliveryAddress().equals(address2));
		assertFalse(order.getDeliveryAddress().equals(address1));

		order.setDeliveryAddress(null);
		assertEquals(null, order.getDeliveryAddress());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setDeliveryAddress(edu.depaul.se491.bean.AddressBean)}.
	 */
	@Test
	public void testSetDeliveryAddress() {
		OrderBean order = new OrderBean();
		assertEquals(null, order.getDeliveryAddress());
		
		AddressBean address1 = new AddressBean();
		AddressBean address2 = new AddressBean();
		
		long id1 = 1;
		long id2 = 2;
		address1.setId(id1);
		address2.setId(id2);
		
		order.setDeliveryAddress(address1);
		assertTrue(order.getDeliveryAddress().equals(address1));
		assertFalse(order.getDeliveryAddress().equals(address2));

		order.setDeliveryAddress(address2);
		assertTrue(order.getDeliveryAddress().equals(address2));
		assertFalse(order.getDeliveryAddress().equals(address1));

		order.setDeliveryAddress(null);
		assertEquals(null, order.getDeliveryAddress());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#getConfirmation()}.
	 */
	@Test
	public void testGetConfirmation() {
		OrderBean order = new OrderBean();

		assertEquals(null, order.getConfirmation());
		
		String Confirmation1 = "conf-1";		
		String Confirmation2 = "conf-2";

		order.setConfirmation(Confirmation1);
		assertTrue(order.getConfirmation().equals(Confirmation1));
		assertFalse(order.getConfirmation().equals(Confirmation2));

		order.setConfirmation(Confirmation2);
		assertTrue(order.getConfirmation().equals(Confirmation2));
		assertFalse(order.getConfirmation().equals(Confirmation1));
		
		order.setConfirmation(null);
		assertEquals(null, order.getConfirmation());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.OrderBean#setConfirmation(java.lang.String)}.
	 */
	@Test
	public void testSetConfirmation() {
		OrderBean order = new OrderBean();

		assertEquals(null, order.getConfirmation());
		
		String Confirmation1 = "conf-1";		
		String Confirmation2 = "conf-2";

		order.setConfirmation(Confirmation1);
		assertTrue(order.getConfirmation().equals(Confirmation1));
		assertFalse(order.getConfirmation().equals(Confirmation2));

		order.setConfirmation(Confirmation2);
		assertTrue(order.getConfirmation().equals(Confirmation2));
		assertFalse(order.getConfirmation().equals(Confirmation1));
		
		order.setConfirmation(null);
		assertEquals(null, order.getConfirmation());
	}
}
