/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/MainActivity.java
 * 
 * Description: 
 * 		Implements the main activity and does a bit of initialization and setup.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.ui;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.background.BackgroundSleepThread;
import com.hogg.catapps.cat.Sex;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
		
		final SharedPreferences prefs = getSharedPreferences("Cat", Context.MODE_PRIVATE);

		// If the preferences are not set up yet
		if (!prefs.getBoolean("setup", false)) {
			setupCat(prefs);
		}
		
		updateSex(prefs);
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
		Init.cat.startHearts(this, R.id.progressHearts, R.id.textHeartsPercentage);
		Init.cat.startMood(this, R.id.progressMood, R.id.textMoodStatus);
		Init.cat.startHunger(this, R.id.progressHunger, R.id.textHungerPercentage);
		Init.cat.startThirst(this, R.id.progressThirst, R.id.textThirstPercentage);
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
				Intent intent = new Intent(this, SettingsActivity.class);
				intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
				intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.MainFragment.class.getName());
				startActivity(intent);
				break;
			// Exit option
			case R.id.menu_exit:
				// Show a confirmation asking whether the user wants to exit the application
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to exit?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                MainActivity.this.finish();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				builder.create();
				builder.show();
				break;
			default:
				break;
		}
	}

	public void onCatButtonClick(View view) {
		// Find the "Meow!" text's TextView so we can manipulate it
		final TextView textMeow = (TextView) findViewById(R.id.textMeow);
		
		// Increase the amount of hearts and update the display
		Init.cat.hearts.increment();
		Init.cat.updateHearts();

		// Only if the TextView is invisible
		if (textMeow.getVisibility() != View.VISIBLE) {
			// Make it visible
			textMeow.setVisibility(View.VISIBLE);

			// Wait for .5 seconds (using threads so we don't freeze the UI)
			// and then make the text invisible again.
			Runnable makeTextInvisible = new Runnable() {
				public void run() {
					textMeow.setVisibility(View.INVISIBLE);
				}
			};
			new BackgroundSleepThread(this, makeTextInvisible, 500);
		}
	}
	
	public void setupCat(final SharedPreferences prefs) {
		final SharedPreferences.Editor editor = prefs.edit();
		
		// Ask the sex of the cat
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose your cat's sex");
		builder.setCancelable(false);
		final CharSequence[] items = {"Male", "Female"};
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	if (item == 0) {
		    		Init.cat.setSex(Sex.MALE);
		    		editor.putString("sex", Sex.MALE.toString());
		    		editor.commit();
		    		updateSex(prefs);
		    		dialog.dismiss();
		    	}
		    	else if (item == 1){
		    		Init.cat.setSex(Sex.FEMALE);
		    		editor.putString("sex", Sex.FEMALE.toString());
		    		editor.commit();
		    		updateSex(prefs);
		    		dialog.dismiss();
		    	}
		    }
		});
		builder.create().show();
		
		editor.putBoolean("setup", true);
		editor.commit();
	}
	
	public void updateSex(final SharedPreferences prefs) {
		// Determine the sex from the preferences
		if (prefs.getString("sex", "Male").equals(Sex.MALE.toString())) {
			Init.cat.setSex(Sex.MALE);
		}
		else {
			Init.cat.setSex(Sex.FEMALE);
		}
		
		// Display the correct sex icon depending on what sex the cat is.
		ImageView imageSex = (ImageView) findViewById(R.id.imageSex);
		if (Init.cat.getSex() == Sex.MALE) {
			imageSex.setImageResource(R.drawable.ic_male);
		}
		else {
			imageSex.setImageResource(R.drawable.ic_female);
		}
	}
}
