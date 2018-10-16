package com.jpmc.Factory;

import com.jpmc.Dao.ProductDao;
import com.jpmc.Dao.Impl.ProductDaoImpl;

/**
 * DaoFactory class to provide ProductDao object
 * 
 * @author Chaitali
 *
 */
public class DaoFactory {
	ProductDao productDao = null;

	public static ProductDao getProductDaoInstance() {
		ProductDao productDao = new ProductDaoImpl();
		return productDao;
	}

}
