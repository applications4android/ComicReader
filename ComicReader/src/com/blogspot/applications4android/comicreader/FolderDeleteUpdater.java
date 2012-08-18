package com.blogspot.applications4android.comicreader;

import java.io.File;

import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.core.FileUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;


/**
 * Class responsible for recursively deleting a directory
 */
public class FolderDeleteUpdater extends AsyncTask<File, Void, Void> {
	/** progress dialog to be shown in case of downloading a new strip */
	private ProgressDialog mPdiag;
	/** activity upon which to display the progress */
	private Activity mMain;
	/** message id to be displayed upon success */
	private int mId;


	/**
	 * Set the activity upon which to display the progress dialog
	 * @param a the desired activity
	 */
	public void setActivity(Activity a) {
		mMain = a;
	}

	/**
	 * Message Id to be displayed upon success
	 * @param id id
	 */
	public void setSuccessId(int id) {
		mId = id;
	}

	@Override
	protected Void doInBackground(File... arg0) {
		for(File f : arg0) {
			FileUtils.recursiveDelete(f);
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		mPdiag = new ProgressDialog(mMain);
		mPdiag.setCancelable(true);
		mPdiag.setMessage(mMain.getResources().getString(R.string.recursive_folder_delete_msg));
		mPdiag.show();
	}

	@Override
	protected void onPostExecute(Void arg0) {
		Toast.makeText(mMain, mMain.getResources().getString(mId), Toast.LENGTH_LONG).show();
		if(mPdiag.isShowing()) {
			mPdiag.dismiss();
		}
	}

	@Override
	protected void onProgressUpdate(Void... arg0) {
	}

}
