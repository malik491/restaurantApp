<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.*" %>
<%@ page import="edu.depaul.se491.enums.*" %>
<%@ page import="edu.depaul.se491.util.DBLabels" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Account</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Add Account </h1>
<%
		// account info
		int usernameLenMax = DBLabels.ACCOUNT_USERNAME_LEN_MAX;
		int passwordLenMax = DBLabels.ACCOUNT_PASSWORD_LEN_MAX;
		int emailLenMax = DBLabels.USER_EMAIL_LEN_MAX;
		int fNameLenMax = DBLabels.USER_FIRST_NAME_LEN_MAX;
		int lNameLenMax = DBLabels.USER_LAST_NAME_LEN_MAX;
		int phoneLenMax = DBLabels.USER_PHONE_LEN_MAX;
		int line1LenMax = DBLabels.ADDRS_LINE1_LEN_MAX;
		int line2LenMax = DBLabels.ADDRS_LINE2_LEN_MAX;
		int cityLenMax = DBLabels.ADDRS_CITY_LEN_MAX;
		int zipcodeLenMax = DBLabels.ADDRS_ZIPCODE_LEN_MAX;
%>		
		<form id="addForm" action="${pageContext.request.contextPath}/account/create" method="POST">
		<table>
			<thead> <tr> <th> Field </th> <th> Value </th> </tr> </thead>
			<tbody>
				<tr> <td> Account </td> <td> </td> </tr>
				<tr> <td> Username </td> <td> <input type="text" name="username" value="" maxlength="<%= usernameLenMax %>" required> </td> </tr>
				<tr> <td> Password </td> <td> <input type="text" name="password" value="" maxlength="<%= passwordLenMax %>" required> </td></tr>
				<tr> <td> Role </td> 
					<td><select name="role" form="addForm">
<%						for (AccountRole r: AccountRole.values()) {%>
							<option value="<%=r.toString()%>"> <%=r.toString()%></option>
<%						}
%>					</select></td>
				</tr>
				
				<tr> </tr>
				<tr> <td> User </td> <td> </td> </tr>
				<tr> <td> Email </td> <td> <input type="email" name="email" value="" maxlength="<%= emailLenMax %>" required> </td></tr>
				<tr> <td> First Name </td> <td> <input type="text" name="firstName" value="" maxlength="<%= fNameLenMax %>" required> </td></tr>
				<tr> <td> Last Name </td> <td> <input type="text" name="lastName" value="" maxlength="<%= lNameLenMax %>" required> </td></tr>
				<tr> <td> Phone </td> <td> <input type="text" name="phone" value="" maxlength="<%= phoneLenMax %>" required> </td></tr>

				<tr> <td> Address </td> <td> </td> </tr>
				<tr> <td> Address 1	</td> <td> <input type="text" name="addrLine1" value="" maxlength="<%= line1LenMax %>" required> </td> </tr>
				<tr> <td> Address 2	</td> <td> <input type="text" name="addrLine2" value="" maxlength="<%= line2LenMax %>" required> </td> </tr>
				<tr> <td> City	</td> <td> <input type="text" name="addrCity" value="" maxlength="<%= cityLenMax %>" required> </td> </tr>
				<tr> <td> State	</td> 
					 <td> <select name="addrState" form="addForm" required>
<% 						for(State s: State.values()) {
%>						 	<option value="<%=s.toString()%>"> <%=s.toString()%></option>
<%						}
%>					 </select></td>
 				</tr>
				<tr> <td> Zipcode	</td> <td> <input type="text" name="addrZipcode" value="" maxlength="<%= zipcodeLenMax %>" required> </td> </tr>  				
			</tbody>
		</table>
		<input type="submit" value="Create Account">
		</form>
	</div>
</body>
</html>