/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/background/BackgroundSleepThread.java
 * 
 * Description: 
 * 		Implements the BackgroundSleepThread class. This is used to sleep in
 * 		a background thread for a specified number of milliseconds before
 * 		executing a specified method.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.background;

import android.app.Activity;

// Wait for sleepTime milliseconds and runs method in activity's thread after done waiting.
public class BackgroundSleepThread {
	Thread thread;
	boolean interrupted;
	
	public BackgroundSleepThread(final Activity activity, final Runnable method, final int sleepTime) {
		interrupted = false;
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					// Wait for the specified amount of time
					Thread.sleep(sleepTime);
					
					if (!interrupted) {
						// Run the specified method in activity's thread
						activity.runOnUiThread(method);
					}
				}
				catch (InterruptedException e) { /* Do nothing */ }
			}
		});
		thread.start();
	}

	public void interrupt() {
		interrupted = true;
		thread.interrupt();
	}
}
