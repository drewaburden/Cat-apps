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
