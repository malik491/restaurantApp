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
	 * line2 defaults to empty string
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
	
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof AddressBean))
			return false;
		AddressBean that = (AddressBean) o;

		/* only compare address ids when both addresses have valid ids
		 * otherwise (one or both addresses have invalid ids) don't compare
		 * address id. */
		boolean sameIds = true;
		if (this.id != Values.UNKNOWN && that.id != Values.UNKNOWN) {
			sameIds = this.id == that.id;
		}
		if (!sameIds)
			return false;
		
		boolean sameLine1 = false;
		if (this.line1 == null && that.line1 == null) {
			sameLine1 = true;
		} else if (this.line1 != null && that.line1 != null) {
			sameLine1 = this.line1.equals(that.line1);
		}
		if (!sameLine1)
			return false;
		
		boolean sameLine2 = false;
		if (this.line2 == null && that.line2 == null) {
			sameLine2 = true;
		} else if (this.line2 != null && that.line2 != null) {
			sameLine2 = this.line2.equals(that.line2);
		}
		if (!sameLine2)
			return false;
		
		boolean sameCity = false;
		if (this.city == null && that.city == null) {
			sameCity = true;
		} else if (this.city != null && that.city != null) {
			sameCity = this.city.equals(that.city);
		}
		if (!sameCity)
			return false;
		
		
		boolean sameSate = false;
		if (this.state == null && that.state == null) {
			sameSate = true;
		} else if (this.state != null && that.state != null) {
			sameSate = this.state == that.state;
		}
		if (!sameSate)
			return false;
		
		
		boolean sameZipcode = false;
		if (this.zipcode == null && that.zipcode == null) {
			sameZipcode = true;
		} else if (this.zipcode != null && that.zipcode != null) {
			sameZipcode = this.zipcode.equals(that.zipcode);
		}
		if (!sameZipcode)
			return false;
		
		return true;
	}


	

}
