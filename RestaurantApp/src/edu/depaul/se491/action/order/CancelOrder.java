package edu.depaul.se491.action.order;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.util.DAOUtil;

/**
 * Cancel order servlet
 * @author Malik
 */

@WebServlet("/cancel")
public class CancelOrder extends BaseAction {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		
		// logic for updating order status order
		// all xyzDAO objects (such as orderDAO) are in the BaseAction (parent) so 
		// you directly access them. example, you just say orderDAO.getAll();
		
		try {
			orderDAO.getAll();
		} catch (SQLException e) {
			DAOUtil.printException(e, "CancelOrder");
		}
				
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	

}
