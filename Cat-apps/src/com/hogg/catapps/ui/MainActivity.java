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
import com.hogg.catapps.cat.Setup;

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
import android.widget.TextView;

public class MainActivity extends Activity {
	SharedPreferences prefs;
	Setup setup;
	AlertDialog showing_diag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = getSharedPreferences("cat", Context.MODE_PRIVATE);

		setup = new Setup(this);
		
		// If the preferences are not set up yet
		if (!prefs.getBoolean("setup", false)) {
			setup.startWizard();
		}
		
		setup.updateCat();
		setup.updateActivity();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		// Stop tracking all meters.
		Init.cat.stopTracking();
		
		// If we have any dialogs showing, we need to dismiss them so memory doesn't leak
		if (showing_diag != null && showing_diag.isShowing()) {
			showing_diag.dismiss();
		}
		// If the preferences are not set up yet, we need to dismiss the dialogs that are undoubtedly showing
		if (!prefs.getBoolean("setup", false)) {
			setup.pauseDialogs();
		}
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
		
		// If the preferences are not set up yet, we need to resume showing the dialogs for the setup
		if (!prefs.getBoolean("setup", false)) {
			setup.resumeDialogs();
		}
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
				builder.setMessage("Are you sure you want to exit?");
				builder.setCancelable(false);
				builder.setPositiveButton(getString(R.string.diag_yes), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                MainActivity.this.finish();
				           }
				       });
				builder.setNegativeButton(getString(R.string.diag_no), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				showing_diag = builder.create();
				showing_diag.show();
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
}
