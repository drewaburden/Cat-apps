package com.hogg.catapps.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class ItemXMLParser {
	
	public List<Item> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Item> items = new ArrayList<Item>();
		
		String name = null;
		String identifier = null;
		String type = null;
		int price = 0;
		int value = 0;
		String description = null;
		
		XmlPullParserFactory factory = null;
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factory.setNamespaceAware(true);

		int eventType = 0;
		try {
			eventType = parser.getEventType();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				String tag = parser.getName();
				int depth = 0;
				if (tag.equals("item")) {
					depth++;
					while(depth > 0) {
						eventType = parser.next();
						if (eventType == XmlPullParser.END_TAG) {
							depth--;
							Log.d("Debug", "Depth decreased: " + depth);
							continue;
						}
						else if (eventType == XmlPullParser.START_TAG) {
							depth++;
							Log.d("Debug", "Depth increased: " + depth);
							
							tag = parser.getName();

							if (tag.equals("name")) {
								parser.next();
								name = parser.getText();
							}
							else if (tag.equals("identifier")) {
								parser.next();
								identifier = parser.getText();
							}
							else if (tag.equals("type")) {
								parser.next();
								type = parser.getText();
							}
							else if (tag.equals("price")) {
								parser.next();
								price = Integer.parseInt(parser.getText());
							}
							else if (tag.equals("value")) {
								parser.next();
								value = Integer.parseInt(parser.getText());
							}
							else if (tag.equals("description")) {
								parser.next();
								description = parser.getText();
							}
						}
					}
					Log.d("Debug", "Added " + name);
					items.add(new Item(type, identifier, name, price, value, description));
				}
			}
			
			try {
				eventType = parser.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return items;
	}
}
