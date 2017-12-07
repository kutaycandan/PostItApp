package server;

import java.lang.Object;


/**
 * This is basic Binary Semaphore example which is showed in lecture slides
 * @author kutay
 *
 */
public class BinarySemaphore {
	boolean value;
	
	public BinarySemaphore(boolean initValue) {
		value = initValue; 
		
	}
	/**
	 * Binary lock
	 */
	public synchronized void P() {
		while (value == false)
			try {
				wait();
			} catch (InterruptedException e) {
				
				//e.printStackTrace();
			}
		value = false;
	}
	/**
	 * Binary unlock
	 */
	public synchronized void V() { 
		value = true;
		notify(); 
	}
	

}
