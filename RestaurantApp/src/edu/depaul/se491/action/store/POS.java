/**
 * 
 */
package edu.depaul.se491.action.store;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Point of Sale
 * Store employee will use this to add order 
 * @author Malik
 */
@WebServlet("/store/pos")
public class POS extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<MenuItemBean> menuItems = null;
		String jsonMenuItems = null;
		String jspMsg = null;
		
		try {
			menuItems = menuItemDAO.getAll();
			jsonMenuItems = new Gson().toJson(menuItems);
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "emp/POS");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		if (menuItems != null) {
			request.setAttribute("menuItems", menuItems);
			request.setAttribute("jsonMenuItems", jsonMenuItems);
		} else {
			request.setAttribute("msg", jspMsg);
		}
		
		String jspURL = "/store/pos.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
