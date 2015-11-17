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
	<div class="component">
	<h1>View Order </h1>
<%
	OrderBean order = (OrderBean) request.getAttribute("order");
	if (order == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
	} else {
		long orderId = order.getId();
		String status = order.getStatus().toString().toLowerCase();
		String type = order.getType().toString().toLowerCase();
		String total = String.format("&#36;%.2f",order.getTotal()); //&#36; html for $
		String confirmation = order.getConfirmation();
		String datetime = order.getTimestamp().toLocalDateTime().toString();
		AddressBean addr = order.getDeliveryAddress();
		String address = addr == null? "None" : String.format("%s,<br> %s.<br> %s, %s %s", 
				addr.getLine1(), addr.getLine2(), addr.getCity(), addr.getState().toString(), addr.getZipcode());
%>
		<table> 
			<thead> <tr> <th> Order </th> <th> </th> </tr> </thead>
			<tbody>
			<tr> <td> ID : </td> <td> <%= orderId %> </td></tr>
			<tr> <td> Status : </td> <td> <%= status %> </td></tr>
			<tr> <td> Type : </td> <td> <%= type %> </td></tr>
			<tr> <td> Total : </td> <td><%= total %> </td></tr>
			<tr> <td> Confirmation : </td> <td> <%= confirmation %> </td></tr>
			<tr> <td> DateTime </td> <td> <%= datetime %> </td></tr>
			<tr> <td> Delivery Address </td> <td> <%= address %></td></tr>
		</tbody> </table>
		
		<h2> Items</h2>
		<table>
			<thead><tr>	<th> Menu Item Name </th><th> Quantity </th><th> Unit Price </th></tr></thead>
			<tbody>
<%
		List<OrderItemBean> orderItems = order.getItems();
		for (OrderItemBean oItem: orderItems) {
			MenuItemBean menuItem = oItem.getMenuItem();
			String name = menuItem.getName();
			String price = String.format("&#36;%.2f", menuItem.getPrice()); //&#36; html for $
			int qunatity = oItem.getQuantity();
%>			<!--  print MENU ITEMS for this order -->
			<tr>
				<td><%=name%></td><td><%=qunatity%></td><td><%=price%></td>
			</tr>
<%		}
%>
		</tbody></table>
<%	}
%>
	</div>

</body>
</html>