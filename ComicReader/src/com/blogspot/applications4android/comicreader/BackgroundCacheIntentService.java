package com.blogspot.applications4android.comicreader;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.ComicClass;
import com.blogspot.applications4android.comicreader.core.ComicClassList;
import com.blogspot.applications4android.comicreader.core.FullyAwakeIntentService;
import com.blogspot.applications4android.comicreader.core.Strip;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Actual class for background caching
 */
public class BackgroundCacheIntentService extends FullyAwakeIntentService {
	/** for logging purposes only */
	private static final String TAG = "BackgroundCache";

	/** sync backwards from latest */
	public static final int SYNC_FROM_LATEST = 1;
	/** sync forwards from previous session */
	public static final int SYNC_FROM_PREV_SESSION = 2;
	/** both the above */
	public static final int SYNC_BOTH = 3;

	/** id for notification manager */
	private static final int NOTIFY_ID = 123456;
	/** id for notification manager for sync progress */
	private static final int PROGRESS_NOTIFY_ID = 456789;
	/** sleep duration in await of wifi (in ms) */
	private static long SLEEP_DURATION = 45000;

	/** sync type */
	private int mSyncType;
	/** sort type */
	private int mSortType;
	/** number of strips to be cached for each comic */
	private int mNumStrips;
	/** number of strips actually downloaded */
	private int mNumDnlds;
	/** context */
	private Context mCtx;
	/** notificatio */
	private Notification mNotify;
	/** class list */
	private ComicClassList mList;
	/** total number of strips to be downloaded */
	private int mTotal;
	/** number of strips checked so far */
	private int mChecked;
	/** notification manager */
	private NotificationManager mMgr;
	/** progress message */
	private String mStr;

	/**
	 * Constructor
	 */
	public BackgroundCacheIntentService() {
		super(TAG);
	}

	@Override
	protected void doWork(Intent intent) {
		_mutexCall();
	}

	/**
	 * Waits for connectivity to come up
	 */
	private void _waitForConnectivity() {
		if (!_isOnWifi(mCtx) && !_isOnMobileData(mCtx)) {
			Log.d(TAG, "Sleeping for " + SLEEP_DURATION + " ms hoping that the network will turn on...");
			try {
				Thread.sleep(SLEEP_DURATION);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Sleeping is over...");
		}
	}

	/**
	 * This sync should be run mutually exclusively!
	 */
	synchronized private void _mutexCall() {
		mCtx = getApplicationContext();
		Log.d(TAG, "starting the service for caching comic strips in background ...");
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mCtx);
		if (!sp.getBoolean("backgroundCacheEnabledPref", false)) {
			Log.d(TAG, "Background caching not enabled. Returing back...");
			return;
		}
		_waitForConnectivity();
		if (_isOnWifi(mCtx)) {
			_cacheStrips(sp);
		}
		else if (_isOnMobileData(mCtx)) {
			if (sp.getBoolean("mobileDataCacheEnabledPref", false)) {
				_cacheStrips(sp);
			}
			else {
				Log.d(TAG, "You're on mobile-data, but caching is disabled on this network. So, returning back...");
			}
		}
		else {
			Log.d(TAG, "No wifi + no mobile-data. So, returing back...");
		}
		Log.d(TAG, "background comic cache service ended ...");
	}

	/**
	 * Callback for cancelling this progress
	 * @param v view generating this callback
	 */
	public void onCancelClick(View v) {
		final Resources res = mCtx.getResources();
		AlertDialog.Builder alertbox = new AlertDialog.Builder(mCtx);
		alertbox.setMessage(res.getString(R.string.my_comic_restore_confirmation));
		alertbox.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				stopSelf();
				mMgr.cancel(PROGRESS_NOTIFY_ID);
			}
		});
	}

	/**
	 * Caching the strips
	 * @param sp shared preferences
	 */
	private void _cacheStrips(SharedPreferences sp) {
		mSortType = Integer.parseInt(sp.getString("mySortPref", Integer.toString(ComicClassList.SORT_ALPHABETICAL)));
		mNumStrips = Integer.parseInt(sp.getString("numStripsCachePref", "5"));
		mSyncType = Integer.parseInt(sp.getString("syncTypePref", Integer.toString(SYNC_FROM_LATEST)));
		Log.d(TAG, "Setting up the progress bar for notifying sync updates...");
		try {
			mList = new ComicClassList(mCtx.getAssets());
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		mMgr = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
		mTotal = mList.numSelected() * mNumStrips;
		if (mSyncType == SYNC_BOTH) {
			mTotal *= 2;
		}
		mChecked = 0;
		mStr = mCtx.getResources().getString(R.string.bg_cache_progress_msg);
		if (sp.getBoolean("notificationPref", true)) {
			mNotify = new Notification(R.drawable.icon, "ComicReader", System.currentTimeMillis());
			Intent in = new Intent(mCtx, ActivityComicReader.class);
			mNotify.contentIntent = PendingIntent.getActivity(mCtx, 0, in, 0);
			mNotify.contentView = new RemoteViews(mCtx.getPackageName(), R.layout.bg_cache_progress);
			mNotify.flags = Notification.FLAG_ONGOING_EVENT;
			_updateProgress();
		}
		try {
			mNumDnlds = 0;
			mList.sortClasses(mSortType);
			int num = mList.length();
			for (int i = 0; i < num; ++i) {
				ComicClass clz = mList.getComicClassFromIndex(i);
				if (!clz.mSel) {
					continue;
				}
				Log.d(TAG, "Working on comic = " + clz.mName);
				Comic com = mList.getComicFromIndex(i);
				com.setComicName(clz.mName);
				com.readProperties();
				com.setLaunchType(Comic.TYPE_CACHING);
				boolean status = _syncAcomic(com, mNumStrips, mSyncType);
				com.writeProperties();
				Log.d(TAG, "Finished working on comic = " + clz.mName + " status=" + status);
				Log.d(TAG, "Number of downloads so far=" + mNumDnlds);
			}
		}
		catch (Exception e) {
			mMgr.cancel(PROGRESS_NOTIFY_ID);
			e.printStackTrace();
		}
		mMgr.cancel(PROGRESS_NOTIFY_ID);
		if (mNumDnlds <= 0) {
			Log.d(TAG, "No new strips to be read. So, not setting up a notification...");
			_commitLastSyncTime();
			return;
		}
		Intent i = new Intent(mCtx, ActivityComicReader.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(mCtx, 0, i, 0);
		// TODO: location of notifies.

		if (sp.getBoolean("notificationPref", true)) {
			mNotify = new Notification(R.drawable.icon, "ComicReader", System.currentTimeMillis());
			mNotify.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
			mNotify.setLatestEventInfo(mCtx, "Comic Reader", "Total of " + mNumDnlds + " newly downloaded strips", pi);
			Log.d(TAG, "Notifying the manager of this new notification...");
			mMgr.notify(NOTIFY_ID, mNotify);
		}

		_commitLastSyncTime();
	}

	/**
	 * Store the last sync time
	 */
	private void _commitLastSyncTime() {
		long lastSync = System.currentTimeMillis();
		Log.d(TAG, "last sync time = " + lastSync);
		SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(mCtx).edit();
		ed.putLong(ActivitySettingsPage.LAST_SYNC_PREF, lastSync);
		Calendar now = Calendar.getInstance();
		int mon = now.get(Calendar.MONTH) + 1;
		String str = "'" + now.get(Calendar.YEAR) + "/" + mon + "/" + now.get(Calendar.DAY_OF_MONTH);
		str += " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND) + "'";
		ed.putString(ActivitySettingsPage.LAST_SYNC_STRING_PREF, str);
		ed.commit();
	}

	/**
	 * Helper function to check whether we are on WIFI or not
	 * @param ctxt application context
	 * @return true if we are
	 */
	private boolean _isOnWifi(Context ctxt) {
		ConnectivityManager cm = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}

	/**
	 * Helper function to check whether we are on Mobile-data or not
	 * @param ctxt application context
	 * @return true if we are
	 */
	private boolean _isOnMobileData(Context ctxt) {
		ConnectivityManager cm = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return mobile.isConnected();
	}

	/**
	 * Updates the view for progress bar
	 */
	private void _updateProgress() {
		++mChecked;
		RemoteViews view = mNotify.contentView;
		view.setProgressBar(R.id.bg_cache_progress_bar, mTotal, mChecked, false);
		String s = mStr + " (" + mChecked + " of " + mTotal + ")";
		view.setTextViewText(R.id.bg_cache_text, s);
		mMgr.notify(PROGRESS_NOTIFY_ID, mNotify);
	}

	/**
	 * Syncs for a comic
	 * @param com comic
	 * @param numStrips number of strips to be synced
	 * @param sType sync type
	 * @return true if the sync succeeded
	 */
	private boolean _syncAcomic(Comic com, int numStrips, int sType) {
		switch (sType) {
		case SYNC_FROM_LATEST:
			return _syncFromLatest(com, numStrips);
		case SYNC_FROM_PREV_SESSION:
			return _syncFromPrevSession(com, numStrips);
		case SYNC_BOTH:
			return _syncBoth(com, numStrips);
		default:
			Log.e(TAG, "Bad sync type passed! (" + sType + ")");
			return false;
		}
	}

	/**
	 * Syncs for a comic from latest strip onwards
	 * @param com comic
	 * @param numStrips number of strips to be synced
	 * @return true if the sync succeeded
	 */
	private boolean _syncFromLatest(Comic com, int numStrips) {
		if (!_getStrip(com, Comic.NAV_LATEST_FORCE)) {
			return false;
		}
		boolean status = true;
		for (int i = 1; i < numStrips; ++i) {
			status = status && _getStrip(com, Comic.NAV_PREVIOUS);
		}
		return status;
	}

	/**
	 * Syncs for a comic from previous session strip onwards
	 * @param com comic
	 * @param numStrips number of strips to be synced
	 * @return true if the sync succeeded
	 */
	private boolean _syncFromPrevSession(Comic com, int numStrips) {
		if (!_getStrip(com, Comic.NAV_PREV_SESSION)) {
			return false;
		}
		boolean status = true;
		for (int i = 1; i < numStrips; ++i) {
			status = status && _getStrip(com, Comic.NAV_NEXT);
		}
		return status;
	}

	/**
	 * Syncs for a comic from both latest strip onwards and previous session
	 * strip onwards
	 * @param com comic
	 * @param numStrips number of strips to be synced in each direction
	 * @return true if the sync succeeded
	 */
	private boolean _syncBoth(Comic com, int numStrips) {
		return (_syncFromLatest(com, numStrips) && _syncFromPrevSession(com, numStrips));
	}

	/**
	 * Helper function to download the strip
	 * @param com comic
	 * @param type strip type
	 * @return true if download is successful
	 */
	private boolean _getStrip(Comic com, int type) {
		try {
			Strip s = com.navigateStrip(type);
			if (s.downloadImage(com)) {
				mNumDnlds++;
			}
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mCtx);
			if (sp.getBoolean("notificationPref", true)) {
				_updateProgress();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
