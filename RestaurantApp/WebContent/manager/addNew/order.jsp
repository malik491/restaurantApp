<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.depaul.se491.enums.*" %>
<%@ page import="edu.depaul.se491.util.*" %>
<%@ page import="edu.depaul.se491.bean.*" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Order</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/component.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
</head>
<body>
	<div class="component">
	<h1>Add Order </h1>
	
	<%
	List<MenuItemBean> menuItems = (List<MenuItemBean>) request.getAttribute("menuItems");
	
	if (menuItems == null) {
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
%> 		<h3><%=msg%></h3> 
<%			
		} else {
			// redirect to home page
			String homePageURL = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(homePageURL);
		}
		
	} else if (menuItems.size() == 0) {
%>
		<h3>There are no menu items (empty menu)</h3>
<%
	} else {
		int line1LenMax = DBLabels.ADDRS_LINE1_LEN_MAX;
		int line2LenMax = DBLabels.ADDRS_LINE2_LEN_MAX;
		int cityLenMax = DBLabels.ADDRS_CITY_LEN_MAX;
		int zipcodeLenMax = DBLabels.ADDRS_ZIPCODE_LEN_MAX;
		
		%>
		
		<Form id="addForm" action="${pageContext.request.contextPath}/order/create" method="post">
		<table>
			<thead> <tr> <th> Order Data</th> <th> </th> </tr> </thead>			
			<tbody>
			<tr> <td> Status </td>
				<td> <select name="status" form="addForm" required>
	<% 			for(OrderStatus s: OrderStatus.values()) {
	%>			 	<option value="<%=s.toString()%>" > <%=s.toString().toLowerCase()%></option>
	<%			}
	%>			</select></td>
			</tr>
			
			<tr> <td> Type </td>
				 <td> <select name="type" form="addForm" required>
	<% 			 for(OrderType t: OrderType.values()) {
	%>			 	<option value="<%=t.toString()%>" > <%=t.toString().toLowerCase()%></option>
	<%			}
	%>			</select></td>									
			</tr>	
			<tr> <td> Delivery Address Information : </td> <td></td></tr>
			<tr> <td> Address 1	</td> <td> <input type="text" name="addrLine1" value="" manxlength="<%=line1LenMax%>"></td> </tr>
			<tr> <td> Address 2	</td> <td> <input type="text" name="addrLine2" value="" manxlength="<%=line2LenMax%>">	</td> </tr>
			<tr> <td> City	</td> <td>	<input type="text" name="addrCity" value="" manxlength="<%=cityLenMax%>"> </td> </tr>
			<tr> <td> State	</td> 
				 <td> <select name="addrState" form="addForm" required>
	<% 					for(State st: State.values()) {
	%>					 	<option value="<%=st.toString()%>"> <%=st.toString()%></option>
	<%					}
	%>				 </select></td> 
			</tr>
			<tr> <td> Zipcdoe </td> <td> <input type="text" name="addrZipcode" value="" manxlength="<%=zipcodeLenMax%>"> </td></tr>
			</tbody>
		</table>

		<table>
			<thead> <tr> <th> Menu Item </th> <th> Quantity </th> </tr> </thead>
			<tbody>
	<% 		for (MenuItemBean menuItem: menuItems) {
				long menuItemId = menuItem.getId();
				String mItemName = menuItem.getName();
	%>			
				<tr> <td> <%=mItemName%> </td> <td> <input type="number" min="0" max="1000" step="1" name="mItemQty-<%=menuItemId%>" value="0" required> </td> </tr>
	<%		}
	%>		</tbody>
			</table>
			<input type="submit" value="Create Order">
		</Form>				
<%	}
%>	
		</div>
</body>
</html>