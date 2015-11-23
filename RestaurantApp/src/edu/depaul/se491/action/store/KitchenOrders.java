/**
 * 
 */
package edu.depaul.se491.action.store;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;

/**
 * Kitchen terminal
 * In-kitchen store employee will use this to update order status to prepared
 * @author Malik
 */
@WebServlet("/store/kitchenOrders")
public class KitchenOrders extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestTypeParam = ParametersUtil.validateString(request.getParameter("requestType"));
		boolean isAjax = ParametersUtil.isAjaxRequest(requestTypeParam);
		
		List<OrderBean> orders = null;
		String msg = null;
		try {
			// DB will sort orders by their order_timestamp in ascending order.
			orders = orderDAO.getAllWithStatus(OrderStatus.SUBMITTED);
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "store/KitchenOrders");
			msg = "DB Exception Occured. See Console for Details.";
		}
		
		if (isAjax) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonResponse = getAjaxResponse(orders);
			out.print(jsonResponse);
			out.flush();
		} else {
			request.setAttribute("orders", orders);
			request.setAttribute("msg", msg);
			
			String jspURL = "/store/kitchenOrders.jsp";
			getServletContext().getRequestDispatcher(jspURL).forward(request, response);			
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	} 
	
	private String getAjaxResponse(List<OrderBean> orders) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"orders\": [");
		
		int size = orders.size();
		int counter = 0;
		for (OrderBean order: orders) {
			long id = order.getId();
			String[] timestamp = order.getTimestamp().toLocalDateTime().toString().split("T");
			String dateTime = String.format("%s &nbsp;&nbsp; %s", timestamp[0], timestamp[1]);
			String orderItems = new Gson().toJson(order.getItems());
			String orderObj = String.format("{ \"orderId\": %d, \"dateTime\": \"%s\", \"orderItems\": %s }", id, dateTime, orderItems);
			
			sb.append(orderObj);
			if (counter + 1 < size)
				sb.append(",");
			counter++;
		}
		sb.append(" ]}");
		return sb.toString();
	}
}
