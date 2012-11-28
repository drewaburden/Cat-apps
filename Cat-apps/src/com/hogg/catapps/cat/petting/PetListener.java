/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Petting.java
 * 
 * Description: 
 * 		Will capture MotionEvents and check that within one gesture, if
 *      a certain percent of the MotionEvents occurred within the confines
 *      of the cat's ImageView, in order to determine if the user has "petted,"
 *      the cat.
 * 
 * Contributors:
 * 		James Garner
 *      Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat.petting;

import java.util.ArrayList;
import java.util.List;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.background.BackgroundSleepThread;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PetListener implements OnTouchListener {
	Activity activity;
	RelativeLayout layout;
	ImageView imageCat;
	
	//Variables for tracking the movement of the users motion for determining if the action qualifies as a "pet"
	float downX, downY, upX, upY;
	List<Boolean> move;
	
	float catXLeft, catYTop, catXRight, catYBottom;
	boolean calibrated;
	float petPercentThreshold;
	int petPokeThreshold; // Number of move actions before the touch action registers as a pet instead of a poke
	
	BackgroundSleepThread catBackgroundImageModifier;
	
	public PetListener(RelativeLayout _layout, float _petPercentThreshold, int _petPokeThreshold){
		layout = _layout;
		activity = (Activity) layout.getContext();
		
		imageCat = (ImageView) layout.findViewById(R.id.imageCat);
		calibrated = false;
		move = new ArrayList<Boolean>();
		petPercentThreshold = _petPercentThreshold;
		petPokeThreshold = _petPokeThreshold;
	}

	public boolean onTouch(View view, MotionEvent event) {
		//On touch, check if the cat's x and y values have been calibrated!
		if (!calibrated) calibrate();
		checkPet(event);
		return true;
	}
	
	public boolean checkPet(MotionEvent event) {
		final int action = event.getAction();
				
		if(action == MotionEvent.ACTION_DOWN) {
			downX = event.getX();
			downY = event.getY();
		} else
		if (action == MotionEvent.ACTION_MOVE){
			float tempX = event.getX();
			float tempY = event.getY();
			
			move.add(isWithinRect(tempX, tempY));
			
		} else
		if(action == MotionEvent.ACTION_UP){
			upX = event.getX();
			upY = event.getY();
			
			//Need to check to see if they have pet the cat!
			//First, calculate the % of ACTION_MOVE's that registered within rectangle
			int totalMoves = move.size();
			int movesInRect = 0;
			for(int loopIter = 0; loopIter < totalMoves; loopIter++) {
				if(move.get(loopIter) == true)
					movesInRect++;
			}
			float currentPercent = 0.0f;
			if(!move.isEmpty()) {
				currentPercent = ((float) movesInRect)/totalMoves;
			}
			if(isWithinRect(downX, downY) && isWithinRect(upX, upY) && totalMoves < petPokeThreshold) {
				onCatButtonClick();
			}
			else if(currentPercent >= petPercentThreshold) {
				//is a pet!
				Init.cat.hearts.increment();
				Init.cat.updateHearts();
				
				imageCat.setImageDrawable(layout.getResources().getDrawable(R.drawable.ic_cat_happy));

				// Wait for .5 seconds (using threads so we don't freeze the UI)
				// and then make the text invisible again.
				Runnable makeCatImageContent = new Runnable() {
					public void run() {
						imageCat.setImageDrawable(layout.getResources().getDrawable(R.drawable.ic_cat_content));
					}
				};
				if (catBackgroundImageModifier != null) {
					catBackgroundImageModifier.interrupt();
				}
				catBackgroundImageModifier = new BackgroundSleepThread(activity, makeCatImageContent, 750);
			}

			downX = 0;
			downY = 0;
			upX = 0;
			upY = 0;
			move.clear();
		}
		return true;
	}
	
	public void calibrate() {
		//Function to get values for the boundaries of the cat.
		catXLeft = imageCat.getLeft();
		catYTop = imageCat.getTop();
		catXRight = imageCat.getRight();
		catYBottom = imageCat.getBottom();
		
		
		if( (catXLeft != 0) && (catYTop != 0) && (catXRight != 0) && (catYBottom != 0) ) {
			calibrated = true;
		}
	}
	
	// Test to see if the passed point is within the bounds of the cat's imageView
	public boolean isWithinRect(float x, float y) {
		if( ( (x > catXLeft) && (x < catXRight) ) && ( (y < catYBottom) && (y > catYTop) ) ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void onCatButtonClick() {
		// Decrease the amount of hearts and update the display
		Init.cat.hearts.decrement(1);
		Init.cat.updateHearts();
		
		imageCat.setImageDrawable(layout.getResources().getDrawable(R.drawable.ic_cat_mad));

		// Wait for .5 seconds (using threads so we don't freeze the UI)
		// and then make the text invisible again.
		Runnable makeCatImageContent = new Runnable() {
			public void run() {
				imageCat.setImageDrawable(layout.getResources().getDrawable(R.drawable.ic_cat_content));
			}
		};
		if (catBackgroundImageModifier != null) {
			catBackgroundImageModifier.interrupt();
		}
		catBackgroundImageModifier = new BackgroundSleepThread(activity, makeCatImageContent, 750);
	}
}
