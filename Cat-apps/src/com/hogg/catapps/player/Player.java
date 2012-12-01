/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/player/Player.java
 * 
 * Description: 
 * 		Implements the Player class. All of the player's information will be
 * 		held here, including all of the player's cats.
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.player;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.cat.Cat;

public class Player {
	String name = "";
	int money = 0;
	public static ArrayList<Cat> cats; // Holds all of the players cats
	
	Button foodButton, waterButton;
	TextView moneyText;
	public int food = 5;
	public int water = 5;

	public Player(String _name) {
		name = _name;
	}
	
	public void startTracking(Button _foodButton, Button _waterButton, TextView _moneyText) {
		foodButton = _foodButton;
		waterButton = _waterButton;
		moneyText = _moneyText;
	}
	
	public void updateButtonText() {
		foodButton.setText(Init.getAppContext().getString(R.string.button_feed) + " " + Integer.toString(Init.player.food));
		waterButton.setText(Init.getAppContext().getString(R.string.button_water) + " " + Integer.toString(Init.player.water));
	}
	
	// Get and set name
	public void setName(String _name) {
		name = _name;
	}
	public String getName() {
		return name;
	}
	
	// Get and set money
	public void setMoney(int _money) {
		money = Math.max(Math.min(_money, 999999), 0);
		commitMoney();
	}
	public int getMoney() {
		return money;
	}
	public void incrementMoney(int _money) {
		money = Math.min(money + _money, 999999);
		commitMoney();
	}
	public void decrementMoney(int _money) {
		money = Math.max(money - _money, 0);
		commitMoney();
	}
	public void commitMoney() {
		SharedPreferences.Editor prefs_editor = Init.getAppContext().getSharedPreferences("player", Context.MODE_PRIVATE).edit();
    	prefs_editor.putInt("money", 0);
    	prefs_editor.apply();
	}
	public void updateMoneyText() {
		moneyText.setText(Integer.toString(money));
	}
}
