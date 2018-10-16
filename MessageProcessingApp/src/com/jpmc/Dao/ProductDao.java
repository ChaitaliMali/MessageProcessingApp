package com.jpmc.Dao;

import java.util.List;

import com.jpmc.DO.Product;

/**
 * Dao layer to perform insert update operations to inmemory database
 * 
 * @author Chaitali
 *
 */
public interface ProductDao {

	public Integer insertProduct(Product objProduct);

	public Integer updateProductValue(Product objProduct);

	public Integer updateProductQuantityAndSales(Product objProduct);

	public List<Product> getAllProductDetails();

	public List<Product> getProductFromProductType(String productName);

	public boolean productExists(Product product);

}
