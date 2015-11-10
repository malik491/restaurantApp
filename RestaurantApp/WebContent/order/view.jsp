<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.depaul.se491.bean.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>View Order</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
<%
	OrderBean order = (OrderBean) request.getAttribute("order");
	if (order == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h2><%=msg%></h2> <%
		} else {
			getServletContext().getRequestDispatcher("/index.html").forward(request, response);
			return;
		}
	} else {
		long orderId = order.getId();
		String status = order.getStatus().toString().toLowerCase();
		String type = order.getType().toString().toLowerCase();
		String total = String.format("%.2f",order.getTotal());
		String confirmation = order.getConfirmation();
		String datetime = order.getTimestamp().toLocalDateTime().toString();
		AddressBean addr = order.getDeliveryAddress();
		String address = addr == null? "None" : String.format("%s,<br> %s.<br> %s, %s %s", 
				addr.getLine1(), addr.getLine2(), addr.getCity(), addr.getState().toString(), addr.getZipcode());
%>
		<div class="component">
		<div class="component">
			<h2>Order Information</h2>
			<table> 
				<thead> <th> Field </th> <th> Value </th> </thead>
				<tbody>
				<tr> <td> ID : </td> <td> <%= orderId %> </td></tr>
				<tr> <td> Status : </td> <td> <%= status %> </td></tr>
				<tr> <td> Type : </td> <td> <%= type %> </td></tr>
				<tr> <td> Total : </td> <td> &#36;<%= total %> </td></tr>
				<tr> <td> Confirmation : </td> <td> <%= confirmation %> </td></tr>
				<tr> <td> DateTime </td> <td> <%= datetime %> </td></tr>
				<tr> <td> Delivery Address </td> <td> <%= address %></td></tr>
			</tbody> </table>
		</div>
		<div class="component">
			<h2>Order Details</h2>
			<table>
			<thead><tr><th> Menu Item Id </th>	<th> Menu Item Name </th><th> Quantity </th><th> Unit Price </th></tr></thead>
			<tbody>
<%
		List<OrderItemBean> orderItems = order.getItems();
		for (OrderItemBean oItem: orderItems) {
			MenuItemBean menuItem = oItem.getMenuItem();
			long mItemId = menuItem.getId(); 
			String name = menuItem.getName();
			String price = String.format("%.2f", menuItem.getPrice());
			int qunatity = oItem.getQuantity();
%>
				<!--  print MENU ITEMS for this order -->
				<tr>
					<td><%=mItemId%></td><td><%=name%></td><td><%=qunatity%></td><td>&#36;<%=price%></td>
				</tr>
<%		}
%>
		</tbody></table>
		</div>
		</div>
		
<%	}
%>

</body>
</html>