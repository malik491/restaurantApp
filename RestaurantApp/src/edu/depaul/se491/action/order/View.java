package edu.depaul.se491.action.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;

/**
 * @author Malik
 *
 */
@WebServlet("/order/view")
public class View extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderIdParam = request.getParameter("orderId");
		Long orderId = ParametersUtil.parseLong(orderIdParam);
		
		OrderBean order = null;
		String jspMsg = "Cannot View order. Invalid or missing Order ID.";
		
		try {
			if (orderId != null) {
				order = orderDAO.get(orderId);
				if (order == null)
					jspMsg = String.format("Cannot view order. No order with ID (%d)", orderId);
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
