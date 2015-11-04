package edu.depaul.se491.bean;

import java.io.Serializable;

import edu.depaul.se491.enums.AccountRole;
import edu.depaul.se491.util.Values;

/**
 * AccountBean to store Account data
 * @author Malik
 */
public class AccountBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private UserBean user;
	private String username;
	private String password;
	private AccountRole role;
	

	/**
	 * construct an empty Account with no id
	 */
	public AccountBean() {
		id = Values.UNKNOWN;
	}
	
	/**
	 * return account id
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * set account id
	 * @param accountId
	 */
	public void setId(long accountId) {
		this.id = accountId;
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
