/**
 * 
 */
package edu.depaul.se491.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.depaul.se491.dao.mysql.AccountDAO;
import edu.depaul.se491.dao.mysql.AddressDAO;
import edu.depaul.se491.dao.mysql.MenuItemDAO;
import edu.depaul.se491.dao.mysql.OrderDAO;
import edu.depaul.se491.dao.mysql.OrderItemDAO;
import edu.depaul.se491.dao.mysql.UserDAO;

/**
 * Data Access Object Factory
 * @author Malik
 *
 */
public class DAOFactory {
	private static DAOFactory factory = null;
	private ProductionConnectionDriver productionDriver;

	public static DAOFactory getInstance() {
		factory = new DAOFactory();
		return factory;
	}
	
	/**
	 * constructor for DAOFactory
	 */
	private DAOFactory() {
		this.productionDriver = new ProductionConnectionDriver();
	}
	
	public Connection getConnection() throws SQLException {
		return productionDriver.getConnection();
	}
	
	public OrderDAO getOrderDAO(){
		return new OrderDAO(this);
	}
	
	public AccountDAO getAccountDAO(){
		return new AccountDAO(this);
	}
	
	public AddressDAO getAddressDAO(){
		return new AddressDAO(this);
	}
	
	public MenuItemDAO getMenuItemDAO(){
		return new MenuItemDAO(this);
	}

	public OrderItemDAO getOrderItemDAO() {
		return new OrderItemDAO(this);
	}
	
	public UserDAO getUserDAO() {
		return new UserDAO(this);
	}
}
