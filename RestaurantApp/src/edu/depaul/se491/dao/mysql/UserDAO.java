package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.bean.loader.AddressBeanLoader;
import edu.depaul.se491.bean.loader.UserBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.Values;

/**
 * UserDAO to access/modify user data in the database
 * @author Malik
 */
public class UserDAO {
	private DAOFactory factory;
	private UserBeanLoader loader;
	private AddressDAO addressDAO;

	public UserDAO(DAOFactory factory) {
		this.factory = factory;
		this.addressDAO = factory.getAddressDAO();
		this.loader = new UserBeanLoader();
	}
	
	/**
	 * return all users in the database
	 * Empty list is returned if there are no users in the database
	 * @return
	 * @throws SQLException
	 */
	public List<UserBean> getAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<UserBean> users = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_ALL_QUERY);
			
			ResultSet rs = ps.executeQuery();
			users = loader.loadList(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return users;
	}
	
	/**
	 * return user associated with the given email
	 * Null is returned if there are no user for the given id
	 * @param email user email
	 * @return User or Null
	 * @throws SQLException
	 */
	public UserBean get(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		UserBean user = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_EMAIL_QUERY);
			
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				user = loader.loadSingle(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return user;
	}
	
	/**
	 * insert a new user (including user address) as a part of a database transaction
	 * @param conn connection
	 * @param user user data. 
	 * @return inserted user or Null
	 * @throws SQLException
	 */
	public UserBean transactionAdd(Connection conn, UserBean user) throws SQLException {
		PreparedStatement ps = null;
		UserBean resultUser = null;
		try {
			// add new address
			AddressBean address = addressDAO.transactionAdd(conn, user.getAddress());
			boolean added = address != null;
			
			if (added) {
				ps = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
				loader.loadParameters(ps, user, 1);
				added = Values.validInsert(ps.executeUpdate());				
			}
			
			if (added) {
				resultUser = new UserBean();
				//set address to added address
				resultUser.setAddress(address);
				
				// copy
				resultUser.setEmail(user.getEmail());
				resultUser.setFirstName(user.getFirstName());
				resultUser.setLastName(user.getLastName());
				resultUser.setPhone(user.getPhone());
			}
						
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return resultUser;
	}

	/**
	 * update an existing user as a part of database transaction
	 * @param user updated user
	 * @return true if user is updated
	 * @throws SQLException
	 */
	public boolean transactionUpdate(Connection conn, UserBean user) throws SQLException {
		PreparedStatement ps = null;
		boolean updated = false;
		try {
			updated = addressDAO.transactionUpdate(conn, user.getAddress());	
			
			if (updated) {
				ps = conn.prepareStatement(UPDATE_USER_QUERY);
				int paramIndex = 1;
				loader.loadUpdateParameters(ps, user, paramIndex);
				ps.setString(paramIndex + UPDATE_USER_COLUMNS_COUNT, user.getEmail());	
				updated = Values.validUpdate(ps.executeUpdate());
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return updated;
	}
	
	/**
	 * delete an existing user as a part of database transaction
	 * @param conn
	 * @param user
	 * @return true if user is deleted
	 * @throws SQLException
	 */
	public boolean transactionDelete(Connection conn, UserBean user) throws SQLException {
		PreparedStatement ps = null;
		boolean deleted = false;
		try {
			ps = conn.prepareStatement(DELETE_USER_QUERY);
			ps.setString(1, user.getEmail());
			deleted = Values.validDelete(ps.executeUpdate());
			
			if (deleted)
				deleted = addressDAO.transactionDelete(conn, user.getAddress().getId());	
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return deleted;
	}

	
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users NATURAL JOIN addresses";
	private static final String SELECT_BY_EMAIL_QUERY = SELECT_ALL_QUERY + " WHERE (user_email = ?)";
	
	private static final String INSERT_USER_QUERY = "INSERT INTO users (user_email, first_name, last_name, phone, address_id) VALUES (?,?,?,?,?)";	
	private static final String UPDATE_USER_QUERY = "UPDATE users SET first_name=?, last_name=?, phone=?, address_id=? WHERE (user_email = ?)";
	private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE (user_email = ?)";
	
	
	private static final int UPDATE_USER_COLUMNS_COUNT = 4;
	
}