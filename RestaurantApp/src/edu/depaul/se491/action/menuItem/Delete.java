/**
 * 
 */
package edu.depaul.se491.action.menuItem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;

/**
 * Delete an existing menu item
 * @author Malik
 */
@WebServlet("/menuItem/delete")
public class Delete extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mItemIdParam = ParametersUtil.validateString(request.getParameter("menuItemId"));
		Long menuItemId = ParametersUtil.parseLong(mItemIdParam);
		String jspMsg = "Cannot delete a menu item. Invalid or missing menu item ID.";
		
		try {
			if (menuItemId != null) {
				jspMsg = String.format("Cannot delete account. No account with username (%s)", menuItemId);
				
				MenuItemBean menuItem = menuItemDAO.get(menuItemId);
				if (menuItem != null) {
					jspMsg = "Valid request parameters but deleting a menu item is unsupported at this time";
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menuItem/Delete");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menuItem/delete.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
