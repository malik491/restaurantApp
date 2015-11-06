package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.depaul.se491.bean.AccountBean;
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
	
	public  AccountDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new AccountBeanLoader();
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
	 * return account associated with the given id
	 * Null is returned if there are no account for the given id
	 * @param accountId
	 * @return
	 * @throws SQLException
	 */
	public AccountBean get(long accountId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		AccountBean account = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ID_Query);
			
			ps.setLong(1, accountId);
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
	 * return account associated with the given username
	 * Null is returned if there are no account for the given username
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public AccountBean getByUsername(String username) throws SQLException {
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
	 * @param account account data (excluding the id)
	 * @return
	 * @throws SQLException
	 */
	public boolean add(AccountBean account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(INSERT_ACC_SQL);
			
			loader.loadParameters(ps, account);
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("AccountDAO.add(): multiple (or 0) rows affected by add(account).");
			
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
	 * update an existing account with new data in the accountBean
	 * @param account updated account data
	 * @return
	 * @throws SQLException
	 */
	public boolean update(AccountBean account) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(UPADTE_ACC_SQL);
			
			loader.loadParameters(ps, account);
			ps.setLong(5, account.getId());
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED) 
				throw new SQLException("AccountDAO.update(): multiple (or 0) rows affected by update(account).");
			
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
	
	private static final String SELECT_ALL_Query = "SELECT * FROM accounts NATURAL JOIN users NATURAL JOIN addresses";
	private static final String SELECT_BY_ID_Query = SELECT_ALL_Query + " WHERE (account_id = ?)";
	private static final String SELECT_BY_USERNAME_Query = SELECT_ALL_Query + " WHERE (username = ?)";
	
	private static final String INSERT_ACC_SQL = "INSERT INTO accounts (user_id, account_role, username, password) VALUES (?,?,?,?)";
	private static final String UPADTE_ACC_SQL = "UPDATE accounts SET user_id=?, account_role=?, username=?, password=? WHERE (account_id=?)";

}
