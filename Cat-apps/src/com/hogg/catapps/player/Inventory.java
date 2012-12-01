package com.hogg.catapps.player;

import java.util.SortedMap;
import java.util.TreeMap;

import com.hogg.catapps.items.Item;

public class Inventory {
	
	SortedMap<Item, Integer> ownedItems;
	
	public Inventory() {
		ownedItems = new TreeMap<Item, Integer>();
	}
	
	public Inventory(SortedMap<Item, Integer> _ownedItems) {
		ownedItems = _ownedItems;
	}
	
	public void addItem(Item item, int quantity) {
		Integer previousQuantity = ownedItems.remove(item);
		if(previousQuantity == null) {
			ownedItems.put(item, quantity);
		} else {
			ownedItems.put(item,  ( quantity + previousQuantity ) );
		}
	}
	
	public boolean removeItem(Item item, int quantity) {
		Integer previousQuantity = ownedItems.get(item);
		if(previousQuantity == null || previousQuantity < quantity) {
			return false;
		} else if(previousQuantity == quantity) {
			ownedItems.remove(item);
			return true;
		} else {
			ownedItems.remove(item);
			ownedItems.put(item, (previousQuantity - quantity) );
			return true;
		}	
	}
	
	public int itemCount(Item i) {
		Integer in = ownedItems.get(i);
		if(in == null) {
			return 0;
		} else {
		return ownedItems.get(i);
		}
	}
	
}
