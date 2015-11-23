package edu.depaul.se491.action.storeManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.AccountBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * Manage Accounts
 * @author Malik
 */
@WebServlet("/manager/manage/accounts")
public class ManageAccounts extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException	{
		List<AccountBean> accounts = null;
		String jspMsg = null;
		
		try {
			accounts = accountDAO.getAll();
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "storeManager/ManageAccounts");
			jspMsg = "DB Exception Occured. See Console for Details.";
		}
		
		// set orders for the jsp
		req.setAttribute("accounts", accounts);
		req.setAttribute("msg", jspMsg);
		
		String jspURL = "/manager/manage/accounts.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
