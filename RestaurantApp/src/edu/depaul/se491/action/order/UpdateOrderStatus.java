/**
 * 
 */
package edu.depaul.se491.action.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * update order status
 * @author Malik
 */

@WebServlet("/updateStatus")
public class UpdateOrderStatus extends BaseAction {	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// logic for updating order status order
		// all xyzDAO objects (such as orderDAO) are in the BaseAction (parent) so 
		// you directly access them. example, you just say orderDAO.getAll();
		
		try {
			orderDAO.getAll();
		} catch (SQLException e) {
			ExceptionUtil.printException(e, "UpdateOrderStatus");
		}
				
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
