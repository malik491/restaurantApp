/**
 * 
 */
package edu.depaul.se491.bean.account;

import java.io.Serializable;

/**
 * @author Malik
 *
 */
public class AddressBean implements Serializable {
	private String streetAddress, optionalLine;
	private String city, zipcode;
	private State state;
	
	public AddressBean() {
		
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getOptionalLine() {
		return optionalLine;
	}
	
	public void setOptionalLine(String optionalLine) {
		this.optionalLine = optionalLine;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	

}
