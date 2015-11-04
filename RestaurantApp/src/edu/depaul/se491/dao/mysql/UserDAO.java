/**
 * 
 */
package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.bean.loader.UserBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.DAOUtil;
import edu.depaul.se491.util.Values;

/**
 * UserDAO to access/modify account data in the database
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
		}
		return users;
	}
	
	/**
	 * return user associated with the given id
	 * Null is returned if there are no user for the given id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public UserBean get(long userId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		UserBean user = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ID_QUERY);
			
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				user = loader.loadSingle(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return user;
	}

	/**
	 * add user to the database using the data in the userBean
	 * @param user user data (excluding the id)
	 * @return
	 * @throws SQLException
	 */
	public boolean add(UserBean user) throws SQLException {
		Connection conn = null;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			
			
			/* Transaction begin :
			 * - add address
			 * - add user
			 * - commit or roll back
			 */
			
			// handle address
			addressDAO.transactionAdd(conn, user.getAddress());
				
			// handle user
			transactionAdd(conn, user);

			// commit
			conn.commit();
		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (conn != null)
				conn.setAutoCommit(true);
		}
		return true;
	}
	
	/**
	 * update an existing user with new data in the userBean
	 * @param user updated user data
	 * @return
	 * @throws SQLException
	 */
	public boolean update(UserBean user) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(UPADTE_USER_QUERY);
			
			loader.loadParameters(ps, user);
			ps.setLong(6, user.getId());
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED) 
				throw new SQLException("UserDAO.update(): multiple (or 0) rows affected by update(user)");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	/**
	 * insert a new user as a part of a database transaction
	 * Also, it will set the id in the passed object (the deliveryAddress parameter)
	 * @param user user data (except the id). 
	 * @return if the user is inserted, its id will be set and return true
	 * @throws SQLException
	 */
	public boolean transactionAdd(Connection conn, UserBean user) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			loader.loadParameters(ps, user);
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("UserDAO.transactionAdd(): multiple (or 0) rows affected by transactionAdd(user)");
			
			// set the address id
			user.setId(DAOUtil.getAutGeneratedKey(ps));
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users NATURAL JOIN addresses";
	private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + " WHERE (user_id = ?)";
	private static final String INSERT_USER_QUERY = "INSERT INTO users (address_id, first_name, last_name, email, phone) VALUES (?,?,?,?,?)";	
	private static final String UPADTE_USER_QUERY = "UPDATE users SET address_id=?, first_name=?, last_name=?, email=?, phone=? WHERE (user_id = ?)";	
}
