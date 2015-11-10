package edu.depaul.se491.action.storeManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Manage orders
 * store manager will use this to manage all orders
 * @author Malik
 */
@WebServlet("/manager/manageOrders")
public class ManageOrders extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException 
	{
		List<OrderBean> orders = null;
		String jspMsg = null;
		
		try {
			orders = orderDAO.getAll();
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "storeManager/ManageOrders");
			jspMsg = "DB Exception Occured. See Console for Details.";
		}
		
		// set orders for the jsp
		req.setAttribute("orders", orders);
		req.setAttribute("msg", jspMsg);
		
		String jspURL = "/manager/manageOrders.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
