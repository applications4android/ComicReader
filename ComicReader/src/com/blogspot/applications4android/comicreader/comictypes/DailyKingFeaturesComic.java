package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import android.util.Log;

import com.blogspot.applications4android.comicreader.core.Strip;


/**
 * Base class for all comics supported by KingFeatures comics (like Zits, BeetleBailey, ...)
 */
public abstract class DailyKingFeaturesComic extends DailyComic {
	/** for logging purposes */
	private static final String TAG = "DailyKingFeaturesComic";

	/** calendar for the latest comic */
	protected Calendar mLatestCal = null;
	/** calendar for the first comic */
	protected Calendar mFirstCal = null;

	/** list of month names */
	private static final String[] MONTH_NAMES = {	"january", "febraury", "march",
													"april", "may", "june", "july",
													"august", "september", "october",
													"november", "december"	};


	@Override
	protected Calendar getLatestCalendar() {
		if(mLatestCal != null) {
			return mLatestCal;
		}
		mLatestCal = Calendar.getInstance(m_zone);
		return mLatestCal;
	}

	@Override
	protected Calendar getFirstCalendar() {
		return mFirstCal;
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String web = getComicWebPageUrl();
		url = url.replace(web, "");
		url = url.replace("/comics/", "");
		url = url.replaceAll("/", "");
		String[] elements = url.split("-");
		int month = _getMonthIdFromName(elements[0]);
		int day = Integer.parseInt(elements[1]);
		int year = Integer.parseInt(elements[2]);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return cal;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		String url = getComicWebPageUrl() + "/comics/";
		url += _getMonthNameFromId(cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
		Log.d(TAG, "getUrlFromTime: url=" + url);
		return url;
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:image");
			if((final_str == null) && (index1 != -1)) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*content=\"","");
		final_str = final_str.replaceAll("\".*","");
		strip.setTitle(this.getClass().getSimpleName() + ": " + _getTimeStringFromUrl(strip.uid()));
		strip.setText("-NA-");
		return final_str;
	}

	private int _getMonthIdFromName(String name) {
		int idx = 0;
		for(String mon: MONTH_NAMES) {
			if(mon.equals(name)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}

	private String _getMonthNameFromId(int idx) {
		return MONTH_NAMES[idx];
	}

	private String _getTimeStringFromUrl(String url) {
		Calendar cal = getTimeFromUrl(url);
		String str = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		return str;
	}
}
