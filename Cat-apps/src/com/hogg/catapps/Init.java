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

import com.hogg.catapps.cat.Cat;
import com.hogg.catapps.cat.Sex;
<<<<<<< HEAD
import com.hogg.catapps.player.Player;
=======
import com.hogg.catapps.simulation.Simulation;
>>>>>>> Simulation Push 1

import android.app.Application;
import android.content.Context;

public class Init extends Application {
<<<<<<< HEAD
	public static Cat cat; // Temporary. This holds our single cat (globally accessible). Later, activity Player variable will be define here which will hold an ArrayList of Cats.
	public static Player player; // Temporary.
	static Context context; // The stored Application's Context as activity global variable 
=======
	public static Cat cat; // Temporary. This holds our single cat (globally accessible). Later, a Player variable will be define here which will hold an ArrayList of Cats.
	static Context context; // The stored Application's Context as a global variable 
	public static Thread simulation;
>>>>>>> Simulation Push 1

	// This runs only when the app is started from scratch.
	// Essentially, this only happens when we don't already have the variables seen above initialized.
	@Override
	public void onCreate() {
		super.onCreate();
		
        context = getApplicationContext();
		
		// Set up our cat and start tracking and updating its meters
<<<<<<< HEAD
		cat = new Cat(getString(R.string.default_cat_name), Sex.MALE, 0, 100, 50, 50);
		player = new Player("");
=======
        simulation = new Thread(new Simulation());
        simulation.start();
		
        cat = new Cat(getString(R.string.default_cat_name), Sex.MALE, 0, 100, 50, 50);
		
>>>>>>> Simulation Push 1
	}
	
	// Retrieve the Application's Context from anywhere
	public static Context getAppContext() {
        return context;
    }
}
