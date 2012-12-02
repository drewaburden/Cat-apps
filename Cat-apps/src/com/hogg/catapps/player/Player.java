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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.cat.Cat;
import com.hogg.catapps.items.Item;
import com.hogg.catapps.items.ItemXMLParser;

public class Player {
	String name = "";
	int money = 0;
	public static ArrayList<Cat> cats; // Holds all of the players cats
	Inventory inv;
	
	TextView moneyText;
	public int food = 5;
	public int water = 5;

	public Player(String _name) {
		name = _name;
		inv = new Inventory();
		updateInv();
		money = Init.getAppContext().getSharedPreferences("cat", Context.MODE_PRIVATE).getInt("money", 0);
	}
	
	public void startTracking(TextView _moneyText) {
		moneyText = _moneyText;
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
		SharedPreferences.Editor prefs_editor = Init.getAppContext().getSharedPreferences("cat", Context.MODE_PRIVATE).edit();
    	prefs_editor.putInt("money", money);
    	prefs_editor.apply();
	}
	public void updateMoneyText() {
		moneyText.setText(Integer.toString(money));
	}
	
	public void setInv(Inventory _inv) {
		inv = _inv;
	}
	public Inventory getInv() {
		return inv;
	}
	
	public void updateInv() {
		List<Item> items = null;
	    XmlPullParser parser = Init.getAppContext().getResources().getXml(R.xml.items);
	    ItemXMLParser itemParser = new ItemXMLParser();
	    try {
			items = itemParser.readFeed(parser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    for (Item item : items) {
	    	inv.getStoredInventoryData(item);
	    }
	}
}
