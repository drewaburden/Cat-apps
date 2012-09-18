package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatHygiene extends Meter {
	
	ProgressBar progressBarHygiene;
	TextView textViewHygienePercentage;
	
	// Upon decrementing or incrementing the cat's mood will go up or down by: 10% of the incrementAmount
	double moodPercentageVariable = 0.10; 
		
	
	public CatHygiene(int initHygiene, int incAmount) {
		value = initHygiene;
		incrementAmount = incAmount;
	}
	
	protected void update() {
		//Need to update, first, the progress bar, then the text view.
		progressBarHygiene.setProgress(getValue());
		textViewHygienePercentage.setText(Integer.toString(getValue()) + "%");
	}

	public void startTracking(Activity _activity) {
		//This will track the value of the meter as it changes.
		activity = _activity;
		
		//Must find the UI components in order to change them
		//textViewHygienePercentage = (TextView) activity.findViewById(R.id.textViewHygienePercentage);
		//progressBarHygiene = (ProgressBar) activity.findViewById(R.id.progressBarHygiene);
		
		tracking = true;
		//Create a new thread in order to actually run the cat's Hygiene simulation
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
