package com.jpmc.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpmc.DO.Product;
import com.jpmc.Dao.ProductDao;
import com.jpmc.Factory.DaoFactory;
import com.jpmc.Service.Consumer;
import com.jpmc.Util.Database;

/**
 * Test class
 * @author Chaitali
 *
 */
public class DaoTest {

	@Before
	@Test
	public void testTables() {
		Database.createProductTableIfNotExist();
		System.out.println("Product table created successfuly");

		insertValues();
		ProductDao objProductDao = DaoFactory.getProductDaoInstance();
		List<Product> objList = objProductDao.getAllProductDetails();
		assertNotNull(objList);

	}

	private void insertValues() {
		ProductDao objProductDao = DaoFactory.getProductDaoInstance();
		Product objProduct1 = new Product("Banana", 10, 2);
		objProduct1.setSales(1);
		Integer result = objProductDao.insertProduct(objProduct1);
		System.out.println("Result : " + result);
	}

	@After
	@Test
	public void testUpdate() {
		ProductDao objProductDao = DaoFactory.getProductDaoInstance();
		Product objProduct1 = new Product("Banana", 20, 2);
		Integer result = objProductDao.updateProductQuantityAndSales(objProduct1);
		assertTrue(result > 0);
	}

	@Test
	public void testConsumer() {

		String msg1 = "apple at 10p";
		String msg2 = "20 sales of apple at 10p each";
		String msg3 = "Add 20p apple";
		String msg4 = "Subtract 20p apple";
		String msg5 = "Multiply 2p apple";
		Consumer objConsumer = new Consumer();
		objConsumer.consumeMessage(msg1);
		objConsumer.consumeMessage(msg2);
		objConsumer.consumeMessage(msg3);
		objConsumer.consumeMessage(msg4);
		objConsumer.consumeMessage(msg5);

	}

}
