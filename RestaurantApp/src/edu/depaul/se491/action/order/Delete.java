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
 * Delete an existing order
 * @author Malik
 *
 */
@WebServlet("/order/delete")
public class Delete extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderIdParam = request.getParameter("orderId");
		Long orderId = ParametersUtil.parseLong(orderIdParam);
		
		String jspMsg = "Cannot delete order. Invalid or missing Order ID";
		
		try {
			if (orderId != null) {
				jspMsg = String.format("Cannot delete order. No order with ID (%d)", orderId);
				
				OrderBean order = orderDAO.get(orderId);
				if (order != null) {
					boolean deleted = orderDAO.delete(order);
					jspMsg = deleted? "Successfully deleted order" : "Failed to delete order";
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/Delete");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/order/delete.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
