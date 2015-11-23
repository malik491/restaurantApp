/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.Values;

/**
 * @author Lamont
 *
 */
public class MenuItemBeanTest {

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#MenuItemBean()}.
	 */
	@Test
	public void testMenuItemBean() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertTrue(menuItem != null);
		assertEquals(Values.UNKNOWN, menuItem.getId());
		assertEquals(null, menuItem.getName());
		assertTrue(menuItem.getDescription().equals(Values.EMPTY_STRING));
		assertTrue(menuItem.getPrice() == 0);
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#getId()}.
	 */
	@Test
	public void testGetId() {
		MenuItemBean menuItem = new MenuItemBean();
		assertEquals(Values.UNKNOWN, menuItem.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		menuItem.setId(id1);
		assertEquals(id1, menuItem.getId());
		assertNotEquals(id2, menuItem.getId());
		
		menuItem.setId(id2);
		assertEquals(id2, menuItem.getId());
		assertNotEquals(id1, menuItem.getId());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#setId(long)}.
	 */
	@Test
	public void testSetId() {
		MenuItemBean menuItem = new MenuItemBean();
		assertEquals(Values.UNKNOWN, menuItem.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		menuItem.setId(id1);
		assertEquals(id1, menuItem.getId());
		assertNotEquals(id2, menuItem.getId());
		
		menuItem.setId(id2);
		assertEquals(id2, menuItem.getId());
		assertNotEquals(id1, menuItem.getId());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#getName()}.
	 */
	@Test
	public void testGetName() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertEquals(null, menuItem.getName());

		String name1 = "soda";
		String name2 = "burger";
		
		menuItem.setName(name1);
		assertTrue(menuItem.getName().equals(name1));
		assertFalse(menuItem.getName().equals(name2));
		
		menuItem.setName(name2);
		assertTrue(menuItem.getName().equals(name2));
		assertFalse(menuItem.getName().equals(name1));
		
		menuItem.setName(Values.EMPTY_STRING);
		assertTrue(menuItem.getName().equals(Values.EMPTY_STRING));

		menuItem.setName(null);
		assertEquals(null, menuItem.getName());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertEquals(null, menuItem.getName());

		String name1 = "soda";
		String name2 = "burger";
		
		menuItem.setName(name1);
		assertTrue(menuItem.getName().equals(name1));
		assertFalse(menuItem.getName().equals(name2));
		
		menuItem.setName(name2);
		assertTrue(menuItem.getName().equals(name2));
		assertFalse(menuItem.getName().equals(name1));

		menuItem.setName(Values.EMPTY_STRING);
		assertTrue(menuItem.getName().equals(Values.EMPTY_STRING));

		menuItem.setName(null);
		assertEquals(null, menuItem.getName());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertTrue(menuItem.getDescription().equals(Values.EMPTY_STRING));

		String desc1 = "cold soda";
		String desc2 = "hot burger";
		
		menuItem.setDescription(desc1);
		assertTrue(menuItem.getDescription().equals(desc1));
		assertFalse(menuItem.getDescription().equals(desc2));
		
		menuItem.setDescription(desc2);
		assertTrue(menuItem.getDescription().equals(desc2));
		assertFalse(menuItem.getDescription().equals(desc1));

		menuItem.setDescription(null);
		assertEquals(null, menuItem.getDescription());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#setDescription(java.lang.String)}.
	 */
	@Test
	public void testSetDescription() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertTrue(menuItem.getDescription().equals(Values.EMPTY_STRING));

		String desc1 = "cold soda";
		String desc2 = "hot burger";
		
		menuItem.setDescription(desc1);
		assertTrue(menuItem.getDescription().equals(desc1));
		assertFalse(menuItem.getDescription().equals(desc2));
		
		menuItem.setDescription(desc2);
		assertTrue(menuItem.getDescription().equals(desc2));
		assertFalse(menuItem.getDescription().equals(desc1));

		menuItem.setDescription(null);
		assertEquals(null, menuItem.getDescription());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#getPrice()}.
	 */
	@Test
	public void testGetPrice() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertTrue(menuItem.getPrice() == 0);
		
		double  price1 = 10.99;
		double  price2 = 10.99998;
		
		menuItem.setPrice(price1);
		
		boolean equalPrice1 = Double.compare(price1, menuItem.getPrice()) == 0;
		assertTrue(equalPrice1);
		
		boolean equalPrice2 = Double.compare(price2, menuItem.getPrice()) == 0;
		assertFalse(equalPrice2);	
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.MenuItemBean#setPrice(double)}.
	 */
	@Test
	public void testSetPrice() {
		MenuItemBean menuItem = new MenuItemBean();
		
		assertTrue(menuItem.getPrice() == 0);
		
		double  price1 = 10.99;
		double  price2 = 10.99998;
		
		menuItem.setPrice(price1);
		
		boolean equalPrice1 = Double.compare(price1, menuItem.getPrice()) == 0;
		assertTrue(equalPrice1);
		
		boolean equalPrice2 = Double.compare(price2, menuItem.getPrice()) == 0;
		assertFalse(equalPrice2);
	}

}
