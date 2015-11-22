<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Kitchen Terminal</title>
	<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui.css">
	<script>
		$(function() {
    		$("#accordion").accordion();
		});
	</script>
</head>
<body>
	<div class="component">
	<h1>Active Orders</h1>
<%
	List<OrderBean> orders = (List<OrderBean>) request.getAttribute("orders");
	if (orders == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
	} else {
		if (orders.size() > 0) {
%>
			<div id="accordion">
<%
			int i =0;
			for (OrderBean order: orders) {
				long id = order.getId();
				String[] timestamp = order.getTimestamp().toLocalDateTime().toString().split("T");
				String dateTime = String.format("%s &nbsp;&nbsp; %s", timestamp[0], timestamp[1]);
				String type = order.getType().toString().toLowerCase();
				List<OrderItemBean> orderItems = order.getItems();
				
%>
				<h3 id="h-<%=i%>"> Order(<%=id%>) </h3>
				<div id="div-<%=i++%>">
					<table>
						<thead> <tr> <th> <input type="hidden" name="orderId" value="<%=id%>"> </th> <th> </tr> </thead>
						<tbody>
							<tr> <td> Placed On </td> <td> <%=dateTime%> </td></tr>
<%							for (OrderItemBean oItem: orderItems) {
%>								<tr> <td> <%= oItem.getMenuItem().getName()%> </td> <td> <%= oItem.getQuantity() %> </td></tr>
<%							}
%>						</tbody>
					</table>
				</div> 
<%			}
%>			</div>
<%		}
	}
%>
	</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/kitchen.js"></script>
		<script>
			var kitchenOrdersCount = <%= orders.size() %>;
			var curDivId = 0;
		</script>
</body>
</html>