/**
 * 
 */
package edu.depaul.se491.action.menuItem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.depaul.se491.action.BaseAction;
import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.util.DBLabels;
import edu.depaul.se491.util.ExceptionUtil;
import edu.depaul.se491.util.ParametersUtil;

/**
 * update data for an existing menu item
 * @author Malik
 */
@WebServlet("/menuItem/update")
public class Update extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuItemIdParam = ParametersUtil.validateString(request.getParameter("menuItemId"));
		String firstUpdateParam = ParametersUtil.validateString(request.getParameter("firstUpdate"));
		
		Long menuItemId = ParametersUtil.parseLong(menuItemIdParam);
		boolean isFirstUpdate = ParametersUtil.isFirstUpdate(firstUpdateParam);
		
		MenuItemBean updatedItem = null;
		String jspMsg = "Cannot update menu item. Invalid or missing menu item id.";
		
		try {
			if (menuItemId != null) {
				jspMsg = String.format("Cannot update menu item. No menu item with id (%d)", menuItemId);
				
				MenuItemBean oldItem = menuItemDAO.get(menuItemId);
				if (oldItem != null) {
					jspMsg = "Cannot update menu item. Invalid parameters";
					
					if (isFirstUpdate) {
						// first time visiting the update page; no update parameters to process
						updatedItem = oldItem;
						jspMsg = null;
					} else {
						boolean validParams = processParameters(request, oldItem);					
						if (validParams) {
							boolean updated = menuItemDAO.update(oldItem);
							jspMsg = updated? "Successfuly update menu item" : "Failed to update menu item";

							// either way (updated or not), grab a fresh for the next update
							updatedItem = menuItemDAO.get(menuItemId);
						}
					}
				}
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menuItem/Update");
			jspMsg = "Exception Occured. See console for Details.";
		}
		
		request.setAttribute("menuItem", updatedItem);
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menuItem/update.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean processParameters(HttpServletRequest request, MenuItemBean menuItem) {
		String nameParam = ParametersUtil.validateString(request.getParameter("itemName"));
		String descParam = ParametersUtil.validateString(request.getParameter("itemDescription"));
		String priceParam = ParametersUtil.validateString(request.getParameter("itemPrice"));
		Double price = ParametersUtil.parseDouble(priceParam);
		
		boolean validParams = nameParam != null && descParam != null && price != null && price >= 0;
		validParams &= ParametersUtil.validateLengths(new String[] {nameParam, descParam}, new int[] {DBLabels.MENU_ITEM_NAME_LEN_MAX, DBLabels.MENU_ITEM_DESC_LEN_MAX});
		
		if (validParams)
			validParams &= Double.compare(price, DBLabels.MENU_ITEM_PRICE_MAX) < 1;
		
		if (validParams) {
			menuItem.setName(nameParam);
			menuItem.setDescription(descParam);
			menuItem.setPrice(price);
		}
		return validParams;
	}
}
