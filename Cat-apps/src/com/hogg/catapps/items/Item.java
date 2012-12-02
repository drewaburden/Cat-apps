/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/items/Items.java
 * 
 * Description: 
 * 		Defines the Items class. This is a skeleton class for all of the
 * 		items in the game. 
 * 
 * Contributors:
 * 		James Garner
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.items;

import com.hogg.catapps.Init;

import android.graphics.drawable.Drawable;

public class Item implements Comparable<Item>{
	String type = "";
	String identifier = "";
	String name = "";
	String description = "";
	int price = 0;
	int value = 0;
	Drawable drawable;
	
	public Item(String _type, String _identifier, String _name, int _price, int _value, String _description) {
		type = _type;
		identifier = _identifier;
		name = _name;
		price = _price;
		value = _value;
		description = _description;
	}
	
	//public abstract void use();
	
	public void setName(String _name) {
		name = _name;
	}
	public String getName() {
		return name;
	}
	
	public void setPrice(int _price) {
		price = _price;
	}
	public int getPrice() {
		return price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setDrawable(Drawable _drawable) {
		drawable = _drawable;
	}
	
	public String toString() {
		return name;
	}

	public int compareTo(Item i) {
		//Need to compare type
		int typeDiff = type.compareToIgnoreCase(i.type);
		if( typeDiff != 0) {
			return typeDiff;
		}
		//Else they are the same type, return difference to price
		return price - i.price;
	}
	
	public void useItem() {
		if(type.equals("food") == true) {
			Init.cat.hunger.increment(value);
			Init.cat.hunger.stopTracking();
		} else if(type.equals("drink") == true) {
			Init.cat.thirst.increment(value);
			Init.cat.thirst.stopTracking();
		} else if(type.equals("catnip") == true) {
				Init.cat.hearts.increment(value);
				Init.cat.hearts.stopTracking();
		} else if(type.equals("toy") == true) {
				//TODO: Implement custom methods for items.
		}
		Init.player.getInv().removeItem(this, 1);
	}
	
}
