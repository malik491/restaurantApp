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
 * AddressDAO to access/modify account data in the database
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
			ps = conn.prepareStatement(SELECT_ALL_SQL);
			
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
			ps = conn.prepareStatement(SELECT_BY_ID_SQL);
			
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
	 * add address to the database using the data in the addressBean
	 * @param address address data (excluding the id)
	 * @return
	 * @throws SQLException
	 */
	public boolean add(AddressBean address) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(INSERT_ADDR_SQL);
			
			loader.loadParameters(ps, address);
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("AddressDAO.add(): multiple (or 0) rows affected by add(address)");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return true;
	}
	
	/**
	 * update an existing address with new data in the addressBean
	 * @param address updated address data
	 * @return
	 * @throws SQLException
	 */
	public boolean update(AddressBean address) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(UPADTE_ADDR_SQL);
			
			loader.loadParameters(ps, address);
			ps.setLong(6, address.getId());
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED) 
				throw new SQLException("AddressDAO.update(): multiple (or 0) rows affected by update(address)");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return true;
	}
	
	
	/**
	 * insert a new address as a part of a database transaction
	 * Also, it will set the id in the passed object (the deliveryAddress parameter)
	 * @param deliveryAddress address data (except the id). 
	 * @return if the address is inserted, its id will be set and return true
	 * @throws SQLException
	 */
	public boolean transactionAdd(Connection conn, AddressBean address) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(INSERT_ADDR_SQL, Statement.RETURN_GENERATED_KEYS);
			
			loader.loadParameters(ps, address);
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("AddressDAO.transactionAdd(): multiple (or 0) rows affected by transactionAdd(address)");
			
			// set the address id
			address.setId(DAOUtil.getAutGeneratedKey(ps));
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	
	
	private static final String SELECT_ALL_SQL = "SELECT * FROM addresses";
	private static final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + " WHERE (address_id = ?)";
	
	private static final String INSERT_ADDR_SQL = "INSERT INTO addresses (line1, line2, city, state, zipcode) VALUES (?,?,?,?,?)";
	private static final String UPADTE_ADDR_SQL = "UPDATE addresses SET line1=?, line2=?, city=?, state=?, zipcode=? WHERE (address_id=?)";
	
}
