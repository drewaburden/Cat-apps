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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	SharedPreferences prefs;
	Setup setup;
	AlertDialog showing_diag;
	RelativeLayout petLayout;
	
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
		petLayout = (RelativeLayout) findViewById(R.id.layoutMain);
		enablePetLayout();
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
		
		//Button foodButton = (Button) findViewById(R.id.button1);
		//Button waterButton = (Button) findViewById(R.id.button2);
		TextView moneyText = (TextView) findViewById(R.id.textMoney);
		Init.player.startTracking(moneyText);
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
	
	public void killPetListener() {
		petLayout.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
			
		});
	}

	public void enablePetLayout() {
		petLayout.setOnTouchListener(new PetListener(petLayout, 0.20f, 8));
		return;
	}
	
}
