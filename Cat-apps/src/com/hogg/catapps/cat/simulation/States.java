package com.hogg.catapps.cat.simulation;

public enum States {
	STANDING ("Standing"),
	WALKING ("Walking"),
	SITTING ("Sitting"),
	GROOMING ("Grooming"),
	LAYING ("Laying"),
	SLEEPING ("Sleeping"),
	PLAYING ("Playing"),
	EATING ("Eating"),
	DRINKING ("Drinking"),
	NOTHING ("Nothing");
	
	public final String name;
	
	States(String state) {
		name = state;
	}
	
	public String toString() {
		return name;
	}
}
