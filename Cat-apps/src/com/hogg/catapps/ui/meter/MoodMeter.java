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
	double thirstThreshold = 0.01;
	double hungerThreshold = 0.01;
	
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
			} else if (Init.cat.getMood() == Mood.SAD) {
				setValue(-100);
			} else if (Init.cat.getMood() == Mood.DEAD && isHardcore()) {
				setValue(-100);
			}
			setValue(getValue() + 100);
		} else if((int) getValue() >= 100) {
			if(Init.cat.getMood() == Mood.HAPPY) {
				setValue(200);
			} else if(Init.cat.getMood() == Mood.CONTENT) {
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
	
	public void increment() {		
		int multiplier = 1;
		if(Init.cat.thirst.getValue() == 0 && isHardcore())
			multiplier *= 3;
		if(Init.cat.hunger.getValue() == 0 && isHardcore())
			multiplier *= 2;
		
		value += (getIncrementAmount()*multiplier);
	}
	
	public void increment(int x) {
		int multiplier = 1;
		if(Init.cat.thirst.getValue() == 0 && isHardcore())
			multiplier *= 3;
		if(Init.cat.hunger.getValue() == 0 && isHardcore())
			multiplier *= 2;
		
		value += (x*multiplier);
	}
	
	
	public void decrement() {
		int multiplier = 1;
		if(Init.cat.thirst.getValue() == 0 && isHardcore())
			multiplier *= 3;
		if(Init.cat.hunger.getValue() == 0 && isHardcore())
			multiplier *= 2;
		value -= ( ( thirstThreshold * (100 - Init.cat.thirst.getValue()) ) + (hungerThreshold * (100 - Init.cat.hunger.getValue()) ) * multiplier);
	}
	
	public void decrement(int i) {
		int multiplier = 1;
		if(Init.cat.thirst.getValue() == 0 && isHardcore())
			multiplier *= 3;
		if(Init.cat.hunger.getValue() == 0 && isHardcore())
			multiplier *= 2;
		value -= (i*multiplier);
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
