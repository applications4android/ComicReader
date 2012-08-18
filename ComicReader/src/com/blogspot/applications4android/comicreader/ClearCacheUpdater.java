package com.blogspot.applications4android.comicreader;

import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.core.Comic;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Class responsible for downloading the comic-strip while displaying a progress dialog
 */
public class ClearCacheUpdater extends AsyncTask<Comic, Void, Void> {
	/** for logging purposes */
	private static final String TAG = "ClearCacheUpdater";

	/** progress dialog to be shown in case of downloading a new strip */
	private ProgressDialog mPdiag;
	/** activity */
	private ComicStripViewer mMain;
	/** navigation type */
	private int mType;


	/**
	 * Set the activity
	 * @param a activity
	 */
	public void setActivity(ComicStripViewer a) {
		mMain = a;
	}

	/**
	 * Navigation type
	 * @param type type
	 */
	public void setType(int type) {
		mType = type;
	}

	@Override
	protected Void doInBackground(Comic... arg0) {
		try {
			Comic c = arg0[0];
			c.clearCache();
			c.clearHistory();
			return null;
		}
		catch(Exception e) {
			Log.e(TAG, "doInBackground failed: "+e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		mPdiag = new ProgressDialog(mMain);
		mPdiag.setCancelable(true);
		mPdiag.setMessage(mMain.getResources().getString(R.string.comic_clear_cache_msg));
		mPdiag.show();
	}

	@Override
	protected void onPostExecute(Void arg0) {
		if(mType > 0) {
			mMain.displayStrip(mType);
		}
		if(mPdiag.isShowing()) {
			mPdiag.dismiss();
		}
	}

	@Override
	protected void onProgressUpdate(Void... arg0) {
	}

}
