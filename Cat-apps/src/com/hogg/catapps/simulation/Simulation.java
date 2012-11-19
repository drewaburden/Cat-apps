/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/simulation/Simulation.java
 * 
 * Description: 
 * 		Simulation Thread
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/
package com.hogg.catapps.simulation;

import java.util.Calendar;
import java.util.Random;

import android.util.Log;

public class Simulation implements Runnable {

	public enum States {
		STANDING ("Standing"),
		WALKING ("Walking"),
		SITTING ("Sitting"),
		GROOMING ("Grooming"),
		LAYING ("Laying"),
		SLEEPING ("Sleeping"),
		PLAYING ("Playing"),
		EATING ("Eating"),
		DRINKING ("Drinking");
		
		private final String name;
		
		States(String s) {
			this.name = s;
		}
	}
	
	
	/*
	 * Will randomly pick a state to go to, based on the previous state.
	 * randomVariable will be a value between 0-9
	 */
	public States RandomState(States startState, int randomVariable) {
		if(startState == States.STANDING) {													//Standing
			if(randomVariable <= 4) { return States.STANDING; }
			else if(randomVariable > 4 && randomVariable <= 6) { return States.SITTING; }
			else if(randomVariable > 6 && randomVariable <= 8) { return States.WALKING; }
			else { return States.PLAYING; }
		} else if(startState == States.SITTING) {											//Sitting
			if(randomVariable <= 4) { return States.SITTING; }
			else if(randomVariable > 4 && randomVariable <= 6) { return States.LAYING; }
			else if(randomVariable > 6 && randomVariable <= 8) { return States.STANDING; }
			else { return States.GROOMING; }
		} else if(startState == States.LAYING) {											//Laying
			if(randomVariable <= 4) { return States.LAYING; }
			else if(randomVariable > 4 && randomVariable <= 6) { return States.SLEEPING; }
			else { return States.SITTING; }
		} else if(startState == States.WALKING) {											//Walking
			if(randomVariable <= 4) { return States.WALKING; }
			else if(randomVariable > 4 && randomVariable <= 6) {return States.SITTING; }
			else { return States.STANDING; }
		} else if(startState == States.SLEEPING) {											//Sleeping
			if(randomVariable <= 3) { return States.SLEEPING; }
			else { return States.LAYING; }
		} else if(startState == States.GROOMING) {											//Grooming
			if(randomVariable <= 4) { return States.GROOMING; }
			else if(randomVariable > 4 && randomVariable <= 6) {return States.SITTING; }
			else { return States.STANDING; }
		} else {																			//Playing
			if(randomVariable <= 4 ) { return States.PLAYING; }
			else { return States.SITTING; }
		}
	}
	
	int stateCount = 9;
	int minimumTime = 5000;
	
	
	public void run() {
			
		States currentState;
		States nextState = States.STANDING;
		//Need to actually run a simulation of a state machine for the cat's "lifecycle"
		Log.i("Debug", "State machine has stated.");
		
		//Need to get a random number generator going
		Calendar c = Calendar.getInstance();
		Random numberGenerator = new Random(c.get(Calendar.SECOND));
		int sleepTime;
		int nextStateNum;
		
		
		while(true) {		
			try {
				//Get random time to be in current state, up to one minute, 
				sleepTime = numberGenerator.nextInt() % 60000;
				nextStateNum = numberGenerator.nextInt() % 9;
				
				//Normalize the numbers. If they are negative, change the sign. If the sleep time is too short a time span, make it longer.
				if(sleepTime < 0) 
					sleepTime *= (-1);
				if(nextStateNum < 0)
					nextStateNum *= (-1);
				if(sleepTime < minimumTime)
					sleepTime = minimumTime;
				
				currentState = nextState;
				nextState = RandomState(currentState, nextStateNum);
				
				Log.i("Debug", "The current state is: " + currentState.name);
				Log.i("Debug", "The next state will be: " + nextState.name);
				if(Thread.interrupted()) { return; }
				Thread.sleep(sleepTime);
				if(Thread.interrupted()) { return; }
			}
			catch (InterruptedException e) {
				Log.i("Debug", "INTERRUPTED, BITCH. SHUTTING DOWN STATE MACHINE!");
				return;
			}
		}
	}

}
