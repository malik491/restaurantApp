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
 * View details of a menu item
 * @author Malik
 */
@WebServlet("/menuItem/view")
public class View extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemIdParam = ParametersUtil.validateString(request.getParameter("menuItemId"));
		Long menuItemId = ParametersUtil.parseLong(menuItemIdParam);
		
		MenuItemBean menuItem = null;
		String jspMsg = "Cannot view menu item. Invalid or missing meun item ID.";
		try {
			if (menuItemId != null) {
				menuItem = menuItemDAO.get(menuItemId);
				if (menuItem == null)
					jspMsg = String.format("Cannot view menu item. No menu item with ID (%d)", menuItemId);
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menuItem/View");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("menuItem", menuItem);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menuItem/view.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
