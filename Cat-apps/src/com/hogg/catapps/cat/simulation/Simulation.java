/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/simulation/Meow.java
 * 
 * Description: 
 * 		Meow Thread
 * 		This class creates a simulation of a state machine so that we may
 * 		simulate a real cat and the events the cat does at any given time.
 * 		For instance, this will enable us to graphically show the cat doing
 * 		Various things such as standing, walking, sitting, playing, etc.
 * 
 * Contributors:
 * 		James Garner
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

/*
 * Note to other project members:
 * This is the simulation, it does everything automatically. If we need to change the state a cat is in, we do it manually, and this will normalize it after.
 * For instance, if at any time you need to break the cat out of a state and have them do something do the following:
 * 		Init.simulation.interrupt();    					//This will ensure that we have control over the cat's behavior and that the state machine will 
 * 															//not change the cats state while we are working
 * 		...code here to do whatever you need to, probably have the cat bend over and eat
 * 		Init.cat.setState(State.WHATEVER);					//Change the state in the cat's state field to whatever the cat should be doing after your code runs.
 * 															//If for instance you are having the cat bend over to eat, after he eats, does he end up SITTING or STANDING
 * 		Init.simulation = new Thread(new Meow());		//The interrupt before made the thread stop, so we need a new one!
 * 		Init.simulation.start();							//Start the thread so the cat can resume simulation
 */

package com.hogg.catapps.cat.simulation;

import java.util.Calendar;
import java.util.Random;

import android.util.Log;

import com.hogg.catapps.Init;


public class Simulation implements Runnable {
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
			if(randomVariable <= 3) { return States.SITTING; }
			else if(randomVariable > 3 && randomVariable <= 5) { return States.LAYING; }
			else if(randomVariable > 5 && randomVariable <= 8) { return States.STANDING; }
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
	
	public void sim(States currentState, States nextState, Random numberGenerator, int sleepTime, int nextStateNum) {
		boolean isInterrupted = false;
		while(!isInterrupted) {
			try {
				//Get random time to be in current state, up to one minute, 
				sleepTime = numberGenerator.nextInt() % 30000;
				nextStateNum = numberGenerator.nextInt() % 9;
				
				//Normalize the numbers. If they are negative, change the sign. If the sleep time is too short a time span, make it longer.
				if(sleepTime < 0) 
					sleepTime *= (-1);
				if(nextStateNum < 0)
					nextStateNum *= (-1);
				if(sleepTime < minimumTime)
					sleepTime = minimumTime;
				
				currentState = nextState;
				nextState = RandomState(Init.cat.getState(), nextStateNum);
				Init.cat.setState(nextState);				
				
				if(Thread.interrupted()) { return; }
				Thread.sleep(sleepTime);
				if(Thread.interrupted()) { return; }
			}
			catch (InterruptedException e) {
				isInterrupted = true;
				Log.i("Debug", "State machine interrupted");
			}
		}
		return;
	}
	
	public void run() {
		States currentState;
		States nextState;
		Calendar c = Calendar.getInstance();
		Random numberGenerator = new Random(c.get(Calendar.SECOND));
		int sleepTime = 0;
		int nextStateNum;
		
		//Log calls to test, comment out when not needed
		Log.i("Debug", "Running state machine thread");
		
		//Check, first, that the cat is not already in a state
		if(Init.cat.getState() == States.NOTHING) {
			//If the cat is not in a state, then need to assign the current state to be nothing, and the next state to the default, standing.
			currentState = States.NOTHING;
			nextState = States.STANDING;
			nextStateNum = 0;
		} else {
			//If the cat is already in a state, we are starting afresh from an interrupt.
			//This means, grab the current state and find a random next state based on it.
			nextStateNum = numberGenerator.nextInt() % 9;
			currentState = Init.cat.getState();
			nextState = RandomState(currentState, nextStateNum);
		}
		
		//Once the state's have been detected, enter the simulation
		sim(currentState, nextState, numberGenerator, sleepTime, nextStateNum);
		Log.i("Debug", "State machine stopping");
	}

}
