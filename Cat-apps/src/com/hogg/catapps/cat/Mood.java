/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Mood.java
 * 
 * Description: 
 * 		Defines the Mood enum. This defines all the possible states of the
 * 		cats' moods. 
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public enum Mood {
	HAPPY {
		public String toString() {
			return "Happy";
		}
	},
	CONTENT {
		public String toString() {
			return "Content";
		}
	},
	SAD {
		public String toString() {
			return "Sad";
		}
	},
	MAD {
		public String toString() {
			return "Mad";
		}
	},
	DEAD {
		public String toString() {
			return "Dead";
		}
	}
}
