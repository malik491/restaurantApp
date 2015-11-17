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
 * Delete an Account
 * @author Malik
 *
 */
@WebServlet("/account/delete")
public class Delete extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = ParametersUtil.validateString(request.getParameter("username"));
		String jspMsg = "Cannot delete account. Invalid or missing account username.";
		
		try {
			if (username != null) {
				jspMsg = String.format("Cannot delete account. No account with username (%s)", username);
				
				AccountBean account = accountDAO.get(username);
				if (account != null) {
					boolean deleted = accountDAO.delete(account);
					jspMsg = deleted? "Successfully deleted account" : "Failed to delete account";
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "account/Delete");
			jspMsg = "Exception Occured. See Console for Details.";
		}

		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/account/delete.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
