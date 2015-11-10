<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Orders</title>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>

<body>
	<br>
<%
	List<OrderBean> orders = (List<OrderBean>) request.getAttribute("orders");
	if (orders == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// send to the home page servlet
			getServletContext().getRequestDispatcher("/index.html").forward(request, response);
			return;
		}
	} else {
%>
	  <div class="component">
		
		<div class="component">
		<h1>Orders</h1>
		<p>list all orders in the database</p>
		<table>
		<thead><tr><th> ID </th><th> Type </th><th> Status </th><th> View </th><th> Update </th><th> Delete </th></tr></thead>
		<tbody>	
<%	
		for (OrderBean order: orders) {
			String orderId = Long.toString(order.getId());
			String type = order.getType().toString();
			String status = order.getStatus().toString();
%>
			<!--  print row for this order -->
			<tr>
				<td><%=orderId%></td>
				<td><%=type%></td>	
				<td><%=status%></td>
				<td>
					<form action="${pageContext.request.contextPath}/order/view" method="POST">
					<input type="hidden" name="orderId" value="<%=orderId%>">
					<input type="submit" value="View">
					</form>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/order/update" method="POST">
					<input type="hidden" name="orderId" value="<%=orderId%>">
					<input type="submit" value="Update">
					</form>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/order/delete" method="POST">
					<input type="hidden" name="orderId" value="<%=orderId%>">
					<input type="submit" value="Delete">
					</form>
				</td>								
			</tr>
<% 		}
%>
		</tbody>
		</table></div>
	  </div>
<%	}
%>
</body>
</html>