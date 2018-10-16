package com.jpmc.Main;

import com.jpmc.Service.Producer;

/**
 * Main class will call Producer sendMessage method
 * 
 * @author Chaitali
 *
 */
public class Main {

	public static void main(String[] args) {

		Producer objP = new Producer();
		objP.sendMessage();
	}

}
