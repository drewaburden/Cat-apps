package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatHearts extends Meter {
	TextView textViewHeartsPercentage; // ProgressBar's percentage display
	ProgressBar progressBarHearts; // ProgressBar
	
	public CatHearts(int initHearts, int _incrementAmount) {
		value = initHearts;
		incrementAmount = _incrementAmount;
	}	
	
	public void update() {
		// Update the progressBar's display
		progressBarHearts.setProgress(getValue());
		// Update the progressBar's percentage display
		textViewHeartsPercentage.setText(this + "%");
	}

	// Start the looping thread to periodically change the value of the meter 
	public void startTracking(Activity _activity) {
		activity = _activity; // What activity the UI components are in
		
		// Find our UI components in the activity so we can manipulate them
		textViewHeartsPercentage = (TextView) activity.findViewById(R.id.textViewHeartsPercentage);
		progressBarHearts = (ProgressBar) activity.findViewById(R.id.progressBarHearts);
		
		tracking = true;
		new Thread(new Runnable() {
			public void run() {
				while(tracking) {
					try {
						Thread.sleep(updateWaitTime);
						
						// If tracking had been stopped while the sleep method
						// was waiting, we don't want to run the commands.
						if (!tracking) {
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					activity.runOnUiThread(new Runnable() {
						public void run() {
							// After waiting, decrement the meter and update its display
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
