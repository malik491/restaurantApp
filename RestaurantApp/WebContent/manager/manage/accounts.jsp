<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.depaul.se491.bean.AccountBean"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Manage Accounts</title>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>

<body>
	<div class="component">
<%
	List<AccountBean> accounts = (List<AccountBean>) request.getAttribute("accounts");
	if (accounts == null) {
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
		<h1>Manage Accounts</h1>
		<table>
			<thead><tr><th> Username </th><th> Role </th> <th> View </th><th> Update </th><th> Delete </th></tr></thead>
			<tbody>	
<%			for (AccountBean account: accounts) {
				String username = account.getUsername();
				String role = account.getRole().toString().toLowerCase();
%>				<!--  print row for this account -->
				<tr>
					<td><%=username%></td>
					<td><%=role%></td>	
					<td>
						<form action="${pageContext.request.contextPath}/account/view" method="POST">
						<input type="hidden" name="username" value="<%=username%>">
						<input type="submit" value="View">
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/account/update" method="POST">
						<input type="hidden" name="username" value="<%=username%>">
						<input type="hidden" name="requestType" value="jsp">
						<input type="hidden" name="firstUpdate" value="true">
						<input type="submit" value="Update">
						</form>
					</td>
					<td>
						<form action="${pageContext.request.contextPath}/account/delete" method="POST">
						<input type="hidden" name="username" value="<%=username%>">
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