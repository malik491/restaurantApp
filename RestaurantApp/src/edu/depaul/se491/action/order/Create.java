/**
 * 
 */
package edu.depaul.se491.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.ConfirmationUtil;
import edu.depaul.se491.util.DBLabels;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;
import edu.depaul.se491.util.Values;

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
		String requestTypeParam = ParametersUtil.validateString(request.getParameter("requestType"));
		boolean isAjax = ParametersUtil.isAjaxRequest(requestTypeParam);
		
		String msg = "Cannot create a new order. Invalid parameters";
		boolean added = false;
		
		try {
			OrderBean order = getOrder(request, isAjax);
			if (order != null) {
				added = orderDAO.add(order);
				msg = added? "Successfuly created a new order" : "Failed to create a new order";
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/Create");
			msg = "Exception Occured. See console for Details.";
		}
		
		if (isAjax) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonResponse = String.format("{\"added\": %b, \"message\":\"%s\"}", added, msg);
			out.print(jsonResponse);
			out.flush();
		} else {
			request.setAttribute("msg", msg);
			
			String jspURL = "/order/create.jsp";
			getServletContext().getRequestDispatcher(jspURL).forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	private OrderBean getOrder(HttpServletRequest request, boolean isAjax) {
		OrderBean newOrder = new OrderBean();
		
		boolean validParams = isAjax? processAjaxParameters(request, newOrder) : processParameters(request, newOrder);
		if (!validParams)
			newOrder = null;
		
		return newOrder;
	}
	
	private boolean processParameters(HttpServletRequest request, OrderBean order) {
		String statusParam = ParametersUtil.validateString(request.getParameter("status"));
		String typeParam = ParametersUtil.validateString(request.getParameter("type"));

		boolean validParams = (statusParam != null && typeParam != null);
		
		// process order status
		if (validParams) {
			OrderStatus status = OrderStatus.valueOf(statusParam.toUpperCase());
			validParams = status != null;
			if (validParams)
				order.setStatus(status);
		}

		// process order type
		if (validParams) {
			OrderType type = OrderType.valueOf(typeParam.toUpperCase());
			validParams = type != null;
			if (validParams) {
				if (type == OrderType.PICKUP) {
					// process pickup
					order.setType(type);
					order.setDeliveryAddress(null);
				} else {
					// process delivery address
					validParams = processAddressParameters(request, order);
					if (validParams)
						order.setType(OrderType.DELIVERY);
				}
			}
		}
		
		// process order items
		if (validParams)
			validParams = processOrderItemsParameters(request, order);
		
		// finally set order confirmation if all params are valid
		if (validParams)
			order.setConfirmation(ConfirmationUtil.getConfirmation());
		
		return validParams;
	}
	
	private boolean processAddressParameters(HttpServletRequest request, OrderBean order) {
		String line1Param = ParametersUtil.validateString(request.getParameter("addrLine1"));
		String line2Param = ParametersUtil.validateString(request.getParameter("addrLine2"));
		String cityParam = ParametersUtil.validateString(request.getParameter("addrCity"));
		String stateParam = ParametersUtil.validateString(request.getParameter("addrState"));
		String zipcodeParam = ParametersUtil.validateString(request.getParameter("addrZipcode"));

		//  address line2 is optional
		boolean validParams = line1Param != null && cityParam != null && stateParam != null && zipcodeParam != null;

		validParams &= ParametersUtil.validateLengths(new String[] {line1Param, cityParam, zipcodeParam}, new int[] {
				DBLabels.ADDRS_LINE1_LEN_MAX, DBLabels.ADDRS_CITY_LEN_MAX, DBLabels.ADDRS_ZIPCODE_LEN_MAX });
		
		if (validParams && line2Param != null)
			validParams = ParametersUtil.validateLength(line2Param, DBLabels.ADDRS_LINE2_LEN_MAX);
		
		if (!validParams)
			return false;
		
		State state = State.valueOf(stateParam.toUpperCase());
		validParams = state != null;			

		if (validParams) {
			AddressBean newAddr = new AddressBean();
			newAddr.setLine1(line1Param);
			newAddr.setLine2(line2Param != null? line2Param : Values.EMPTY_STRING);		
			newAddr.setCity(cityParam);
			newAddr.setState(state);
			newAddr.setZipcode(zipcodeParam);
			
			order.setDeliveryAddress(newAddr);			
		}
		
		return validParams;
	}
	
	private boolean processOrderItemsParameters(HttpServletRequest request, OrderBean order) {
		List<OrderItemBean> orderItems = new ArrayList<OrderItemBean>();
		
		boolean validParams = false;
		double total = 0;
		Menu menu = getCurrentMenu();
		
		for (MenuItemBean mItem: menu.menuItems) {
			long id = mItem.getId();
			String quantityParam = ParametersUtil.validateString(request.getParameter("mItemQty-" + id));
			Integer quantity = ParametersUtil.parseInt(quantityParam);
			
			validParams = (quantity != null && quantity >= 0 && quantity <= DBLabels.ORDER_ITEM_QUANTITY_MAX);
			if (!validParams)
				break;
			
			if (quantity > 0) {
				total += (mItem.getPrice() * quantity);
				OrderItemBean newOrderItem = new OrderItemBean();
				newOrderItem.setMenuItem(mItem);
				newOrderItem.setQuantity(quantity);
				orderItems.add(newOrderItem);				
			}
		}
		
		validParams &= orderItems.size() > 0;
		validParams &= Double.compare(total, DBLabels.ORDER_TOTAL_MAX) < 1;
		
		if (validParams) {
			order.setTotal(total);
			order.setItems(orderItems);
		}

		return validParams;
	}
	
	
	private boolean processAjaxParameters(HttpServletRequest request, OrderBean order) {
		String orderItemsListParam = ParametersUtil.validateString(request.getParameter("orderItemsList"));
		
		boolean validParams = (orderItemsListParam != null);
		if (!validParams)
			return validParams;
		
		// use Google JSON parser to parse a list of JSON-formated order item objects
		OrderItemBean[] orderItems = null;
		try {
			orderItems = new Gson().fromJson(orderItemsListParam, OrderItemBean[].class);
		} catch (JsonSyntaxException e) {
			ExceptionUtil.printException(e, "order/Create.processAjaxParameters(). Gson parsing");
		}
		
		validParams = (orderItems != null && orderItems.length > 0);
		
		double total = 0;
		if (validParams) {
			Menu menu = getCurrentMenu();
			for(OrderItemBean oItem :orderItems) {
				MenuItemBean requestMenuItem = oItem.getMenuItem();
				MenuItemBean dbMenuItem = menu.getItem(requestMenuItem.getId());
				int quantity = oItem.getQuantity();
				
				if (dbMenuItem == null || quantity < 1 || quantity > DBLabels.ORDER_ITEM_QUANTITY_MAX)
					validParams = false;
				
				// compare prices (menu item price can change when it is updated by a manager at any time)
				if (validParams)
					validParams = Double.compare(dbMenuItem.getPrice(), requestMenuItem.getPrice()) == 0;
				
				if (!validParams)
					break;
				
				total += (dbMenuItem.getPrice() * quantity);
			}
		}
		validParams &= Double.compare(total, DBLabels.ORDER_TOTAL_MAX) < 1;
		
		if (validParams) {
			order.setStatus(OrderStatus.SUBMITTED);
			order.setType(OrderType.PICKUP);
			order.setTotal(total);
			order.setConfirmation(ConfirmationUtil.getConfirmation());
			order.setItems(Arrays.asList(orderItems));
		}
		
		return validParams;
	}
	
	private Menu getCurrentMenu() {
		Menu menu = null;
		try {
			menu = new Menu(menuItemDAO.getAll());	
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "order/Create.GetCurrentMenu()");
		}
		return menu;
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
