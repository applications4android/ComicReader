package com.blogspot.applications4android.comicreader.core;

import java.io.IOException;

import org.json.JSONException;

import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;
import com.blogspot.applications4android.comicreader.exceptions.ComicSortException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


/** 
 * Useful class for defining some of the common things for all the
 * activities used in this application.
 */
// TODO: add unit-tests
public class ComicActivity extends Activity {
	/** for logging purposes only */
	private static final String TAG = "ComicActivity";

	/** color1 for the list view row */
	protected static int mColor1 = Color.parseColor("#222222");
	/** color2 for the list view row */
	protected static int mColor2 = Color.parseColor("#333333");

	/** the broadcast receiver for network connectivity check */
	//private ConnectivityCheck mNetworkChk;


	/**
	 * Hide the reset button which comes when double-tap is succeeded
	 */
	public void hideResetButton() {
		Button b = (Button) findViewById(R.id.reset_button);
		if(b != null) {
			b.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Display the reset button which comes when double-tap is succeeded
	 */
	public void showResetButton() {
		Button b = (Button) findViewById(R.id.reset_button);
		if(b != null) {
			b.setVisibility(View.VISIBLE);
		}
	}

    /**
	 * Called when the activity is first created.
	 * @param savedInstanceState bundle containing saved data from a previous onPause.
	 */
    @Override
    protected void onResume() {
    	Log.d(TAG, "on resume ....");
        super.onResume();
    	//mNetworkChk = new ConnectivityCheck();
    	//registerReceiver(mNetworkChk, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    	Log.d(TAG, "on resume finished ....");
    }

	/**
	 * Called when the activity is finished.
	 * @param savedInstanceState bundle containing saved data from a previous onPause.
	 */
    @Override
    protected void onPause() {
    	Log.d(TAG, "on pause ....");
    	//unregisterReceiver(mNetworkChk);
    	//mNetworkChk = null;
        super.onPause();
    	Log.d(TAG, "on pause finished ....");
    }

	/**
	 * Exit from this activity.
	 * @param v View which generated this callback.
	 */
    protected void ExitActivity(View v) {
    	finish();
    }

    /**
     * Set the content view and display a html page.
     * Useful if the activity is going to display ONLY a html page.
     * @param layout_id the resource-id of the layout for which to set the content view.
     * @param web_view_id resource-id of the webView widget where to display the html page.
     * @param page the desired html page.
     */
    protected void displayHtml(int layout_id, int web_view_id, String page) {
   		setContentView(layout_id);
  		WebView wv = (WebView) findViewById(web_view_id);
   		wv.loadUrl(page);
   		return;
    }
    /**
     * Helper function to store the list of comics
     * @param list comic list
     * @param store whether to store or not
     * @throws ComicSDCardFull 
     * @throws IOException 
     */
    protected void storeComicClassList(ComicClassList list, boolean store) throws IOException, ComicSDCardFull {
    	if(!store) {
			Log.d(TAG, "Not storing the changes made to comic class list...");
			return;
    	}
    	if(list == null) {
    		Log.d(TAG, "Comic class list is null. Not proceeding with storage...");
    		return;
    	}
    	Log.d(TAG, "Storing the changes made to comic class list...");
    	list.storeSelected();
    }

    /**
     * Helper function to setup the comic list
     * @param pref sort type pref-key
     * @return comic list
     * @throws ComicSortException 
     * @throws ComicNotFoundException 
     * @throws JSONException 
     * @throws IOException 
     */
    protected ComicClassList setupComicsList(String pref) throws ComicSortException, ComicNotFoundException, IOException, JSONException {
		ComicClassList list = new ComicClassList(getAssets());
		_readOldFavs(list);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String val = sp.getString(pref, String.valueOf(ComicClassList.SORT_ALPHABETICAL));
		int type = Integer.parseInt(val);
		list.sortClasses(type);
		return list;
    }

    /**
     * For backwards compatibility
     * @param list comic list
     * @throws ComicNotFoundException 
     */
    private void _readOldFavs(ComicClassList list) throws ComicNotFoundException {
    	String settings = "ComicReaderSettings";
    	Log.d(TAG, "Trying to read favorite comics from old prefs at '"+settings+"'");
    	SharedPreferences sp = getSharedPreferences(settings, MODE_PRIVATE);
    	int num = list.length();
    	for(int i=0;i<num;++i) {
    		ComicClass clz = list.getComicClassFromIndex(i);
    		String pref = clz.mPref;
    		if(sp.getBoolean(pref, false)) {
    			clz.mSel = true;
    		}
    	}
    	//  If this file exists the next time we are loading the app, this can cause issues.
    	SharedPreferences.Editor ed = sp.edit();
    	ed.clear();
    	ed.commit();
    }
}
