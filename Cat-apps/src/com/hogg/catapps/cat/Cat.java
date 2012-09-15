package com.hogg.catapps.cat;

import android.app.Activity;

public class Cat {
	String name;
	Sex sex;
	public CatHearts hearts;
	// Todo:
	//public CatHunger hunger;
	//public CatThirst thirst;
	//public CatMood mood;
	
	public Cat(String _name, int initHearts, Sex _sex) {
		// Initialize hearts with a specified initial value and an increment/decrement value of 5
		hearts = new CatHearts(initHearts, 5);
		
		sex = _sex;
	}
	
	// Start tracking any meters
	public void start(Activity activity) {
		hearts.startTracking(activity); // Hearts meter
	}
	
	public String getName() {
		return name;
	}
	
	public Sex getSex() {
		return sex;
	}
}
