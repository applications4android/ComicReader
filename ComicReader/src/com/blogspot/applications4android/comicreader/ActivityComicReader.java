package com.blogspot.applications4android.comicreader;

import java.io.File;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.core.Cache;
import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.ComicActivity;
import com.blogspot.applications4android.comicreader.core.ComicClassList;
import com.blogspot.applications4android.comicreader.core.FileUtils;
import com.blogspot.applications4android.comicreader.core.IntentGen;
import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



/**
 * Main activity which will displayed to the user.
 * This will contain all the comic strips which the user can click to
 * browse through them.
 */
public class ActivityComicReader extends ComicActivity {
	/** for logging purposes only */
	private static String TAG = "ComicReader";
	/** location of the listview during the last time it was read */
	private static final String ITEM_POSITION_PREF = "myComicsListViewPosPref";
	/** pref-key for welcome message */
	private static final String WELCOME_KEY = "welcomeMsg_1";
	/** pref-key for first run */
	private static final String FIRST_RUN = "firstRun_1";
	/** pref-key for last update! */
	private static final String LAST_UPDATE_NOTICE = "lastUpdateNotice";

	/** list of comics */
	protected static int[] mComicItems;
	/** comic list */
	protected static ComicClassList mList;
	/** latest activity */
	protected static LatestLauncher mLL = null;
	/** favorites activity */
	protected static FavoriteLauncher mFL = null;
	/** prev-session activity */
	protected static PrevSessionLauncher mPL = null;

	/** sort type */
	protected int mType;


	/** to launch the strip viewer activity in latest mode */
	private class LatestLauncher implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "LatestLauncher getting triggered...");
			Intent i = new Intent();
			i.setClass(ActivityComicReader.this, ComicStripViewer.class);
			i.putExtra("title", (String)v.getTag());
			i.putExtra("mode", Comic.TYPE_LATEST);
			startActivityForResult(i, 0);
		}
	}

	/** to launch the strip viewer activity in prev-session mode */
	private class PrevSessionLauncher implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "PrevSessionLauncher getting triggered...");
			Intent i = new Intent();
			i.setClass(ActivityComicReader.this, ComicStripViewer.class);
			i.putExtra("title", (String)v.getTag());
			i.putExtra("mode", Comic.TYPE_PREV_SESSION);
			startActivityForResult(i, 0);
		}
	}

	/** to launch the strip viewer activity in favorite strips mode */
	private class FavoriteLauncher implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "FavoriteLauncher getting triggered...");
			Intent i = new Intent();
			i.setClass(ActivityComicReader.this, ComicStripViewer.class);
			i.putExtra("title", (String)v.getTag());
			i.putExtra("mode", Comic.TYPE_FAVORITE);
			startActivityForResult(i, 0);
		}
	}


	/**
	 * adapter class for efficient traversal of list items displayed in the main activity
	 */
	private static class EfficientAdapter extends BaseAdapter {
		/** Used to inflate the layout */
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return mComicItems.length;
		}
		@Override
		public Object getItem(int position) {
			return position;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.main_listview_content, null);
				holder = new ViewHolder();
				holder.latest = (Button) convertView.findViewById(R.id.comic_item_latest);
				holder.latest.setOnClickListener(mLL);
				holder.favorite = (Button) convertView.findViewById(R.id.comic_item_fav);
				holder.favorite.setOnClickListener(mFL);
				holder.previous = (Button) convertView.findViewById(R.id.comic_item_prev);
				holder.previous.setOnClickListener(mPL);
				convertView.setTag(holder);
				convertView.setBackgroundColor(mColor1);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				String txt = mList.getComicClassFromIndex(mComicItems[position]).mName;
				holder.latest.setText(txt);
				holder.latest.setTag(txt);
				holder.favorite.setTag(txt);
				holder.previous.setTag(txt);
			}
			catch(ComicNotFoundException e) { // This should never occur!
				e.printStackTrace();
			}
			return convertView;		
		}

		/**
		 * A private class to hold the view which will be displayed in one row of the listview.
		 */
		static class ViewHolder {
			/** Previous button*/
			public Button previous;
			/** Favorite button*/
			public Button favorite;
			/** Latest button*/
			public Button latest;
		}
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "on resume...");
		super.onResume();
		if(mLL == null) {
			mLL = new LatestLauncher();
		}
		if(mFL == null) {
			mFL = new FavoriteLauncher();
		}
		if(mPL == null) {
			mPL = new PrevSessionLauncher();
		}
		GetComicsTask get_task = new GetComicsTask();
		get_task.execute((Void[])null);
	}

	@Override
	protected void onPause() {
		ListView l1 = (ListView) findViewById(R.id.main_list_view);
		if(l1 != null) {
			int last = l1.getFirstVisiblePosition();
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor ed = sp.edit();
			ed.putInt(ITEM_POSITION_PREF, last);
			ed.commit();
		}
		try {
			storeComicClassList(mList, true);
		}
		catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		super.onPause();
		Log.d(TAG, "on pause...");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inf = getMenuInflater();
    	inf.inflate(R.menu.comic_reader_menu, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	final Resources res = getResources();
    	Intent i = new Intent();
    	switch(item.getItemId()) {
    	case R.id.select:
    		i.setClass(ActivityComicReader.this, ActivityComicSelector.class);
    		startActivityForResult(i, 0);
    		return true;
    	case R.id.settings:
    		i.setClass(ActivityComicReader.this, ActivitySettingsPage.class);
    		startActivityForResult(i, 0);
    		return true;
    	case R.id.backup_favs:
    	{
    		String msg = "";
    		try {
    			mList.storeSelected();
        		File src = mList.selectedFile();
        		File dst = new File(FileUtils.getSdcard(), "backup_selected.json");
        		dst.delete();
        		if(FileUtils.copyFile(src, dst)) {
        			msg = "Successfully backed up 'My Comics' list to '" + dst.getPath() + "'";
        		}
        		else {
        			msg = "Failed to backup 'My Comics' list to '" + dst.getPath() + "'";
        		}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    			msg = "Failed to backup 'My Comics' list. Reason:" + e.getMessage();
    		}
    		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    	}
    		return true;
    	case R.id.restore_favs:
    	{
    		final File dst = mList.selectedFile();
    		dst.delete();
    		final File src = new File(FileUtils.getSdcard(), "backup_selected.json");
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ActivityComicReader.this);
	        alertbox.setMessage(res.getString(R.string.my_comic_restore_confirmation));
	        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(ActivityComicReader.this, res.getString(R.string.my_comic_restore_abort), Toast.LENGTH_LONG).show();
				}
			});
	        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	        		String msg;
	        		if(FileUtils.copyFile(src, dst)) {
	        			msg = "Successfully restored 'My Comics' list from '" + src.getPath() + "' to" + dst.getPath();
	        		}
	        		else {
	        			msg = "Failed to restore 'My Comics' list from '" + src.getPath() + "'";
	        		}
	        		Toast.makeText(ActivityComicReader.this, msg, Toast.LENGTH_LONG).show();
	        		GetComicsTask get_task = new GetComicsTask();
	        		get_task.execute((Void[])null);
	            }
	        });
	        alertbox.show();
    		return true;
    	}
    	case R.id.sync_now:
    		_syncNow();
    		return true;
    	case R.id.clear_all_cache:
    	{
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ActivityComicReader.this);
	        alertbox.setMessage(res.getString(R.string.clear_all_cache_confirmation));
	        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(ActivityComicReader.this, res.getString(R.string.clear_all_cache_abort), Toast.LENGTH_LONG).show();
				}
			});
	        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	            	FolderDeleteUpdater fdu = new FolderDeleteUpdater();
	            	fdu.setActivity(ActivityComicReader.this);
	            	fdu.setSuccessId(R.string.clear_all_cache_success);
	            	fdu.execute(new File(FileUtils.getComicRoot(), Cache.CACHE),
	            				new File(FileUtils.getComicRoot(), Comic.PROPS));
	            }
	        });
	        alertbox.show();
    		return true;
    	}
    	case R.id.last_synced_time:
    	{
			AlertDialog.Builder alertbox = new AlertDialog.Builder(ActivityComicReader.this);
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String last = sp.getString(ActivitySettingsPage.LAST_SYNC_STRING_PREF, "Never");
			String msg = "Last synced on: " + last;
			msg += "\n Press 'Sync' to forcefully retrieve the latest strips.";
	        alertbox.setMessage(msg);
	        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
	        alertbox.setPositiveButton("Sync", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	            	_syncNow();
	            }
	        });
	        alertbox.show();
    		return true;
    	}
    	case R.id.disclaimer:
    		i.putExtra("page", "file:///android_asset/Disclaimer.html");
    		i.setClass(ActivityComicReader.this, ActivityPageViewer.class);
    		startActivityForResult(i, 0);
    		return true;
    	case R.id.about:
    		i.putExtra("page", "file:///android_asset/About.html");
    		i.setClass(ActivityComicReader.this, ActivityPageViewer.class);
    		startActivityForResult(i, 0);
    		return true;
    	case R.id.help:
    		i.putExtra("page", "file:///android_asset/Help.html");
    		i.setClass(ActivityComicReader.this, ActivityPageViewer.class);
    		startActivityForResult(i, 0);
    		return true;
    	case R.id.share:
    		ActivityComicReader.this.startActivity(
    				IntentGen.shareLinkChooserIntent(res.getString(R.string.share_header),
    						res.getString(R.string.app_blog_url),
    						"Share this app..."));
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }


    /**
	 * Helper function to initialize the list view containing all comics
	 * which the user wants to see. This will read the preferences set by
	 * the user in the settings activity.
	 * Also updates the callbacks for various UI elements in the activity
	 */
	protected void initListView() {
		_showWelcomeMessage();
		_lastUpdateNotice();
		if((mComicItems == null) || (mComicItems.length == 0)) {
			setContentView(R.layout.comic_reader_empty_list);
			return;
		}
		setContentView(R.layout.comic_reader);
		ListView l1 = (ListView) findViewById(R.id.main_list_view);
		if(l1.getAdapter() == null) {
			l1.setAdapter(new EfficientAdapter(this));
		}
		else {
			EfficientAdapter ea = (EfficientAdapter) l1.getAdapter();
			ea.notifyDataSetChanged();
		}
		ColorDrawable divcolor = new ColorDrawable(Color.WHITE);
		l1.setDivider(divcolor);
		l1.setDividerHeight(1);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		int last = sp.getInt(ITEM_POSITION_PREF, 0);
		l1.setSelection(last);
		if(ActivitySettingsPage.checkForLastSynced(getApplicationContext())) {
			_syncNow();
		}
	}

	/**
	 * Things to be performed when running for the first time
	 */
	private void _firstRun() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		if(!sp.getBoolean(FIRST_RUN, true)) {
			return;
		}
		Log.d(TAG, "Running this app for the first time!");
		ActivitySettingsPage.setupDefaults(this);
		SharedPreferences.Editor e = sp.edit();
		e.putBoolean(FIRST_RUN, false);
		e.commit();
	}

	/**
	 * Showing that we are not going to support this app for long! Need all your help in keeping ComicReader alive!
	 */
	private void _lastUpdateNotice() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final SharedPreferences.Editor e = sp.edit();
		ActivitySettingsPage.launchRepeatedCaching(this, true);
		if(!sp.getBoolean(LAST_UPDATE_NOTICE, true)) {
			return;
		}
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle(getResources().getString(R.string.last_update_title));
		alertbox.setMessage(getResources().getString(R.string.last_update_msg));
		alertbox.setPositiveButton("I'm ready to help!", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				e.putBoolean(LAST_UPDATE_NOTICE, false);
	           	e.commit();
            	Resources res = ActivityComicReader.this.getResources();
    	   		String[] email = new String[] { res.getString(R.string.dev_email) };
    	   		String subj = res.getString(R.string.last_update_volunteer_subj);
    	   		String body = res.getString(R.string.last_update_volunteer_body);
    	   		ActivityComicReader.this.startActivity(IntentGen.emailChooserIntent(email, subj, body, false, "Send Report..."));
			}
		});
		alertbox.setNeutralButton("Go to github", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				e.putBoolean(LAST_UPDATE_NOTICE, false);
	           	e.commit();
				String url = ActivityComicReader.this.getString(R.string.github_link);
				ActivityComicReader.this.startActivity(IntentGen.linkViewIntent(url));
			}
		});
		alertbox.setNegativeButton("Not interested", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
				e.putBoolean(LAST_UPDATE_NOTICE, false);
	           	e.commit();
	        }
	    });
		alertbox.show();
	}

	/**
	 * Display a welcome message for the first time users!
	 */
	private void _showWelcomeMessage() {
		_firstRun();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final SharedPreferences.Editor e = sp.edit();
		ActivitySettingsPage.launchRepeatedCaching(this, true);
		if(!sp.getBoolean(WELCOME_KEY, true)) {
			return;
		}
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setTitle(getResources().getString(R.string.help_title));
		alertbox.setMessage(getResources().getString(R.string.help_msg));
		alertbox.setPositiveButton("Show Again", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
				e.putBoolean(FIRST_RUN, false);
	        	e.putBoolean(WELCOME_KEY, true);
	        	e.commit();
	        }
	    });
		alertbox.setNeutralButton("Go to FAQs", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String url = ActivityComicReader.this.getResources().getString(R.string.faq_url);
				ActivityComicReader.this.startActivity(IntentGen.linkViewIntent(url));
			}
		});
		alertbox.setNegativeButton("Don't Show", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
				e.putBoolean(FIRST_RUN, false);
	        	e.putBoolean(WELCOME_KEY, false);
	           	e.commit();
	        }
	    });
		alertbox.show();
	}

	/**
	 * Sync now
	 */
	private void _syncNow() {
		Log.d(TAG, "Syncing starting now...");
		Intent i = new Intent();
		i.setClass(this, SyncNowReciever.class);
		PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
		AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		mgr.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pi);
	}

	/**
	 * Asynctask responsible for getting the list of comics
	 */
	private class GetComicsTask extends AsyncTask<Void, Void, Void> {
	   	private ProgressDialog m_pdia;
	   	private Exception mExp = null;
	   	@Override
	   	protected void onPreExecute() {
	   		m_pdia = new ProgressDialog(ActivityComicReader.this);
	   		m_pdia.setCancelable(true);
	   		m_pdia.setMessage(getResources().getString(R.string.my_comics_setup_msg));
	   		m_pdia.show();
	   	}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				mList = setupComicsList("mySortPref");
				mComicItems = mList.getSelectedComicList();
			}
			catch(Exception e) {
				mExp = e;
			}
			return null;
		}
	   	@Override
	   	protected void onProgressUpdate(Void... arg0) {
	   	}
	   	@Override
	   	protected void onPostExecute(Void arg0) {
    		if(mExp != null) {
    			mExp.printStackTrace();
    			Toast.makeText(getApplicationContext(), "Failed to 'setupComicsList'! Reason: "+mExp.getMessage(), Toast.LENGTH_LONG).show();
    		}
	   		initListView();
	   		m_pdia.dismiss();
	   	}
	};

}
