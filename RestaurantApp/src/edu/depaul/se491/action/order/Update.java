package edu.depaul.se491.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.ExceptionUtil;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		String reqType = request.getParameter("reqType");
		boolean isAjax = reqType != null && reqType.equals("ajax");

		OrderBean updatedOrder = null;
		String jspMsg = null;
		boolean updated = false;

		try {
			if (orderId == null) {
				jspMsg = "Cannot update order. Missing order ID.";
			} else {
				OrderBean oldOrder = orderDAO.get(Long.valueOf(orderId));
				if (oldOrder == null) {
					jspMsg = "Failed to find order.";
				} else {
					if (isAjax) {
						/* handle ajax request (coming from kitchen terminal)*/
						if (oldOrder.getStatus() == OrderStatus.SUBMITTED) {
							oldOrder.setStatus(OrderStatus.PREPARED);
							updated = orderDAO.update(oldOrder);
						}
					} else {
						boolean validParams = hasValidParameters(request, oldOrder);
						if (validParams) {
							updated = validParams ? orderDAO.update(oldOrder) : false;							
						}
						// get the updated order from DB (to make sure the updates are saved)
						updatedOrder = orderDAO.get(oldOrder.getId());
						jspMsg = updated? "Successfuly updated the order" : Values.EMPTY_STRING;
					}
				}
			}

		} catch (Exception e) {
			ExceptionUtil.printException(e, "order/Update");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		if (isAjax) {
			/* write response to client using json */
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String jsonResponse = String.format("{\"updated\": %b}", updated);
			out.print(jsonResponse);
			out.flush();
		} else {
			/* send to a jsp for html */
			request.setAttribute("order", updatedOrder);
			request.setAttribute("msg", jspMsg);

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

	private boolean hasValidParameters(HttpServletRequest request, OrderBean order) throws SQLException {
		String status = request.getParameter("status");
		String type = request.getParameter("type");

		// at least status or type param is included in the request
		// not null and not empty strings (strings are trimmed to remove extra
		// white spaces before checking for empty string)
		boolean validParams = (status != null && !(status = status.trim()).isEmpty())
				|| (type != null && !(type = type.trim()).isEmpty());

		if (validParams && status != null) {
			OrderStatus statusObj = OrderStatus.valueOf(status.toUpperCase());
			validParams = statusObj != null;
			if (validParams)
				order.setStatus(statusObj);
		}

		if (validParams && type != null) {
			OrderType typeObj = OrderType.valueOf(type.toUpperCase());
			validParams = typeObj != null;
			if (validParams) {
				if (typeObj == OrderType.PICKUP) {
					order.setType(typeObj);
					order.setDeliveryAddress(null);
				} else {
					// update order type only when delivery address data is
					// valid
					validParams = updateOrderObjAddress(request, order);
					if (validParams)
						order.setType(OrderType.DELIVERY);
				}
			}
		}
		return validParams;
	}

	/**
	 * 
	 * @param request
	 * @param order
	 *            order
	 * @return
	 * @throws SQLException
	 */
	private boolean updateOrderObjAddress(HttpServletRequest request, OrderBean order) throws SQLException {
		String addrLine1 = request.getParameter("addrLine1");
		String addrLine2 = request.getParameter("addrLine2");
		String addrCity = request.getParameter("addrCity");
		String addrState = request.getParameter("addrState");
		String addrZipcode = request.getParameter("addrZipcode");

		// not null and not empty strings (strings are trimmed to remove extra
		// white spaces before checking for empty string)
		boolean validParams = addrLine1 != null && !(addrLine1 = addrLine1.trim()).isEmpty() && addrCity != null
				&& !(addrCity = addrCity.trim()).isEmpty() && addrState != null
				&& !(addrState = addrState.trim()).isEmpty() && addrZipcode != null
				&& !(addrZipcode = addrZipcode.trim()).isEmpty();

		if (!validParams)
			return false;

		State state = State.valueOf(addrState.toUpperCase());
		validParams = state != null;

		if (!validParams)
			return false;

		AddressBean newAddr = new AddressBean();
		newAddr.setLine1(addrLine1);
		newAddr.setCity(addrCity);
		newAddr.setState(state);
		newAddr.setZipcode(addrZipcode);

		// line 2 optional and by default is empty string
		if (addrLine2 != null && !(addrLine2 = addrLine2.trim()).isEmpty())
			newAddr.setLine2(addrLine2);

		AddressBean oldAddr = order.getDeliveryAddress();
		boolean sameAsOldAddr = oldAddr != null ? oldAddr.equals(newAddr) : false;
		if (!sameAsOldAddr) {
			AddressBean searchMatchAddr = addressDAO.search(newAddr);
			if (searchMatchAddr != null) {
				order.setDeliveryAddress(searchMatchAddr);
			} else {
				// call addAndUpdateNewDeliveryAddress to add the new address to
				// the DB and then set its id
				// set order delivery address only of newAddr is added and its
				// id is set
				validParams = addAndUpdateNewDeliveryAddress(order, newAddr);
				if (validParams)
					order.setDeliveryAddress(newAddr);
			}
		}

		return validParams;
	}

	private boolean addAndUpdateNewDeliveryAddress(OrderBean order, AddressBean newAddr) throws SQLException {

		if (order.getDeliveryAddress() != null) {
			// todo: remove old address in db only if no other orders are
			// associated with that address too
		}

		long newAddrId = addressDAO.add(newAddr);
		boolean added = newAddrId != Values.UNKNOWN;
		if (added)
			newAddr.setId(newAddrId);
		return added;
	}

}
