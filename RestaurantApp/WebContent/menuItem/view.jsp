<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.MenuItemBean" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Delete Menu Item</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>View Menu Item </h1>
	
<%
	MenuItemBean menuItem = (MenuItemBean) request.getAttribute("menuItem");

	if (menuItem == null) {
		String msg = (String) request.getAttribute("msg");
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
		String price = String.format("&#36;%.2f", menuItem.getPrice()); //&#36; html for $
%>		
		<table>
			<thead> <tr> <th> Menu Item Information </th> <th> </th> </tr> </thead>
			<tbody>
				<tr> <td> Item ID </td> <td> <%=id%></td></tr>
				<tr> <td> Item Name </td> <td> <%=name %> </td></tr>
				<tr> <td> Description </td> <td> <%=description %> </td></tr>
				<tr> <td> Unit Price</td> <td> <%=price%> </td></tr>
			</tbody>
		</table>
<%	}
%>
	</div>
</body>
</html>