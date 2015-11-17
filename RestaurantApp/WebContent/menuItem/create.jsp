<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title> Create Menu Item </title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Create Menu Item</h1>
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