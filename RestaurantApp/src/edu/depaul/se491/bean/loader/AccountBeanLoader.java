package edu.depaul.se491.bean.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.enums.AccountRole;
import static edu.depaul.se491.util.DBLabels.*;

/**
 * Account Bean loader
 * - populate a preparedStatment using data store in an Account bean
 * - populate/recreate a new Account bean using data in a ResultSet
 * 
 * @author Malik
 */
public class AccountBeanLoader implements BeanLoader<AccountBean> {
	private UserBeanLoader loader;
	
	public AccountBeanLoader() {
		loader = new UserBeanLoader();
	}
	
	/**
	 * return a list of account beans using accounts data in the ResultSet (rows)
	 * Empty list is return if the ResultSet is empty
	 * The ResultSet cursor should be positioned before the first row before calling
	 * this method. Otherwise, the first row will not be included in the result.
	 * @param rs a ResultSet containing accounts data from the database
	 * @return list of accounts
	 */
	@Override
	public List<AccountBean> loadList(ResultSet rs) throws SQLException {
		List<AccountBean> accounts = new ArrayList<>();
		while(rs.next())
			accounts.add(loadSingle(rs));
		return accounts;
	}

	/**
	 * return an account bean using the ResultSet (a single row)
	 * THIS METHOD SHOULD BE CALLED ONLY WHEN (rs.next() is true before the call).
	 * It expects a ResultSet its cursor pointing at a row
	 * @param rs a ResultSet containing account data from the database
	 * @return account bean object containing the data from an account in the database
	 */
	@Override
	public AccountBean loadSingle(ResultSet rs) throws SQLException {
		UserBean user = loader.loadSingle(rs);
		
		AccountBean bean = new AccountBean();
		bean.setUsername(rs.getString(USERNAME_LABEL));
		bean.setPassword(rs.getString(PASSOWRD_LABEL));
		bean.setRole(AccountRole.valueOf(rs.getString(ACCOUNT_ROLE_LABEL)));
		bean.setUser(user);
		
		return bean;
	}

	/**
	 * populate the PreparedStatment with data in the account bean
	 * @param ps preparedStatement with sql string containing at least 4 '?'/placeholders
	 * @param bean account bean with data
	 * @return return the passed ps
	 */
	@Override
	public void loadParameters(PreparedStatement ps, AccountBean bean, int paramIndex) throws SQLException {
		ps.setString(paramIndex++, bean.getUsername());
		ps.setString(paramIndex++, bean.getPassword());		
		ps.setString(paramIndex++, bean.getRole().name());
		ps.setString(paramIndex, bean.getUser().getEmail());
	}
	

	/**
	 * populate the PreparedStatment with data in the account bean
	 * @param ps preparedStatement with update sql
	 * @param bean account bean with data
	 * @return return the passed ps
	 */
	public void loadUpdateParameters(PreparedStatement ps, AccountBean bean, int paramIndex) throws SQLException {
		ps.setString(paramIndex++, bean.getPassword());		
		ps.setString(paramIndex++, bean.getRole().name());
		ps.setString(paramIndex, bean.getUser().getEmail());
	}
}
