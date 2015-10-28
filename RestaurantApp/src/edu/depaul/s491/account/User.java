package edu.depaul.s491.account;

/**
 * @author Malik
 */

public class User {
	private String fName, lName, phone, email;
	private Address address;
	
	/**
	 * construct a new User
	 * @param fName first name
	 * @param lName last name
	 */
	public User(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
		this.phone = this.email = "";
	}
	
	/**
	 * construct a new user
	 * @param fName first name
	 * @param lName last name
	 * @param phone
	 * @param email
	 * @param address
	 */
	public User(String fName, String lName, String phone,
			String email, Address address) {
		this(fName, lName);
		this.phone = phone;
		this.email = email;
		this.address = address;
	}
		
	/**
	 * return user full name
	 * @return
	 */
	public String getName() {
		return fName + " " + lName;
	}
	
	/**
	 * return first name
	 * @return
	 */
	public String getFirstName() {
		return fName;
	}
	
	/**
	 * return last name
	 * @return
	 */
	public String getLastName() {
		return lName;
	}
	
	/**
	 * set first name
	 * @param fName
	 */
	public void setFirstName(String fName) {
		this.fName = fName;
	}
	
	/**
	 * set last name
	 * @param lName
	 */
	public void setLastName(String lName) {
		this.lName = lName;
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
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * return email address
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * set email address
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * return address
	 * @return
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * set address
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		
		User that = (User) o;
		if (!getName().equals(that.getName()))
			return false;
		else if(!phone.equals(that.phone))
			return false;
		else if (!email.equals(that.email))
			return false;
		else if (!address.equals(that.address))
			return false;
		
		return true;
	}
}
