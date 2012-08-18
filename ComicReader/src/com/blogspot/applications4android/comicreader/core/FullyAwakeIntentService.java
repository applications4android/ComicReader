package com.blogspot.applications4android.comicreader.core;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.PowerManager;
import android.util.Log;


/**
 * Class which is expected to launch a service while fully awake throughout
 * Code borrowed from Mark Murphy's WakefulIntentService
 */
public abstract class FullyAwakeIntentService extends IntentService {
	/** for logging purposes only */
	private static final String TAG = "FullyAwakeIntentService";
	/** wake lock */
	private static PowerManager.WakeLock mWakelock = null;
	/** wifi wake lock */
	private static WifiManager.WifiLock mWifiLock = null;


	/**
	 * Constructor
	 * @param name name of this class
	 */
	public FullyAwakeIntentService(String name) {
		super(name);
	}

	/**
	 * Acquire a lock on the static lock object
	 * @param context context
	 */
	public static void acquireStaticLock(Context context) {
		Log.d(TAG, "Acquiring all the wake locks...");
		getPowerLock(context).acquire();
		getWifiLock(context).acquire();
	}

	@Override
	final protected void onHandleIntent(Intent intent) {
		try {
			doWork(intent);
		}
		finally {
			Log.d(TAG, "Releasing all the wake locks...");
			getPowerLock(this).release();
			getWifiLock(this).release();
		}
	}

	/**
	 * Perform the actual work
	 * @param intent intent
	 */
	abstract protected void doWork(Intent intent);

	/**
	 * Acquires the lock object
	 * @param context context
	 * @return lock object
	 */
	synchronized private static PowerManager.WakeLock getPowerLock(Context context) {
		if (mWakelock == null) {
			PowerManager mgr = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			// This is the only way I was able to get the phone to be awake for the background-sync to happen!
			int flags = PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;
			mWakelock = mgr.newWakeLock(flags, TAG);
			mWakelock.setReferenceCounted(true);
		}
		return mWakelock;
	}

	/**
	 * Acquires the lock object
	 * @param context context
	 * @return lock object
	 */
	synchronized private static WifiLock getWifiLock(Context context) {
		if (mWifiLock == null) {
			WifiManager mgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			mWifiLock = mgr.createWifiLock(TAG);
			mWifiLock.setReferenceCounted(true);
		}
		return mWifiLock;
	}
}
