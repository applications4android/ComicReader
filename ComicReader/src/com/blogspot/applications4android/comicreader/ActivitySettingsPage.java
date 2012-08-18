package com.blogspot.applications4android.comicreader;

import java.util.Calendar;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Main class for comic-reader settings
 */
public class ActivitySettingsPage extends PreferenceActivity {
	/** for logging purposes only */
	private static final String TAG = "ComicReaderSettings";
	/** milliseconds in a day */
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;
	/** key storing the last time synced */
	public static final String LAST_SYNC_PREF = "lastTimeSyncedPref";
	/** key storing the last time synced as a string */
	public static final String LAST_SYNC_STRING_PREF = "lastTimeSyncedStringPref";


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
	protected void onPause() {
    	launchRepeatedCaching(this, false);
    	super.onPause();
    }

    /**
     * Sets up the default values for background caching
     * @param act activity calling this function
     */
    public static void setupDefaults(Activity act) {
    	Log.d(TAG, "Setting up the default values for ComicReader...");
    	Context ctx = act.getApplicationContext();
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
    	SharedPreferences.Editor e = sp.edit();
    	e.putBoolean("backgroundCacheEnabledPref", true);
    	e.putString("backgroundCacheFreqPref", "4");
    	e.putString("numStripsCachePref", "3");
    	e.commit();
    }

    /**
     * Start/setup the caching service
     * @param ctx context used
     * @param force forcefully setup the alarm even if background caching is currently disabled
     * This will NOT cache the strips, but'll only setup the alarm
     */
    public static void launchRepeatedCaching(Context ctx, boolean force) {
    	Intent i = new Intent(ctx, BackgroundCacheReciever.class);
    	PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, i, 0);
    	AlarmManager mgr = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
    	Log.d(TAG, "Cancelling the previous alarm...");
    	mgr.cancel(pi);
    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
    	if(!sp.getBoolean("backgroundCacheEnabledPref", false) && !force) {
    		Log.d(TAG, "Caching disabled. Returning back...");
    		return;
    	}
    	int hour = Integer.parseInt(sp.getString("backgroundCacheStartTimePref", "6"));
    	int cacheFreq = Integer.parseInt(sp.getString("backgroundCacheFreqPref", "1"));
    	long interval = ONE_DAY / cacheFreq;
    	Calendar cal = Calendar.getInstance();
    	long now = System.currentTimeMillis();
    	cal.set(Calendar.HOUR_OF_DAY, hour);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	long sixAm = cal.getTimeInMillis();
    	long gap = now - sixAm;
    	if(gap < 0) {
    		cal.add(Calendar.DAY_OF_MONTH, -1);
    		sixAm = cal.getTimeInMillis();
    		gap = now - sixAm;
    	}
    	long next = ((gap / interval) + 1) * interval;
    	next += sixAm;
    	long diff = next - now;
    	Log.d(TAG, "Alarm for interval="+interval+" firstTime="+next+" now="+now+" diff="+diff);
    	mgr.setRepeating(AlarmManager.RTC_WAKEUP, next, interval, pi);
    	return;
    }

    /**
     * Checks when was the last time sync was performed
     * @param ctx context
     * @return true if there needs to be a sync to be performed now
     */
    public static boolean checkForLastSynced(Context ctx) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		long last = sp.getLong(LAST_SYNC_PREF, 0);
		long now = System.currentTimeMillis();
		long diff = now - last;
		long freq = Long.parseLong(sp.getString("backgroundCacheFreqPref", "1"));
		freq = ActivitySettingsPage.ONE_DAY / freq;
		if(diff > freq) {
			Log.d(TAG, "lastsynced="+last+" now="+now+" diff="+diff+" freq="+freq+". So syncing now...");
			return true;
		}
		else {
			Log.d(TAG, "lastsynced="+last+" now="+now+" diff="+diff+" freq="+freq+". So NOT syncing now...");
			return false;
		}
    }

}
