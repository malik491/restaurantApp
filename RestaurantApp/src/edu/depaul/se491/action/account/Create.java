package edu.depaul.se491.action.account;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.UserBean;
import edu.depaul.se491.enums.AccountRole;
import edu.depaul.se491.enums.State;
import edu.depaul.se491.util.DBLabels;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;
import edu.depaul.se491.util.Values;

/**
 * Create an Account
 * @author Malik
 */

@WebServlet("/account/create")
public class Create extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = ParametersUtil.validateString(request.getParameter("username"));
		String email = ParametersUtil.validateString(request.getParameter("email"));
		
		String jspMsg = "Cannot create a new account. Invalid or missing account username and/or user email";

		try {
			if (username != null && email != null) {
				jspMsg = "Cannnot create a new account. Username and/or User Email is already taken.";

				AccountBean oldAccount = accountDAO.get(username);
				UserBean oldUser = userDAO.get(email);
				boolean isUnique = (oldAccount == null && oldUser == null);
				
				if (isUnique) {
					jspMsg = "Cannot create a new account. Invalid parameters";
					
					AccountBean newAccount = getAccount(request);
					if (newAccount != null) {
						boolean added = accountDAO.add(newAccount);
						jspMsg = added? "Successfuly created a new account" : "Failed to create a new account";
					}
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "account/Create");
			jspMsg = "Exception Occured. See console for Details.";
		}
		
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/account/create.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private AccountBean getAccount(HttpServletRequest request) {
		AccountBean newAccount = new AccountBean();
		
		boolean validParams = processParameters(request, newAccount);
		if (!validParams)
			newAccount = null;
		
		return newAccount;
	}
	
	
	private boolean processParameters(HttpServletRequest request, AccountBean account) {
		String userNameParam = ParametersUtil.validateString(request.getParameter("username"));
		String passwordParam = ParametersUtil.validateString(request.getParameter("password"));
		String roleParam = ParametersUtil.validateString(request.getParameter("role"));
		AccountRole role = roleParam != null? AccountRole.valueOf(roleParam.toUpperCase()) : null;
		
		boolean validParams = userNameParam != null && passwordParam != null && role != null;
		
		// validate string lengths
		validParams &= ParametersUtil.validateLengths(new String[] {userNameParam, passwordParam}, new int[] 
				{DBLabels.ACCOUNT_USERNAME_LEN_MAX, DBLabels.ACCOUNT_PASSWORD_LEN_MAX});
		
		if (!validParams)
			return false;
		
		account.setUsername(userNameParam);
		account.setPassword(passwordParam);
		account.setRole(role);
		
		validParams = processUserParameters(request, account);
		
		return validParams;
	}

	private boolean processUserParameters(HttpServletRequest request, AccountBean account) {
		String emailParam = ParametersUtil.validateString(request.getParameter("email"));
		String firstNameParam = ParametersUtil.validateString(request.getParameter("firstname"));
		String lastnameParam = ParametersUtil.validateString(request.getParameter("lastname"));
		String phoneParam = ParametersUtil.validateString(request.getParameter("phone"));
		
		// phone is optional
		boolean validParams = emailParam != null && firstNameParam != null && lastnameParam != null;
		
		validParams &= ParametersUtil.validateLengths(new String[] {emailParam, firstNameParam, lastnameParam}, new int[] 
				{DBLabels.USER_EMAIL_LEN_MAX, DBLabels.USER_FIRST_NAME_LEN_MAX, DBLabels.USER_LAST_NAME_LEN_MAX});
		
		if (validParams && phoneParam != null)
			validParams = ParametersUtil.validateLength(phoneParam, DBLabels.USER_PHONE_LEN_MAX);
		
		if (!validParams)
			return false;
		
		UserBean newUser = new UserBean();
		newUser.setEmail(emailParam);
		newUser.setFirstName(firstNameParam);
		newUser.setLastName(lastnameParam);
		newUser.setPhone(phoneParam != null? phoneParam : Values.EMPTY_STRING);
		
		validParams = processAddressParameters(request, newUser);
		if (validParams)
			account.setUser(newUser);
		
		return validParams;
	}

	private boolean processAddressParameters(HttpServletRequest request, UserBean user) {
		String line1Param = ParametersUtil.validateString(request.getParameter("addrLine1"));
		String line2Param = ParametersUtil.validateString(request.getParameter("addrLine2"));
		String cityParam = ParametersUtil.validateString(request.getParameter("addrCity"));
		String stateParam = ParametersUtil.validateString(request.getParameter("addrState"));
		String zipcodeParam = ParametersUtil.validateString(request.getParameter("addrZipcode"));

		// line2Param is optional
		boolean validParams = line1Param != null && cityParam != null && stateParam != null && zipcodeParam != null;

		validParams &= ParametersUtil.validateLengths(new String[] {line1Param, cityParam, zipcodeParam}, new int[] {
				DBLabels.ADDRS_LINE1_LEN_MAX, DBLabels.ADDRS_CITY_LEN_MAX, DBLabels.ADDRS_ZIPCODE_LEN_MAX });
		
		if (validParams && line2Param != null)
			validParams = ParametersUtil.validateLength(line2Param, DBLabels.ADDRS_LINE2_LEN_MAX);
		
		if (!validParams)
			return false;

		State state = State.valueOf(stateParam.toUpperCase());
		validParams = state != null;

		if (!validParams)
			return false;

		AddressBean newAddr = new AddressBean();
		newAddr.setLine1(line1Param);
		newAddr.setLine2(line2Param != null? line2Param : Values.EMPTY_STRING);
		newAddr.setCity(cityParam);
		newAddr.setState(state);
		newAddr.setZipcode(zipcodeParam);

		user.setAddress(newAddr);
		
		return validParams;
	}
}
