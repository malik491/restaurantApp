package edu.depaul.se491.bean.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.MenuItemBean;
import edu.depaul.se491.bean.OrderItemBean;

import static edu.depaul.se491.util.DBLabels.*;

/**
 * OrderItem Bean loader
 * - populate a preparedStatment using data store in an orderItem bean
 * - populate/recreate a new orderItem bean using data in a ResultSet
 * 
 * @author Malik
 */
public class OrderItemBeanLoader implements BeanLoader<OrderItemBean> {
	private MenuItemBeanLoader loader;
	
	public OrderItemBeanLoader() {
		loader = new MenuItemBeanLoader();
	}
	
	/**
	 * return a list of orderItem beans using order items data in the ResultSet (rows)
	 * Empty list is return if the ResultSet is empty
	 * The ResultSet cursor should be positioned before the first row before calling
	 * this method. Otherwise, the first row will not be included in the result.
	 * @param rs a ResultSet containing order items data from the database
	 * @return list of order items
	 */
	@Override
	public List<OrderItemBean> loadList(ResultSet rs) throws SQLException {
		List<OrderItemBean> oItems = new ArrayList<>();
		while (rs.next())
			oItems.add(loadSingle(rs));
		return oItems;
	}

	/**
	 * return an orderItem bean using the ResultSet (a single row)
	 * THIS METHOD SHOULD BE CALLED ONLY WHEN (rs.next() is true before the call).
	 * It expects a ResultSet its cursor pointing at a row
	 * @param rs a ResultSet containing orderItem data from the database
	 * @return orderItem bean object containing the data from an orderItem in the database
	 */
	@Override
	public OrderItemBean loadSingle(ResultSet rs) throws SQLException {
		OrderItemBean bean = new OrderItemBean();
		
		MenuItemBean menuItem = loader.loadSingle(rs);
		
		bean.setMenuItem(menuItem);
		bean.setQuantity(rs.getInt(QUANTITY_LABEL));
		
		return bean;
	}

	
	@Override
	public void loadParameters(PreparedStatement ps, OrderItemBean bean, int paramIndex) throws SQLException {
		throw new SQLException("Unsupported method. order id paramater is needed.");
	}
	

	/**
	 * populate the PreparedStatment with data in the orderItem bean
	 * @param ps preparedStatement with sql string containing at least 3 '?'/placeholders
	 * @param bean orderItem bean with data
	 * @param orderId orderId for the order contain this order item
	 * @return return the passed ps
	 */
	public void loadParameters(PreparedStatement ps, OrderItemBean bean, int paramIndex, long orderId) throws SQLException {
		ps.setLong(paramIndex++, orderId);
		ps.setLong(paramIndex++, bean.getMenuItem().getId());
		ps.setLong(paramIndex, bean.getQuantity());

	}
}
