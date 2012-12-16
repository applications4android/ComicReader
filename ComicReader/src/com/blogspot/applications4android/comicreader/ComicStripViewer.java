package com.blogspot.applications4android.comicreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.ComicActivity;
import com.blogspot.applications4android.comicreader.core.ComicClassList;
import com.blogspot.applications4android.comicreader.core.FileUtils;
import com.blogspot.applications4android.comicreader.core.ImageType;
import com.blogspot.applications4android.comicreader.core.IntentGen;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.BitMapException;
import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;
import com.blogspot.applications4android.comicreader.numpicker.NumberPicker;
import com.blogspot.applications4android.comicreader.numpicker.NumberPickerDialog;
import com.blogspot.applications4android.comicreader.zoom.PanZoomControl;
import com.blogspot.applications4android.comicreader.zoom.PanZoomListener;
import com.blogspot.applications4android.comicreader.zoom.PanZoomView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Activity which is responsible for displaying a comic-series.
 * This also provides the UI for 'navigating' in this comic-series.
 */
// TODO: add unit-tests
public class ComicStripViewer extends ComicActivity {
	/** for logging purposes only */
	private static final String TAG = "StripViewer";

	/** Holds the comic series responsible for providing the requested comic-strip */
	private Comic mComic;
	/** The imageView where to display the current comic strip */
	private PanZoomView mPzv;
	/** title for this viewer */
	private String mTitle;
	/** listener for the activity */
	private PanZoomListener mPzl;
	/** controller for the view */
	private PanZoomControl mPzc;
	/** layout containing the zoom controls */
	private LinearLayout mCtrls;
	/** view mode */
	private int mMode = Comic.TYPE_LATEST;
	/** comic list for navigation */
	private ComicClassList mList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "on create ....");
        super.onCreate(savedInstanceState);
    	Bundle extras = getIntent().getExtras();
    	if(extras != null) {
    		mTitle = extras.getString("title");
    		mMode = extras.getInt("mode");
    	}
    	Log.d(TAG, "on create finished ...");
    }

    @Override
    public void onResume() {
    	Log.d(TAG, "on resume ....");
    	super.onResume();
    	setContentView(R.layout.comic_strip_viewer);
    	setTitle(mTitle);
    	mCtrls = (LinearLayout) findViewById(R.id.view_control);
   		mPzv = (PanZoomView) findViewById(R.id.image_viewer);
        mPzc = new PanZoomControl(mPzv);
        mPzv.setController(mPzc);
        mPzl = new PanZoomListener(mPzc, mCtrls);
        mPzv.setOnTouchListener(mPzl);
        try {
        	mList = setupComicsList("mSortPref");
        }
        catch(Exception e) {
        	e.printStackTrace();
        	Toast.makeText(getApplicationContext(), "Failed to 'setupComicsList'! Reason: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        _initComic();
    	Log.d(TAG, "on resume finished ...");
    }

    @Override
	protected void onPause() {
		Log.d(TAG, "on pause ....");
		try {
			_storeCurrentComic(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    	super.onPause();
		Log.d(TAG, "on pause finished ....");
    }

    /**
     * Returns the comic series being displayed right now
     * @return comic series
     */
    public Comic getComic() {
    	return mComic;
    }

	/**
	 * Returns the layout containing the controls for strip navigation
	 * @return layout
	 */
	public LinearLayout getControlsLayout() {
		return mCtrls;
	}

	/**
	 * Tells whether the cucrent comic-strip being displayed is favorite or not
	 * @return true if it is
	 */
	public boolean isCurrentFavorite() {
		if(mComic == null) {
			return false;
		}
		Strip curr = mComic.getCurrentStrip();
		if(curr == null) {
			return false;
		}
		return curr.isFavorite();
	}

    /**
     * Set the next comic in the list
     * @throws IOException 
     * @throws ComicNotFoundException 
     * @throws JSONException 
     * @throws ComicSDCardFull 
     */
    public void setupNextComic() throws IOException, ComicNotFoundException, JSONException, ComicSDCardFull {
    	if(mComic == null) {
    		return;
    	}
    	String title = _storeCurrentComic(false);
    	mComic = (mMode == Comic.TYPE_PREVIEW)? mList.getNextComic(title) : mList.getNextMyComic(title);
    	int type = (mMode == Comic.TYPE_PREV_SESSION)? Comic.NAV_PREV_SESSION : Comic.NAV_LATEST;
    	_setupCurrentComic(type);
    }

    /**
     * Set the previous comic in the list
     * @throws ComicNotFoundException 
     * @throws IOException 
     * @throws JSONException 
     * @throws ComicSDCardFull 
     */
    public void setupPreviousComic() throws ComicNotFoundException, IOException, JSONException, ComicSDCardFull {
    	if(mComic == null) {
    		return;
    	}
    	String title = _storeCurrentComic(false);
    	mComic = (mMode == Comic.TYPE_PREVIEW)? mList.getPreviousComic(title) : mList.getPreviousMyComic(title);
    	int type = (mMode == Comic.TYPE_PREV_SESSION)? Comic.NAV_PREV_SESSION : Comic.NAV_LATEST;
    	_setupCurrentComic(type);
    }

    /**
     * Setup the current comic
     * @param type navigation type
     * @throws FileNotFoundException
     * @throws IOException
     * @throws JSONException
     */
    private void _setupCurrentComic(int type) throws FileNotFoundException, IOException, JSONException {
    	_initCurrentComic();
    	displayStrip(type);
    }

    /**
     * Initialize the current comic
     * @throws FileNotFoundException
     * @throws IOException
     * @throws JSONException
     */
    private void _initCurrentComic() throws FileNotFoundException, IOException, JSONException {
    	mTitle = mComic.getComicName();
    	setTitle(mTitle);
    	mComic.readProperties();
    	mComic.setLaunchType(mMode);
    	if(mMode == Comic.TYPE_PREVIEW) {
    		mComic.setCacheEnabled(false);
    	}
    	else {
	    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    	mComic.setCacheEnabled(sp.getBoolean("backgroundCacheEnabledPref", false));
    	}
    	mPzv.setZoom(mComic.getDefaultZoom());
		mPzl.setComicActivity(this);
    }

    /**
     * Store the current comic's properties, before loading a new one
     * @param preserve do not delete the current comic.
     * @return title of the current comic
     * @throws IOException
     * @throws ComicSDCardFull 
     */
    private String _storeCurrentComic(boolean preserve) throws IOException, ComicSDCardFull {
    	mComic.setDefaultZoom(mPzv.getZoom());
    	mComic.writeProperties();
    	String title = mComic.getComicName();
    	if(!preserve) {
    		mComic = null;
    	}
    	return title;
    }

	/**
     * Helper function to setup a comic from title
     */
    private void _initComic() {
    	int type = Comic.NAV_CURRENT;
    	try {
    		if(mComic == null) {
    			mComic = mList.getComicFromTitle(mTitle);
    			type = (mMode == Comic.TYPE_PREV_SESSION)? Comic.NAV_PREV_SESSION : Comic.NAV_LATEST;
    		}
			_initCurrentComic();
    		setZoomButtonListeners();
    		Log.d(TAG, "type being displayed = "+type);
        	displayStrip(type);
    	}
    	catch(Exception e) {
    		Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
    	}
    }

	/**
	 * This updates the view with the current comic-strip
	 * @throws BitMapException 
	 * @throws FileNotFoundException 
	 */
	public void updateBitmap() throws FileNotFoundException, BitMapException {
		if(mComic == null) {
			return;
		}
		Strip s = mComic.getCurrentStrip();
		if(s == null) {
			mPzv.setImageBitmap(null);
			mPzv.invalidate();
			return;
		}
		mPzv.invalidate();
		mPzv.setImageBitmap(s.getBitmapFromFile());
		Log.d(TAG, "Setting the title to be "+s.getTitle());
		setTitle(s.getTitle());
		mPzv.setPanXY(0, 0);
		hideResetButton();
		mPzv.invalidate();
		s.setAsRead(true);
	}

    /**
     * Set listeners for zoom in/out buttons
     */
    private void setZoomButtonListeners() {
    	Button text = (Button) findViewById(R.id.image_text);
    	text.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				mPzl.resetChangeVisibility();
	    	    AlertDialog.Builder alertbox = new AlertDialog.Builder(ComicStripViewer.this);
	    	    if(mComic.currentHasImageText()) {
	    	    	alertbox.setMessage(mComic.getCurrentStrip().getText());
	    	    }
	    	    else {
	    	    	alertbox.setMessage("-NA-");
	    	    }
	            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface arg0, int arg1) {
	                }
	            });
	            alertbox.show();
			}
		});
    	Button fav = (Button) findViewById(R.id.strip_favorite);
    	fav.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				mPzl.resetChangeVisibility();
				Button b = (Button) arg0;
				int id;
				if(mComic.isCurrentFavorite()) {
					mComic.setCurrentAsFavorite(false);
					id = R.drawable.star_nonfav;
				}
				else {
					mComic.setCurrentAsFavorite(true);
					id = R.drawable.star_fav;
				}
				b.setBackgroundDrawable(getResources().getDrawable(id));
			}
		});
    	Button share = (Button) findViewById(R.id.strip_share);
    	share.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				mPzl.resetChangeVisibility();
				if(mComic == null) {
					return;
				}
				Strip s = mComic.getCurrentStrip();
				if(s == null) {
					return;
				}
				Log.d(TAG, "Share strip="+s.getTitle()+" uid="+s.uid());
				String url = s.getStripUrl();
				if(url == null) {
					url = s.uid();
				}
	    		ComicStripViewer.this.startActivity(IntentGen.shareLinkChooserIntent("ComicReader- "+s.getTitle(), url, "Share this comic..."));
			}
		});
		Button zout = (Button) findViewById(R.id.zoom_out_button);
		zout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(mComic == null) {
					return;
				}
				if(mComic.getCurrentStrip() == null) {
					return;
				}
				mPzl.resetChangeVisibility();
				mPzl.setModeToZoom();
				mPzc.zoom(PanZoomListener.ZOOM_FACTOR, PanZoomListener.MODE_ZOOM_OUT);
				if(mPzc.isMinimumZoom()) {
					mPzc.resetState();
				}
			}
		});
		Button zin = (Button) findViewById(R.id.zoom_in_button);
		zin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(mComic == null) {
					return;
				}
				if(mComic.getCurrentStrip() == null) {
					return;
				}
				mPzl.resetChangeVisibility();
				mPzl.setModeToZoom();
				mPzc.zoom(PanZoomListener.ZOOM_FACTOR, PanZoomListener.MODE_ZOOM_IN);
			}
		});
		Button resetB = (Button) findViewById(R.id.reset_button);
		resetB.setVisibility(View.INVISIBLE);
		resetB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mPzl.setModeToPan();
				Button b = (Button) v;
				b.setVisibility(View.INVISIBLE);
			}
		});
    	Button prev = (Button) findViewById(R.id.strip_prev);
    	prev.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				displayStrip(Comic.NAV_PREVIOUS);
			}
		});
    	Button next = (Button) findViewById(R.id.strip_next);
    	next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				displayStrip(Comic.NAV_NEXT);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inf = getMenuInflater();
    	inf.inflate(R.menu.image_viewer_menu, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	final Resources res = getResources();
    	try {
	    	switch(item.getItemId()) {
	    	case R.id.comic_select:
	    		_getUserInputAndDisplayStrip();
	    		return true;
	    	case R.id.comic_clear_cache:
	    	{
				AlertDialog.Builder alertbox = new AlertDialog.Builder(ComicStripViewer.this);
		        alertbox.setMessage(res.getString(R.string.comic_cache_clean_confirmation));
		        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(ComicStripViewer.this, res.getString(R.string.comic_cache_clean_abort), Toast.LENGTH_LONG).show();
					}
				});
		        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface arg0, int arg1) {
		            	cacheCleaner(-1);
		            }
		        });
		        alertbox.show();
	    		return true;
	    	}
	    	case R.id.comic_first:
	    		displayStrip(Comic.NAV_FIRST);
	    		return true;
	    	case R.id.comic_latest:
	    		displayStrip(Comic.NAV_LATEST);
	    		return true;
	    	case R.id.comic_rand:
	    		displayStrip(Comic.NAV_RANDOM);
	    		return true;
	    	case R.id.comic_save:
	    		if(mComic.getCurrentStrip() == null) {
	    			return true;
	    		}
	    		File src = new File(mComic.getCurrentStrip().getImagePath());
	    		Intent i = new Intent();
	    		File root = new File(FileUtils.getSdcard(), "SavedComics");
	    		root.mkdirs();
	    		String fi = mComic.currentTitleAsValidFilename() + ImageType.getImageType(src.getPath()).toString();
	    		File out = new File(root, fi);
	    		i.putExtra("src_file", src.getPath());
	    		i.putExtra("dest_file", out.getPath());
	    		i.setClass(ComicStripViewer.this, ActivitySaveStrip.class);
	    		ComicStripViewer.this.startActivityForResult(i, 0);
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
    	}
    	catch(Exception e) {
    		Log.e(TAG, "onOptionsItemSelected: failed. Reason: "+e);
    		e.printStackTrace();
    		return false;
    	}
    }

    /**
     * Updater to clear the cache
     * @param type whether to update the strip (pass a value <0 to not update)
     */
    public void cacheCleaner(int type) {
		ClearCacheUpdater ccu = new ClearCacheUpdater();
		ccu.setActivity(this);
		ccu.setType(type);
		ccu.execute(mComic);
    }

	/**
	 * Update the view in the given activity with the comic type
	 * @param comic_type type of comic to be fetched.
	 */
	public void displayStrip(int comic_type) {
		try {
			ComicUpdater cu = new ComicUpdater();
			cu.setActivity(this);
			cu.execute(comic_type);
		}
		catch(Exception e) {
			Log.e(TAG, "displayStrip failed! Reason: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Update the view in the given activity with the comic selected by the user
	 * @param comic_url comic url to be displayed
	 */
	public void selectAndDisplayStrip(String comic_url) {
		try {
			ComicSelectUpdater csu = new ComicSelectUpdater();
			csu.setActivity(this);
			csu.execute(comic_url);
		}
		catch(Exception e) {
			Log.e(TAG, "selectAndDisplayStrip failed! Reason: " + e);
			e.printStackTrace();
		}
	}

    /**
     * Function responsible for asking user to input a comic strip id/calendar
     * and display that strip.
     */
    private void _getUserInputAndDisplayStrip() {
    	if(mComic == null) {
    		return;
    	}
		_updateBound();
    	if(mComic.dialogType() == Comic.DIALOG_DATE) {
    		DailyComic dc = (DailyComic) mComic;
    		Calendar cal = dc.getCurrentCal();
    		int year = cal.get(Calendar.YEAR);
    		int monthOfYear = cal.get(Calendar.MONTH);
    		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    		DatePickerDialog dpd = new DatePickerDialog(this, mDatesetListener, year, monthOfYear, dayOfMonth);
    		dpd.show();
    	}
    	else {
    		IndexedComic ic = (IndexedComic) mComic;
    		Bound b = mComic.getBound();
    		NumberPickerDialog npd = new NumberPickerDialog(this, mNumsetListener, (int)b.min, (int)b.max, ic.getCurrentId());
    		npd.show();
    	}
    }

	/**
	 * Update the bound for the current comic
	 */
	private void _updateBound() {
		try {
			ComicBoundUpdater cbu = new ComicBoundUpdater();
			cbu.setActivity(this);
			cbu.execute((Void[])null);
		}
		catch(Exception e) {
			Log.e(TAG, "updateBound failed! Reason: " + e);
			e.printStackTrace();
		}
	}

    /**
     * Get the strip from the given url
     * @param url url
     * @return the strip
     * @throws ClientProtocolException
     * @throws URISyntaxException
     * @throws IOException
     * @throws ComicSDCardFull
     */
    public Strip getStripFromUrl(String url) throws ClientProtocolException, URISyntaxException, IOException, ComicSDCardFull {
    	return mComic.getStripFromUrl(url);
    }

    /**
     * NumberPicker onsetlistener for comics which use ints for indexing
     */
    private NumberPickerDialog.OnSetListener mNumsetListener = new NumberPickerDialog.OnSetListener() {
		public void onNumSet(NumberPicker view, int num) {
			IndexedComic ic = (IndexedComic) mComic;
			num = ic.addException(num, -1);
			String url = ic.getStripUrlFromId(num);
			selectAndDisplayStrip(url);
		}
	};

	/**
     * DatePicker onsetlistener for comics which use calendar for indexing
     * This also has an option for bound check
     */
    private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
    	private String getCalendarString(long millis) {
			Date da = new Date(millis);
			Calendar c = Calendar.getInstance();
			c.setTime(da);
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			return String.format("%4d-%02d-%02d", y, m, d);
    	}
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
			long val = cal.getTimeInMillis();
			Bound b = mComic.getBound();
			if(b.isUnderLimit(val)) {
				DailyComic dc = (DailyComic) mComic;
				dc.addException(cal, -1);
				String url = dc.getUrlFromTime(cal);
				selectAndDisplayStrip(url);
			}
			else {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(ComicStripViewer.this);
				alertbox.setTitle("Bad Date Selected!");
				alertbox.setMessage("You should select dates which are within the range.\n"+
									"First Comic Date  = " + getCalendarString(b.min) + "\n"+
									"Latest Comic Date = " + getCalendarString(b.max));
				alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface arg0, int arg1) {
				    	arg0.cancel();
				    }
				 });
				alertbox.show();
			}
		}
	};
}
