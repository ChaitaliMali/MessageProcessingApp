package com.jpmc.Service;

import com.jpmc.DO.Message;

/**
 * Waiter class
 * 
 * @author Chaitali
 *
 */
public class Waiter implements Runnable {
	private Message msg;

	public Waiter(Message m) {
		this.msg = m;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		synchronized (msg) {
			try {
				System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
				msg.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
			// process the message now
		}
	}

}
