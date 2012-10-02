/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/meter/HygieneMeter.java
 * 
 * Description: 
 * 		Implements the HygieneMeter class. This hold the information about the
 * 		cat's current hygiene level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 *  	Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui.meter;


public class HygieneMeter extends Meter {
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 		
	
	public HygieneMeter(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
}
