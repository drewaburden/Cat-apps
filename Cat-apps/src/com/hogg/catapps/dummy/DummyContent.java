package com.hogg.catapps.dummy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.items.Item;
import com.hogg.catapps.items.ItemXMLParser;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Item> ITEMS = new ArrayList<Item>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

	static {
		List<Item> items = null;
		
	    XmlPullParser parser = Init.getAppContext().getResources().getXml(R.xml.items);
	    ItemXMLParser itemParser = new ItemXMLParser();
	    try {
			items = itemParser.readFeed(parser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Item item : items) {
			Log.d("Debug", "Item name: " + item.getName());
			addItem(item);
		}
		
		// Add 3 sample items.
		/*addItem(new Item("Item 1", 100));
		addItem(new Item("Item 2", 100));
		addItem(new Item("Item 3", 100));*/
	}

	private static void addItem(Item item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.getName(), item);
	}
}
