/**
 * 
 */
package edu.depaul.se491.action.store;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Kitchen terminal
 * In-kitchen store employee will use this to update order status to prepared
 * @author Malik
 */
@WebServlet("/store/kitchen")
public class Kitchen extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		List<OrderBean> orders = null;
		String jspMsg = null;
		
		try {
			orders = orderDAO.getAllWithStatus(OrderStatus.SUBMITTED);
			orders.sort(sortAlgorithm);
			
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "emp/Kitchen");
			jspMsg = "DB Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("orders", orders);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/store/kitchen.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	private static final OrderTimeComparator sortAlgorithm = new OrderTimeComparator();
	
	private static final class OrderTimeComparator implements Comparator<OrderBean> {
		@Override
		public int compare(OrderBean o1, OrderBean o2) {
			return o1.getTimestamp().compareTo(o2.getTimestamp());
		}
	} 
}
