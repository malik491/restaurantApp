/**
 * 
 */
package edu.depaul.se491.bean.account;

import java.io.Serializable;

/**
 * user bean
 * @author Malik
 *
 */
public class UserBean implements Serializable {
	private String firstName, lastName;
	private String email, phone;
	private AddressBean address;
	
	public UserBean() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}
	
	
	

}
