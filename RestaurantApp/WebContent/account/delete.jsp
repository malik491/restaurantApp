<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Delete Account</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Delete Account</h1>
<%
	String msg = (String) request.getAttribute("msg");
	if (msg != null) {
%> 		<h3><%=msg%></h3> 
<%	} else {
		// redirect to home page
		String homePageURL = getServletContext().getContextPath() + "/index.html";
		response.sendRedirect(homePageURL);
	}
%>
	</div>
</body>
</html>