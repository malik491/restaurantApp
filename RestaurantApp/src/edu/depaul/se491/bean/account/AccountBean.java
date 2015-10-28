/**
 * 
 */
package edu.depaul.se491.bean.account;

import java.io.Serializable;

/**
 * Account Bean
 * @author Malik
 */
public class AccountBean implements Serializable {
	private String id;
	private String username;
	private String password;
	private UserBean user;
	
	public AccountBean() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
	
}
