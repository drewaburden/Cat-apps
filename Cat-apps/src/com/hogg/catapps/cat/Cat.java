/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Cat.java
 * 
 * Description: 
 * 		Implements the Cat class. Each of the player's cats will be defined as
 * 		a Cat class variable.
 * 
 * Contributors:
 * 		Drew Burden
 * 		Daniel Thomas
 * 		James Garner
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.cat.simulation.Meow;
import com.hogg.catapps.ui.meter.HungerMeter;
import com.hogg.catapps.ui.meter.MoodMeter;
import com.hogg.catapps.ui.meter.ThirstMeter;
import com.hogg.catapps.ui.meter.HeartsMeter;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Cat {
	Activity activity;
	String name;
	Sex sex;
	double difficultyMultiplier;
	int realisticHeartsHours = 24;
	int realisticHungerHours = 48;
	int realisticThirstHours = 24;
	public HeartsMeter hearts;
	public HungerMeter hunger;
	public ThirstMeter thirst;
	public MoodMeter mood;
	public Meow simulation;
	States currentState;
	TextView stateText;
	int timeRemaining;
	Mood currentMood;

	public Cat(String _name, Sex _sex, int initHearts, int initMood,
			int initHunger, int initThirst) {
		name = _name;
		sex = _sex;
		currentState = States.NOTHING;
		timeRemaining = 0;
		currentMood = Mood.CONTENT;
		
		Context appContext = Init.getAppContext();
		String difficultyPref = PreferenceManager.getDefaultSharedPreferences(appContext).getString("difficulty", appContext.getString(R.string.pref_difficulty_default));
		if(difficultyPref.equals(appContext.getString(R.string.pref_difficulty_easy))){
			difficultyMultiplier = 1/4.0; // 1/4 Realistic
		}
		else if(difficultyPref.equals(appContext.getString(R.string.pref_difficulty_normal))){
			difficultyMultiplier = 1/2.0; // 1/2 Realistic
		}
		else if(difficultyPref.equals(appContext.getString(R.string.pref_difficulty_hard))){
			difficultyMultiplier = 1.0; // 1x Realistic
		}
		else if(difficultyPref.equals(appContext.getString(R.string.pref_difficulty_hardcore))){
			difficultyMultiplier = 2.0; // 2x Realistic
		}
		
		
		// Initialize meters with specified initial values and an
		hearts = new HeartsMeter(initHearts, HeartsMeter.getMaxValue() / realisticHeartsHours / 3600 / difficultyMultiplier, 1.0 / difficultyMultiplier);
		
		mood = new MoodMeter(initMood, 1);

		// the 3600 is because 60 minutes in an hour times 60 seconds in a minute
		hunger = new HungerMeter(initHunger, HungerMeter.getMaxValue() / realisticHungerHours / 3600.0 / difficultyMultiplier);
		//hunger = new HungerMeter(initHunger, 0.5);

		// the 3600 is because 60 minutes in an hour times 60 seconds in a minute
		thirst = new ThirstMeter(initThirst, ThirstMeter.getMaxValue() / realisticThirstHours / 3600.0 / difficultyMultiplier);
		//thirst = new ThirstMeter(initThirst, 1);

		simulation = new Meow();
	}

	public void setActivity(Activity _activity) {
		activity = _activity;
	}

	public void startHearts(Activity activity, int progressBar, int textView) {
		hearts.startTracking(activity, progressBar, textView);
	}

	public void startMood(Activity activity, int progressBar, int textView) {
		mood.startTracking(activity, progressBar, textView);
	}

	public void startHunger(Activity activity, int progressBar, int textView) {
		hunger.startTracking(activity, progressBar, textView);
	}

	public void startThirst(Activity activity, int progressBar, int textView) {
		thirst.startTracking(activity, progressBar, textView);
	}

	public void stopTracking() {
		hearts.stopTracking(); // Hearts meter
		hunger.stopTracking(); // Hunger meter
		thirst.stopTracking(); // Thirst meter
		mood.stopTracking(); // Mood meter
	}

	// Update all meters
	public void update() {
		updateHearts();
		updateHunger();
		updateThirst();
		updateMood();
	}

	// Update hearts meter
	public void updateHearts() {
		hearts.update();
	}

	// Update hunger meter
	public void updateHunger() {
		hunger.update();
	}

	// Update thirst meter
	public void updateThirst() {
		thirst.update();
	}

	// Update mood meter
	public void updateMood() {
		mood.update();
	}

	// Get and set name
	public void setName(String _name) {
		name = _name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	// Get and set sex
	public void setSex(Sex _sex) {
		sex = _sex;
	}

	public Sex getSex() {
		return sex;
	}
	
	public void updateDifficulty(String difficulty){
		if(difficulty.equals(Init.getAppContext().getString(R.string.pref_difficulty_easy))){
			difficultyMultiplier = 1/4.0; // 1/4 Realistic
		}
		else if(difficulty.equals(Init.getAppContext().getString(R.string.pref_difficulty_normal))){
			difficultyMultiplier = 1/2.0; // 1/2 Realistic
		}
		else if(difficulty.equals(Init.getAppContext().getString(R.string.pref_difficulty_hard))){
			difficultyMultiplier = 1.0; // 1x Realistic
		}
		else if(difficulty.equals(Init.getAppContext().getString(R.string.pref_difficulty_hardcore))){
			difficultyMultiplier = 2.0; // 2x Realistic
		}
		
		hearts.setDecrementAmount(100.0 / realisticHeartsHours / 3600.0 / difficultyMultiplier);
		hearts.setIncrementAmount(1.0 / difficultyMultiplier);
		hunger.setDecrementAmount(100.0 / realisticHungerHours / 3600.0 / difficultyMultiplier);
		thirst.setDecrementAmount(100.0 / realisticThirstHours / 3600.0 / difficultyMultiplier);
	}

	// Get and set currentState
	public void setState(States _currentState) {
		currentState = _currentState;
		updateStateText();
	}

	public States getState() {
		return currentState;
	}
	
	public double getTimeRemaining() {
		return timeRemaining;
	}
	
	public void setTimeRemaining(int _timeRemaining) {
		timeRemaining = _timeRemaining;
	}
	
	public Mood getMood() {
		return currentMood;
	}
	
	public void setMood(Mood _currentMood) {
		currentMood = _currentMood;
	}

	public void updateStateText() {
		stateText = (TextView) activity.findViewById(R.id.textState);
		activity.runOnUiThread(
				new Runnable() {
					public void run() {
						stateText.setText(currentState.toString());
					}
				}
			);
	}
}
