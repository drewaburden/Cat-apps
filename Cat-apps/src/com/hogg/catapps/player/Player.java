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

import com.hogg.catapps.cat.Cat;

public class Player {
	String name = "";
	int money = 0;
	public ArrayList<Cat> cats; // Holds all of the players cats

	public Player(String _name) {
		name = _name;
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
		money = _money;
	}
	public int getMoney() {
		return money;
	}

}
