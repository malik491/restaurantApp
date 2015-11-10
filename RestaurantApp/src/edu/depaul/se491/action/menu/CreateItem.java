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
 * create/add a new menu item
 * @author Malik
 */
@WebServlet("/menu/createItem")
public class CreateItem extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemName = request.getParameter("menuItemName");
		String menuItemDesc = request.getParameter("menuItemDesc");
		String menuItemPrice = request.getParameter("menuItemPrice");
		
		String jspMsg = null;
		
		try {
			MenuItemBean mItem = getMenuItem(menuItemName, menuItemDesc, menuItemPrice);
			boolean added = mItem != null? menuItemDAO.add(mItem) : false;
			
			if (added)
				jspMsg = "Successfuly created a new Menu Item";
			else
				jspMsg = "Failed to create a new Menu Item";
			
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menu/Create");
			jspMsg = "Exception Occured. See console for Details.";
		}
		
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menu/createItem.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private MenuItemBean getMenuItem(String menuItemName, String menuItemDesc, String menuItemPrice) {
		MenuItemBean mItem = null;
		
		menuItemName = menuItemName == null? null : (menuItemName = menuItemName.trim()).isEmpty()? null: menuItemName;
		menuItemDesc = menuItemDesc == null? null : (menuItemDesc = menuItemDesc.trim()).isEmpty()? null: menuItemDesc;
		menuItemPrice = menuItemPrice == null? null : (menuItemPrice = menuItemPrice.trim()).isEmpty()? null: menuItemPrice;
		
		if (menuItemName != null && menuItemDesc != null && menuItemPrice != null) {
			double price = 0;
			try {
				price = Double.parseDouble(menuItemPrice);
				if (price < 0)
					price = 0;
			} catch (NumberFormatException e) {		
			}
			
			if (price > 0) {
				mItem = new MenuItemBean();
				mItem.setName(menuItemName);
				mItem.setDescription(menuItemDesc);
				mItem.setPrice(price);
			}
		} 
		return mItem;
	}

}
