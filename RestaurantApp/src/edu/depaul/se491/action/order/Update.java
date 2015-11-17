package edu.depaul.se491.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.bean.OrderItemBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.DBLabels;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;
import edu.depaul.se491.util.Values;

/**
 * updates an existing order
 * ajax: updates the order status for a submitted order to "prepared"
 * regular (html/jsp): updates other order fields
 * 
 * @author Malik
 */
@WebServlet("/order/update")
public class Update extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderIdParam = ParametersUtil.validateString(request.getParameter("orderId"));
		String requestTypeParam = ParametersUtil.validateString(request.getParameter("requestType"));
		String firstUpdateParam = ParametersUtil.validateString(request.getParameter("firstUpdate"));

		Long orderId = ParametersUtil.parseLong(orderIdParam);
		boolean isAjax = ParametersUtil.isAjaxRequest(requestTypeParam);
		boolean isFirstUpdate = (!isAjax && ParametersUtil.isFirstUpdate(firstUpdateParam));
		
		OrderBean updatedOrder = null;
		String msg = "Cannot update order. Invalid Or missing order ID parameters.";
		boolean updated = false;

		try {
			if (orderId != null) {
				msg = String.format("Cannot update order. No order with id (%d)", orderId);
				
				OrderBean order = orderDAO.get(orderId);
				if (order != null) {
					boolean validUpdateParams = false;
					
					if (isFirstUpdate) {
						// first time visiting the update page; no update parameters to process
						updatedOrder = order;
						msg = null;
					} else if (isAjax) {
						// handle status update from kitchen terminal
						msg = String.format("Cannot update order. Order status: Expected (SUBMITTED) Found (%s).", order.getStatus().toString());
						validUpdateParams = processAjaxParameters(request, order);
					} else {
						// expect order update parameters
						msg = "Cannot update order. Invalid parameters.";
						validUpdateParams = processParameters(request, order);
					}
					
					if (validUpdateParams) {
						updated = orderDAO.update(order);
						msg = updated? "Successfuly updated the order" : "Failed to update order.";
						
						// either way (updated or not) grab a fresh copy from the next JSP update
						if (!isAjax)
							updatedOrder = orderDAO.get(orderId);
					}
				}
			}

		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/Update");
			msg = "Exception Occured. See Console for Details.";
		}

		if (isAjax) {
			/* write response to client using json */
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonResponse = String.format("{\"updated\": %b, \"message\":\"%s\"}", updated, msg);
			out.print(jsonResponse);
			out.flush();
		} else {
			request.setAttribute("order", updatedOrder);
			request.setAttribute("msg", msg);

			String jspURL = "/order/update.jsp";
			getServletContext().getRequestDispatcher(jspURL).forward(request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	private boolean processAjaxParameters(HttpServletRequest request, OrderBean order) {
		boolean validParam = false;
		
		if (order.getStatus() == OrderStatus.SUBMITTED) {
			order.setStatus(OrderStatus.PREPARED);
			validParam = true;
		}
		return validParam;
	}
	
	
	private boolean processParameters(HttpServletRequest request, OrderBean order) {
		String statusParam = ParametersUtil.validateString(request.getParameter("status"));
		String typeParam = ParametersUtil.validateString(request.getParameter("type"));

		// at least status or type param is included in the request
		boolean hasStatus = statusParam != null;
		boolean hasType = typeParam != null;
		
		boolean hasValidParams = (hasStatus && hasType);
		
		// process order status
		if (hasValidParams) {
			OrderStatus status = OrderStatus.valueOf(statusParam.toUpperCase());
			hasValidParams = status != null;
			if (hasValidParams)
				order.setStatus(status);
		}

		// process order type
		if (hasValidParams) {
			OrderType type = OrderType.valueOf(typeParam.toUpperCase());
			hasValidParams = type != null;
			if (hasValidParams) {
				if (type == OrderType.PICKUP) {
					// process pickup
					order.setType(type);
					order.setDeliveryAddress(null);
				} else {
					// process delivery address
					hasValidParams = processAddressParameters(request, order);
					if (hasValidParams)
						order.setType(OrderType.DELIVERY);
				}
			}
		}
		
		// process order items
		if (hasValidParams) {
			hasValidParams = processOrderItemsParameters(request, order);
		}
		
		return hasValidParams;
	}

	private boolean processAddressParameters(HttpServletRequest request, OrderBean order) {
		String addrIdParam = ParametersUtil.validateString(request.getParameter("addrId"));
		String line1Param = ParametersUtil.validateString(request.getParameter("addrLine1"));
		String line2Param = ParametersUtil.validateString(request.getParameter("addrLine2"));
		String cityParam = ParametersUtil.validateString(request.getParameter("addrCity"));
		String stateParam = ParametersUtil.validateString(request.getParameter("addrState"));
		String zipcodeParam = ParametersUtil.validateString(request.getParameter("addrZipcode"));

		// addrId and line2Param are optional
		boolean validParams = line1Param != null && cityParam != null && stateParam != null && zipcodeParam != null;

		validParams &= ParametersUtil.validateLengths(new String[] {line1Param, cityParam, zipcodeParam}, new int[] {
				DBLabels.ADDRS_LINE1_LEN_MAX, DBLabels.ADDRS_CITY_LEN_MAX, DBLabels.ADDRS_ZIPCODE_LEN_MAX });
		
		if (validParams && line2Param != null)
			validParams = ParametersUtil.validateLength(line2Param, DBLabels.ADDRS_LINE2_LEN_MAX);
		
		if (!validParams)
			return false;

		State state = State.valueOf(stateParam.toUpperCase());
		validParams = state != null;

		if (!validParams)
			return false;

		Long addrId = addrIdParam != null? ParametersUtil.parseLong(addrIdParam): null;

		AddressBean newAddr = new AddressBean();
		newAddr.setId(addrId != null? addrId : Values.UNKNOWN);
		newAddr.setLine1(line1Param);
		newAddr.setLine2(line2Param != null? line2Param : Values.EMPTY_STRING);		
		newAddr.setCity(cityParam);
		newAddr.setState(state);
		newAddr.setZipcode(zipcodeParam);
		
		order.setDeliveryAddress(newAddr);
		
		return validParams;
	}

	private boolean processOrderItemsParameters(HttpServletRequest request, OrderBean order) {
		List<OrderItemBean> oItems = order.getItems();

		boolean validParams = true;
		boolean allZeroQty = true;
		double newTotal = 0;
		
		for (OrderItemBean oItem :oItems) {
			String id = Long.toString(oItem.getMenuItem().getId());
			
			// parameters for this order item
			String quantityParam = ParametersUtil.validateString(request.getParameter("mItemQty-" + id));
			Integer quantity = ParametersUtil.parseInt(quantityParam);
			
			validParams = quantity != null && quantity > -1 && quantity <= DBLabels.ORDER_ITEM_QUANTITY_MAX;
			if (!validParams)
				break;
			
			oItem.setQuantity(quantity);
			newTotal += quantity * oItem.getMenuItem().getPrice();

			allZeroQty &= quantity == 0;  
		}
		
		// at least on updated order item quantity must be > 0
		// (note: all orders will have at least one order item)
		if (validParams && !allZeroQty && Double.compare(newTotal, DBLabels.ORDER_TOTAL_MAX) < 1)
			order.setTotal(newTotal);
		else
			validParams = false;
		
		return validParams;
	}
}
