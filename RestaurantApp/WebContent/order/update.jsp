<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.*" %>
<%@ page import="edu.depaul.se491.enums.*" %>

<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Update Order</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
<%
	OrderBean order = (OrderBean) request.getAttribute("order");
	String msg = (String) request.getAttribute("msg");
	if (order == null) {
		if (msg != null) {
%> 			<h2><%=msg%></h2> 
<%		} else {
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
		AddressBean address = order.getDeliveryAddress();
		String addrLine1 = address == null? "" : address.getLine1();
		String addrLine2 = address == null? "" : address.getLine2();
		String addrCity = address == null? "" : address.getCity();
		String addrZipcode = address == null? "" : address.getZipcode();
%>
		<div class="component">
			<h2>Update Order </h2>
<% 			if (msg != null) {
%>				<h4><%=msg%></h4>
<%			}
%>
			<Form id="updateForm" action="update" method="post">
				<input type="hidden" name="orderId" value="<%=orderId%>">				
				<label>Status </label>
				<select name="status" form="updateForm">
<% 					for(OrderStatus s: OrderStatus.values()) {
%>						 <option value="<%=s.toString()%>" <%if (s == order.getStatus()) { %> selected="selected" <%};%>> <%=s.toString().toLowerCase()%></option>
<%					}
%>				</select> <br>
				<label>Type </label>
				<select name="type" form="updateForm">
<% 					for(OrderType t: OrderType.values()) {
%>						 <option value="<%=t.toString()%>" <% if (t == order.getType()) { %> selected="selected" <%};%>> <%=t.toString().toLowerCase()%></option>
<%					}
%>				</select> <br>
				<label> Total : &#36;<%=total%> </label> <br>			
				<label> Confirmation : <%=confirmation%> </label> <br>		
				<label> Submitted On : <%=datetime%> </label> <br> <br>
				
				<label> Delivery Address Information </label> <br>
				<label> Address 1 </label> <input type="text" name="addrLine1" value="<%=addrLine1%>"> <br>
				<label> Address 2 </label> <input type="text" name="addrLine2" value="<%=addrLine2%>"> <br>
				<label> City </label> <input type="text" name="addrCity" value="<%=addrCity%>"> <br>
				<label> State </label>
				<select name="addrState" form="updateForm">
<% 					for(State st: State.values()) {
%>						 <option value="<%=st.toString()%>" <% if (address != null && st == address.getState()) { %> selected="selected" <%};%>> <%=st.toString()%></option>
<%					}
%>				</select> <br>
				<label> Zipcode </label> <input type="text" name="addrZipcode" value="<%=addrZipcode%>"> <br>
				
				<input type="submit" value="Update Order">
			</Form>			
		</div>
<%	}
%>
</body>
</html>