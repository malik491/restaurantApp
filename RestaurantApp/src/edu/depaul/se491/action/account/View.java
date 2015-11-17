package edu.depaul.se491.action.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;

/**
 * View an Account
 * @author Malik
 */

@WebServlet("/account/view")
public class View extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = ParametersUtil.validateString(request.getParameter("username"));
		
		AccountBean account = null;
		String jspMsg = "Cannot view account. Invalid or missing username.";
		
		try {
			if (username != null) {
				account = accountDAO.get(username);
				if (account == null)
					jspMsg = String.format("Cannot view account. No account with username (%d)", username);
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menuItem/View");
			jspMsg = "Exception Occured. See Console for Details.";
		}
		
		request.setAttribute("account", account);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/account/view.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
