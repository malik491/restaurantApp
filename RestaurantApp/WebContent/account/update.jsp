<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.bean.*" %>
<%@ page import="edu.depaul.se491.enums.*" %>
<%@ page import="edu.depaul.se491.util.DBLabels" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Update Account</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Update Account </h1>
<%
	AccountBean account = (AccountBean) request.getAttribute("account");
	String msg = (String) request.getAttribute("msg");	
	if (account == null) {
		if (msg != null) {
%>	 		<h2><%=msg%></h2> 
<%		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
	} else {
		// account info
		String username = account.getUsername();
		String password = account.getPassword();
		AccountRole role = account.getRole();
		AccountRole[] roles = AccountRole.values();
		// user info
		UserBean user = account.getUser();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String email = user.getEmail();
		String phone = user.getPhone();
		
		// user address info
		AddressBean address = user.getAddress();
		String addrLine1 = address.getLine1();
		String addrLine2 = address.getLine2();
		String city = address.getCity();
		State state = address.getState();
		String zipcode = address.getZipcode();
		
		int passwordLenMax = DBLabels.ACCOUNT_PASSWORD_LEN_MAX;
		int fNameLenMax = DBLabels.USER_FIRST_NAME_LEN_MAX;
		int lNameLenMax = DBLabels.USER_LAST_NAME_LEN_MAX;
		int phoneLenMax = DBLabels.USER_PHONE_LEN_MAX;
		int line1LenMax = DBLabels.ADDRS_LINE1_LEN_MAX;
		int line2LenMax = DBLabels.ADDRS_LINE2_LEN_MAX;
		int cityLenMax = DBLabels.ADDRS_CITY_LEN_MAX;
		int zipcodeLenMax = DBLabels.ADDRS_ZIPCODE_LEN_MAX;
		
		if (msg != null) {
%>	 		<h3><%=msg%></h3> 
<%		}
%>
		<form id="updateForm" action="update" method="POST">
		<table>
			<thead> <tr> <th> Field </th> <th> Value </th> </tr> </thead>
			<tbody>
				<tr> <td> Account </td> <td> <input type="hidden" name="username" value="<%= username %>"> </td> </tr>
				<tr> <td> Username </td> <td> <%= username %> </td> </tr>
				<tr> <td> Password </td> <td> <input type="text" name="password" value="<%= password %>" maxlength="<%= passwordLenMax %>" required> </td></tr>
				<tr> <td> Role </td> 
					<td><select name="role" form="updateForm">
<%						for (AccountRole r: roles) {%>
							<option value="<%=r.toString()%>" <%if (role == r){%> selected="selected" <%}%>> <%=r.toString()%></option>
<%						}
%>					</select></td>
				</tr>
				
				
				<tr> <td> User Information </td> <td> </td> </tr>
				<tr> <td> Email </td> <td> <%= email %> </td></tr>
				<tr> <td> First Name </td> <td> <input type="text" name="firstName" value="<%= firstName %>" maxlength="<%= fNameLenMax %>" required> </td></tr>
				<tr> <td> Last Name </td> <td> <input type="text" name="lastName" value="<%= lastName %>" maxlength="<%= lNameLenMax %>" required> </td></tr>
				<tr> <td> Phone (optional) </td> <td> <input type="text" name="phone" value="<%= phone %>" maxlength="<%= phoneLenMax %>"> </td></tr>

				<tr> <td> Address </td> <td> </td> </tr>
				<tr> <td> Address 1	</td> <td> <input type="text" name="addrLine1" value="<%= addrLine1 %>" maxlength="<%= line1LenMax %>" required> </td> </tr>
				<tr> <td> Address 2	(optional) </td> <td> <input type="text" name="addrLine2" value="<%= addrLine2 %>" maxlength="<%= line2LenMax %>"> </td> </tr>
				<tr> <td> City	</td> <td> <input type="text" name="addrCity" value="<%= city %>" maxlength="<%= cityLenMax %>" required> </td> </tr>
				<tr> <td> State	</td> 
					 <td> <select name="addrState" form="updateForm">
<% 						for(State s: State.values()) {
%>						 	<option value="<%=s.toString()%>" <% if(s == state){%> selected="selected" <%}%>> <%=s.toString()%></option>
<%						}
%>					 </select></td>
 				</tr>
				<tr> <td> Zipcode	</td> <td> <input type="text" name="addrZipcode" value="<%= zipcode %>" maxlength="<%= zipcodeLenMax %>" required> </td> </tr>  				
			</tbody>
		</table>
		<input type="submit" value="Update Account">
		</form>
<%	}
%>
	</div>
</body>
</html>