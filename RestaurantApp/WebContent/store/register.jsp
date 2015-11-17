<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.depaul.se491.bean.MenuItemBean" %>

<!DOCTYPE html>
<html>
<head>
	<title>Point Of Sale</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addOrder.css"/>
</head>
<body>
<%
	List<MenuItemBean> menuItems = (List<MenuItemBean>) request.getAttribute("menuItems");
	String jsonMenuItems = (String) request.getAttribute("jsonMenuItems");
	if (menuItems == null || jsonMenuItems == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);		
		}
	} else {
%>
		<div class="menuDiv">
		<table class="buttons"><tbody>
<%		
		int buttonPerRow = 3;
		int total = menuItems.size();
		
		// a 1 based loop so loop (total) times
		for (int i=1; i < total + 1 ; i++) {
			MenuItemBean mItem = menuItems.get(i-1); // i - 1 (zero based index)
			String id = Long.toString(mItem.getId());
			String label = String.format("%-20s $ %.2f", mItem.getName(), mItem.getPrice());	
			
			int modValue = i % buttonPerRow; 
			if (modValue == 1) {
%>  
				<!-- row tag starts (start a new row) --> 
				<tr>  
<% 			}
%>
			<!-- add menu item button -->
			<td><button class="myButton" id="item-<%=id%>" onClick="order.addItem(<%=id%>)"> <%=label%> </button> </td>
<%
			if (modValue == 0 || i == total ) {
				// if the added button is the last button for a row OR 
				//it's the last button to be added (regardless of number of buttonPerRow)
%>
				<!-- row tag ends (close a row tag) -->
				</tr>
<%
			}
		}
%>
		</tbody> </table> </div>
		
		<div class="summaryDiv">
			<textarea id="summary" rows="15" readonly></textarea>
			<br> <br>
			<table>
				<tr> <td> Total : </td> <td id="total"> </td></tr>
			</table>
			<button type="button" class="myButton" onClick="placeOrder()"> Place Order </button>
		</div>
		
		
		<%
			String jsMenuItemsList = String.format("var menuItems = %s", jsonMenuItems);
		%>
		<script type="text/javascript"> <%=jsMenuItemsList%></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/createOrder.js"></script>
<%
	}
%>
</body>	
</html>