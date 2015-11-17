package edu.depaul.se491.bean.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.enums.State;
import static edu.depaul.se491.util.DBLabels.*;


/**
 * Address Bean loader
 * - populate a preparedStatment using data store in an Address bean
 * - populate/recreate a new Address bean using data in a ResultSet
 * 
 * @author Malik
 */
public class AddressBeanLoader implements BeanLoader<AddressBean> {

	/**
	 * return a list of address beans using addresses data in the ResultSet (rows)
	 * Empty list is return if the ResultSet is empty
	 * The ResultSet cursor should be positioned before the first row before calling
	 * this method. Otherwise, the first row will not be included in the result.
	 * @param rs a ResultSet containing addresses data from the database
	 * @return list of addresses
	 */
	@Override
	public List<AddressBean> loadList(ResultSet rs) throws SQLException {
		List<AddressBean> addresses = new ArrayList<>();
		while(rs.next())
			addresses.add(loadSingle(rs));
		
		return addresses;
	}
	
	/**
	 * return an account bean using the ResultSet (a single row)
	 * THIS METHOD SHOULD BE CALLED ONLY WHEN (rs.next() is true before the call).
	 * It expects a ResultSet its cursor pointing at a row
	 * @param rs a ResultSet containing account data from the database
	 * @return account bean object containing the data from an account in the database
	 */
	@Override
	public AddressBean loadSingle(ResultSet rs) throws SQLException {
		AddressBean bean = new AddressBean();		
		
		bean.setId(rs.getLong(ADDRESS_ID_LABEL));
		bean.setLine1(rs.getString(ADDRESS_LINE1_LABEL));
		bean.setLine2(rs.getString(ADDRESS_LINE2_LABEL));
		bean.setCity(rs.getString(ADDRESS_CITY_LABEL));		
		bean.setState(State.valueOf(rs.getString(ADDRESS_STATE_LABEL)));
		bean.setZipcode(rs.getString(ADDRESS_ZIPCODE_LABEL));
		
		return bean;
	}

	/**
	 * populate the PreparedStatment with data in the address bean
	 * @param ps preparedStatement with sql string containing at least 5 '?'/placeholders
	 * @param bean address bean with data
	 * @return return the passed ps
	 */
	@Override
	public void loadParameters(PreparedStatement ps, AddressBean bean, int paramIndex) throws SQLException {		
		ps.setString(paramIndex++, bean.getLine1());
		ps.setString(paramIndex++, bean.getLine2());
		ps.setString(paramIndex++, bean.getCity());
		ps.setString(paramIndex++, bean.getState().name());
		ps.setString(paramIndex, bean.getZipcode());
	}

	
}
