package com.jpmc.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpmc.Dao.Impl.DatabaseConnection;

/**
 * Database Utility class to create table if not exists and gets db connection
 * 
 * @author Chaitali
 *
 */
public class Database {

	public static void createProductTableIfNotExist() {
		PreparedStatement createPreparedStatement = null;

		Connection connection = DatabaseConnection.getDBConnection();
		try {
			String createQuery = "CREATE TABLE IF NOT EXISTS Product(pid INT not null auto_increment primary key, type varchar(255), value INT, quantity INT, sales INT)";
			connection.setAutoCommit(true);
			createPreparedStatement = connection.prepareStatement(createQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void openServerModeInBrowser() throws SQLException { Server
	 * server = Server.createTcpServer().start();
	 * System.out.println("Server started and connection is open.");
	 * System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:test"); }
	 */

}
