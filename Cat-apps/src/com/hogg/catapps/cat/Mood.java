/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Mood.java
 * 
 * Description: 
 * 		Defines the Mood enum. This defines all the possible states of the
 * 		cats' moods along with methods to easily convert them to strings. 
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;

public enum Mood {
	HAPPY {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_mood_happy);
		}
	},
	CONTENT {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_mood_content);
		}
	},
	SAD {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_mood_sad);
		}
	},
	MAD {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_mood_mad);
		}
	},
	DEAD {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_mood_dead);
		}
	}
}
