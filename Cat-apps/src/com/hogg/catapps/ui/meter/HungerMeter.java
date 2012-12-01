/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/HungerMeter.java
 * 
 * Description: 
 * 		Implements the HungerMeter class. This hold the information about the
 * 		cat's current hunger level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 		Daniel Thomas
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hogg.catapps.Init;


public class HungerMeter extends Meter {
	final static double defaultValue = 50;
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 
	
	public HungerMeter(int initValue, double incAmount) {
		super(initValue, incAmount);
	}
	
	@Override
	public void startTracking(Activity _activity, int _progressBar,
			int _textView) {
		super.startTracking(_activity, _progressBar, _textView);

		SharedPreferences preferences = Init.getAppContext()
				.getSharedPreferences("cat", Context.MODE_PRIVATE);
		this.value = (double) preferences.getFloat("hunger_value", (float) defaultValue);
		Long currTime = System.currentTimeMillis();
		Long timeChange = (currTime - preferences.getLong("hunger_time", currTime))/1000; // in Seconds
		this.value = this.value - (timeChange * this.decrementAmount);
		this.update();
	}
	
	@Override
	public void stopTracking() {
		super.stopTracking();
		Editor prefEditor = Init.getAppContext().getSharedPreferences("cat",
				Context.MODE_PRIVATE).edit();
		prefEditor.putFloat("hunger_value", (float) this.value);
		prefEditor.putLong("hunger_time", System.currentTimeMillis());
		prefEditor.apply();
	}
}
