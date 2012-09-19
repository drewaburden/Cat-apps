/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/MainActivity.java
 * 
 * Description: 
 * 		Implements the main activity.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps;

import com.hogg.catapps.background.BackgroundSleepThread;
import com.hogg.catapps.cat.Sex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Display the correct sex icon depending on what sex the cat is.
		ImageView imageViewSex = (ImageView) findViewById(R.id.imageViewSex);
		if (Init.cat.getSex() == Sex.MALE) {
			imageViewSex.setImageResource(R.drawable.ic_male);
		}
		else {
			imageViewSex.setImageResource(R.drawable.ic_female);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		// Stop tracking all meters.
		Init.cat.stopTracking();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Start tracking all the meters and update their displays.
		Init.cat.startHearts(this, R.id.progressBarHearts, R.id.textViewHeartsPercentage);
		Init.cat.startMood(this, R.id.progressBarMood, R.id.textViewMoodStatus);
		Init.cat.startHunger(this, R.id.progressBarHunger, R.id.textViewHungerPercentage);
		Init.cat.startThirst(this, R.id.progressBarThirst, R.id.textViewThirstPercentage);
		Init.cat.update();
	}

	// Create the menu for the activity
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onOptionsItemSelect(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			// Settings option
			case R.id.menu_settings:
				// Start the settings activity
				Intent myIntent = new Intent(this, SettingsActivity.class);
				startActivity(myIntent);
				break;
			default:
				break;
		}
	}

	public void onCatButtonClick(View view) {
		// Find the "Meow!" text's TextView so we can manipulate it
		final TextView textViewMeow = (TextView) findViewById(R.id.textViewMeow);
		
		// Increase the amount of hearts and update the display
		Init.cat.hearts.increment();
		Init.cat.updateHearts();

		// Only if the TextView is invisible
		if (textViewMeow.getVisibility() != View.VISIBLE) {
			// Make it visible
			textViewMeow.setVisibility(View.VISIBLE);

			// Wait for .5 seconds (using threads so we don't freeze the UI)
			// and then make the text invisible again.
			Runnable makeTextInvisible = new Runnable() {
				public void run() {
					textViewMeow.setVisibility(View.INVISIBLE);
				}
			};
			new BackgroundSleepThread(this, makeTextInvisible, 500);
		}
	}
}
