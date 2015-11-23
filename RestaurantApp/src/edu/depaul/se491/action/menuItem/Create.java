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
 * create a new menu item
 * @author Malik
 */
@WebServlet("/menuItem/create")
public class Create extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jspMsg = "Cannot create a new menu item. Invalid parameters";
		
		try {
			MenuItemBean newMenuItem = getMenuItem(request);
			if (newMenuItem != null) {
				boolean added = menuItemDAO.add(newMenuItem);
				jspMsg = added? "Successfuly created a new Menu Item" : "Failed to create a new Menu Item";
			}
		} catch (Exception e) {
			ExceptionUtil.printException(e, "menuItem/Create");
			jspMsg = "Exception Occured. See console for Details.";
		}
		request.setAttribute("msg", jspMsg);
		
		String jspURL = "/menuItem/create.jsp";
		getServletContext().getRequestDispatcher(jspURL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private MenuItemBean getMenuItem(HttpServletRequest request) {
		MenuItemBean newMenuItem = new MenuItemBean();
		
		boolean validParams = processParameters(request, newMenuItem);
		if (!validParams)
			newMenuItem = null;
		
		return newMenuItem;
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
