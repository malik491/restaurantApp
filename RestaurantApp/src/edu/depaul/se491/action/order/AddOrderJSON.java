package edu.depaul.se491.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.util.ConfirmationUtil;
import edu.depaul.se491.util.ExceptionUtil;

@WebServlet("/addOrderJSON")
public class AddOrderJSON extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderData = request.getParameter("orderItems");
		String isAjax = request.getParameter("ajax");
		JSONOrderItem[] jsonOrderItems = null;
		boolean added = false;

		if (orderData != null) {
			try {
				jsonOrderItems = new Gson().fromJson(orderData, JSONOrderItem[].class);
				if (jsonOrderItems.length > 0) {
					Menu menu = new Menu(menuItemDAO.getAll());
					
					List<OrderItemBean> oItems = new ArrayList<OrderItemBean>();
					double orderTotal = 0;
					for(JSONOrderItem joi :jsonOrderItems) {
						MenuItemBean mItem = menu.getItem(joi.menuItemId);
						if (mItem == null)
							throw new IllegalArgumentException("Invalid menuItem ID (" + joi.menuItemId + ")");
						if (joi.quantity < 1)
							throw new IllegalArgumentException("Invalid orderItem quantity (value" + joi.quantity + ")");
						
						OrderItemBean oItem = new OrderItemBean();
						oItem.setMenuItem(mItem);
						oItem.setQuantity(joi.quantity);
						
						oItems.add(oItem);
						orderTotal += (mItem.getPrice() * oItem.getQuantity());
					}
					
					OrderBean order = new OrderBean();
					order.setItems(oItems);
					order.setStatus(OrderStatus.SUBMITTED);
					order.setType(OrderType.PICKUP);
					order.setTotal(orderTotal);
					order.setConfirmation(ConfirmationUtil.getConfirmation());
					// order datetime is set by the database by default
					
					added = orderDAO.add(order);
				}
			} catch (Exception e) {
				ExceptionUtil.printException(e, "AddOrderJSON");
			}
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String jsonResponse = String.format("{\"added\": %b}", added);
		out.print(jsonResponse);
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private class JSONOrderItem {
		long menuItemId;
		int quantity;
	}
	
	private class Menu {
		List<MenuItemBean> menuItems;
		
		Menu(List<MenuItemBean> menuItems) {
			this.menuItems = menuItems;
		}
		
		MenuItemBean getItem(long itemId) {
			for(MenuItemBean mItem: menuItems) {
				if (mItem.getId() == itemId)
					return mItem;
			}
			return null;
		}
		
	}
}
