package com.jpmc.DO;

/**
 * Message containing product details and operations to be performed
 * 
 * @author Chaitali
 *
 */
public class Message {

	private Product product;
	private String operation;

	public Message() {
	}

	public Message(Product product, String operation) {
		super();
		this.product = product;
		this.operation = operation;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [product=" + product.toString() + ", operation=" + operation + "]";
	}

}
