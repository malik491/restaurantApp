package edu.depaul.se491.bean.loader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.depaul.se491.bean.AddressBean;
import edu.depaul.se491.bean.OrderBean;
import edu.depaul.se491.enums.OrderStatus;
import edu.depaul.se491.enums.OrderType;
import edu.depaul.se491.util.Values;
import static edu.depaul.se491.util.DBLabels.*;


/**
 * Order Bean loader
 * - populate a preparedStatment using data store in an Order bean
 * - populate/recreate a new Order bean using data in a ResultSet
 * 
 * @author Malik
 */
public class OrderBeanLoader implements BeanLoader<OrderBean>{
	private AddressBeanLoader addressLoader;	
	
	public OrderBeanLoader() {
		addressLoader = new AddressBeanLoader();
	}
	
	/**
	 * return a list of order beans using orders data in the ResultSet (rows)
	 * Empty list is return if the ResultSet is empty
	 * The ResultSet cursor should be positioned before the first row before calling
	 * this method. Otherwise, the first row will not be included in the result.
	 * @param rs a ResultSet containing orders data from the database
	 * @return list of orders
	 */
	@Override
	public List<OrderBean> loadList(ResultSet rs) throws SQLException {
		List<OrderBean> orders = new ArrayList<>();
		while (rs.next())
			orders.add(loadSingle(rs));
	
		return orders;
	}
	
	/**
	 * return an order bean using the ResultSet (a single row)
	 * THIS METHOD SHOULD BE CALLED ONLY WHEN (rs.next() is true before the call).
	 * It expects a ResultSet its cursor pointing at a row
	 * @param rs a ResultSet containing order data from the database
	 * @return order bean object containing the data from an order in the database
	 */
	@Override
	public OrderBean loadSingle(ResultSet rs) throws SQLException {
		OrderBean bean = new OrderBean();
		
		bean.setId(rs.getLong(ORDER_ID_LABEL));	
		bean.setStatus(OrderStatus.valueOf(rs.getString(ORDER_STATUS_LABEL)));
		bean.setType(OrderType.valueOf(rs.getString(ORDER_TYPE_LABEL)));
		bean.setTimestamp(rs.getTimestamp(ORDER_TIMESTAMP_LABEL));
		bean.setTotal(rs.getDouble(ORDER_TOTAL_LABEL));
		bean.setConfirmation(rs.getString(ORDER_CONF_LABEL));
		
		boolean isNullAddr = rs.getLong(ADDRESS_ID_LABEL) == Values.SQL_NULL;
		AddressBean address = isNullAddr? null : addressLoader.loadSingle(rs);
		bean.setDeliveryAddress(address);	
		
		return bean;
	}

	/**
	 * populate the PreparedStatment with data in the order bean
	 * @param ps preparedStatement with sql string containing at least 5 '?'/placeholders
	 * @param bean order bean with data
	 * @return return the passed ps
	 */
	@Override
	public void loadParameters(PreparedStatement ps, OrderBean bean, int paramIndex) throws SQLException {
		ps.setString(paramIndex++, bean.getStatus().name());
		ps.setString(paramIndex++, bean.getType().name());
		ps.setTimestamp(paramIndex++, new Timestamp(System.currentTimeMillis()));
		ps.setDouble(paramIndex++, bean.getTotal());
		ps.setString(paramIndex++, bean.getConfirmation());
		
		AddressBean address = bean.getDeliveryAddress(); // null for pickup orders
		if (address == null)
			ps.setNull(paramIndex, Values.SQL_NULL);
		else
			ps.setLong(paramIndex, address.getId());
	}
}
