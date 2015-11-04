<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Orders</title>
	<link rel="stylesheet" type="text/css" href="css/component.css"/>
	<link rel="stylesheet" type="text/css" href="css/demo.css"/>
</head>

<body>
	<br>
	
	<%
	List<OrderBean> orders = (List<OrderBean>) request.getAttribute("orders");
	if (orders == null) {
		// send to the home page servlet
		getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	} else {
		//
	%>
	<div class="component">
		<div class="component">
			<h1>Orders</h1>
			<p>list all orders in the database</p>
			<table>
				<thead>
					<tr>
						<th> ID </th>
						<th> Date </th>
						<th> Status </th>
						<th> Type </th>
						<th> Total </th>
						<th> Delivery Address </th>
					</tr>
				</thead>
				<tbody>

		<%	
		for (OrderBean order: orders) {
			String id = Long.toString(order.getId());
			String status = order.getStatus().toString();
			String type = order.getType().toString();
			String data = order.getTimestamp().toString();
			String total = String.format("%.2f", order.getTotal());
			AddressBean addr = order.getDeliveryAddress();
			String address = addr == null? "" : 
				String.format("%s,<br> %s.<br> %s, %s %s", addr.getLine1(), addr.getLine2(), addr.getCity(), 
						addr.getState().toString().toLowerCase(), addr.getZipcode());
			%>
			<!--  print row for this order -->
			<tr>
				<td><%=id%></td> 
				<td><%=data%></td>
				<td><%=status%></td>
				<td><%=type%></td>
				<td>&#36;<%=total%></td>
				<td><%=address%></td>
			</tr>
			<% 
		}
		%>
		</tbody>
		</table>
		</div>
		
		<%
		for (OrderBean order: orders) 
		{
		%>	
			<div class="component">
				<h2>Order Detail <%=String.format("(ID:%d)", order.getId())%></h2>
				<table>
					<thead>
						<tr>
							<th> Menu Item Id </th>
							<th> Menu Item Name </th>
							<th> Quantity </th>
							<th> Unit Price </th>
						</tr>
					</thead>
				<tbody>
		<%	
			for (OrderItemBean oItem: order.getItems()) {
				MenuItemBean mItem = oItem.getMenuItem();
				String mItemId = Long.toString(mItem.getId());
				String mItemName = mItem.getName();
				String unitPrice = String.format("%.2f", mItem.getPrice());
				int qunatity = oItem.getQuantity();
		%>
				
				<tr>
					<td><%=mItemId%></td> 
					<td><%=mItemName%></td>
					<td><%=qunatity%></td>
					<td>&#36;<%=unitPrice%></td>
				</tr>
		<% 
			}
		%>
				</tbody>
				</table>
			</div>
		<%
		}
		%>
	<%
	}
	%>
	</div>
</body>
</html>