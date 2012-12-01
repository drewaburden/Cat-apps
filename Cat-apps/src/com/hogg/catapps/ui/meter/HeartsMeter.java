/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/HeartsMeter.java
 * 
 * Description: 
 * 		Implements the HeartsMeter class. This hold the information about the
 * 		cat's current heart level and manages the progress bar for it.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;

import com.hogg.catapps.Init;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeartsMeter extends Meter {
	final static double defaultValue = 0;
	
	public HeartsMeter(int initValue, double decAmount) {
		super(initValue, decAmount);
	}

	public HeartsMeter(int initValue, double decAmount, double incAmount) {
		super(initValue, decAmount, incAmount);
	}

	@Override
	// Start the looping thread to periodically change the value of the meter
	public void startTracking(Activity _activity, int _progressBar,
			int _textView) {
		activity = _activity; // What activity the UI components are in

		// Find our UI components in the activity so we can manipulate them
		textView = (TextView) activity.findViewById(_textView);
		progressBar = (ProgressBar) activity.findViewById(_progressBar);
		tracking = true;
		SharedPreferences preferences = Init.getAppContext()
				.getSharedPreferences("cat", Context.MODE_PRIVATE);
		this.value = (double) preferences.getFloat("hearts_value",
				(float) defaultValue);
		this.update();
	}

	@Override
	public void stopTracking() {
		tracking = false;
		
		Editor prefEditor = Init.getAppContext()
				.getSharedPreferences("cat", Context.MODE_PRIVATE).edit();
		prefEditor.putFloat("hearts_value", (float) this.value);
		prefEditor.apply();
	}

	@Override
	public void increment(double amount) {
		super.increment(amount);
		if (value + amount >= getMaxValue()) {
			setValue(minValue);
			Init.player.incrementMoney(100);
			Init.player.updateMoneyText();
			Init.player.food += 5;
			Init.player.water += 5;
			Init.player.updateButtonText();
		}
	}

	@Override
	public void increment() {
		super.increment();
		if (value + getIncrementAmount() >= getMaxValue()) {
			setValue(minValue);
			Init.player.incrementMoney(100);
			Init.player.updateMoneyText();
			Init.player.food += 5;
			Init.player.water += 5;
			Init.player.updateButtonText();
		}
	}
}
