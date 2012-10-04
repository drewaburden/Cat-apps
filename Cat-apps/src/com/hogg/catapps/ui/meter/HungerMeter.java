/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/HungerMeter.java
 * 
 * Description: 
 * 		Implements the HungerMeter class. This hold the information about the
 * 		cat's current hunger level and manages the progress bar for it.
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


public class HungerMeter extends Meter {
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 
	
	public HungerMeter(int initValue, double incAmount) {
		super(initValue, incAmount);
	}
}
