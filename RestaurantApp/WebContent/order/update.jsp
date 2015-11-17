<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.*" %>
<%@ page import="edu.depaul.se491.enums.*" %>
<%@ page import="edu.depaul.se491.util.*" %>

<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Update Order</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Update Order </h1>
<%
	OrderBean order = (OrderBean) request.getAttribute("order");
	String msg = (String) request.getAttribute("msg");
	if (order == null) {
		if (msg != null) {
%> 			<h3><%=msg%></h3> 
<%		} else {
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
		AddressBean address = order.getDeliveryAddress();
		String addrId = address == null? Values.EMPTY_STRING : Long.toString(address.getId());
		String addrLine1 = address == null? Values.EMPTY_STRING : address.getLine1();
		String addrLine2 = address == null? Values.EMPTY_STRING : address.getLine2();
		String addrCity = address == null? Values.EMPTY_STRING : address.getCity();
		String addrZipcode = address == null? Values.EMPTY_STRING : address.getZipcode();

 		if (msg != null) {
%>			<h3><%=msg%></h3>
<%		}
%>
		<Form id="updateForm" action="update" method="post">
		<table>
			<thead> <tr> <th> Order Data</th> <th> <input type="hidden" name="orderId" value="<%=orderId%>"> </th> </tr> </thead>			
			<tbody>
			<tr> <td> Status </td>
				 <td> <select name="status" form="updateForm">
<% 				 for(OrderStatus s: OrderStatus.values()) {
%>					 	<option value="<%=s.toString()%>" <%if (s == order.getStatus()) { %> selected="selected" <%};%>> <%=s.toString().toLowerCase()%></option>
<%				 }
%>				</select></td>
			</tr>
			
			<tr> <td> Type </td>
				 <td> <select name="type" form="updateForm">
<% 					for(OrderType t: OrderType.values()) {
%>					 	<option value="<%=t.toString()%>" <% if (t == order.getType()) { %> selected="selected" <%}%>> <%=t.toString().toLowerCase()%></option>
<%					}
%>					</select></td>									
			</tr>
			
			<tr> <td> Total </td> <td> <%=total%>	</td> </tr>
		
			<tr> <td> Confirmation	</td> <td>  <%=confirmation%>	</td> </tr>
			<tr> <td> Date/Time </td> <td> <%=datetime%> </td> </tr>
			
			<tr> <td> Delivery Address Information : </td> <td> <input type="hidden" name="addrId" value="<%=addrId%>"></td></tr>
			<tr> <td> Address 1	</td> <td> <input type="text" name="addrLine1" value="<%=addrLine1%>"></td> </tr>
			<tr> <td> Address 2	</td> <td> <input type="text" name="addrLine2" value="<%=addrLine2%>">	</td> </tr>
			<tr> <td> City	</td> <td>	<input type="text" name="addrCity" value="<%=addrCity%>"> </td> </tr>
			<tr> <td> State	</td> 
				 <td> <select name="addrState" form="updateForm">
<% 					for(State st: State.values()) {
%>					 	<option value="<%=st.toString()%>" <% if (address != null && st == address.getState()) { %> selected="selected" <%}%>> <%=st.toString()%></option>
<%					}
%>				 </select></td> 
			</tr>
			<tr> <td> Zipcdoe </td> <td> <input type="text" name="addrZipcode" value="<%=addrZipcode%>"> </td></tr>
			</tbody>
		</table>

			<table>
			<thead> <tr> <th> Menu Item </th> <th> Quantity </th> </tr> </thead>
			<tbody>
<% 			for (OrderItemBean oItem: order.getItems()) {
				String id = Long.toString(oItem.getMenuItem().getId());
				String mItemName = oItem.getMenuItem().getName();
				String quantity = Integer.toString(oItem.getQuantity());
%>				
				<tr> <td> <%=mItemName%> </td> <td> <input type="number" min="0" max="1000" name="mItemQty-<%=id%>" value="<%=quantity%>" required> </td> </tr>
<%			}
%>			</tbody>
			</table>
			<input type="submit" value="Update Order">
			</Form>			
		</div>
<%	}
%>
</body>
</html>