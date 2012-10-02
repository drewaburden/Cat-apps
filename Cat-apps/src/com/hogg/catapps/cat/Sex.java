/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Sex.java
 * 
 * Description: 
 * 		Defines the Sex enum. This defines all the possible states of the
 * 		cats' sex along with methods to easily convert them to strings. 
 * 
 * Contributors:
 * 		Drew Burden
 * 
 * Copyright © 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

public enum Sex {
	MALE {
		public String toString() {
			return "Male";
		}
	},
	FEMALE {
		public String toString() {
			return "Female";
		}
	}
}
