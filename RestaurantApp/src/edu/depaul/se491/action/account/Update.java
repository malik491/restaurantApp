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
 * Update an existing account
 * @author Malik
 */
@WebServlet("/account/update")
public class Update extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = ParametersUtil.validateString(request.getParameter("username"));
		String firstUpdateParam = ParametersUtil.validateString(request.getParameter("firstUpdate"));
		
		boolean isFirstUpdate = ParametersUtil.isFirstUpdate(firstUpdateParam);
		
		AccountBean updatedAccount = null;
		String jspMsg = "Cannot update account. Invalid or missing account username.";
		try {
			if (username != null) {
				jspMsg = String.format("Cannot update account. No account with the given username (%s).", username);
				
				AccountBean oldAccount = accountDAO.get(username);
				if (oldAccount != null) {
					jspMsg = "Cannot update account. Invalid parameters";
					
					if (isFirstUpdate) {
						updatedAccount = oldAccount;
						jspMsg = null;
					} else {
						boolean validParams = processParameters(request, oldAccount);
						if (validParams) {
							boolean updated = accountDAO.update(oldAccount);
							jspMsg = updated? "Successfully updated account" : "Failed to update account";
						}
						// either way (updated or not) , grab a fresh copy for the next update
						updatedAccount = accountDAO.get(username);
					}
				}
			}

		} catch (Exception e) {
			ExceptionUtil.printException(e, "account/Update");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		request.setAttribute("account", updatedAccount);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/account/update.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private boolean processParameters(HttpServletRequest request, AccountBean account) {
		String passwordParam = ParametersUtil.validateString(request.getParameter("password"));
		String roleParam = ParametersUtil.validateString(request.getParameter("role"));
		AccountRole role = roleParam != null? AccountRole.valueOf(roleParam.toUpperCase()) : null;
		
		boolean validParams = passwordParam != null && role != null;
		validParams &= ParametersUtil.validateLength(passwordParam, DBLabels.ACCOUNT_PASSWORD_LEN_MAX);
		
		if (!validParams)
			return false;
		
		validParams = processUserParameters(request, account.getUser());
		if (validParams) {
			account.setPassword(passwordParam);
			account.setRole(role);			
		}
		
		return validParams;
	}

	private boolean processUserParameters(HttpServletRequest request, UserBean user) {
		String firstNameParam = ParametersUtil.validateString(request.getParameter("firstName"));
		String lastnameParam = ParametersUtil.validateString(request.getParameter("lastName"));
		String phoneParam = ParametersUtil.validateString(request.getParameter("phone"));
		
		// phone is optional
		boolean validParams = firstNameParam != null && lastnameParam != null;
		
		validParams &= ParametersUtil.validateLengths(new String[] {firstNameParam, lastnameParam}, new int[] 
				{DBLabels.USER_FIRST_NAME_LEN_MAX, DBLabels.USER_LAST_NAME_LEN_MAX});
		
		if (validParams && phoneParam != null)
			validParams = ParametersUtil.validateLength(phoneParam, DBLabels.USER_PHONE_LEN_MAX);
		
		if (!validParams)
			return false;
		
		validParams = processAddressParameters(request, user.getAddress());
		if (validParams) {
			user.setFirstName(firstNameParam);
			user.setLastName(lastnameParam);
			user.setPhone(phoneParam != null? phoneParam : Values.EMPTY_STRING);
		}
		
		return validParams;
	}

	private boolean processAddressParameters(HttpServletRequest request, AddressBean address) {
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

		address.setLine1(line1Param);
		address.setLine2(line2Param != null? line2Param : Values.EMPTY_STRING);
		address.setCity(cityParam);
		address.setState(state);
		address.setZipcode(zipcodeParam);
		
		return validParams;
	}
}
