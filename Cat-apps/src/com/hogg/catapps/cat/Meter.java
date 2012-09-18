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
	public String toString() {
		return Integer.toString(value);
	}
}
