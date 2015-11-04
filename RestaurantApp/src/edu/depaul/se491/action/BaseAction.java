package edu.depaul.se491.action;

import javax.servlet.http.HttpServlet;

import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.dao.mysql.AccountDAO;
import edu.depaul.se491.dao.mysql.AddressDAO;
import edu.depaul.se491.dao.mysql.MenuItemDAO;
import edu.depaul.se491.dao.mysql.OrderDAO;
import edu.depaul.se491.dao.mysql.OrderItemDAO;
import edu.depaul.se491.dao.mysql.UserDAO;

/**
 * Base Servlet for all other Servlets
 * It just holds dao objects used by different servlets
 * @author Malik
 *
 */
public abstract class BaseAction extends HttpServlet {
	protected static DAOFactory factory;
	protected static AddressDAO addressDAO;
	protected static AccountDAO accountDAO;
	protected static OrderDAO orderDAO;
	protected static OrderItemDAO orderItemDAO;
	protected static MenuItemDAO menuItemDAO;
	protected static UserDAO userDAO;
	
	static {
		factory = DAOFactory.getInstance();
		addressDAO = factory.getAddressDAO();
		accountDAO = factory.getAccountDAO();
		orderDAO = factory.getOrderDAO();
		orderItemDAO = factory.getOrderItemDAO();
		menuItemDAO = factory.getMenuItemDAO();
		userDAO = factory.getUserDAO();
	}
}
