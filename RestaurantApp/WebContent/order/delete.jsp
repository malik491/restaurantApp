<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Delete Order</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
<%
	String msg = (String) request.getAttribute("msg");
	if (msg != null) {
%> 		<h2><%=msg%></h2> 
<%	} else {
		getServletContext().getRequestDispatcher("/index.html").forward(request, response);
		return;
	}
%>
	</div>
</body>
</html>