<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>View Account</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>View Account</h1>
<%
	AccountBean account = (AccountBean) request.getAttribute("account");

	if (account == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
%>	 		<h3><%=msg%></h3> 
<%		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
	} else {
		// account info
		String username = account.getUsername();
		String password = account.getPassword();
		String role = account.getRole().toString().toLowerCase();
		
		// user info
		UserBean user = account.getUser();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String email = user.getEmail();
		String phone = user.getPhone();
		
		// user address info
		AddressBean address = user.getAddress();
		String formatedAddr = String.format("%s,<br> %s.<br> %s, %s %s", 
				address.getLine1(), address.getLine2(), address.getCity(), address.getState().toString(), address.getZipcode());
%>
		<table>
			<thead> <tr> <th> Account Information </th> <th> </th> </tr> </thead>
			<tbody>
				<tr> <td> Username </td> <td> <%= username%></td></tr>
				<tr> <td> Password </td> <td> <%= password %> </td></tr>
				<tr> <td> Role </td> <td> <%= role %> </td></tr>
				
				<tr> </tr>
				<tr> <td> User :</td> <td> </td> </tr>
				<tr> <td> First Name </td> <td> <%= firstName %></td></tr>
				<tr> <td> Last Name </td> <td> <%= lastName %> </td></tr>
				<tr> <td> Email </td> <td> <%= email %> </td></tr>
				<tr> <td> Phone </td> <td> <%= phone %> </td></tr>
				<tr> <td> Address </td> <td> <%=formatedAddr %> </td> </tr>
				<tr> </tr>				
			</tbody>
		</table>
<%	}
%>
	</div>
</body>
</html>