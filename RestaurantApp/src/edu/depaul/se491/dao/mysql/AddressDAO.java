package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.loader.AddressBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.DAOUtil;
import edu.depaul.se491.util.Values;

/**
 * AddressDAO to access/modify address data in the database
 * @author Malik
 *
 */
public class AddressDAO {
	private DAOFactory factory;
	private AddressBeanLoader loader;
	
	public  AddressDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new AddressBeanLoader();
	}
	
	/**
	 * return all addresses in the database
	 * Empty list is returned if there are no addresses in the database
	 * @return
	 * @throws SQLException
	 */
	public List<AddressBean> getAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<AddressBean> addresses = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_ALL_QUERY);
			
			ResultSet rs = ps.executeQuery();
			addresses = loader.loadList(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return addresses;
	}

	/**
	 * return address associated with the given id
	 * Null is returned if there are no address for the given id
	 * @param addressId
	 * @return
	 * @throws SQLException
	 */
	public AddressBean get(long addressId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		AddressBean address = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ID_QUERY);
			
			ps.setLong(1, addressId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				address = loader.loadSingle(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return address;
	}
	
	/**
	 * insert a new address as a part of a database transaction
	 * @param conn database connection
	 * @param deliveryAddress address data (except the id). 
	 * @return address
	 * @throws SQLException
	 */
	public AddressBean transactionAdd(Connection conn, AddressBean address) throws SQLException {
		PreparedStatement ps = null;
		AddressBean resultAddr = null;
		try {
			ps = conn.prepareStatement(INSERT_ADDR_QUERY, Statement.RETURN_GENERATED_KEYS);
			loader.loadParameters(ps, address, 1);
			
			boolean added = Values.validInsert(ps.executeUpdate());
			if (added) {
				// set new address id
				resultAddr = new AddressBean();
				resultAddr.setId(DAOUtil.getAutGeneratedKey(ps));
				// copy
				resultAddr.setLine1(address.getLine1());
				resultAddr.setLine2(address.getLine2());
				resultAddr.setCity(address.getCity());
				resultAddr.setState(address.getState());
				resultAddr.setZipcode(address.getZipcode());								
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return resultAddr;
	}
		
	/**
	 * update an existing address as a part of a database transaction
	 * @param conn database connection
	 * @param address updated address
	 * @return true if the address in database is updated
	 * @throws SQLException
	 */
	public boolean transactionUpdate(Connection conn, AddressBean address) throws SQLException {
		PreparedStatement ps = null;
		boolean updated = false;
		try {
			ps = conn.prepareStatement(UPADTE_ADDR_QUERY);
			
			int paramIndex = 1;
			loader.loadParameters(ps, address, paramIndex);
			ps.setLong(paramIndex + UPDATE_COULMNS_COUNT, address.getId());
			updated = Values.validUpdate(ps.executeUpdate());			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return updated;
	}
	
	/**
	 * delete an existing address as a part of a database transaction
	 * @param conn database connection
	 * @param addressId 
	 * @return true if address is deleted
	 * @throws SQLException
	 */
	public boolean transactionDelete(Connection conn, long addressId) throws SQLException {
		PreparedStatement ps = null;
		boolean deleted = false;
		try {
			ps = conn.prepareStatement(DELETE_ADDR_QUERY);			
			ps.setLong(1, addressId);
			deleted = Values.validDelete(ps.executeUpdate());
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return deleted;
	}
	
	
	
	private static final String SELECT_ALL_QUERY = "SELECT * FROM addresses ORDER BY address_id";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM addresses WHERE (address_id = ?)";
	
	private static final String INSERT_ADDR_QUERY = "INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES (?,?,?,?,?)";
	private static final String UPADTE_ADDR_QUERY = "UPDATE addresses SET line1=?, line2=?, city=?, state=?, zipcode=? WHERE (address_id=?)";
	private static final String DELETE_ADDR_QUERY = "DELETE FROM addresses WHERE (address_id=?)";
	
	private static final int UPDATE_COULMNS_COUNT = 5;
	
}
