/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatThirst.java
 * 
 * Description: 
 * 		Implements the CatThirst class. This hold the information about the
 * 		cat's current thirst level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public class CatThirst extends Meter {
	// Upon decrementing or incrementing the cat's mood will go up or down
	// by moodPercentageVariable% of the incrementAmount
	double moodPercentageVariable = 0.10; 	
	
	public CatThirst(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
}
