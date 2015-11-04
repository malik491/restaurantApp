/**
 * 
 */
package edu.depaul.se491.action.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.util.DAOUtil;

/**
 * List ALL orders in the database
 * @author Malik
 */
@WebServlet("/listAll")
public class ListAllOrders extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		List<OrderBean> orders = null;
		String jspMsg = null;
		
		try {
			orders = orderDAO.getAll();
		} catch (SQLException e) {
			DAOUtil.printException(e, "ListAllOrder");
			jspMsg = "DB Exception Occured. See Console for Details.";
		}
		
		if (orders != null) {
			// set orders for the jsp
			request.setAttribute("orders", orders);
		} else {
			// set message for the jsp
			request.setAttribute("msg", jspMsg);
		}
		
		String jspURL = "/listOrders.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
