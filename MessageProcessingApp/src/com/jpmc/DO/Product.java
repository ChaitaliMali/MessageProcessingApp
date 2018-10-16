package com.jpmc.DO;

/**
 * Product details
 * 
 * @author Chaitali
 *
 */
public class Product {
	public Product() {
	}

	public Product(Integer pid, String type, Integer value, Integer quantity) {
		this.pid = pid;
		this.type = type;
		this.value = value;
		this.quantity = quantity;
	}

	public Product(String type, Integer value, Integer quantity) {
		this.type = type;
		this.value = value;
		this.quantity = quantity;
	}

	private Integer pid;
	private String type;
	private Integer value;

	/**
	 * Quantity is the total sales as per the problem statement
	 */
	private Integer quantity;

	/**
	 * Sales are just the message count of that product apart from adjustments
	 * messages (not required though)
	 */
	private Integer sales;

	/**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quatity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the sales
	 */
	public Integer getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", type=" + type + ", value=" + value + ", quantity=" + quantity + ", sales="
				+ sales + "]";
	}

}
