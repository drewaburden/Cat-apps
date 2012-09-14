package com.hogg.catapps.cat;

import android.app.Activity;

public class Cat {
	String name;
	public CatHearts hearts;
	// Todo:
	//public CatHunger hunger;
	//public CatThirst thirst;
	//public CatMood mood;
	
	public Cat(String _name, int initHearts) {
		// Initialize hearts with a specified initial value and an increment/decrement value of 5
		hearts = new CatHearts(initHearts, 5);
	}
	
	// Start tracking any meters
	public void start(Activity activity) {
		hearts.startTracking(activity); // Hearts meter
	}

}
