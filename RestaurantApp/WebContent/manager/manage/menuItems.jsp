<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.MenuItemBean"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Manage Menu Items</title>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>

<body>
	<div class="component">
<%
	List<MenuItemBean> menuItems = (List<MenuItemBean>) request.getAttribute("menuItems");
	if (menuItems == null) {
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
		<h1>Manage Menu Items</h1>
		<table>
			<thead><tr><th> ID </th><th> Name </th> <th> View </th><th> Update </th><th> Delete </th></tr></thead>
			<tbody>	
<%			for (MenuItemBean mItem: menuItems) {
				long menuItemId = mItem.getId();
				String name = mItem.getName();
%>				<!--  print row for this menu item -->
				<tr>
					<td><%=menuItemId%></td>
					<td><%=name%></td>	
					<td>
						<form action="${pageContext.request.contextPath}/menuItem/view" method="POST">
						<input type="hidden" name="menuItemId" value="<%=menuItemId%>">
						<input type="submit" value="View">
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/menuItem/update" method="POST">
						<input type="hidden" name="menuItemId" value="<%=menuItemId%>">
						<input type="hidden" name="requestType" value="jsp">
						<input type="hidden" name="firstUpdate" value="true">
						<input type="submit" value="Update">
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/menuItem/delete" method="POST">
						<input type="hidden" name="menuItemId" value="<%=menuItemId%>">
						<input type="submit" value="Delete">
						</form>
					</td>								
				</tr>
<% 			}
%>			</tbody>
		</table>
<%	}
%>
	</div>
</body>
</html>