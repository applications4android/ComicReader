package com.blogspot.applications4android.comicreader;

import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.core.AppInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Class responsible for updating the 'bound' for the current comic
 */
public class ComicBoundUpdater extends AsyncTask<Void, Void, Void> {
	/** for logging purposes */
	private static final String TAG = "ComicBoundUpdater";

	/** progress dialog to be shown in case of downloading a new strip */
	private ProgressDialog mPdiag;
	/** activity upon which to display the progress */
	private ComicStripViewer mMain;
	/** exception thrown by doInBackground */
	private Exception mExp = null;


	/**
	 * Set the activity upon which to display the progress dialog
	 * @param a the desired activity
	 */
	public void setActivity(ComicStripViewer a) {
		mMain = a;
	}

	/**
	 * Main function responsible for downloading the strip in another thread
	 * @param arg0 null
	 * @return null
	 */
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			mMain.getComic().getBound();
			return null;
		}
		catch(Exception e) {
			mExp = e;
			Log.e(TAG, "doInBackground failed! " + e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		mPdiag = new ProgressDialog(mMain);
		mPdiag.setCancelable(true);
		mPdiag.setMessage(mMain.getResources().getString(R.string.comic_bound_fetch_msg));
		mPdiag.show();
	}

	@Override
	protected void onPostExecute(Void arg0) {
		if(mExp != null) {
			if(!mMain.isFinishing()) {
				showError(mExp);
			}
			mExp.printStackTrace();
		}
		if(mPdiag.isShowing()) {
			mPdiag.dismiss();
		}
	}

	@Override
	protected void onProgressUpdate(Void... arg0) {
	}

	/**
	 * Creates error message from exception
	 * @param msg initial message
	 * @param sb string builder
	 * @param e exception
	 */
	private void createMessageFromException(String msg, StringBuilder sb, Exception e) {
		if(e != null) {
			String ver = AppInfo.getVersion(mMain.getApplicationContext(), mMain.getClass());
			sb.append(msg + " comic=" + mMain.getComic().getComicName() + " version=" + ver + " " + e + "\n");
			StackTraceElement[] trace = e.getStackTrace();
			if(trace.length > 0) {
				sb.append("Stack Trace follows:\n");
				int i = 1;
				for(StackTraceElement ste : e.getStackTrace()) {
					sb.append(" "+i+". "+ste+"\n");
					i++;
				}
			}
			else {
				sb.append("No stack trace!\n");
			}
		}
	}

	/**
	 * Showing an alertbox in case of errors
	 * @param e exception caused
	 */
	private void showError(Exception e) {
		if((mExp != null) && (mExp.getClass().getSimpleName().equals("ComicSDCardFull"))) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(mMain);
	        alertbox.setMessage(mMain.getResources().getString(R.string.sd_card_full_msg));
	        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
	        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	            	// TODO:
	            	/*
	        		Janitor clean = new Janitor();
	        		clean.setActivity(mMain);
	        		clean.execute((Void)null);
	        		mComic.displayStrip(mType);
	        		*/
	            }
	        });
	        alertbox.show();
	        return;
		}
		// all other errors!
		StringBuilder sb = new StringBuilder();
		createMessageFromException("ComicUpdater.onPostExecute failed! Reason: ", sb, e);
		createMessageFromException("ComicUpdater.doInBackground failed! Reason: ", sb, mExp);
		final String body = sb.toString();
		Log.e(TAG, body);
		AlertDialog.Builder alertbox = new AlertDialog.Builder(mMain);
        alertbox.setMessage(mMain.getResources().getString(R.string.dnld_failed_msg));
        alertbox.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
        alertbox.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO:
				/*
        		Janitor clean = new Janitor();
        		clean.setActivity(mMain);
        		clean.execute((Void)null);
        		mComic.displayStrip(mType);
        		*/
			}
		});
        alertbox.show();
	}

}
