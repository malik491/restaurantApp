/**
 * 
 */
package edu.depaul.se491.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

/**
 * Create a new order
 * ajax: expects order details in json format
 * regular (html/jsp): not supported yet
 * @author Malik
 *
 */
@WebServlet("/order/create")
public class Create extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		boolean isAjax = reqType != null && reqType.equals(AJAX_REQUEST);
		boolean added = false;
		
		try {
			
			if (isAjax) {
				/* ajax request */
				OrderBean order = getNewOrder(request);
				added = order != null? orderDAO.add(order) : false;				
				
			} else {
				/* none ajax (regular) requests not supported for now */				
			}
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/Create");
		}
		
		if (isAjax) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonResponse = String.format("{\"added\": %b}", added);
			out.print(jsonResponse);
			out.flush();
		} else {
			// jsp for none ajax
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	private OrderBean getNewOrder(HttpServletRequest request) throws IllegalArgumentException, SQLException {
		OrderBean order = null;
		
		// js for javascript
		String jsOrderItems = request.getParameter("orderItems");
		JSONOrderItem[] jsonOrderItemsList = null;
		
		jsOrderItems = jsOrderItems == null? null : (jsOrderItems = jsOrderItems.trim()).isEmpty()? null : jsOrderItems;
		
		if (jsOrderItems != null) {
			
			jsonOrderItemsList = new Gson().fromJson(jsOrderItems, JSONOrderItem[].class);
			
			if (jsonOrderItemsList.length > 0) {
				List<OrderItemBean> orderItems = new ArrayList<OrderItemBean>();
				Menu menu = new Menu(menuItemDAO.getAll());
				double orderTotal = 0;
				
				for(JSONOrderItem joi :jsonOrderItemsList) {
					MenuItemBean mItem = menu.getItem(joi.menuItemId);
					if (mItem == null)
						throw new IllegalArgumentException("Invalid menuItem ID (" + joi.menuItemId + ")");
					if (joi.quantity < 1)
						throw new IllegalArgumentException("Invalid orderItem quantity (value" + joi.quantity + ")");
					
					OrderItemBean oItem = new OrderItemBean();
					oItem.setMenuItem(mItem);
					oItem.setQuantity(joi.quantity);
					
					orderItems.add(oItem);
					orderTotal += (mItem.getPrice() * oItem.getQuantity());
				}
					
				order = new OrderBean();
				order.setItems(orderItems);
				order.setStatus(OrderStatus.SUBMITTED);
				order.setType(OrderType.PICKUP);
				order.setTotal(orderTotal);
				order.setConfirmation(ConfirmationUtil.getConfirmation());
			}
		}
		return order;
	}
	
	
	/**
	 * Temp class to hold data for a js object
	 * similar to OrderItemBean but with primitive datatype
	 * purpose: GSon serialization
	 */
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
	private static final String AJAX_REQUEST = "ajax";

}
