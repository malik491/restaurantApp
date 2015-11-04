package edu.depaul.se491.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Malik
 */
public class ProductionConnectionDriver {
	private InitialContext initialContext;

	// In production situations
	public ProductionConnectionDriver() {
	}

	// For our special unit test - do not use unless you know what you are doing
	public ProductionConnectionDriver(InitialContext context) {
		initialContext = context;
	}

	public Connection getConnection() throws SQLException {
		try {
			if (initialContext == null)
				initialContext = new InitialContext();
			return ((DataSource) (((Context) initialContext.lookup("java:comp/env"))).lookup("jdbc/se491"))
					.getConnection();
		} catch (NamingException e) {
			throw new SQLException(("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}
}
