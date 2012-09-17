package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatThirst extends Meter{
	ProgressBar progressBarThirst;
	TextView textViewThirstPercentage;
	
	CatThirst(int initThirst, int incAmount) {
		value = initThirst;
		incrementAmount = incAmount;
	}

	@Override
	protected void update() {
		progressBarThirst.setProgress(getValue());
		textViewThirstPercentage.setText(Integer.toString(getValue()) + "%");
	}

	@Override
	public void startTracking(Activity _activity) {
		//This will track the value of the meter as it changes.
		activity = _activity;
		
		//Must find the UI components in order to change them
		textViewThirstPercentage = (TextView) activity.findViewById(R.id.textViewThirstPercentage);
		progressBarThirst = (ProgressBar) activity.findViewById(R.id.progressBarThirst);
		
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

	@Override
	public void stopTracking() {
		tracking = false;
	}

	
	
}
