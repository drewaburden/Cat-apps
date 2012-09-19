/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatHunger.java
 * 
 * Description: 
 * 		Implements the CatHunger class. This hold the information about the
 * 		cat's current hunger level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public class CatHunger extends Meter {
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 
	
	public CatHunger(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
}
