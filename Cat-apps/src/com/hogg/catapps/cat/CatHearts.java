/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatHearts.java
 * 
 * Description: 
 * 		Implements the CatHearts class. This hold the information about the
 * 		cat's current heart level and manages the progress bar for it.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public class CatHearts extends Meter {
	public CatHearts(int initValue, int incAmount) {
		super(initValue, incAmount);
	}
}
