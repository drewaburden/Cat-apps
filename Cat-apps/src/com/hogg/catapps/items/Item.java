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

public abstract class Item {
	String name = "";
	float price = 0.0f;
	Drawable drawable;
	
	public abstract void use();
	
	public void setName(String _name) {
		name = _name;
	}
	
	public void setCost(float _price) {
		price = _price;
	}
	
	public void setDrawable(Drawable _drawable) {
		drawable = _drawable;
	}
}
