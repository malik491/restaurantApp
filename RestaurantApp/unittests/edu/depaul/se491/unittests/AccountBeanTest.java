/**
 * 
 */
package edu.depaul.se491.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.enums.*;

/**
 * @author Lamont
 *
 */
public class AccountBeanTest {
	
	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#AccountBean()}.
	 */
	@Test
	public void testAccountBean() {
		AccountBean account = new AccountBean();
		
		assertTrue(account != null);
		assertEquals(null, account.getUsername());  
		assertEquals(null, account.getPassword());  
		assertEquals(null, account.getRole()); 
		assertEquals(null, account.getUser());  
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		AccountBean account = new AccountBean();

		assertEquals(null, account.getUsername());

		String username1 = "username1";
		String username2 = "username2";
					
		account.setUsername(username1);
		assertTrue(account.getUsername().equals(username1));
		assertFalse(account.getUsername().equals(username2));
		
		account.setUsername(username2);
		assertTrue(account.getUsername().equals(username2));
		assertFalse(account.getUsername().equals(username1));
		
		account.setUsername(null);
		assertEquals(null, account.getUsername());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#setUsername(String)}.
	 */
	@Test
	public void testSetUsername() {
		AccountBean account = new AccountBean();

		assertEquals(null, account.getUsername());

		String username1 = "username1";
		String username2 = "username2";
					
		account.setUsername(username1);
		assertTrue(account.getUsername().equals(username1));
		assertFalse(account.getUsername().equals(username2));
		
		account.setUsername(username2);
		assertTrue(account.getUsername().equals(username2));
		assertFalse(account.getUsername().equals(username1));
		
		account.setUsername(null);
		assertEquals(null, account.getUsername());
	}


	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#getPassword()}.
	 */
	@Test
	public void testGetPassword() {
		AccountBean account = new AccountBean();

		assertEquals(null, account.getPassword());

		String pwd1 = "pwd1";
		String pwd2 = "pwd2";
					
		account.setPassword(pwd1);
		assertTrue(account.getPassword().equals(pwd1));
		assertFalse(account.getPassword().equals(pwd2));
		
		account.setPassword(pwd2);
		assertTrue(account.getPassword().equals(pwd2));
		assertFalse(account.getPassword().equals(pwd1));
		
		account.setPassword(null);
		assertEquals(null, account.getPassword());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#setPassword(java.lang.String)}.
	 */
	@Test
	public void testSetPassword() {
		AccountBean account = new AccountBean();

		assertEquals(null, account.getPassword());

		String pwd1 = "pwd1";
		String pwd2 = "pwd2";
					
		account.setPassword(pwd1);
		assertTrue(account.getPassword().equals(pwd1));
		assertFalse(account.getPassword().equals(pwd2));
		
		account.setPassword(pwd2);
		assertTrue(account.getPassword().equals(pwd2));
		assertFalse(account.getPassword().equals(pwd1));
		
		account.setPassword(null);
		assertEquals(null, account.getPassword());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#getUser()}.
	 */
	@Test
	public void testGetUser() {
		AccountBean account = new AccountBean();
		
		assertEquals(null, account.getUser());
		
		UserBean user1 = new UserBean();
		UserBean user2 = new UserBean();
		
		account.setUser(user1);
		assertSame(user1, account.getUser());
		assertNotSame(user2, account.getUser());
		
		account.setUser(user2);
		assertSame(user2, account.getUser());
		assertNotSame(user1, account.getUser());
		
		account.setUser(null);
		assertEquals(null, account.getUser());
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#setUser(edu.depaul.se491.bean.UserBean)}.
	 */
	@Test
	public void testSetUser() {
		AccountBean account = new AccountBean();
		
		assertEquals(null, account.getUser());
		
		UserBean user1 = new UserBean();
		UserBean user2 = new UserBean();
		
		account.setUser(user1);
		assertSame(user1, account.getUser());
		assertNotSame(user2, account.getUser());
		
		account.setUser(user2);
		assertSame(user2, account.getUser());
		assertNotSame(user1, account.getUser());
		
		account.setUser(null);
		assertEquals(null, account.getUser());
		
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#getRole()}.
	 */
	@Test
	public void testGetRole() {
		AccountBean account = new AccountBean();
		
		assertEquals(null, account.getRole());
		
		AccountRole admin = AccountRole.ADMIN;
		AccountRole manager = AccountRole.MANAGER;
		
		account.setRole(admin);
		assertEquals(admin, account.getRole());
		assertNotEquals(manager, account.getRole());
		
		for (AccountRole role: AccountRole.values()) {
			account.setRole(role);
			assertEquals(role, account.getRole());
		}
		
		account.setRole(null);
		assertTrue(account.getRole() == null);	
	}

	/**
	 * Test method for {@link edu.depaul.se491.bean.AccountBean#setRole(edu.depaul.se491.enums.AccountRole)}.
	 */
	@Test
	public void testSetRole() {
		AccountBean account = new AccountBean();
		
		assertEquals(null, account.getRole());
		
		AccountRole admin = AccountRole.ADMIN;
		AccountRole manager = AccountRole.MANAGER;
		
		account.setRole(admin);
		assertEquals(admin, account.getRole());
		assertNotEquals(manager,account.getRole());
		
		for (AccountRole role: AccountRole.values()) {
			account.setRole(role);
			assertEquals(role, account.getRole());
		}
		
		account.setRole(null);
		assertTrue(account.getRole() == null);
	}
}
