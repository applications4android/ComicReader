package com.blogspot.applications4android.comicreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Receives the on-boot complete
 */
public class BackgroundCacheBootReciever extends BroadcastReceiver {
	/** for logging purposes only */
	private final static String TAG = "BGCacheOnBootComplete";


	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.d(TAG, "On Boot complete received. Setting up the alarm...");
		ActivitySettingsPage.launchRepeatedCaching(arg0, false);
	}

}
