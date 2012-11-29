/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/Meter.java
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

package com.hogg.catapps.ui.meter;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

// This is used as a base class for any type of meter that needs to be shown to the user
public class Meter {
	Activity activity; // What activity the UI components are in
	ProgressBar progressBar; // ProgressBar
	TextView textView; // ProgressBar's percentage display
	double minValue = 0.0;
	double maxValue = 100.0;
	protected double incrementAmount = 1.0;
	protected double decrementAmount = 1.0;
	int updateWaitTime = 1000; // Milliseconds to wait before updating in the
								// tracking thread
	Thread thread; // Since most meters' values will be constantly changing,
					// this variable will hold the thread that will be updating
					// the values in the background.
	volatile boolean tracking = false; // This variable will be set to false
										// when not tracking
	double value = 0.0; // This variable holds the core value of the meter, whatever
					// that may be implemented to represent.

	public Meter(int initValue, double decAmount) {
		value = initValue;
		setDecrementAmount(decAmount);
	}
	
	public Meter(int initValue, double decAmount, double incAmount){
		value = initValue;
		setIncrementAmount(incAmount);
		setDecrementAmount(decAmount);
	}

	// Update the display of the meter
	public void update() {
		// Update the progressBar's display
		progressBar.setProgress((int) Math.ceil(getValue()));
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
	
	public void resetTracking(){
		stopTracking();
		startTracking(this.activity, this.progressBar.getId(), this.textView.getId());
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

	public double getIncrementAmount() {
		return incrementAmount;
	}

	public void setIncrementAmount(double incrementAmount) {
		this.incrementAmount = incrementAmount;
	}

	public double getDecrementAmount() {
		return decrementAmount;
	}

	public void setDecrementAmount(double decrementAmount) {
		this.decrementAmount = decrementAmount;
	}

	public String toString() {
		return Integer.toString((int) Math.ceil(value));
	}

	// Incrementing methods (with polymorphism)
	public void increment() {
		// Make sure the value does not exceed the max value
		if ((value + getIncrementAmount()) < maxValue) {
			value += getIncrementAmount();
		} else {
			value = maxValue;
		}
	}

	public void increment(double amount) {
		if (value + amount <= maxValue) {
			value += amount;
		} else {
			value = maxValue;
		}
	}

	// Decrementing methods (with polymorphism)
	public void decrement() {
		// Make sure the value does not equal less than the min value
		if (value - getDecrementAmount() > minValue) {
			value -= getDecrementAmount();
		} else {
			value = minValue;
		}
	}

	public void decrement(double amount) {
		if (value - amount > minValue) {
			value -= amount;
		} else {
			value = minValue;
		}
	}
}
