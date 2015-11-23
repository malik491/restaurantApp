<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.depaul.se491.bean.MenuItemBean" %>

<!DOCTYPE html>
<html>
<head>
	<title>Register</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css"/>
</head>
<body>
	<div class="container">
<%
	List<MenuItemBean> menuItems = (List<MenuItemBean>) request.getAttribute("menuItems");
	String jsonMenuItemList = (String) request.getAttribute("jsonMenuItemList");
	
	if (menuItems == null || jsonMenuItemList == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
			%> <h3><%=msg%></h3> <%
		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);		
		}
	} else {
		int buttonPerRow = 3;
		int total = menuItems.size();
%>
		<div class="menu">
		<table class="menuItems"><tbody>
<%
		for (int i=1; i < total + 1 ; i++) {
			MenuItemBean mItem = menuItems.get(i-1); // i - 1 (zero based index)
			long id = mItem.getId();
			String label = mItem.getName();	
			
			int modValue = i % buttonPerRow; 
			if (modValue == 1) {
%>  			<!-- row tag starts (start a new row) --> 
				<tr>  
<% 			}
%>			<!-- add menu item button -->
			<td><button class="menuItemButton" id="item-<%=id%>" onClick="addItem(<%=id%>)"> <%=label%> </button> </td>
<%
			if (modValue == 0 || i == total ) {
				// if the added button is the last button for a row OR 
				//it's the last button to be added (regardless of number of buttonPerRow)
%>				<!-- row tag ends (close a row tag) -->
				</tr>
<%			}
		}
%>		</tbody> </table> 
		</div>
		
		<div class="order">
			<div class="summary">
				<h3>Order Summary</h3>
				<table id="orderItemsTable">
					<thead> <tr> <th> Item </th> <th> Price </th> <th> Qty </th> </tr></thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<br> <br>
			<div class="actions">
				<table>
					<tr> <td> Total: &#36;</td><td id="orderTotal">0</td> </tr>
					<tr> <td colspan="2"> <button type="button" id="clearOrderBtn"  onClick="clearOrder()"> Start Over </button> </td></tr>
					<tr> <td colspan="2"> <button type="button" id="submitOrderBtn" onClick="submitOrder()"> Submit Order </button> </td> </tr>
				</table>
			</div>
		</div>		

		<script type="text/javascript"> <%=String.format("var registerMenuItemList = %s", jsonMenuItemList)%></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/register.js"></script>
<%	}
%>
	</div>
</body>	
</html>