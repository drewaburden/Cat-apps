/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/ui/SettingsActivity.java
 * 
 * Description: 
 * 		Implements the settings activity using a PreferenceFragment and a
 * 		PreferenceActivity.
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

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class SettingsActivity extends PreferenceActivity {
	static AlertDialog showing_diag;
	static AlertDialog clear_data_diag;
	OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
	static Preference difficultyPref;
	static CheckBoxPreference storeTwoPanePref;
	static boolean twoPaneSet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up the back button in the action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Set up the dialog for confirming whether the user wants to delete all
		// his or her cat's data
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.diag_clear_data));
		builder.setCancelable(true);
		builder.setPositiveButton(getString(R.string.diag_delete),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Init.cat.stopTracking();
						SharedPreferences prefs = Init.getAppContext()
								.getSharedPreferences("cat",
										Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.clear();
						editor.commit();
						dialog.dismiss();
						showing_diag = null;
					}
				});
		builder.setNegativeButton(getString(R.string.diag_cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						showing_diag = null;
					}
				});
		clear_data_diag = builder.create();
	}

	@Override
	public void onResume() {
		super.onResume();

		// If showing a dialog before we paused, show it again
		if (showing_diag != null) {
			showing_diag.show();
		}

		if (isTwoPane() && !twoPaneSet) {
			storeTwoPanePref.setChecked(true);
		}

		difficultyPref.setSummary(PreferenceManager
				.getDefaultSharedPreferences(Init.getAppContext()).getString(
						"difficulty",
						getString(R.string.pref_difficulty_default)));

		sharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// A preference value changes
				if (key.equals("difficulty")) {
					difficultyPref.setSummary(sharedPreferences.getString(key,
							getString(R.string.pref_difficulty_default)));
					Init.cat.updateDifficulty(sharedPreferences.getString(key,
							getString(R.string.pref_difficulty_default)));
				}
			}
		};

		PreferenceManager.getDefaultSharedPreferences(Init.getAppContext())
				.registerOnSharedPreferenceChangeListener(
						sharedPreferenceChangeListener);
	}

	@Override
	public void onPause() {
		super.onPause();

		// If showing a dialog, dismiss it to prevent memory leaks
		if (showing_diag != null && showing_diag.isShowing()) {
			showing_diag.dismiss();
		}

		PreferenceManager.getDefaultSharedPreferences(Init.getAppContext())
				.unregisterOnSharedPreferenceChangeListener(
						sharedPreferenceChangeListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// If the user clicked the back button in the action bar, take them back
		// to the parent activity
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class MainFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Add all our preferences to the fragment
			addPreferencesFromResource(R.xml.preferences);

			// Set up the click listener for the data clear button
			Preference myPref = findPreference("clear_data");
			myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				public boolean onPreferenceClick(Preference preference) {
					// Show the confirmation dialog
					showing_diag = clear_data_diag;
					showing_diag.show();
					return true;
				}
			});

			storeTwoPanePref = (CheckBoxPreference) findPreference("store_twopane");
			difficultyPref = findPreference("difficulty");

			// Set up the click listener for the two pane store preference
			storeTwoPanePref
					.setOnPreferenceClickListener(new OnPreferenceClickListener() {
						public boolean onPreferenceClick(Preference preference) {
							SharedPreferences.Editor prefs_editor = PreferenceManager
									.getDefaultSharedPreferences(
											Init.getAppContext()).edit();
							prefs_editor.putBoolean("store_twopane_set", true);
							prefs_editor.apply();
							return true;
						}
					});

			twoPaneSet = PreferenceManager.getDefaultSharedPreferences(
					Init.getAppContext())
					.getBoolean("store_twopane_set", false);
		}
	}

	public boolean isTwoPane() {
		Configuration config = getResources().getConfiguration();
		if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE
				|| (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE
				|| config.smallestScreenWidthDp >= 600) {
			return true;
		}
		return false;
	}
}
