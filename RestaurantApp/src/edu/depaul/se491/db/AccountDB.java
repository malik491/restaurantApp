package edu.depaul.se491.db;

import java.util.List;

import edu.depaul.s491.account.Account;
import edu.depaul.s491.account.User;

/**
 * @author Malik
 *
 */
public interface AccountDB {
		
	/**
	 * add account
	 * @param ac account
	 * @return
	 */
	public boolean addAccount(Account account);
	
	/**
	 * remove account if present
	 * @param accountId
	 * @return
	 */
	public boolean removeAccount(String accountId);
	
	/**
	 * return account if present or null
	 * @param accountId
	 * @return
	 */
	public Account getAccount(String accountId);
	
	/**
	 * return account if present or null
	 * @param username
	 * @param password
	 * @return
	 */
	public Account getAccount(String username, String password);
	
	/**
	 * return all accounts in the DB
	 * @return
	 */
	public List<Account> getAccounts();

	/**
	 * return a list of all accounts for a user
	 * empty list is return is user has no account in the DB
	 * @param user
	 * @return
	 */
	public List<Account> findAccountForUser(User user);
	
	/**
	 * return true if there is an account with the given account id
	 * @param accountId
	 * @return
	 */
	public boolean hasAccount(String accountId);
}
