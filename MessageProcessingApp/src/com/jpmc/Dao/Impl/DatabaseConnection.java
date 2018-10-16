package com.jpmc.Dao.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * get DB Connection
 * @author Chaitali
 *
 */
public class DatabaseConnection {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/Sales;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	
	DatabaseConnection() {
	}
	
	public static Connection getDBConnection() {
		Connection dbConnection = null;
			try {
				Class.forName(DB_DRIVER);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
			try {
				dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
				// openServerModeInBrowser();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		return dbConnection;
	}

}
