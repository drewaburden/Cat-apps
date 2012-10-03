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

import com.hogg.catapps.Init;
import com.hogg.catapps.R;

public enum Sex {
	MALE {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_sex_male);
		}
	},
	FEMALE {
		public String toString() {
			return Init.getAppContext().getString(R.string.cat_sex_female);
		}
	}
}
