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
 * Manage menu items
 * @author Malik
 *
 */
@WebServlet("/manager/manage/menuItems")
public class ManageMenuItems extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<MenuItemBean> menuItems = null;
		String jspMsg = null;
		
		try {
			menuItems = menuItemDAO.getAll();
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "storeManager/ManageMenuItems");
			jspMsg = "DB Exception Occured. See Console for Details.";
		}
		
		// set menuItems for the jsp
		req.setAttribute("menuItems", menuItems);
		req.setAttribute("msg", jspMsg);
		
		String jspURL = "/manager/manage/menuItems.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
