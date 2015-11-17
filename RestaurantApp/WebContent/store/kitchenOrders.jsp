<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Kitchen Terminal</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">

<script>
$(function() {
    $( "#accordion" ).accordion();
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
			OrderBean topOrder = orders.get(0);
%>
			<div id="accordion">
<%
				int i = 1;
				for (OrderBean order: orders) {
					long id = order.getId();
					String[] timestamp = order.getTimestamp().toLocalDateTime().toString().split("T");
					String dateTime = String.format("%s &nbsp;&nbsp; %s", timestamp[0], timestamp[1]);
					String type = order.getType().toString().toLowerCase();
					if (i == 0) {
						
					}
%>
					<h3 id="orderHeader-<%=i%>"> <%=id%> </h3>
					<div id="orderDiv-<%=i++%>">
						<input type="hidden" name="orderId" value="<%=id%>">
						 <p> <%= id %>  &nbsp; <%= type %> &nbsp; <%= dateTime %> </p> 
					</div> 
<%				}
%>
			</div>
<%		}
	}
%>
	</div>
	<script type="text/javascript">
	var counter = 1;
	window.onkeyup = function changeStatus(e){
		if (e.keyCode === 32) {
			// if spacebar
			var orderDiv = document.getElementById('orderDiv-'+ counter);
			var orderHeader = document.getElementById('orderHeader-' + counter);
			counter++;
			if (orderDiv) {
				var orderId = orderDiv.getElementsByTagName('input')[0].value;
				console.log(orderDiv);
				console.log(orderId);
				
				var parent = orderDiv.parentNode;
				orderDiv.setAttribute("aria-selected", "false");
				orderDiv.setAttribute("aria-expanded", "false");
				parent.removeChild(orderHeader);
				parent.removeChild(orderDiv);
				
				if (counter < <%=orders.size()%>) {
					var nextDiv = document.getElementById('orderDiv-'+ counter);
					nextDiv.setAttribute("aria-selected", "true");
					nextDiv.setAttribute("aria-expanded", "true");
					nextDiv.setAttribute("style", "clear:both;");
				}
				
			}
		}
	}
	</script>
</body>
</html>