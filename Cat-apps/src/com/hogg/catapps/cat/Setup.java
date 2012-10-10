/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Setup.java
 * 
 * Description: 
 * 		Implements the cat's setup wizard that will walk the user through a
 * 		number of dialogs that ask the user what to name the cat and what the
 * 		sex of the cat is.
 * 		Also, this class handles the loading of SharedPreferences pertaining to
 * 		the cat, applying the preferences to the current cat, and updating the
 * 		UI to reflect the loaded cat data.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CommitPrefEdits")
public class Setup {
	Activity activity;
	final SharedPreferences prefs;
	final SharedPreferences.Editor prefs_editor;
	AlertDialog showing_diag;
	AlertDialog sex_diag;
	AlertDialog name_diag;

	public Setup(Activity _activity) {
		activity = _activity;
		prefs = activity.getSharedPreferences("cat", Context.MODE_PRIVATE);
		prefs_editor = prefs.edit();
	}
	
	public void pauseDialogs() {
		if (showing_diag != null && showing_diag.isShowing()) {
			showing_diag.dismiss();
		}
	}
	
	public void resumeDialogs() {
		if (showing_diag != null) {
			showing_diag.show();
		}
		else {
			this.startWizard();
		}
	}
	
	public void startWizard() {
		createSexDialog();
		createNameDialog();
		showing_diag = name_diag;
		showing_diag.show();
	}
	
	private void createNameDialog() {
		// Prepare temporary preferences
		final SharedPreferences entered_text = activity.getSharedPreferences("temp", Context.MODE_PRIVATE);
		final SharedPreferences.Editor entered_text_editor = entered_text.edit();
		
		// Create and set up edit box
		final EditText input = new EditText(activity);
		input.setSingleLine();
		input.setSelection(input.getText().length());
		input.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				entered_text_editor.putString("entered_text", input.getText().toString());
				entered_text_editor.commit();
			}
			public void afterTextChanged(Editable s) { }
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
		});
		
		// Create and set up dialog 
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getString(R.string.diag_enter_name));
		builder.setCancelable(false);
		builder.setView(input);
		builder.setPositiveButton(activity.getString(R.string.diag_ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { /* Do nothing here because we're going to override this later */ }
		});
		name_diag = builder.create();
		
		// Handle OK button click
		name_diag.setOnShowListener(new DialogInterface.OnShowListener() {
		    public void onShow(DialogInterface dialog) {
		    	input.setText(entered_text.getString("entered_text", activity.getString(R.string.default_cat_name)));
		        Button ok = name_diag.getButton(AlertDialog.BUTTON_POSITIVE);
		        ok.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View view) {
		            	String name = input.getText().toString();
		            	name = name.replaceAll("\\s+", " "); // remove redundant spaces
		            	name = name.replaceAll("^\\s+", ""); // remove leading whitespace
		            	name = name.replaceAll("\\s+$", ""); // remove trailing whitespace
		            	
		                if (!name.equals("") && !name.equals(" ")) {
	                		if (name.length() <= 25) {
	                			//if (name.matches("[A-Za-zÀ-ÖØ-öø-ÿ0-9&&[^_]\'-\\.\\s]*")) {
	                			if (name.matches("([A-Za-zÀ-ÖØ-öø-ÿ0-9\\.\\'-]*\\s*)*")) {
	                				prefs_editor.putString("name", name);
	                				prefs_editor.commit();
	                				
	                				entered_text_editor.remove("entered_text");
	                				entered_text_editor.commit();
	                				
		                			name_diag.dismiss();
		                			showing_diag = sex_diag;
		                			showing_diag.show();
		                		}
		                		else {
		                			Toast.makeText(activity, activity.getString(R.string.toast_name_chars), Toast.LENGTH_LONG).show();
		                		}
		                	}
		                	else {
		                		Toast.makeText(activity, activity.getString(R.string.toast_name_length), Toast.LENGTH_SHORT).show();
		                	}
		                }
		                else {
		                	Toast.makeText(activity, activity.getString(R.string.toast_name_empty), Toast.LENGTH_SHORT).show();
		                }
		            }
		        });
		    }
		});
	}
	
	private void createSexDialog() {
		// Ask the sex of the cat
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getString(R.string.diag_choose_sex));
		builder.setCancelable(false);
		final CharSequence[] items = {Sex.MALE.toString(), Sex.FEMALE.toString()};
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	if (item == 0) {
		    		Init.cat.setSex(Sex.MALE);
		    		prefs_editor.putString("sex", Sex.MALE.toString());
		    		prefs_editor.putBoolean("setup", true);
		    		prefs_editor.commit();
		    		
		    		updateCat();
		    		updateActivity();
		    		
		    		sex_diag.dismiss();
		    		showing_diag = null;
		    	}
		    	else if (item == 1){
		    		Init.cat.setSex(Sex.FEMALE);
		    		prefs_editor.putString("sex", Sex.FEMALE.toString());
		    		prefs_editor.putBoolean("setup", true);
		    		prefs_editor.commit();
		    		
		    		updateCat();
		    		updateActivity();
		    		
		    		sex_diag.dismiss();
		    		
		    		// In case the setup needs to be run again during the life of the application
		    		showing_diag = name_diag;		    	}
		    }
		});
		sex_diag = builder.create();
	}
	
	public void updateCat() {
		// Set cat's name from preferences
		Init.cat.setName(prefs.getString("name", activity.getString(R.string.default_cat_name)));
		
		// Set cat's sex from the preferences
		if (prefs.getString("sex", Sex.MALE.toString()).equals(Sex.MALE.toString())) {
			Init.cat.setSex(Sex.MALE);
		}
		else {
			Init.cat.setSex(Sex.FEMALE);
		}
	}
	
	public void updateActivity() {
		// Update the name text in the activity
		TextView name = (TextView) activity.findViewById(R.id.textName);
		name.setText(Init.cat.getName());
	
		// Update the sex icon in the activity
		ImageView sex = (ImageView) activity.findViewById(R.id.imageSex);
		if (Init.cat.getSex() == Sex.MALE) {
			sex.setImageResource(R.drawable.ic_male);
		}
		else {
			sex.setImageResource(R.drawable.ic_female);
		}
	}
}
