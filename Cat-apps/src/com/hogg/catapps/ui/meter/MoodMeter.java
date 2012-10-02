/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/MoodMeter.java
 * 
 * Description: 
 * 		Implements the MoodMeter class. This hold the information about the
 * 		cat's current mood level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;

import com.hogg.catapps.cat.Mood;

public class MoodMeter extends Meter {
	public MoodMeter(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
	
	// Update the display of the meter
	@Override
	public void update() {
		// Update the progressBar's display
		progressBar.setProgress(getValue());
		// Update the progressBar's percentage display
		textView.setText(Mood.CONTENT.toString());
	}
}
