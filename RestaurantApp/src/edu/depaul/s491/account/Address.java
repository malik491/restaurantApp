package edu.depaul.s491.account;


/**
 * a generic address class.
 * @author Lamont (modified by Malik)
 */
public class Address{
	String streetLine, optionalLine;
	String city, state, zipcode;

	/**
	 * construct an empty address
	 * 
	 * Address
	 * --------------------------------------
	 * Street Line  : buildingNumber StreetName
	 * optional Line: apt# or unit#
	 * City Line	: city 
	 * State Line   : state
	 * zipCode Line : zipcode
	 * ---------------------------------------
	 */
	public Address() {
		streetLine = optionalLine = city = state = zipcode = ""; // all set to empty string
	}
	
	/**
	 * construct a new address
	 *  
	 * Address
	 * --------------------------------------
	 * Street Line  : buildingNumber StreetName
	 * optional Line: apt# or unit#
	 * City Line	: city 
	 * State Line   : state
	 * zipCode Line : zipcode
	 * ---------------------------------------
	 * @param sLine StreetLine
	 * @param optLine optionaLine
	 * @param city CityLine
	 * @param state StateLine
	 * @param zipcode zipCodeLine
	 */
	public Address(String sLine, String optLine, String city, String state, String zipcode) {
		this.streetLine = sLine;
		this.optionalLine = optLine;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	/**
	 * return street line of this address
	 * @return
	 */
	public String getStreetLine() {
		return streetLine;
	}

	/**
	 * set the street line of this address
	 * @param street
	 */
	public void setStreetLine(String street) {
		this.streetLine = street;
	}

	/**
	 * return optional line of this address
	 * @return
	 */
	public String getOptionalLine() {
		return optionalLine;
	}

	/**
	 * set optional line of this address
	 * @param optionalLine
	 */
	public void setOptionalLine(String optionalLine) {
		this.optionalLine = optionalLine;
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
	public String getState() {
		return state;
	}

	/**
	 * set state
	 * @param state
	 */
	public void setState(String state) {
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
	 * set zipcode. 
	 * Allowable format: ##### or #####-#### 
	 * @param zipcode
	 * @return
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
		
	
	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != this.getClass())
			return false;
		
		Address that = (Address) o;
		if (!this.streetLine.equals(that.streetLine))
			return false;
		if (!this.optionalLine.equals(that.optionalLine))
			return false;
		if (!this.city.equals(that.city))
			return false;
		if (!this.state.equals(that.state))
			return false;
		if (!this.zipcode.equals(that.zipcode))
			return false;
		
		return true;
	}
}
