/**
 * 
 */
package edu.depaul.se491.action.storeManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;

/**
 * Add a new menu item.
 * @author Malik
 *
 */
@WebServlet("/manager/add/menuItem")
public class AddMenuItem extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jspURL = "/manager/addNew/menuItem.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
