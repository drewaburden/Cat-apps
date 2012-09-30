/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Meter.java
 * 
 * Description: 
 * 		Implements the abstract class Meter. This is a base class to be used to
 * 		to implement the hearts, mood, thirst, hunger, and hygiene meters, and
 * 		any other meters we might need.
 * 
 * Contributors:
 * 		Drew Burden
 * 		James Garner
 * 		Daniel Thomas
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

// This is used as a base class for any type of meter that needs to be shown to the user
public class Meter {
	Activity activity; // What activity the UI components are in
	ProgressBar progressBar; // ProgressBar
	TextView textView; // ProgressBar's percentage display
	double minValue = 0;
	double maxValue = 100;
	double incrementAmount = 1;
	int updateWaitTime = 1000; // Milliseconds to wait before updating in the
								// tracking thread
	Thread thread; // Since most meters' values will be constantly changing,
					// this variable will hold the thread that will be updating
					// the values in the background.
	volatile boolean tracking = false; // This variable will be set to false
										// when not tracking
	double value = 0; // This variable holds the core value of the meter, whatever
					// that may be implemented to represent.

	public Meter(int initValue, double incAmount) {
		value = initValue;
		incrementAmount = incAmount;
	}

	// Update the display of the meter
	public void update() {
		// Update the progressBar's display
		progressBar.setProgress((int) getValue());
		// Update the progressBar's percentage display
		textView.setText(this + "%");
	}

	// Check to see if the meter is empty
	public boolean isEmpty() {
		return (value == 0);
	}

	// Start the looping thread to periodically change the value of the meter
	public void startTracking(Activity _activity, int _progressBar,
			int _textView) {
		activity = _activity; // What activity the UI components are in

		// Find our UI components in the activity so we can manipulate them
		textView = (TextView) activity.findViewById(_textView);
		progressBar = (ProgressBar) activity.findViewById(_progressBar);
		tracking = true;
		Runnable r = new Runnable() {
			public void run() {
				while (tracking) {
					try {
						Thread.sleep(updateWaitTime);
					}
					// If tracking had been stopped while the sleep method
					// was waiting, we don't want to run the commands.
					catch (InterruptedException e) {
						break;
					}
					activity.runOnUiThread(new Runnable() {
						public void run() {
							// After waiting, decrement the meter and update its
							// display
							decrement();
							update();
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

	public void setUpdateWaitTime(int _updateWaitTime) {
		updateWaitTime = _updateWaitTime;
	}

	// Get and set value
	public void setValue(double _value) {
		value = _value;
	}

	public double getValue() {
		return value;
	}

	public String toString() {
		return Integer.toString((int) value);
	}

	// Incrementing methods (with polymorphism)
	public void increment() {
		// Make sure the value does not exceed the max value
		if ((value + incrementAmount) < maxValue) {
			value += incrementAmount;
		} else {
			value = maxValue;
		}
	}

	public void increment(double i) {
		if (value + i <= maxValue) {
			value += i;
		} else {
			value = maxValue;
		}
	}

	// Decrementing methods (with polymorphism)
	public void decrement() {
		// Make sure the value does not equal less than the min value
		if (value - incrementAmount > minValue) {
			value -= incrementAmount;
		} else {
			value = minValue;
		}
	}

	public void decrement(double i) {
		if (value - i > minValue) {
			value -= i;
		} else {
			value = minValue;
		}
	}
}
