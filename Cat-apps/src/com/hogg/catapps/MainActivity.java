package com.hogg.catapps;

import com.hogg.catapps.background.BackgroundSleepThread;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

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
		// Find the TextView so we can manipulate it
		final TextView text = (TextView) findViewById(R.id.textView1);

		// Only if the TextView is invisible
		if (text.getVisibility() != View.VISIBLE) {
			// Make it visible
			text.setVisibility(View.VISIBLE);

			// Wait for .5 seconds (using threads so we don't freeze the UI)
			// and then make the text invisible again.
			Runnable makeTextInvisible = new Runnable() {
				public void run() {
					text.setVisibility(View.INVISIBLE);
				}
			};
			new BackgroundSleepThread(this, makeTextInvisible, 500);

		}
	}
}
