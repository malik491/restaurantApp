package edu.depaul.se491.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.loader.MenuItemBeanLoader;
import edu.depaul.se491.dao.DAOFactory;
import edu.depaul.se491.util.Values;

/**
 * MenuItemDAO to access/modify account data in the databas
 * @author Malik
 *
 */
public class MenuItemDAO {
	private DAOFactory factory;
	private MenuItemBeanLoader loader;
	
	public MenuItemDAO(DAOFactory factory) {
		this.factory = factory;
		this.loader = new MenuItemBeanLoader();
	}
	
	/**
	 * return all menu items in the database
	 * Empty list is returned if there are no menu items in the database
	 * @return
	 * @throws SQLException
	 */
	public List<MenuItemBean> getAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<MenuItemBean> menu = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_ALL_SQL);
			
			ResultSet rs = ps.executeQuery();
			menu = loader.loadList(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return menu;
	}
	
	/**
	 * return menuItem associated with the given id
	 * Null is returned if there are no menuItem for the given id
	 * @param menuItemId
	 * @return
	 * @throws SQLException
	 */
	public MenuItemBean get(long menuItemId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		MenuItemBean menuItem = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(SELECT_BY_ID_SQL);
			
			ps.setLong(1, menuItemId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				menuItem = loader.loadSingle(rs);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return menuItem;
	}
	
	/**
	 * add a menu item to the database using the data in the menuItemBean
	 * @param menuItem menuItem data (excluding the id)
	 * @return
	 * @throws SQLException
	 */
	public boolean add(MenuItemBean menuItem) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(INSERT_ITEM_SQL);
			
			loader.loadParameters(ps, menuItem);
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED)
				throw new SQLException("MenuItemDAO.add(): multiple (or 0) rows affected by add(menuItem)");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	/**
	 * update an existing menuItem with new data in the menuItemBean
	 * @param menuItem updated menuItem data
	 * @return
	 * @throws SQLException
	 */
	public boolean update(MenuItemBean menuItem) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(UPADTE_ITEM_SQL);
			
			loader.loadParameters(ps, menuItem);
			ps.setLong(4, menuItem.getId());
			
			if (ps.executeUpdate() != Values.ONE_ROW_AFFECTED) 
				throw new SQLException("MenuItemDAO.update(): multiple (or 0) rows affected by update(menuItem)");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		return true;
	}
	
	private static final String SELECT_ALL_SQL = "SELECT * FROM menu_items ORDER BY menu_item_id";
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM menu_items WHERE (menu_item_id = ?)";
	
	private static final String INSERT_ITEM_SQL = "INSERT INTO menu_items (item_name, item_description, item_price) VALUES (?,?,?)";
	private static final String UPADTE_ITEM_SQL = "UPDATE menu_items SET item_name=?, item_description=?, item_price=? WHERE (menu_item_id=?)";

}
