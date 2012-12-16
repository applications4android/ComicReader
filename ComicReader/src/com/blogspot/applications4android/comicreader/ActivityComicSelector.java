package com.blogspot.applications4android.comicreader;

import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.ComicActivity;
import com.blogspot.applications4android.comicreader.core.ComicClass;
import com.blogspot.applications4android.comicreader.core.ComicClassList;
import com.blogspot.applications4android.comicreader.core.IntentGen;
import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import android.app.AlertDialog;
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
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Activity to select your favorite comics
 */
public class ActivityComicSelector extends ComicActivity {
	/** for logging purposes only */
	private static final String TAG = "ComicSelector";
	/** location of the listview during the last time it was read */
	private static final String ITEM_POSITION_PREF = "selectComicsListViewPosPref";
	/** comic list */
	protected static ComicClassList mList;
	/** preview activity */
	protected static PreviewLauncher mPL = null;
	/** web page display activity */
	protected static ComicMainWebPageDisplay mWP = null;

	/** sort type */
	protected int mType;

	/** whether or not to store the comic class list */
	private boolean mStoreList = true;


	/** to launch the strip viewer activity in preview mode */
	private class PreviewLauncher implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "PreviewLauncher getting triggered...");
			Intent i = new Intent();
			i.setClass(ActivityComicSelector.this, ComicStripViewer.class);
			i.putExtra("title", (String)v.getTag());
			i.putExtra("mode", Comic.TYPE_PREVIEW);
			startActivityForResult(i, 0);
		}
	}

	/** to launch the alertdialog for opening the comic in a web page */
	private class ComicMainWebPageDisplay implements OnLongClickListener {
		public boolean onLongClick(View v) {
			String title = (String) v.getTag();
			try {
				Comic c = mList.getComicFromTitle(title);
				if(c == null) {
					Log.e(TAG, "no comic found for title="+title);
					return false;
				}
				final String url = c.getComicWebPageUrl();
				AlertDialog.Builder alertbox = new AlertDialog.Builder(ActivityComicSelector.this);
				alertbox.setMessage("Visit website of '" + title + "'?");
				alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						ActivityComicSelector.this.startActivity(IntentGen.linkViewIntent(url));
					}
				});
				alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
				alertbox.show();
				return true;
			}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}


	/**
	 * adapter class for efficient traversal of list items displayed in the main activity
	 */
	public static class EfficientAdapter extends BaseAdapter {
		/** Used to inflate the layout */
		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return mList.length();
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
				convertView = mInflater.inflate(R.layout.comic_selector_listview_content, null);
				holder = new ViewHolder();
				// comic select checkbox
				OnClickListener ocl = new OnClickListener() {
					public void onClick(View v) {
						try {
							int pos = (Integer) v.getTag();
							mList.toggleSelected(pos);
						}
						catch (ComicNotFoundException e) {
							e.printStackTrace();
						}
						notifyDataSetChanged();
					}
				};
				holder.text_name = (TextView) convertView.findViewById(R.id.comic_settings_item_name);
				holder.text_name.setOnLongClickListener(mWP);
				holder.sel = (CheckBox) convertView.findViewById(R.id.comic_settings_item);
				holder.sel.setOnClickListener(ocl);
				holder.preview = (Button) convertView.findViewById(R.id.comic_settings_item_preview);
				holder.preview.setOnClickListener(mPL);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				ComicClass clz = mList.getComicClassFromIndex(position);
				holder.text_name.setText(clz.mName);
				holder.text_name.setTag(clz.mName);
				holder.sel.setChecked(clz.mSel);
				holder.sel.setTag(position);
				holder.preview.setTag(clz.mName);
			}
			catch(ComicNotFoundException e) {
				e.printStackTrace();
			}
			convertView.setBackgroundColor((position & 1) == 1 ? mColor1 : mColor2);
			return convertView;		
		}

		/**
		 * A private class to hold the view which will be displayed in one row of the listview.
		 */
		static class ViewHolder {
			/** stores and displays the comic name */
			TextView text_name;
			/** whether this comic is selected or not */
			CheckBox sel;
			/** preview button */
			Button preview;
		}
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "on resume...");
		super.onResume();
		if(mPL == null) {
			mPL = new PreviewLauncher();
		}
		if(mWP == null) {
			mWP = new ComicMainWebPageDisplay();
		}
		GetAllComicsTask get_task = new GetAllComicsTask();
		get_task.execute((Void[])null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			storeComicClassList(mList, mStoreList);
		}
		catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		ListView l1 = (ListView) findViewById(R.id.comic_settings_list_view);
		int last = l1.getFirstVisiblePosition();
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(ITEM_POSITION_PREF, last);
		ed.commit();
		Log.d(TAG, "on pause...");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inf = getMenuInflater();
    	inf.inflate(R.menu.comic_selector_menu, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   		Resources res = getResources();
	   	switch(item.getItemId()) {
	   	case R.id.select_all:
	   		SelectAllComics sac = new SelectAllComics();
	   		sac.setMessage(res.getString(R.string.comic_select_all_msg));
	   		sac.execute(true);
	   		return true;
	   	case R.id.select_none:
	   		SelectAllComics dac = new SelectAllComics();
	   		dac.setMessage(res.getString(R.string.comic_select_none_msg));
	   		dac.execute(false);
	   		return true;
	   	case R.id.comic_request:
			AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
			alertbox.setTitle(getResources().getString(R.string.comic_request_subj));
			alertbox.setMessage(res.getString(R.string.comic_request_msg));
			alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			alertbox.show();
	   		return true;
	   	default:
	   		return super.onOptionsItemSelected(item);
	   	}
    }

    /**
     * Callback for the button to save changes and exit this activity
     * @param v button
     */
    public void saveChangesAndExit(View v) {
    	finish();
    }

    /**
     * Callback for the button to cancel the changes made and exit this activity
     * @param v button
     */
    public void cancelChangesAndExit(View v) {
    	mStoreList = false;
    	finish();
    }


    /**
	 * Helper function to initialize the list view containing all comics
	 * which the user wants to see. This will read the preferences set by
	 * the user in the settings activity.
	 * Also updates the callbacks for various UI elements in the activity
	 */
	protected void initListView() {
		setContentView(R.layout.comic_selector);
		ListView l1 = (ListView) findViewById(R.id.comic_settings_list_view);
		l1.setAdapter(new EfficientAdapter(this));
		ColorDrawable divcolor = new ColorDrawable(Color.WHITE);
		l1.setDivider(divcolor);
		l1.setDividerHeight(1);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		int last = sp.getInt(ITEM_POSITION_PREF, 0);
		l1.setSelection(last);
	}

    /**
     * Async task for selecting/deselecting all comics.
     */
    private class SelectAllComics extends AsyncTask<Boolean, Void, Void> {
    	private ProgressDialog mPdiag;
    	private String mMsg;
    	public void setMessage(String msg) {
    		mMsg = msg;
    	}
    	@Override
    	protected void onPreExecute() {
    		mPdiag = new ProgressDialog(ActivityComicSelector.this);
    		mPdiag.setCancelable(false);
    		mPdiag.setMessage(mMsg);
    		mPdiag.show();
    	}
		@Override
		protected Void doInBackground(Boolean... params) {
			mList.setAllSelected(params[0]);
			return null;
		}
    	@Override
    	protected void onProgressUpdate(Void... arg0) {
    	}
    	@Override
    	protected void onPostExecute(Void arg0) {
    		mPdiag.dismiss();
    		ListView l1 = (ListView) findViewById(R.id.comic_settings_list_view);
    		EfficientAdapter ea = (EfficientAdapter) l1.getAdapter();
    		ea.notifyDataSetChanged();
    	}
    }


	/**
	 * Asynctask responsible for getting the list of comics
	 */
	private class GetAllComicsTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog mPdiag;
    	private Exception mExp = null;
    	@Override
    	protected void onPreExecute() {
    		mPdiag = new ProgressDialog(ActivityComicSelector.this);
    		mPdiag.setCancelable(true);
    		mPdiag.setMessage(getResources().getString(R.string.comics_selector_setup_msg));
    		mPdiag.show();
    	}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				mList = setupComicsList("selectSortPref");
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
    		mPdiag.dismiss();
    	}
	};

}
