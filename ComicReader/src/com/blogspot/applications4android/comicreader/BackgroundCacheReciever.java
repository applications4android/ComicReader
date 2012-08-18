package com.blogspot.applications4android.comicreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Receives the alarm-off broadcast event and starts a fully-awake intent service
 */
public class BackgroundCacheReciever extends BroadcastReceiver {
	/** for logging purposes only */
	private static final String TAG = "BgCacheReceiver";


	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.d(TAG, "BackgroundCacheReciever has started...");
		BackgroundCacheIntentService.acquireStaticLock(arg0);
		Intent i = new Intent(arg0, BackgroundCacheIntentService.class);
		arg0.startService(i);
		Log.d(TAG, "BackgroundCacheReciever has ended...");
	}

}
