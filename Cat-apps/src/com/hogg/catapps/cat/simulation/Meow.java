/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/simulation/Meow.java
 * 
 * Description: 
 * 		Currently, this randomly makes the cat meow every so often. 
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat.simulation;

import java.util.Random;

import com.hogg.catapps.Init;
import com.hogg.catapps.background.BackgroundSleepThread;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Meow {
	Activity activity;
	Thread thread;
	TextView textView;
	boolean tracking = false;
	Random randomGenerator;
	
	// Start the looping thread to periodically change the value of the meter
	public void startTracking(Activity _activity, int _textView) {
		activity = _activity; // What activity the UI components are in
		randomGenerator = new Random();
	
		// Find our UI components in the activity so we can manipulate them
		textView = (TextView) activity.findViewById(_textView);
		tracking = true;
		Runnable r = new Runnable() {
			public void run() {
				while (tracking) {
					try {
						Thread.sleep(1000);
					}
					// If tracking had been stopped while the sleep method
					// was waiting, we don't want to run the commands.
					catch (InterruptedException e) {
						break;
					}
					activity.runOnUiThread(new Runnable() {
						public void run() {
							// After waiting, decrement the meter and update its
							// display, but only meow if the cat is awake
							if (randomGenerator.nextInt(15) == 0 && Init.cat.getState().name.equals("Sleeping") != true) {
								Log.i("slerp", "Meowed");
								// Find the "Meow!" text's TextView so we can manipulate it
								
								// Only if the TextView is invisible
								if (textView.getVisibility() != View.VISIBLE) {
									// Make it visible
									textView.setVisibility(View.VISIBLE);

									// Wait for .5 seconds (using threads so we don't freeze the UI)
									// and then make the text invisible again.
									Runnable makeTextInvisible = new Runnable() {
										public void run() {
											textView.setVisibility(View.INVISIBLE);
										}
									};
									new BackgroundSleepThread(activity, makeTextInvisible, 1000);
								}
							}
						}
					});
				}
			}
		};
		thread = new Thread(r);
		thread.start();
	}
	
	public void stopTracking() {
		tracking = false;
		// Interrupt the thread because there is a chance that the tracking
		// variable
		// won't work immediately. If this happens, another thread might be
		// spawned
		// on top of the previously running thread, causing the sleep to happen
		// twice
		// as often.
		thread.interrupt();
	}
	
}
