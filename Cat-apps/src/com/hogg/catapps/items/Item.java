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

import android.graphics.drawable.Drawable;

public class Item {
	String type = "";
	String name = "";
	String description = "";
	int price = 0;
	int value = 0;
	Drawable drawable;
	
	public Item(String _type, String _name, int _price, int _value, String _description) {
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
	
	public void setCost(int _price) {
		price = _price;
	}
	
	public void setDrawable(Drawable _drawable) {
		drawable = _drawable;
	}
	
	public String toString() {
		return name;
	}
}
