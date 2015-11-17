package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.bean.loader.AccountBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.Values;

/**
 * AccountDAO to access/modify account data in the database
 * @author Malik
 *
 */
public class AccountDAO {
	private DAOFactory factory;
	private AccountBeanLoader loader;
	private UserDAO userDAO;
	
	public  AccountDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new AccountBeanLoader();
		this.userDAO = factory.getUserDAO();
	}
	
	
	/**
	 * return all accounts in the database
	 * Empty list is returned if there are no addresses in the database
	 * @return
	 * @throws SQLException
	 */
	public List<AccountBean> getAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<AccountBean> accounts = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_ALL_Query);
			
			ResultSet rs = ps.executeQuery();
			accounts = loader.loadList(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return accounts;
	}
	
	/**
	 * return account associated with the given username
	 * Null is returned if there are no account for the given username
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public AccountBean get(String username) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		AccountBean account = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_USERNAME_Query);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				account = loader.loadSingle(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return account;
	}
	
	/**
	 * add account to the database using the data in the accountBean
	 * @param account account data
	 * @return true if account is added
	 * @throws SQLException
	 */
	public boolean add(AccountBean account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean added = false;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			
			/* Transaction:
			 * - add user
			 * - add account
			 */
			UserBean addedUser = userDAO.transactionAdd(conn, account.getUser());
			added = addedUser != null;
			if (added) {				
				ps = conn.prepareStatement(INSERT_ACC_QUERY);
				loader.loadParameters(ps, account, 1);
				added = Values.validInsert(ps.executeUpdate());
			}
			
			if (!added)
				throw new SQLException("AccountDAO.add(): falied to add new account.");
			
			// commit
			conn.commit();

		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return added;
	}
	
	/**
	 * update an existing account with new data in the accountBean
	 * @param account updated account data
	 * @return true if account is updated
	 * @throws SQLException
	 */
	public boolean update(AccountBean account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean updated = false;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(UPADTE_ACC_QUERY);
			
			/*
			 * Transaction:
			 * - update user
			 * - update account
			 */
			
			updated = userDAO.transactionUpdate(conn, account.getUser());
			
			if (updated) {
				int paramIndex = 1;
				loader.loadUpdateParameters(ps, account, paramIndex);
				ps.setString(paramIndex + UPDATE_COLUMNS_COUNT, account.getUsername());
				updated = Values.validUpdate(ps.executeUpdate());
			}
			
			if (!updated)
				throw new SQLException("AccountDAO.update(): failed to update account.");

			// commit
			conn.commit();
		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return updated;
	}
	
	/**
	 * delete an existing account from the database
	 * @param account account to delete
	 * @return true if account is delete
	 * @throws SQLException
	 */
	public boolean delete(AccountBean account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean deleted = false;
		try {
			conn = factory.getConnection();
			conn.setAutoCommit(false);
			
			/*
			 * Transaction:
			 * - delete account (has foreign key to user)
			 * - delete user
			 */
			
			ps = conn.prepareStatement(DELETE_ACC_QUERY);
			ps.setString(1, account.getUsername());
			deleted = Values.validDelete(ps.executeUpdate());
			
			if (deleted)
				deleted = userDAO.transactionDelete(conn, account.getUser());
			
			if (!deleted)
				throw new SQLException("AccountDAO.deleted(): failed to deleted account.");

			// commit
			conn.commit();
			
		} catch (SQLException e1) {
			SQLException excp = new SQLException(e1);
			if (conn != null) {
				/*rollback*/
				try { conn.rollback();} 
				catch (SQLException e2) {excp.addSuppressed(e2);}
			}
			throw excp;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
		return deleted;
	}
	
	private static final String SELECT_ALL_Query = "SELECT * FROM accounts NATURAL JOIN users NATURAL JOIN addresses";
	private static final String SELECT_BY_USERNAME_Query = SELECT_ALL_Query + " WHERE (username = ?)";
	
	private static final String INSERT_ACC_QUERY = "INSERT INTO accounts (username, password, account_role, user_email) VALUES (?,?,?,?)";
	private static final String UPADTE_ACC_QUERY = "UPDATE accounts SET password=?, account_role=?, user_email=? WHERE (username=?)";
	private static final String DELETE_ACC_QUERY = "DELETE FROM accounts WHERE (username=?)";
	
	private static final int UPDATE_COLUMNS_COUNT = 3;

}
