package edu.depaul.se491.bean;

import java.io.Serializable;

import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.Values;

/**
 * Address Bean to store address data
 * @author Malik
 *
 */
public class AddressBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String line1, line2;
	private String city, zipcode;
	private State state;
	
	/**
	 * construct an empty address with no id
	 */
	public AddressBean() {
		this.id = Values.UNKNOWN;
		this.line2 = Values.EMPTY_STRING;
	}
	
	/**
	 * return address id
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * set address id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * return address first line 
	 * (buildingNumber street-info)
	 * @return
	 */
	public String getLine1() {
		return line1;
	}
	
	/**
	 * set address first line
	 * (buildingNumber street-info)
	 * @param street
	 */
	public void setLine1(String street) {
		this.line1 = street;
	}
	
	/**
	 * return address second line
	 * (unit/apt number etc)
	 * @return
	 */
	public String getLine2() {
		return line2;
	}
	
	/**
	 * set address second line
	 * (unit/apt number etc)
	 * @param optionalLine
	 */
	public void setLine2(String optionalLine) {
		this.line2 = optionalLine;
	}
	
	/**
	 * return city
	 * @return
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * set city
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * return state
	 * @return
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * set state
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * return zipcode
	 * @return
	 */
	public String getZipcode() {
		return zipcode;
	}
	
	/**
	 * set zipcode
	 * use format: ddddd[-dddd]
	 * @param zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	

}
