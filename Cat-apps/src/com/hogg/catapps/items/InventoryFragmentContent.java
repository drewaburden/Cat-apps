package com.hogg.catapps.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class InventoryFragmentContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Item> ITEMS = new ArrayList<Item>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

	static {
		for (SortedMap.Entry<Item, Integer> item : Init.player.getInv().getItems().entrySet()) {
			Log.d("Debug", "Item name: " + item.getKey().getName());
			addItem(item.getKey());
		}
	}

	private static void addItem(Item item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.getName(), item);
	}
}
