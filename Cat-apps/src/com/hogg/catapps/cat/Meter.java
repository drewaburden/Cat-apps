package com.hogg.catapps.cat;

import android.app.Activity;

// This is used as a base class for any type of meter that needs to be shown to the user
public abstract class Meter {
	Activity activity; // What activity the UI components are in
	int value = 0;
	int minValue = 0;
	int maxValue = 100;
	public int incrementAmount = 1;
	// Since most meters' values will be constantly changing, this variable will
	// determine whether or not the background meter updating should be running.
	boolean tracking = false;
	
	// Update the display of the meter
	protected abstract void update();
	
	// Start the looping thread to periodically change the value of the meter 
	public abstract void startTracking(Activity activity);
	public abstract void stopTracking();
}
