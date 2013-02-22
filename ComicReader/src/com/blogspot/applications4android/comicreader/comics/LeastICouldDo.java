package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Strip;

public class LeastICouldDo extends ArchivedComic {

//	private static final int mFirstYr = 2003;
//	private int mCurrYr;

	@Override
	public String getComicWebPageUrl() {
		return "http://www.leasticoulddo.com/";
	}

	
	//I dont know what should go here. FetchAllComicUrls does all the work.
	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
	//The comics have a start. So I only use that in combination with todays date.
	//Start date: 2003, 2, 10
	@Override
	protected void fetchAllComicUrls() {
		if (mComicUrls == null) {
			ArrayList<String> all_yrs = new ArrayList<String>();
			// get for all years
			final Calendar c = Calendar.getInstance();
			final Calendar today = Calendar.getInstance();
			c.clear();
			// for (c.set(2003, 2, 10); c.get(Calendar.MONTH) <= 2004;
			// c.add(Calendar.DAY_OF_YEAR, 1)) {
			for (c.set(2003, 2, 10); c.compareTo(today) <= -1; c.add(Calendar.DAY_OF_YEAR, 1)) {
				String surl = String.format("http://leasticoulddo.com/comic/%4d%02d%02d/", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
						c.get(Calendar.DAY_OF_MONTH));
				all_yrs.add(surl);
				Log.d("ComicDay", surl);

			}
			mComicUrls = new String[all_yrs.size()];
			all_yrs.toArray(mComicUrls);
		}
		mBound = new Bound(0, (long) (mComicUrls.length - 1));
	}

	@Override
	protected String getLatestStripUrl() {
		fetchAllComicUrls();
		return getStripUrlFromId(mComicUrls.length - 1);
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.leasticoulddo.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String date = url.replaceAll(".*comic/", "");
		date = date.replaceAll("/", "");
		strip.setTitle("Least I Could Do: " + date);
		strip.setText("-NA-");
		return "http://cdn.leasticoulddo.com/comics/" + date + ".gif";
	}

}
