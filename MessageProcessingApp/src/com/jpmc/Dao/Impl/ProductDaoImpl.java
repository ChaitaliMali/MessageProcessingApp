package com.jpmc.Dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpmc.DO.Product;
import com.jpmc.Dao.ProductDao;
import com.jpmc.Service.Consumer;
import com.jpmc.Util.ResultSetMapper;

/**
 * Dao impl to perform perform insert update operations
 * 
 * @author Chaitali
 *
 */
public class ProductDaoImpl implements ProductDao {

	private Connection dbConnection = null;
	private static final Logger logger = Logger.getLogger(Consumer.class.getName());

	public ProductDaoImpl() {
	}

	/**
	 * inserts the product details
	 */
	@Override
	public Integer insertProduct(Product objProduct) {
		dbConnection = DatabaseConnection.getDBConnection();
		Integer result = null;
		PreparedStatement preparedStatement = null;

		String insertQuery = "INSERT INTO Product" + "(type, value, quantity, sales) VALUES" + "(?,?,?,?)";
		try {
			dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(insertQuery);
			preparedStatement.setString(1, objProduct.getType());
			preparedStatement.setInt(2, objProduct.getValue());
			preparedStatement.setInt(3, objProduct.getQuantity());
			preparedStatement.setInt(4, objProduct.getSales());

			result = preparedStatement.executeUpdate();
			preparedStatement.close();

			dbConnection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * gets all the product details
	 */
	@Override
	public List<Product> getAllProductDetails() {
		dbConnection = DatabaseConnection.getDBConnection();
		String SelectQuery = "select * from Product";
		PreparedStatement selectPreparedStatement = null;
		List<Product> objProductList = null;
		try {
			dbConnection.setAutoCommit(false);

			selectPreparedStatement = dbConnection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			ResultSetMapper<Product> objMapper = new ResultSetMapper<Product>();
			objProductList = objMapper.mapRersultSetToObject(rs, Product.class);
			selectPreparedStatement.close();
			dbConnection.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception Message ", e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return objProductList;
	}

	/**
	 * fetches the product based on the type
	 */
	@Override
	public List<Product> getProductFromProductType(String productType) {
		dbConnection = DatabaseConnection.getDBConnection();
		String SelectQuery = "select * from Product where type = '" + productType + "';";
		PreparedStatement selectPreparedStatement = null;
		List<Product> objProductList = null;
		try {
			dbConnection.setAutoCommit(false);

			selectPreparedStatement = dbConnection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			ResultSetMapper<Product> objMapper = new ResultSetMapper<Product>();
			objProductList = objMapper.mapRersultSetToObject(rs, Product.class);

			selectPreparedStatement.close();
			dbConnection.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception Message ", e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return objProductList;

	}

	/**
	 * updates the product values if any adjustments are made
	 */
	@Override
	public Integer updateProductValue(Product objProduct) {
		dbConnection = DatabaseConnection.getDBConnection();
		Integer result = null;
		PreparedStatement preparedStatement = null;
		String updateQuery = "Update Product set value = " + objProduct.getValue() + " where type = '"
				+ objProduct.getType() + "';";
		try {
			dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(updateQuery);
			result = preparedStatement.executeUpdate();

			preparedStatement.close();
			dbConnection.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception Message ", e);
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * updates the quantity and sales
	 */
	@Override
	public Integer updateProductQuantityAndSales(Product objProduct) {
		dbConnection = DatabaseConnection.getDBConnection();
		Integer result = null;
		PreparedStatement preparedStatement = null;
		String updateQuery = "Update Product set quantity = " + objProduct.getQuantity() + ", sales = "
				+ objProduct.getSales() + " where type = '" + objProduct.getType() + "';";
		try {
			dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(updateQuery);
			result = preparedStatement.executeUpdate();
			preparedStatement.close();
			dbConnection.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception Message ", e);
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * checks if product exists
	 */
	@Override
	public boolean productExists(Product product) {
		dbConnection = DatabaseConnection.getDBConnection();
		boolean result = false;
		String SelectQuery = "select count(*) AS rowcount from Product where type = '" + product.getType() + "';";
		PreparedStatement selectPreparedStatement = null;
		try {
			dbConnection.setAutoCommit(false);

			selectPreparedStatement = dbConnection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			if (rs != null) {
				rs.next();
				int count = rs.getInt("rowcount");
				if (count > 0) {
					result = true;
				}
			}

			selectPreparedStatement.close();
			dbConnection.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception Message ", e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
