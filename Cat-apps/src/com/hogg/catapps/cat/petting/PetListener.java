/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/petting/PetListener.java
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
import com.hogg.catapps.cat.States;
import com.hogg.catapps.cat.simulation.Simulation;

import android.app.Activity;
import android.preference.PreferenceManager;
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
	List<Float> moveX;
	
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
		moveX = new ArrayList<Float>();
		petPercentThreshold = _petPercentThreshold;
		petPokeThreshold = _petPokeThreshold;
	}

	public boolean onTouch(View view, MotionEvent event) {
		//On touch, check if the cat's x and y values have been calibrated!
		if (!calibrated) calibrate();
		checkPet(event);
		return true;
	}
	
	private boolean checkPet(MotionEvent event) {
		final int action = event.getAction();
				
		if(action == MotionEvent.ACTION_DOWN) {
			downX = event.getX();
			downY = event.getY();
		} else
		if (action == MotionEvent.ACTION_MOVE){
			float tempX = event.getX();
			float tempY = event.getY();
			
			if(isWithinRect(tempX, tempY)) {
				move.add(true);
				moveX.add(tempX);
			} else {
				move.add(false);
			}
			
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
			if(isWithinRect(downX, downY) && isWithinRect(upX, upY) && totalMoves < petPokeThreshold && isAsleep()) {
				onCatButtonClickAsleep();
			} else if(isWithinRect(downX, downY) && isWithinRect(upX, upY) && totalMoves < petPokeThreshold) {
				onCatButtonClick();
			} else if(isAsleep() && currentPercent >= petPercentThreshold) {
				onPetAsleep();
			} else if(isHardcore() && moveX.size() > 0 && isAgainstGrain(moveX)) {
				onBackwardsPet();
			} else if(currentPercent >= petPercentThreshold) {
				//is a pet!
				Init.cat.hearts.increment(10);
				Init.cat.updateHearts();
				Init.cat.mood.increment();
				Init.cat.updateMood();
								
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
			moveX.clear();
		}
		return true;
	}
	
	private void calibrate() {
		catXLeft = imageCat.getLeft();
		catYTop = imageCat.getTop();
		catXRight = imageCat.getRight();
		catYBottom = imageCat.getBottom();
		
		
		if( (catXLeft != 0) && (catYTop != 0) && (catXRight != 0) && (catYBottom != 0) ) {
			calibrated = true;
		}
	}
	
	private boolean isWithinRect(float x, float y) {
		if( ( (x > catXLeft) && (x < catXRight) ) && ( (y < catYBottom) && (y > catYTop) ) ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void badMotionAction(int x) {
		// Decrease the amount of hearts and update the display
		Init.cat.mood.decrement(x);
		Init.cat.updateMood();

		
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
	
	private void onCatButtonClick() {
		badMotionAction(1);
	}
	
	private void onBackwardsPet() {
		badMotionAction(10);
	}
	
	private void sleepingEventHandler(int x) {
		Init.simulation.interrupt();
		badMotionAction(x);
		Init.cat.setState(States.LAYING);
		Init.simulation = new Thread(new Simulation());
		Init.simulation.start();
	}
	
	private void onCatButtonClickAsleep() {
		sleepingEventHandler(5);
	}
	
	private void onPetAsleep() {
		sleepingEventHandler(15);
	}
	
	private boolean isAgainstGrain(List<Float> moveX) {
		
		double percentAgainst = 0.5;
		
		int i;
		double lessThan = 0;
		for(i = 0; i != (moveX.size()-1); i++) {
			if( (moveX.get(i+1) - moveX.get(i)) < 0 )
				lessThan += 1;
		}
		
		double calculatedPercent = (double) lessThan/ (double) moveX.size();
		
		if(calculatedPercent > percentAgainst ) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAsleep() {
		if (Init.cat.getState().name.equals("Sleeping"))
			return true;
		return false;
	}
	
	private boolean isHardcore() {
		if( (PreferenceManager.getDefaultSharedPreferences(Init.getAppContext()).getString("difficulty", "none")).equals("Hardcore") ) {
			return true;
		} else {
			return false;
		}
	}
}
