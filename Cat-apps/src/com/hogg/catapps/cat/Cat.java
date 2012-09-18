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
	
	public Cat(String _name, int initHearts, Sex _sex) {
		// Initialize hearts with a specified initial value and an increment/decrement value of 5
		hearts = new CatHearts(initHearts, 5);
		
		sex = _sex;
	}
	
	// Start tracking any meters
	public void start(Activity activity) {
		hearts.startTracking(activity); // Hearts meter
	}
	
	// Update all meters
	public void update() {
		updateHearts();
		//updateHunger();
		//updateThirst();
		//updateMood();
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
