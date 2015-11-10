package edu.depaul.se491.action.menu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * View details of a menu item
 * @author Malik
 */
@WebServlet("/menu/viewItem")
public class ViewItem extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemId = request.getParameter("menuItemId");
		MenuItemBean menuItem = null;
		String jspMsg = null;
		
		try {
			if (menuItemId == null) {
				jspMsg = "Cannot view menu item. Unknown meun item ID.";
			} else {
				menuItem = menuItemDAO.get(Long.valueOf(menuItemId));
				if (menuItem == null)
					jspMsg = String.format("Failed to find menu item with ID (%d)", menuItemId);
				
			}
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menu/View");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("menuItem", menuItem);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menu/viewItem.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
