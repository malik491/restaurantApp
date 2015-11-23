<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.util.DBLabels" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Menu Item</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1> Add Menu Item </h1>
<%
	int nameLenMax = DBLabels.MENU_ITEM_NAME_LEN_MAX;
	int descLenMax = DBLabels.MENU_ITEM_DESC_LEN_MAX;
	double priceMax = DBLabels.MENU_ITEM_PRICE_MAX;
%>
	<form id="addForm" action="${pageContext.request.contextPath}/menuItem/create" method="post">
	<table>
		<thead> <tr> <th> Menu Item </th> <th> </th> </tr> </thead>
		<tbody>
			<tr> <td> Item Name </td> <td> <input type="text" name="itemName" value="" maxlength="<%=nameLenMax%>" required> </td></tr>
			<tr> <td> Description </td> <td><textarea name="itemDescription" form="addForm" maxlength="<%=descLenMax%>" required></textarea> </td></tr>
			<tr> <td> Unit Price ($0.0 - $1000.00)</td> <td> &#36;<input type="number" min="0" max="<%=priceMax%>" step="0.50" name="itemPrice" value="" required> </td></tr>
		</tbody>
	</table>
	<input type="submit" value="Create Menu Item">
	</form>
	</div>
</body>
</html>