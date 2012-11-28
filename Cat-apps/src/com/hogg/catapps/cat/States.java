/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/States.java
 * 
 * Description: 
 * 		Defines the States enum. This defines all the possible states of the
 * 		cat along with a method to easily convert them to strings. 
 * 
 * Contributors:
 * 		James Garner
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

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
