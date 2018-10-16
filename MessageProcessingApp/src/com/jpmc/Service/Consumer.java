package com.jpmc.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpmc.DO.Message;
import com.jpmc.DO.Product;
import com.jpmc.Dao.ProductDao;
import com.jpmc.Factory.DaoFactory;
import com.jpmc.Util.Database;

/**
 * Consumes messages and logs the details
 * 
 * @author Chaitali
 *
 */
public class Consumer {
	private static final Logger logger = Logger.getLogger(Consumer.class.getName());
	private static List<String> adjustments = new ArrayList<String>();
	private static final String ADD = "Add";
	private static final String SUBTRACT = "Subtract";
	private static final String MULTIPLY = "Multiply";
	private static final String NONE = "None";
	public static int count = 0;
	ProductDao objProductDao = null;

	public Consumer() {
		// create the table if not exists
		Database.createProductTableIfNotExist();
	}

	public void consumeMessage(String message) {

		logger.info("Message recieved");
		logger.info("Processing message ");

		// converts the incoming message into Message and Product POJO
		Message objMessage = convertMessagetoPOJO(message);
		// maintaining to count of message in order to log report at every 10th message
		// and adjustment details after every 50th message
		count++;

		// checks if message received is not null
		if (objMessage != null) {
			// checks if the product is not null
			if (objMessage.getProduct() != null) {
				// get the instance of ProductDao to perform db operations
				objProductDao = DaoFactory.getProductDaoInstance();
				try {
					processMessage(objMessage);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// logs the product details and sales report after every 10th message receieved
		if (isMultipleof10(count)) {
			logReport();

		}

		// logs the adjustment reports, storing the adjustments messages in static list
		// object
		if (isMultipleof50(count)) {
			Waiter waiter = new Waiter(objMessage);
			new Thread(waiter, "waiter").start();
			boolean notify = false;
			if (null != adjustments) {
				logger.info("Adjustments that have been made to each sale type");
				for (String logAdjustments : adjustments) {
					logger.info(logAdjustments);
				}
				notify = true;
			}
			if (notify) {
				Notifier notifier = new Notifier(objMessage);
				new Thread(notifier, "notifier").start();
			}
		}
	}

	/**
	 * Converts the received message to POJO
	 * 
	 * @param msg
	 * @return
	 */
	private Message convertMessagetoPOJO(String msg) {
		List<String> message = null;
		/**
		 * splitting the string on blank space and all the words are maintained in list
		 */
		if (msg != null) {
			message = new ArrayList<String>();
			String[] details = msg.trim().split(" ");
			message = Arrays.asList(details);
		}

		Message objMessage = new Message();
		Product objProduct = null;
		if (message != null) {
			// cehcking for list size, if message contains 3 words then its simply updating
			// value or inserting the sale
			if (message.size() == 3) {
				if (message.contains(ADD)) {
					adjustments.add(msg);
					objMessage.setOperation(ADD);
					objProduct = new Product(message.get(2), Integer.parseInt(message.get(1).replaceAll("[\\D]", "")),
							null);
				} else if (message.contains(SUBTRACT)) {
					adjustments.add(msg);
					objMessage.setOperation(SUBTRACT);
					objProduct = new Product(message.get(2), Integer.parseInt(message.get(1).replaceAll("[\\D]", "")),
							null);
				} else if (message.contains(MULTIPLY)) {
					adjustments.add(msg);
					objMessage.setOperation(MULTIPLY);
					objProduct = new Product(message.get(2), Integer.parseInt(message.get(1).replaceAll("[\\D]", "")),
							null);
				} else {
					objMessage.setOperation(NONE);
					objProduct = new Product(message.get(0), Integer.parseInt(message.get(2).replaceAll("[\\D]", "")),
							1);
				}

			}
			// if the message contains more than 3 words, then we have to update the sales
			else if (message.size() > 3) {
				objMessage.setOperation(NONE);
				objProduct = new Product(message.get(3), Integer.parseInt(message.get(5).replaceAll("[\\D]", "")),
						Integer.parseInt(message.get(0)));

			}
			objMessage.setProduct(objProduct);

		}

		return objMessage;

	}

	/**
	 * logging the details to console
	 * 
	 * @return
	 */
	private boolean logReport() {
		objProductDao = DaoFactory.getProductDaoInstance();
		boolean reportDone = false;
		List<Product> objProductList = objProductDao.getAllProductDetails();
		if (objProductList != null && objProductList.size() != 0) {

			for (Product product : objProductList) {
				Integer p_quantity = product.getQuantity();
				Integer p_value = product.getValue();
				Double totalVal = (double) (p_quantity * p_value);
				logger.info(" Product Type : " + product.getType() + " Product Value : " + product.getValue()
						+ " Total Sales : " + product.getQuantity() + " Total Value : " + totalVal);

			}
			reportDone = true;
		}
		return reportDone;
	}

	/**
	 * process the message based on operations
	 * 
	 * @param objMessage
	 * @throws SQLException
	 */
	private void processMessage(Message objMessage) throws SQLException {
		Product objProduct = objMessage.getProduct();
		boolean productExists = false;
		productExists = objProductDao.productExists(objProduct);
		// checks if the product exists
		if (productExists) {
			// if the product exists, then get the product details
			List<Product> productList = objProductDao.getProductFromProductType(objProduct.getType());
			Product dbProduct = productList.get(0);
			// size should be 1, for every unique product we are maintaining it in 1 single
			// record
			if (productList.size() == 1) {
				// if we have to add the value, then just fetch the existing value present in
				// database, add and update it
				if (ADD.equals(objMessage.getOperation())) {
					Integer value = objProduct.getValue();
					value += dbProduct.getValue();
					objProduct.setValue(value);
					Integer result = objProductDao.updateProductValue(objProduct);
					if (result > 0) {
						logger.info("Value updated successuly");
					} else {
						logger.log(Level.SEVERE, "Unable to update value");
					}
				} // if we have to subtract the value, then just fetch the existing value present
					// in database, subtract and update it
				else if (SUBTRACT.equals(objMessage.getOperation())) {
					Integer value = objProduct.getValue();
					value = dbProduct.getValue() - value;
					objProduct.setValue(value);
					Integer result = objProductDao.updateProductValue(objProduct);
					if (result > 0) {
						logger.info("Value updated successuly");
					} else {
						logger.log(Level.SEVERE, "Unable to update value");
					}

				} // if we have to multiply the value, then just fetch the existing value present
					// in database, multiply and update it
				else if (MULTIPLY.equals(objMessage.getOperation())) {
					Integer value = objProduct.getValue();
					value *= dbProduct.getValue();
					objProduct.setValue(value);
					Integer result = objProductDao.updateProductValue(objProduct);
					if (result > 0) {
						logger.info("Value updated successuly");
					} else {
						logger.log(Level.SEVERE, "Unable to update value");
					}

				} // if there are no adjustments, then we have to update the sales
				else if (NONE.equals(objMessage.getOperation())) {

					Integer quantity = objProduct.getQuantity();

					quantity += dbProduct.getQuantity();
					Integer totalSales = dbProduct.getSales();
					totalSales++;
					objProduct.setQuantity(quantity);
					objProduct.setSales(totalSales);
					Integer updateResult = objProductDao.updateProductQuantityAndSales(objProduct);
					if (updateResult > 0) {
						logger.info("Quantity and Sales updated");
					} else {
						logger.log(Level.SEVERE, "Unable to update quantity");
					}
				}
			}
		} // if product does not exit, then we have to add it in db
		else {
			objProduct.setSales(1);
			Integer insertResult = objProductDao.insertProduct(objProduct);
			if (insertResult > 0) {
				logger.info("Product Added");
			} else {
				logger.log(Level.SEVERE, "Unable to add product");
			}
		}
	}

	/**
	 * Check if the number is multiple of 50
	 * 
	 * @param n
	 * @return
	 */
	private boolean isMultipleof50(Integer n) {
		while (n > 0)
			n = n - 50;

		if (n == 0)
			return true;

		return false;
	}

	/**
	 * check if the number is multiple of 10
	 * 
	 * @param n
	 * @return
	 */
	private boolean isMultipleof10(Integer n) {
		while (n > 0)
			n = n - 10;

		if (n == 0)
			return true;

		return false;
	}

}
