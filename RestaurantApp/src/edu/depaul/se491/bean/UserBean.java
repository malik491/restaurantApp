package edu.depaul.se491.bean;

import java.io.Serializable;

import edu.depaul.se491.util.Values;

/**
 * User bean to store user information
 * @author Malik
 */
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String firstName, lastName;
	private String email, phone;
	private AddressBean address;
	
	/**
	 * construct an empty user
	 * phone field defaults to empty string
	 */
	public UserBean() {
		this.phone = Values.EMPTY_STRING;
	}
		
	/**
	 * return first name
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * set first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * return last name
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * set last name
	 * @return
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * return email
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * return phone number
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * set phone number
	 * use the format: ddd-ddd-dddd
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * return user address
	 * @return
	 */
	public AddressBean getAddress() {
		return address;
	}

	/**
	 * set user address
	 * @param address
	 */
	public void setAddress(AddressBean address) {
		this.address = address;
	}
	
	
	

}
