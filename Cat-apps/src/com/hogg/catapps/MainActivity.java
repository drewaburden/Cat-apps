package com.hogg.catapps;

import com.hogg.catapps.background.BackgroundSleepThread;
import com.hogg.catapps.cat.Cat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	Cat cat; // This holds all the data for our cat

	// Initialize the activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up our cat and start tracking and updating its meters
		cat = new Cat(getString(R.string.catName), 0);
		cat.hearts.startTracking(this);
		cat.hearts.update();
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
		cat.hearts.increment();
		cat.hearts.update();

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
