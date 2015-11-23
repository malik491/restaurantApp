<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.MenuItemBean" %>
<%@ page import="edu.depaul.se491.util.DBLabels" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Update Menu Item</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1> Update Menu Item </h1>
<%
	MenuItemBean menuItem = (MenuItemBean) request.getAttribute("menuItem");
	String msg = (String) request.getAttribute("msg");
	
	if (menuItem == null) {
		if (msg != null) {
%>	 		<h3><%=msg%></h3> 
<%		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
	} else {
		Long id = menuItem.getId();
		String name = menuItem.getName();
		String description = menuItem.getDescription();
		String price = String.format("%.2f", menuItem.getPrice()); //&#36; html for $
		int nameLenMax = DBLabels.MENU_ITEM_NAME_LEN_MAX;
		int descLenMax = DBLabels.MENU_ITEM_DESC_LEN_MAX;
		double priceMax = DBLabels.MENU_ITEM_PRICE_MAX;
		if (msg != null) {%>
	 		<h3><%=msg%></h3> 
<%		}
%>
		<form id="updateForm" action="update" method="post">
		<table>
			<thead> <tr> <th> Menu Item </th> <th> <input type="hidden" name="menuItemId" value="<%= id %>"> </th> </tr> </thead>
			<tbody>
				<tr> <td> ID </td> <td> <%=id%></td></tr>
				<tr> <td> Item Name </td> <td> <input type="text" name="itemName" value="<%= name %>" maxlength="<%=nameLenMax%>" required> </td></tr>
				<tr> <td> Description </td> <td><textarea name="itemDescription" form="updateForm" maxlength="<%=descLenMax%>" required> <%= description %> </textarea> </td></tr>
				<tr> <td> Unit Price ($0.0 - $1000.00)</td> <td> &#36;<input type="number" min="0" max="<%=priceMax%>" step="0.50" name="itemPrice" value="<%= price %>" required> </td></tr>
			</tbody>
		</table>
		<input type="submit" value="Update Menu Item">
		</form>
<%	}
%>
	</div>
</body>
</html>