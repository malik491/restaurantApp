/**
 * 
 */
package edu.depaul.se491.action.menu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Delete an existing menu item
 * @author Malik
 */
@WebServlet("/menu/deleteItem")
public class DeleteItem extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemId = request.getParameter("menuItemId");
		String jspMsg = null;
		
		try {
			if (menuItemId == null) {
				jspMsg = "Cannot delete a menu item. Unknown menu item ID.";
			} else {
				boolean deleted = menuItemDAO.delete(Long.valueOf(menuItemId));
				if (deleted)
					jspMsg = "Successfully deleted a menu item";
				else
					jspMsg = "Failed to delete a menu item. See console for details";				
			}
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menu/Delete");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menu/deleteItem.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
