package com.hogg.catapps.cat;

import android.app.Activity;

// This is used as a base class for any type of meter that needs to be shown to the user
public abstract class Meter {
	Activity activity; // What activity the UI components are in
	int minValue = 0;
	int maxValue = 100;
	int incrementAmount = 1;
	int updateWaitTime = 500; // Milliseconds to wait before updating in the tracking thread
	
	// Since most meters' values will be constantly changing, this variable will
	// determine whether or not the background meter updating should be running.
	boolean tracking = false;
	
	// This variable holds the core value of the meter,
	// whatever that may be implemented to represent.
	int value = 0;
	
	// Update the display of the meter
	protected abstract void update();
	
	// Start the looping thread to periodically change the value of the meter 
	public abstract void startTracking(Activity activity);
	public abstract void stopTracking();
	
	public void setUpdateWaitTime(int _updateWaitTime) {
		updateWaitTime = _updateWaitTime;
	}
	
	// Get and set value
	public void setValue(int _value) {
		value = _value;
	}
	public int getValue() {
		return value;
	}
<<<<<<< catsim
	public String toString() {
		return Integer.toString(value);
=======
<<<<<<< HEAD
	
	
	// Incrementing methods (with polymorphism)
	public void increment() {
		//Make sure the value does not exceed the max value
		if ((value + incrementAmount) <= maxValue ) {
			value += incrementAmount;
		}
	}
	
	public void increment(int i) {
		if (value + i <= maxValue) {
			value += i;
		} else {
			value = maxValue;
		}
	}

	// Decrementing methods (with polymorphism)
	public void decrement() {
		//Make sure the value does not equal less than the min value
		if ((value - incrementAmount) >= minValue )
			value -= incrementAmount;
	}
	
	public void decrement(int i) {
		if (value - i >= minValue) {
			value -= i;
		} else {
			value = minValue;
		}
=======
	public String toString() {
		return Integer.toString(value);
>>>>>>> Added some functionality and initial Player class
>>>>>>> local
	}
}
