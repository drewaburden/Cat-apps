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
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import android.app.Activity;

public class Cat {
	String name;
	Sex sex;
	public CatHearts hearts;
	public CatHunger hunger;
	public CatThirst thirst;
	public CatMood mood;
	
	public Cat(String _name, Sex _sex, int initHearts, int initMood, int initHunger, int initThirst) {
		name = _name;
		sex = _sex;
		
		// Initialize meters with specified initial values and an increment/decrement values
		hearts = new CatHearts(initHearts, 5);
		mood = new CatMood(initMood, 1);
		hunger = new CatHunger(initHunger, 1);
		thirst = new CatThirst(initThirst, 1);
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
}
