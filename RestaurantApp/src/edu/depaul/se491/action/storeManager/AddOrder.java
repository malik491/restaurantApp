/**
 * 
 */
package edu.depaul.se491.action.storeManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Add new order
 * used by a manager. Employee will use point of sale to add orders
 * @author Malik
 *
 */
@WebServlet("/manager/add/order")
public class AddOrder extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String jspMsg = null;
		List<MenuItemBean> menuItems = null;
		
		try {
			menuItems = menuItemDAO.getAll();
			
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "stroeManager/AddOrder");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("menuItems", menuItems != null? menuItems: null);
		request.setAttribute("msg", jspMsg != null? jspMsg : null);
		String jspURL = "/manager/addNew/order.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
