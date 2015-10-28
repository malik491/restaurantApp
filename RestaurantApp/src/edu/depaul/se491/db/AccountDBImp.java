package edu.depaul.se491.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.depaul.s491.account.Account;
import edu.depaul.s491.account.User;
import edu.depaul.se491.order.Order;

/**
 * @author Malik
 *
 */
public class AccountDBImp implements AccountDB {
	// same database for all clients
	private static AccountDB instance = null;
	
	private static HashMap<String, Account> accounts = new HashMap<>();
	
	// hidden constructor to enforce a single instance
	private AccountDBImp() {}
	
	/**
	 * return an account database
	 * @return
	 */
	public static AccountDB getInstance() {
		if (instance == null)
			instance = new AccountDBImp();
		return instance;
	}
		
	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#addAccount(edu.depaul.s491.account.Account)
	 */
	@Override
	public boolean addAccount(Account account) {
		boolean added = false;
		if (isValidAccount(account)) {
			accounts.put(account.getId(), account);
			added = true;
		}
		return added;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#removeAccount(java.lang.String)
	 */
	@Override
	public boolean removeAccount(String accountId) {
		return isValidString(accountId)? (accounts.remove(accountId) != null): false;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String accountId) {
		return isValidString(accountId)? accounts.get(accountId) : null;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#getAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public Account getAccount(String username, String password) {
		Account result = null;
		if (isValidString(username) && isValidString(password)) {
			for (Account acc: accounts.values()) {
				if (acc.getUsername().equalsIgnoreCase(username) &&
					acc.getPassword().equalsIgnoreCase(password)) {
					result = acc;
					break;
				}
			}			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#getAccounts()
	 */
	@Override
	public List<Account> getAccounts() {
		List<Account> copy = new ArrayList<>();
		copy.addAll(accounts.values());
		return Collections.unmodifiableList(copy);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#findAccountForUser(edu.depaul.s491.account.User)
	 */
	@Override
	public List<Account> findAccountForUser(User user) {
		List<Account> userAccounts = new ArrayList<>();
		if (user == null)
			return userAccounts;
		
		for(Account acc: accounts.values()) {
			if (acc.getUser().equals(user))
				userAccounts.add(acc);
		}
		return Collections.unmodifiableList(userAccounts);
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.se491.db.AccountDB#hasAccount(java.lang.String)
	 */
	@Override
	public boolean hasAccount(String accountId) {
		return isValidString(accountId)? accounts.containsKey(accountId) : false;
	}
	
	private boolean isValidString(String s) {
		return (s != null && !s.isEmpty());
	}
	
	private boolean isValidAccount(Account acc) {
		return (acc != null && isValidString(acc.getId()));
	}

}
