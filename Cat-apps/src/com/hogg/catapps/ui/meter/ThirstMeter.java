/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/ThirstMeter.java
 * 
 * Description: 
 * 		Implements the ThirstMeter class. This hold the information about the
 * 		cat's current thirst level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;


public class ThirstMeter extends Meter {
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 	
	
	public ThirstMeter(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
}
