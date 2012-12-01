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
import com.hogg.catapps.cat.petting.PetListener;
import com.hogg.catapps.cat.simulation.Simulation;
import com.hogg.catapps.ui.store.StoreActivity;

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
import android.widget.Button;
import android.widget.RelativeLayout;
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
		
		// This will make sure our preferences are loaded and
		// the Activity's UI components are up to date
		setup.updateCat();
		setup.updateActivity();
		
		//Need to start listener		
		RelativeLayout petLayout = (RelativeLayout) findViewById(R.id.layoutMain);
		petLayout.setOnTouchListener(new PetListener(petLayout, 0.20f, 8));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		// Stop tracking all meters.
		Init.cat.stopTracking();
		Init.cat.simulation.stopTracking();
		
		//Interrupt the state machine simulation
		Init.simulation.interrupt();
		
		// If any dialogs showing, dismiss them so memory doesn't leak
		if (showing_diag != null && showing_diag.isShowing()) {
			showing_diag.dismiss();
		}
		// If the preferences are not set up yet, dismiss the dialogs that are undoubtedly showing
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
		Init.cat.simulation.startTracking(this, R.id.textMeow);
		Init.cat.setActivity(this);
		Init.cat.update();
		
		//A new thread must be started
		Init.simulation = new Thread(new Simulation());
        Init.simulation.start();
        Init.cat.updateStateText();
		
		Button foodButton = (Button) findViewById(R.id.button1);
		Button waterButton = (Button) findViewById(R.id.button2);
		TextView moneyText = (TextView) findViewById(R.id.textMoney);
		Init.player.startTracking(foodButton, waterButton, moneyText);
		Init.player.updateButtonText();
		Init.player.updateMoneyText();
		
		// If the preferences are not set up yet, we need to resume showing the dialogs for the setup
		if (!prefs.getBoolean("setup", false)) {
			setup.updateCat();
			setup.updateActivity();
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
			// Settings option
			case R.id.menu_store:
				// Start the settings activity
				Intent intent1 = new Intent(this, StoreActivity.class);
				startActivity(intent1);
				break;
			// Exit option
			case R.id.menu_exit:
				// Create and show a confirmation asking whether the user wants to exit the application
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
	
	public void onGiveFoodClick(View v) {
		if(Init.player.food > 0) {
			Init.player.food--;
			Init.player.updateButtonText();
			final Button foodButton = (Button) findViewById(R.id.button1);
			Runnable makeFoodButtonEnabled = new Runnable() {
				public void run() {
					foodButton.setEnabled(true);
				}
			};
			foodButton.setEnabled(false);
			new BackgroundSleepThread(this, makeFoodButtonEnabled, 3000);
			
			double x = Init.cat.hunger.getValue();
			if (x < 100.0) {
				double heartsInc = 0.00114286*x*x - 0.254286*x + 14.4286;
				
				Init.cat.hearts.increment(heartsInc);
				Init.cat.updateHearts();
				
				Init.cat.hunger.increment(5.0);
				Init.cat.updateHunger();
			}
		}
	}
	
	public void onGiveWaterClick(View v) {
		if(Init.player.water > 0) {
			Init.player.water--;
			Init.player.updateButtonText();
			final Button waterButton = (Button) findViewById(R.id.button2);
			Runnable makeWaterButtonEnabled = new Runnable() {
				public void run() {
					waterButton.setEnabled(true);
				}
			};
			waterButton.setEnabled(false);
			new BackgroundSleepThread(this, makeWaterButtonEnabled, 3000);
			
			double x = Init.cat.thirst.getValue();
			if (x < 100.0) {
				double heartsInc = 0.002*x*x - 0.39*x + 19.5;
				
				Init.cat.hearts.increment(heartsInc);
				Init.cat.updateHearts();
				
				Init.cat.thirst.increment(10.0);
				Init.cat.updateThirst();
			}
		}
	}
}
