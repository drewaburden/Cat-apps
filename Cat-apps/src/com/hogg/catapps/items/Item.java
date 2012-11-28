package com.hogg.catapps.items;

import android.graphics.drawable.Drawable;

public abstract class Item {
	String name = "";
	float cost = 0.0f;
	Drawable drawable;
	
	
	public abstract void use();
	
	
	public void setName(String _name) {
		name = _name;
	}
	
	public void setCost(float _cost) {
		cost = _cost;
	}
	
	public void setDrawable(Drawable _drawable) {
		drawable = _drawable;
	}
}
