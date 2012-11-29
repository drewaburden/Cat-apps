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
 * 		Daniel Thomas
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;

import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.hogg.catapps.Init;
import com.hogg.catapps.cat.Mood;
import com.hogg.catapps.R;

public class MoodMeter extends Meter {
	
	public MoodMeter(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
	
	// Update the display of the meter
	@Override
	public void update() {
		// Update the progressBar's display
		if((int) getValue() <= 0) {
			//Need to change the mood to one below
			if(Init.cat.getMood() == Mood.HAPPY) {
				Init.cat.setMood(Mood.CONTENT);
				changeIcon(R.drawable.ic_mood_content);
			} else if(Init.cat.getMood() == Mood.CONTENT) {
				Init.cat.setMood(Mood.MAD);
				changeIcon(R.drawable.ic_mood_mad);
			} else if (Init.cat.getMood() == Mood.MAD) {
				Init.cat.setMood(Mood.SAD);
				changeIcon(R.drawable.ic_mood_sad);
			} else if (Init.cat.getMood() == Mood.SAD && isHardcore()) {
				Init.cat.setMood(Mood.DEAD);
				changeIcon(R.drawable.ic_mood_dead);
			}				
			setValue(getValue() + 100);
		} else if((int) getValue() >= 100) {
			if(Init.cat.getMood() == Mood.CONTENT) {
				Init.cat.setMood(Mood.HAPPY);
				changeIcon(R.drawable.ic_mood_happy);
			} else if (Init.cat.getMood() == Mood.MAD) {
				Init.cat.setMood(Mood.CONTENT);
				changeIcon(R.drawable.ic_mood_content);
			} else if(Init.cat.getMood() == Mood.SAD) {
				Init.cat.setMood(Mood.MAD);
				changeIcon(R.drawable.ic_mood_mad);
			}
			setValue(getValue() - 100);
		}
		progressBar.setProgress((int) getValue());
		
		// Update the progressBar's percentage display
		textView.setText(Init.cat.getMood().toString());
	}
	
	//TODO: Make this scale correctly. Currently, the resources it changes to are too large.
	private void changeIcon(int _id) {
		ImageView iconView = (ImageView) activity.findViewById(R.id.imageMood);
		iconView.setImageDrawable(activity.getResources().getDrawable(_id));
	}
	
	private boolean isHardcore() {
		if( (PreferenceManager.getDefaultSharedPreferences(Init.getAppContext()).getString("difficulty", "none")).equals("Hardcore") ) {
			return true;
		} else {
			return false;
		}
	}
}
