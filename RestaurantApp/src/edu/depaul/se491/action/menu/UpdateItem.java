/**
 * 
 */
package edu.depaul.se491.action.menu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.ExceptionUtil;

/**
 * update data for an existing menu item
 * @author Malik
 */
@WebServlet("/menu/updateItem")
public class UpdateItem extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemId = request.getParameter("menuItemId");
		MenuItemBean updatedMenuItem = null;
		String jspMsg = null;
		
		try {
			MenuItemBean oldMenuItem = menuItemDAO.get(Long.valueOf(menuItemId));
			boolean validParams = hasValidParams(request, oldMenuItem);
			
			boolean updated = validParams? menuItemDAO.update(oldMenuItem) : false;
			
			if (updated) {
				// get updated menu item from DB (to make sure update occurred)
				updatedMenuItem = menuItemDAO.get(oldMenuItem.getId());
				jspMsg = "Successfuly update a menu item";
			} else {
				jspMsg = "Failed to update a menu item";
			}
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menu/Update");
			jspMsg = "Exception Occured. See console for Details.";
		}
		
		request.setAttribute("menuItem", updatedMenuItem);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menu/updateItem.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	private boolean hasValidParams(HttpServletRequest request, MenuItemBean oldMenuItem) {
		String menuItemName = request.getParameter("menuItemName");
		String menuItemDesc = request.getParameter("menuItemDesc");
		String menuItemPrice = request.getParameter("menuItemPrice");
		
		
		menuItemName = menuItemName == null? null : (menuItemName = menuItemName.trim()).isEmpty()? null: menuItemName;
		menuItemDesc = menuItemDesc == null? null : (menuItemDesc = menuItemDesc.trim()).isEmpty()? null: menuItemDesc;
		menuItemPrice = menuItemPrice == null? null : (menuItemPrice = menuItemPrice.trim()).isEmpty()? null: menuItemPrice;
		
		boolean validParams = menuItemName != null && menuItemDesc != null && menuItemPrice != null;
		
		if (!validParams)
			return false;
		
		double price = 0;
		try {
			price = Double.parseDouble(menuItemPrice);
			if (price < 0)
				price = 0;
		} catch (NumberFormatException e) {	}
		
		validParams = price > 0;
		if (validParams) {
			oldMenuItem.setName(menuItemName);
			oldMenuItem.setDescription(menuItemDesc);
			oldMenuItem.setPrice(price);
		}
		
		return validParams;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
