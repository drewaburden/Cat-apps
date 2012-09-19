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
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatMood extends Meter {
	
	ProgressBar progressBarMood;
	TextView textViewMoodStatus;
	
	public CatMood(int initMood, int incAmount) {
		value = initMood;
		incrementAmount = incAmount;
	}

	protected void update() {
		//Need to update, first, the progress bar, then the text view.
		progressBarMood.setProgress(getValue());
		textViewMoodStatus.setText(Mood.HAPPY.toString());
	}

	public void startTracking(Activity _activity) {
		//This will track the value of the meter as it changes.
		activity = _activity;
		
		//Must find the UI components in order to change them
		textViewMoodStatus = (TextView) activity.findViewById(R.id.textViewMoodStatus);
		progressBarMood = (ProgressBar) activity.findViewById(R.id.progressBarMood);
		
		tracking = true;
		//Create a new thread in order to actually run the cat's Mood simulation
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
