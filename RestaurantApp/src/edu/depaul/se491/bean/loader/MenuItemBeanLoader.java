package edu.depaul.se491.bean.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.MenuItemBean;

import static edu.depaul.se491.util.DBLabels.*;

/**
 * MenuItem Bean loader
 * - populate a preparedStatment using data store in a MenuItem bean
 * - populate/recreate a new MenuItem bean using data in a ResultSet
 * 
 * @author Malik
 */
public class MenuItemBeanLoader implements BeanLoader<MenuItemBean> {

	/**
	 * return a list of menuItem beans using menuItems data in the ResultSet (rows)
	 * Empty list is return if the ResultSet is empty
	 * The ResultSet cursor should be positioned before the first row before calling
	 * this method. Otherwise, the first row will not be included in the result.
	 * @param rs a ResultSet containing menu items data from the database
	 * @return list of menu items
	 */
	@Override
	public List<MenuItemBean> loadList(ResultSet rs) throws SQLException {
		List<MenuItemBean> mItems = new ArrayList<MenuItemBean>();
		while (rs.next())
			mItems.add(loadSingle(rs));
		return mItems;
	}


	/**
	 * return an menuItem bean using the ResultSet (a single row)
	 * THIS METHOD SHOULD BE CALLED ONLY WHEN (rs.next() is true before the call).
	 * It expects a ResultSet its cursor pointing at a row
	 * @param rs a ResultSet containing menuItem data from the database
	 * @return MenuItem bean object containing the data from a menuItem in the database
	 */
	@Override
	public MenuItemBean loadSingle(ResultSet rs) throws SQLException {
		MenuItemBean bean = new MenuItemBean();
		
		bean.setId(rs.getLong(ITEM_ID_LABEL));
		bean.setName(rs.getString(ITEM_NAME_LABEL));
		bean.setDescription(rs.getString(ITEM_DESC_LABEL));
		bean.setPrice(rs.getDouble(ITEM_PRICE_LABEL));
		
		return bean;
	}

	/**
	 * populate the PreparedStatment with data in the menuItem bean
	 * @param ps preparedStatement with sql string containing at least 3 '?'/placeholders
	 * @param bean menuItem bean with data
	 * @return return the passed ps
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, MenuItemBean bean) throws SQLException {
		int paramIndex = 1;
		ps.setString(paramIndex++, bean.getName());
		ps.setString(paramIndex++, bean.getDescription());
		ps.setDouble(paramIndex, bean.getPrice());
		return ps;
	}

}
