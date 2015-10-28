package edu.depaul.s491.account;

/**
 * a class representing a user account
 * @author Malik
 */

public class Account {
	private int id;
	private String username, password;
	private User user;
	private AccountRole role;
	
	/**
	 * construct a new Account
	 * @param username
	 * @param password
	 */
	public Account(String username, String password) {
		this.id = UNIQUE_ID++;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * construct a new Account
	 * @param username
	 * @param password
	 * @param user
	 * @param role
	 */
	public Account(String username, String password, User user, AccountRole role) {
		this(username, password);
		this.setUser(user);
		this.setRole(role);
	}
	
	/**
	 * return account id
	 * @return
	 */
	public String getId() {
		return Integer.toString(id);
	}
	
	/**
	 * return username
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * set username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * return password
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * set password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * return user associated with this account
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * set user to associate with this account
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * return account role
	 * @return
	 */
	public AccountRole getRole() {
		return role;
	}
	
	/**
	 * set account role
	 * @param role
	 */
	public void setRole(AccountRole role) {
		this.role = role;
	}
	
	// class variable
	private static int UNIQUE_ID = 1;
}
