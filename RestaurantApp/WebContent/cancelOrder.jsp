<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.*"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Cancel Order</title>
	<link rel="stylesheet" type="text/css" href="css/component.css"/>
	<link rel="stylesheet" type="text/css" href="css/demo.css"/>
	<style type="text/css"></style>
</head>
<body>
<%
	List<OrderBean> orders = (List<OrderBean>) request.getAttribute("orders");
	if (orders == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// send to the home page
			getServletContext().getRequestDispatcher("/index.html").forward(request, response);			
		}
	} else {
%>
		<div class="component">
		<h1> Cancel Orders </h1>
<%
		
		for (OrderBean order: orders) {
			String orderId = Long.toString(order.getId()); 
			String confirmation = order.getConfirmation();
%>	
			<div class="component">
			<h3> Order (ID: <%=orderId%> Confirmation: <%=confirmation%>)</h3>
			
			<!--  cancel form (cancelOrder servlet is called (passing it orderId) when the button is clicked -->
			<form action="order/update">
				<input type="hidden" name="orderId" value="<%=orderId%>">
				<input type="submit" value="update Order">
			</form>
			
			<!-- print order details -->
			<table>
				<thead><tr><th> Menu Item Id </th>	<th> Menu Item Name </th><th> Quantity </th><th> Unit Price </th></tr></thead>
				<tbody>
<%
			for (OrderItemBean oItem: order.getItems()) {
				MenuItemBean mItem = oItem.getMenuItem();
				String mItemId = Long.toString(mItem.getId());
				String mItemName = mItem.getName();
				String unitPrice = String.format("%.2f", mItem.getPrice());
				int qunatity = oItem.getQuantity();
%>
				<!--  print MENU ITEMS for this order -->
				<tr>
					<td><%=mItemId%></td><td><%=mItemName%></td><td><%=qunatity%></td><td>&#36;<%=unitPrice%></td>
				</tr>
<%	 		}	
%>
				</tbody>
			</table></div>
<%		} 
%>
	  	</div>
<%	}
%>
</body>
</html>