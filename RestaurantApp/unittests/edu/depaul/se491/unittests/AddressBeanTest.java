/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.Values;

/**
 * @author Lamont
 *
 */
public class AddressBeanTest {

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#AddressBean()}.
	 */
	@Test
	public void testAddressBean() {
		AddressBean address = new AddressBean();
		
		assertTrue(address != null);
		
		// string equality
		assertTrue(address.getLine2().equals(Values.EMPTY_STRING));

		// numeric equality
		assertEquals(Values.UNKNOWN, address.getId());
		
		assertEquals(null, address.getLine1());
		assertEquals(null, address.getCity());
		assertEquals(null, address.getState());
		assertEquals(null, address.getZipcode());	
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getId()}.
	 */
	@Test
	public void testGetId() {
		AddressBean address = new AddressBean();
		
		assertEquals(Values.UNKNOWN, address.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		address.setId(id1);
		assertEquals(id1, address.getId());
		assertNotEquals(id2, address.getId());
		
		address.setId(id2);
		assertEquals(id2, address.getId());
		assertNotEquals(id1, address.getId());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setId(long)}.
	 */
	@Test
	public void testSetId() {
		AddressBean address = new AddressBean();
		
		assertEquals(Values.UNKNOWN, address.getId());
		
		long id1 = 1;
		long id2 = 2;
		
		address.setId(id1);
		assertEquals(id1, address.getId());
		assertNotEquals(id2, address.getId());
		
		address.setId(id2);
		assertEquals(id2, address.getId());
		assertNotEquals(id1, address.getId());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getLine1()}.
	 */
	@Test
	public void testGetLine1() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getLine1());
		
		String mainSt = "123 Main st";
		String stateSt = "456 State st";
		
		address.setLine1(mainSt);
		assertTrue(address.getLine1().equals(mainSt));
		assertFalse(address.getLine1().equals(stateSt));
		
		address.setLine1(stateSt);
		assertTrue(address.getLine1().equals(stateSt));
		assertFalse(address.getLine1().equals(mainSt));
		
		address.setLine1(null);
		assertEquals(null, address.getLine1());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setLine1(java.lang.String)}.
	 */
	@Test
	public void testSetLine1() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getLine1());
		
		String mainSt = "123 Main st";
		String stateSt = "456 State st";
		
		address.setLine1(mainSt);
		assertTrue(address.getLine1().equals(mainSt));
		assertFalse(address.getLine1().equals(stateSt));
		
		address.setLine1(stateSt);
		assertTrue(address.getLine1().equals(stateSt));
		assertFalse(address.getLine1().equals(mainSt));
		
		address.setLine1(null);
		assertEquals(null, address.getLine1());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getLine2()}.
	 */
	@Test
	public void testGetLine2() {
		AddressBean address = new AddressBean();
		
		assertTrue(address.getLine2().equals(Values.EMPTY_STRING));
		
		String mainSt = "123 Main st";
		String stateSt = "456 State st";
		
		address.setLine2(mainSt);
		assertTrue(address.getLine2().equals(mainSt));
		assertFalse(address.getLine2().equals(stateSt));
		
		address.setLine2(stateSt);
		assertTrue(address.getLine2().equals(stateSt));
		assertFalse(address.getLine2().equals(mainSt));
		
		address.setLine2(null);
		assertEquals(null, address.getLine2());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setLine2(java.lang.String)}.
	 */
	@Test
	public void testSetLine2() {
		AddressBean address = new AddressBean();
		
		assertTrue(address.getLine2().equals(Values.EMPTY_STRING));
		
		String mainSt = "123 Main st";
		String stateSt = "456 State st";
		
		address.setLine2(mainSt);
		assertTrue(address.getLine2().equals(mainSt));
		assertFalse(address.getLine2().equals(stateSt));
		
		address.setLine2(stateSt);
		assertTrue(address.getLine2().equals(stateSt));
		assertFalse(address.getLine2().equals(mainSt));
		
		address.setLine2(null);
		assertEquals(null, address.getLine2());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getCity()}.
	 */
	@Test
	public void testGetCity() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getCity());
		
		String chicago = "Chicago";
		String nyc = "NYC";
		
		address.setCity(chicago);
		assertTrue(address.getCity().equals(chicago));
		assertFalse(address.getCity().equals(nyc));
		
		address.setCity(nyc);
		assertTrue(address.getCity().equals(nyc));
		assertFalse(address.getCity().equals(chicago));
		
		address.setCity(null);
		assertEquals(null, address.getCity());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setCity(java.lang.String)}.
	 */
	@Test
	public void testSetCity() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getCity());
		
		String chicago = "Chicago";
		String nyc = "NYC";
		
		address.setCity(chicago);
		assertTrue(address.getCity().equals(chicago));
		assertFalse(address.getCity().equals(nyc));
		
		address.setCity(nyc);
		assertTrue(address.getCity().equals(nyc));
		assertFalse(address.getCity().equals(chicago));
		
		address.setCity(null);
		assertEquals(null, address.getCity());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getState()}.
	 */
	@Test
	public void testGetState() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getState());
		
		State ak = State.AK;
		State ca = State.CA;
		
		address.setState(ak);
		assertEquals(ak, address.getState());
		assertNotEquals(ca, address.getState());
		
		address.setState(ca);
		assertEquals(ca, address.getState());
		assertNotEquals(ak, address.getState());
		
		address.setState(null);
		assertEquals(null, address.getState());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setState(edu.depaul.se491.enums.State)}.
	 */
	@Test
	public void testSetState() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getState());
		
		State ak = State.AK;
		State ca = State.CA;
		
		address.setState(ak);
		assertEquals(ak, address.getState());
		assertNotEquals(ca, address.getState());
		
		for (State state: State.values()) {
			address.setState(state);
			assertEquals(state, address.getState());
		}
		
		address.setState(null);
		assertEquals(null, address.getState());

	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#getZipcode()}.
	 */
	@Test
	public void testGetZipcode() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getZipcode());
		
		String zipcode1 = "1234";
		String zipcode2 = "5678";
		
		address.setZipcode(zipcode1);
		assertTrue(address.getZipcode().equals(zipcode1));
		assertFalse(address.getZipcode().equals(zipcode2));
		
		address.setZipcode(zipcode2);
		assertTrue(address.getZipcode().equals(zipcode2));
		assertFalse(address.getZipcode().equals(zipcode1));
		
		address.setZipcode(null);
		assertEquals(null, address.getZipcode());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#setZipcode(java.lang.String)}.
	 */
	@Test
	public void testSetZipcode() {
		AddressBean address = new AddressBean();
		
		assertEquals(null, address.getZipcode());
		
		String zipcode1 = "1234";
		String zipcode2 = "5678";
		
		address.setZipcode(zipcode1);
		assertTrue(address.getZipcode().equals(zipcode1));
		assertFalse(address.getZipcode().equals(zipcode2));
		
		address.setZipcode(zipcode2);
		assertTrue(address.getZipcode().equals(zipcode2));
		assertFalse(address.getZipcode().equals(zipcode1));
		
		address.setZipcode(null);
		assertEquals(null, address.getZipcode());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AddressBean#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		AddressBean address1 = new AddressBean();
		AddressBean address2 = new AddressBean();
		
		// default ids, line1, line2, city, state, zipcode
		assertEquals(Values.UNKNOWN, address1.getId());
		assertEquals(Values.UNKNOWN, address2.getId());		
		
		assertEquals(null, address1.getLine1());
		assertEquals(null, address2.getLine1());
		
		assertTrue(address1.getLine2().equals(Values.EMPTY_STRING));
		assertTrue(address2.getLine2().equals(Values.EMPTY_STRING));
		
		assertEquals(null, address1.getCity());
		assertEquals(null, address2.getCity());
		
		assertEquals(null, address1.getState());
		assertEquals(null, address2.getState());
				
		
		assertEquals(null, address1.getZipcode());
		assertEquals(null, address2.getZipcode());
	
		
		/* start testing Address.equal() implementation */
		 
		/* Test ids 
		 * unless both addresses have valid ids, the ids comparison is ignored by
		 * AddressBean.equals() implementation.
		 */
		
		// both have invalid ids (Values.UNKNOWN)
		assertEquals(address1.getId(), address2.getId());
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// one id is valid, one is not
		address1.setId(1);
		address2.setId(Values.UNKNOWN);
		assertNotEquals(address1.getId(), address2.getId());
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		address1.setId(Values.UNKNOWN);
		address2.setId(1);
		assertNotEquals(address1.getId(), address2.getId());
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// both valid ids
		address1.setId(100);
		address2.setId(100);
		assertEquals(address1.getId(), address2.getId());
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		address1.setId(100);
		address2.setId(200);
		assertNotEquals(address1.getId(), address2.getId());
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));		

		
		/*
		 * Test line1
		 */
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same line1
		address1.setLine1("line 1");
		address2.setLine1("line 1");
		assertTrue(address1.getLine1().equals(address2.getLine1()));
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// different line1
		address1.setLine1("line X");
		address2.setLine1("line Y");
		assertFalse(address1.getLine1().equals(address2.getLine1()));
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));	

		/*
		 * Test line2
		 */
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same line2
		address1.setLine2("line 2");
		address2.setLine2("line 2");
		assertTrue(address1.getLine2().equals(address2.getLine2()));
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// different line2
		address1.setLine2("line X");
		address2.setLine2("line Y");
		assertFalse(address1.getLine2().equals(address2.getLine2()));
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));	

		/*
		 * Test city
		 */
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same city
		address1.setCity("Chicago");
		address2.setCity("Chicago");
		assertTrue(address1.getCity().equals(address2.getCity()));
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// different city
		address1.setCity("NYC");
		address2.setCity("LA");
		assertFalse(address1.getCity().equals(address2.getCity()));
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));
		
		
		/*
		 * Test State
		 */
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same state
		address1.setState(State.AK);
		address2.setState(State.AK);
		assertEquals(address1.getState(), address2.getState());
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// different state
		address1.setState(State.NY);
		address2.setState(State.CA);
		assertNotEquals(address1.getState(), address2.getState());
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));	

		/*
		 * Test zipcode
		 */
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same zipcode
		address1.setZipcode("12345");
		address2.setZipcode("12345");
		assertTrue(address1.getZipcode().equals(address2.getZipcode()));
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// different zipcode
		address1.setZipcode("12345");
		address2.setZipcode("67890");
		assertFalse(address1.getZipcode().equals(address2.getZipcode()));
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));
		
		
		/*
		 * Test with mix values
		 */	
		address1 = new AddressBean();
		address2 = new AddressBean();
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		// same values
		long id = 1;
		String line1 = "line";
		String line2 = "line";
		String city = "city";
		State state = State.FL;
		String zipcode = "1234";

		address1.setId(id);
		address1.setLine1(line1);
		address1.setLine2(line2);
		address1.setCity(city);
		address1.setState(state);
		address1.setZipcode(zipcode);
		
		address2.setId(id);
		address2.setLine1(line1);
		address2.setLine2(line2);
		address2.setCity(city);
		address2.setState(state);
		address2.setZipcode(zipcode);
		
		assertEquals(address1.getId(), address2.getId());
		assertTrue(address1.getLine1().equals(address2.getLine1()));
		assertTrue(address1.getLine2().equals(address2.getLine2()));
		assertTrue(address1.getCity().equals(address2.getCity()));
		assertEquals(address1.getState(), address2.getState());
		assertTrue(address1.getZipcode().equals(address2.getZipcode()));
		
		assertTrue(address1.equals(address2));
		assertTrue(address2.equals(address1));
		
		
		// different values
		
		address1.setId(1);						// (a) different ids
		address2.setId(2);
		assertNotEquals(address1.getId(), address2.getId());
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));
		
		address1.setLine1(line1);				// (b) same line1 & (a)
		address2.setLine1(line1);
		assertTrue(address1.getLine1().equals(address2.getLine1()));
		assertFalse(address1.equals(address2));
		assertFalse(address2.equals(address1));
		
		address1.setId(id);					// (c) same ids, different line1
		address2.setId(id);
		assertEquals(address1.getId(), address2.getId());
		address1.setLine1("Line X");
		address2.setLine1("Line Y");
		assertFalse(address1.getLine1().equals(address2.getLine1()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));

		
		address1.setLine2(line2);			// (d) same ids, different line1, same line2
		address2.setLine2(line2);
		assertTrue(address1.getLine2().equals(address2.getLine2()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		

		address1.setLine2("Line X");			// (e) same ids, different line1, different line2
		address2.setLine2("Line Y");
		assertFalse(address1.getLine2().equals(address2.getLine2()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		
		
		address1.setCity(city);					// (f) same ids, different line1, different line2, same city
		address2.setCity(city);
		assertTrue(address1.getCity().equals(address2.getCity()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		
		address1.setLine1(line1);				// (g) same ids, same line1, same line2, different city
		address2.setLine1(line1);
		address1.setLine2(line2);
		address2.setLine2(line2);
		assertTrue(address1.getLine1().equals(address2.getLine1()));
		assertTrue(address1.getLine2().equals(address2.getLine2()));
		address1.setCity("Chicago");
		address2.setCity("NYC");
		assertFalse(address1.getCity().equals(address2.getCity()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		
		address1.setState(state);				// (h)same ids, same line1, same line2, different city, different state
		address2.setState(State.IA);
		assertNotEquals(address1.getState(), address2.getState());
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		
		address1.setZipcode(zipcode);   		// (i)same ids, same line1, same line2, different city, different state, same zipcode
		address2.setZipcode(zipcode);
		assertTrue(address1.getZipcode().equals(address2.getZipcode()));
		assertFalse(address1.equals(address2)); 
		assertFalse(address2.equals(address1));
		
		
	}

}
