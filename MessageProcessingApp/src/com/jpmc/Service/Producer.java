package com.jpmc.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Producer class will read the messages from the txt file and send the messages
 * to the consumer
 * 
 * @author Chaitali
 *
 */
public class Producer {

	public void sendMessage() {
		String workingDir = System.getProperty("user.dir");
		File f = new File(workingDir + "\\data\\Messages.txt");
		Consumer objConsumer = new Consumer();
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (null != line) {
					objConsumer.consumeMessage(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}