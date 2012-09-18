/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatThirst.java
 * 
 * Description: 
 * 		Implements the CatThirst class. This hold the information about the
 * 		cat's current thirst level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 
 * Copyright � 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatThirst extends Meter{
	ProgressBar progressBarThirst;
	TextView textViewThirstPercentage;
	
	// Upon decrementing or incrementing the cat's mood will go up or down by: 10% of the incrementAmount
	double moodPercentageVariable = 0.10; 
		
	
	CatThirst(int initThirst, int incAmount) {
		value = initThirst;
		incrementAmount = incAmount;
	}

	protected void update() {
		progressBarThirst.setProgress(getValue());
		textViewThirstPercentage.setText(Integer.toString(getValue()) + "%");
	}

	public void startTracking(Activity _activity) {
		//This will track the value of the meter as it changes.
		activity = _activity;
		
		//Must find the UI components in order to change them
		//textViewThirstPercentage = (TextView) activity.findViewById(R.id.textViewThirstPercentage);
		//progressBarThirst = (ProgressBar) activity.findViewById(R.id.progressBarThirst);
		
		tracking = true;
		//Create a new thread in order to actually run the cat's Thirst simulation
		new Thread(new Runnable() {
			public void run() {
				while (tracking) {
					try {
						Thread.sleep(updateWaitTime);
						
						//Sleep the appropriate time, then check to see if we are still tracking before continuing.
						if(!tracking) {
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					activity.runOnUiThread(new Runnable() {
						public void run() {
							//After sleeping for the appropriate time, decrement value, and update display.
							decrement();
							update();
						}
					});
				}
			}
		}).start();
	}
	
	public void stopTracking() {
		tracking = false;
	}

	
	
}
