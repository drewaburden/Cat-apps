package com.hogg.catapps.player;

import java.util.SortedMap;
import java.util.TreeMap;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hogg.catapps.Init;
import com.hogg.catapps.items.Item;

public class Inventory {
	
	SortedMap<Item, Integer> ownedItems;
	
	public Inventory() {
		ownedItems = new TreeMap<Item, Integer>();
	}
	
	public Inventory(SortedMap<Item, Integer> _ownedItems) {
		ownedItems = _ownedItems;
	}
	
	public SortedMap<Item, Integer> getItems() {
		return ownedItems;
	}
	
	public void addItem(Item item, int quantity) {
		Integer previousQuantity = ownedItems.remove(item);
		if(previousQuantity == null) {
			ownedItems.put(item, quantity);
		} else {
			ownedItems.put(item,  ( quantity + previousQuantity ) );
		}
		updateInventory(item);
	}
	
	public boolean removeItem(Item item, int quantity) {
		Integer previousQuantity = ownedItems.get(item);
		if(previousQuantity == null || previousQuantity < quantity) {
			return false;
		} else if(previousQuantity == quantity) {
			ownedItems.remove(item);
		} else {
			ownedItems.remove(item);
			ownedItems.put(item, (previousQuantity - quantity) );
		}
		updateInventory(item);
		return true;
	}
	
	public int itemCount(Item i) {
		Integer in = ownedItems.get(i);
		if(in == null) {
			//attempt to retrieve the item from the 
			return 0;
		} else {
		return ownedItems.get(i);
		}
	}
	
	public void updateInventory(Item i) {
		Editor prefEditor = Init.getAppContext()
		.getSharedPreferences("inventory", Context.MODE_PRIVATE).edit();
		
		//Need to dynamically store the preferences based on the item's name.
		String prefName = "inv_" + i.getIdentifier();
		int l = itemCount(i);
		prefEditor.putInt(prefName, l);
		prefEditor.apply();
		Log.d("Debug", "Just stored " + Integer.toString(itemCount(i)) + " of " + i.getName() + " in the inventory on file in key " + prefName);
		int x =	PreferenceManager.getDefaultSharedPreferences(Init.getAppContext()).getInt(prefName, 0);
		Log.d("Debug", "On file we have number: " + Integer.toString(x));
	}
	
	public void getStoredInventoryData(Item i) {
		Log.d("Debug", "Retrieving stored value for item: " + i.getName());
		String prefName = "inv_" + i.getIdentifier();		
		int x =	PreferenceManager.getDefaultSharedPreferences(Init.getAppContext()).getInt(prefName, 0);
		Log.d("Debug", "From shared preferences, there are: " + Integer.toString(x) + " of this item in the inventory on file under key" + prefName);
		ownedItems.remove(i);
		ownedItems.put(i,  x);
	}
	
}
