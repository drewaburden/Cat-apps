/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/petting/Petting.java
 * 
 * Description: 
 * 		Will capture MotionEvents and check that within one gesture, if
 *      a certain percent of the MotionEvents occurred within the confines
 *      cat's ImageView, in order to determine if the user has "petted,"
 *      the cat.
 * 
 * Contributors:
 * 		James Garner
 *      Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.petting;

import java.util.ArrayList;
import java.util.List;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.background.BackgroundSleepThread;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PetListener implements OnTouchListener {
	
	//Variables for tracking the movement of the users motion for determining if the action qualifies as a "pet"
	float downX;
	float downY;
	
	List<Boolean> move;
	
	float upX;
	float upY;
	
	Activity a;
	RelativeLayout r;
	ImageView cat;
	
	float catXLeft;
	float catYTop;
	float catXRight;
	float catYBottom;
	boolean calibrated;
	float percentPet;
	
	
	public PetListener(RelativeLayout _r, Activity _a){
		r = _r;
		cat = (ImageView) r.findViewById(R.id.imageCat);
		a = _a;
		calibrated = false;
		move = new ArrayList<Boolean>();
		percentPet = (float) 0.40;
	}

	public boolean onTouch(View v, MotionEvent event) {
		//On touch, check if the cat's x and y values have been calibrated!
		if (!calibrated) calibrate(cat);
		checkPet(v, event);
		return true;
	}
	
	public boolean checkPet(View v, MotionEvent ev) {
		final int action = ev.getAction();
				
		if(action == MotionEvent.ACTION_DOWN) {
			downX = ev.getX();
			downY = ev.getY();
		} else
		if (action == MotionEvent.ACTION_MOVE){
			float tempX = ev.getX();
			float tempY = ev.getY();
			
			move.add(isWithinRect(tempX, tempY));
			
		} else
		if(action == MotionEvent.ACTION_UP){
			upX = ev.getX();
			upY = ev.getY();
				if(isWithinRect(downX, downY) && isWithinRect(upX, upY))
					onCatButtonClick();
			
			//Need to check to see if they have pet the cat!
			//First, calculate the % of ACTION_MOVE's that registered within rectangle
			float n = move.size();
			int i = 0;
			float j = 0;
			for(; i < n; i++) {
				if(move.get(i) == true)
					j++;
			}
			float f = 0;
			if(!move.isEmpty()) { f = j/n; }
			if(f >= percentPet) {
				//is a pet!
				Init.cat.hearts.increment(10);
				Init.cat.updateHearts();
				try { Init.simulation.interrupt(); }
				catch(SecurityException se) { Log.i("Debug", "SECURITY EXCEPTION");}
				catch(NullPointerException npe) { Log.i("Debug", "Null pointer exception???");}
			}
			downX = 0;
			downY = 0;
			upX = 0;
			upY = 0;
			move.clear();
		}
		return true;
	}
	
	public void calibrate(ImageView _cat) {
		//Function to get values for the boundaries of the cat.
		catXLeft = cat.getLeft();
		catYTop = cat.getTop();
		catXRight = cat.getRight();
		catYBottom = cat.getBottom();
		
		
		if( (catXLeft != 0) && (catYTop != 0) && (catXRight != 0) && (catYBottom != 0) )
			calibrated = true;
		
		return;
	}
	
	public boolean isWithinRect(float x, float y) {
		if( ( (x > catXLeft) && (x < catXRight) ) && ( (y < catYBottom) && (y > catYTop) ) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onCatButtonClick() {
		// Find the "Meow!" text's TextView so we can manipulate it
		final TextView textMeow = (TextView) r.findViewById(R.id.textMeow);
		
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
			new BackgroundSleepThread(a, makeTextInvisible, 500);
		}
	}
}
