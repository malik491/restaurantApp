/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.util.Values;

/**
 * @author Lamont
 *
 */
public class UserBeanTest {

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#UserBean()}.
	 */
	@Test
	public void testUserBean() {
		UserBean user = new UserBean();
		
		assertTrue(user != null);
		assertTrue(user.getPhone().equals(Values.EMPTY_STRING));

		assertEquals(null, user.getEmail());
		assertEquals(null, user.getFirstName());
		assertEquals(null, user.getLastName());
		assertEquals(null, user.getAddress());		
	}


	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getFirstName());
		
		String fname1 = "fn1";
		String fname2 = "fn2";
		
		user.setFirstName(fname1);
		assertTrue(user.getFirstName().equals(fname1));
		assertFalse(user.getFirstName().equals(fname2));
		
		user.setFirstName(fname2);
		assertTrue(user.getFirstName().equals(fname2));
		assertFalse(user.getFirstName().equals(fname1));
		
		user.setFirstName(null);
		assertEquals(null, user.getFirstName());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getFirstName());
		
		String fname1 = "fn1";
		String fname2 = "fn2";
		
		user.setFirstName(fname1);
		assertTrue(user.getFirstName().equals(fname1));
		assertFalse(user.getFirstName().equals(fname2));
		
		user.setFirstName(fname2);
		assertTrue(user.getFirstName().equals(fname2));
		assertFalse(user.getFirstName().equals(fname1));
		
		user.setFirstName(null);
		assertEquals(null, user.getFirstName());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#getLastName()}.
	 */
	@Test
	public void testGetLastName() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getLastName());
		
		String lname1 = "ln1";
		String lname2 = "ln2";
		
		user.setLastName(lname1);
		assertTrue(user.getLastName().equals(lname1));
		assertFalse(user.getLastName().equals(lname2));
		
		user.setLastName(lname2);
		assertTrue(user.getLastName().equals(lname2));
		assertFalse(user.getLastName().equals(lname1));
		
		user.setLastName(null);
		assertEquals(null, user.getLastName());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getLastName());
		
		String lname1 = "ln1";
		String lname2 = "ln2";
		
		user.setLastName(lname1);
		assertTrue(user.getLastName().equals(lname1));
		assertFalse(user.getLastName().equals(lname2));
		
		user.setLastName(lname2);
		assertTrue(user.getLastName().equals(lname2));
		assertFalse(user.getLastName().equals(lname1));
		
		user.setLastName(null);
		assertEquals(null, user.getLastName());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#getEmail()}.
	 */
	@Test
	public void testGetEmail() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getEmail());
		
		String email1 = "email1@email.com";
		String email2 = "email2@email.com";
		
		user.setEmail(email1);
		assertTrue(user.getEmail().equals(email1));
		assertFalse(user.getEmail().equals(email2));
		
		user.setEmail(email2);
		assertTrue(user.getEmail().equals(email2));
		assertFalse(user.getEmail().equals(email1));
		
		user.setEmail(null);
		assertEquals(null, user.getEmail());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() {
		UserBean user = new UserBean();
		
		assertEquals(null, user.getEmail());
		
		String email1 = "email1@email.com";
		String email2 = "email2@email.com";
		
		user.setEmail(email1);
		assertTrue(user.getEmail().equals(email1));
		assertFalse(user.getEmail().equals(email2));
		
		user.setEmail(email2);
		assertTrue(user.getEmail().equals(email2));
		assertFalse(user.getEmail().equals(email1));
		
		user.setEmail(null);
		assertEquals(null, user.getEmail());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#getPhone()}.
	 */
	@Test
	public void testGetPhone() {
		UserBean user = new UserBean();
		
		assertTrue(user.getPhone().equals(Values.EMPTY_STRING));
		
		String phone1 = "0001112222";
		String phone2 = "3334445555";
		
		user.setPhone(phone1);
		assertTrue(user.getPhone().equals(phone1));
		assertFalse(user.getPhone().equals(phone2));
		
		user.setPhone(phone2);
		assertTrue(user.getPhone().equals(phone2));
		assertFalse(user.getPhone().equals(phone1));
		
		user.setPhone(null);
		assertEquals(null, user.getPhone());

	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#setPhone(java.lang.String)}.
	 */
	@Test
	public void testSetPhone() {
		UserBean user = new UserBean();
		
		assertTrue(user.getPhone().equals(Values.EMPTY_STRING));
		
		String phone1 = "0001112222";
		String phone2 = "3334445555";
		
		user.setPhone(phone1);
		assertTrue(user.getPhone().equals(phone1));
		assertFalse(user.getPhone().equals(phone2));
		
		user.setPhone(phone2);
		assertTrue(user.getPhone().equals(phone2));
		assertFalse(user.getPhone().equals(phone1));
		
		user.setPhone(null);
		assertEquals(null, user.getPhone());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#getAddress()}.
	 */
	@Test
	public void testGetAddress() {
		UserBean user = new UserBean();
		assertEquals(null, user.getAddress());
		
		AddressBean address1 = new AddressBean();
		AddressBean address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		
		address1.setLine1("123 Main St");
		address2.setLine1("456 State St");
		assertFalse(address1.equals(address2));
		
		user.setAddress(address1);
		assertTrue(user.getAddress().equals(address1));
		assertFalse(user.getAddress().equals(address2));
		
		user.setAddress(address2);
		assertTrue(user.getAddress().equals(address2));
		assertFalse(user.getAddress().equals(address1));
		
		user.setAddress(null);
		assertEquals(null, user.getAddress());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.UserBean#setAddress(edu.depaul.se491.bean.AddressBean)}.
	 */
	@Test
	public void testSetAddress() {
		UserBean user = new UserBean();
		assertEquals(null, user.getAddress());
		
		AddressBean address1 = new AddressBean();
		AddressBean address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		
		address1.setLine1("123 Main St");
		address2.setLine1("456 State St");
		assertFalse(address1.equals(address2));
		
		user.setAddress(address1);
		assertTrue(user.getAddress().equals(address1));
		assertFalse(user.getAddress().equals(address2));
		
		user.setAddress(address2);
		assertTrue(user.getAddress().equals(address2));
		assertFalse(user.getAddress().equals(address1));
		
		user.setAddress(null);
		assertEquals(null, user.getAddress());
	}

}
