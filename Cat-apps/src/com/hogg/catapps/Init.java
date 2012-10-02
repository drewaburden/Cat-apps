/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/Init.java
 * 
 * Description: 
 * 		This class is used to declare any global variables and do any
 * 		initializations that should only be run when the application is started
 * 		from scratch. 
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps;

import java.util.Random;

import com.hogg.catapps.cat.Cat;
import com.hogg.catapps.cat.Sex;

import android.app.Application;
import android.content.Context;

public class Init extends Application {
	public static Cat cat; // Temporary. This holds our single cat (globally accessible). Later, a Player variable will be define here which will hold an ArrayList of Cats.
	static Context context; //  

	// This runs only when the app is started from scratch.
	// Essentially, this only happens when we don't already have the variables seen above initialized.
	@Override
	public void onCreate() {
		super.onCreate();
		
        context = getApplicationContext();
		
		// Set up our cat and start tracking and updating its meters
		// Give the cat a random sex (this is just temporary).
		Random rand = new Random();
		Sex s;
		if (rand.nextBoolean()) {
			s = Sex.MALE;
		}
		else {
			s = Sex.FEMALE;
		}
		cat = new Cat(getString(R.string.catName), s, 0, 100, 100, 100);
	}
	
	public static Context getAppContext() {
        return context;
    }
}
