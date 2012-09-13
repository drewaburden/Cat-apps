package com.hogg.catapps.background;

import android.app.Activity;

// Wait for sleepTime milliseconds and runs method in activity's thread after done waiting.
public class BackgroundSleepThread {
	
	public BackgroundSleepThread(final Activity activity, final Runnable method, final int sleepTime) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				activity.runOnUiThread(method);
			}
		}).start();
	}

}
