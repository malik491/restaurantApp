package edu.depaul.se491.action.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * @author Malik
 *
 */
@WebServlet("/order/view")
public class View extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		OrderBean order = null;
		String jspMsg = null;
		
		try {
			if (orderId == null) {
				jspMsg = "Cannot View order. Unknown Order ID.";
			} else {
				order = orderDAO.get(Long.valueOf(orderId));
				if (order == null)
					jspMsg = String.format("Failed to find order with ID (%d)", orderId);
				
			}
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/View");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("order", order);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/order/view.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
