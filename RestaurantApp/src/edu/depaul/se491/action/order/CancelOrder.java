package edu.depaul.se491.action.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
 * Cancel order servlet
 * @author Malik
 */

@WebServlet("/cancelOrder")
public class CancelOrder extends BaseAction {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String orderId = request.getParameter("orderId");
		List<OrderBean> cancelableOrders = null;
		String jspMsg = null;
		
		try {
			if (orderId != null) {
				// change order status logic
				OrderBean savedOrder = orderDAO.get(Long.valueOf(orderId));
				if (savedOrder != null) {
					savedOrder.setStatus(OrderStatus.CANCELED);
					orderDAO.update(savedOrder);
				}
			}
			
			// get orders that can be canceled
			cancelableOrders = new ArrayList<>();
			List<OrderBean> allOrders = orderDAO.getAll();
			for (OrderBean order: allOrders) {
				if (order.getStatus() != OrderStatus.CANCELED) {
					cancelableOrders.add(order);
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "CancelOrder");
			jspMsg = "Exception occured. See console for details";
		}
		
		// set attributes to be used by the jsp for presentation
		if (cancelableOrders != null) {
			request.setAttribute("orders", cancelableOrders);
		} else {
			request.setAttribute("msg", jspMsg);
		}
		
		String jspURL = "/cancelOrder.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	

}
