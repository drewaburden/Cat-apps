package com.hogg.catapps.items;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;

import android.util.Log;
import android.util.Xml;

public class ItemXMLParser {
	private static final String ns = null;
	
	public List<Item> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
	
	public List<Item> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Item> items = new ArrayList<Item>();
		
		String name = null;
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
					items.add(new Item(type, name, price, value, description));
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
	
	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
	    String type = null;
	    String name = null;
	    int price = 0;
	    int value = 0;
	    String description = null;
	    
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        if (tag.equals("name")) {
	            name = readTag(parser, tag);
	        } else if (tag.equals("type")) {
	            type = readTag(parser, tag);
	        } else if (tag.equals("price")) {
	            price = Integer.parseInt(readTag(parser, tag));
	        } else if (tag.equals("value")) {
	            value = Integer.parseInt(readTag(parser, tag));
	        } else if (tag.equals("description")) {
	            description = readTag(parser, tag);
	        } else {
	            skip(parser);
	        }
	    }
	    return new Item(type, name, price, value, description);
	}
	
	// Processes title tags in the feed.
	private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, tag);
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, tag);
	    return title;
	}
	
	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}
