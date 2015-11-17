package edu.depaul.se491.bean;

import java.io.Serializable;

import edu.depaul.se491.enums.AccountRole;

/**
 * AccountBean to store Account data
 * @author Malik
 */
public class AccountBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UserBean user;
	private String username;
	private String password;
	private AccountRole role;
	

	/**
	 * construct an empty Account
	 */
	public AccountBean() {
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
	public UserBean getUser() {
		return user;
	}

	/**
	 * set user associated with this account
	 * @param user
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * return role associated with this account
	 * @return
	 */
	public AccountRole getRole() {
		return role;
	}

	/**
	 * set a role for this account
	 * @param role
	 */
	public void setRole(AccountRole role) {
		this.role = role;
	}
	
	
}
