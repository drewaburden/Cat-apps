/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatMood.java
 * 
 * Description: 
 * 		Implements the CatMood class. This hold the information about the
 * 		cat's current mood level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 		Daniel Thomas
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public class CatMood extends Meter {
	public CatMood(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
	
	// Update the display of the meter
	@Override
	public void update() {
		// Update the progressBar's display
		progressBar.setProgress((int) getValue());
		// Update the progressBar's percentage display
		textView.setText(Mood.CONTENT.toString());
	}
}
