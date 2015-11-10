/**
 * 
 */
package edu.depaul.se491.util;

import java.util.Random;

/**
 * @author Malik
 *
 */
public abstract class ConfirmationUtil {
	private static final Random rand;
	
	static {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
	}
	
	public static synchronized String getConfirmation() {
		// return a string hashcode of (currentTimeMillis + random integer)
		return "conf#" + Integer.toString(Long.toString(System.currentTimeMillis() + rand.nextInt()).hashCode());
	}
}
