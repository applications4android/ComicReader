package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class SafeHavens extends DailyComic {

	/** list of month names */
	private static final String[] MONTH_NAMES = {
		"january", "february", "march",
		"april", "may", "june", "july",
		"august", "september", "october",
		"november", "december"	};


	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2012, 6, 9); // 2012-Jul-09
		return first;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.safehavenscomic.com";
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		url = url.replaceAll(".*comics/", "");
		url = url.replaceAll("/", "");
		String[] time = url.split("-");
		int month = _getMonthIdFromName(time[0]);
		int day = Integer.parseInt(time[1]);
		int year = Integer.parseInt(time[2]);
//Log.d("SafeHavens", String.format("date=%d %d %d",year,month,day));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
// The date format is (using Linux date command formats)
// +%Y-%m-%d 	up thru 2012-Jun-30
// +%Y-%m-%d-2  for 2012-Jul-1 and 2012-Jul-8
// +fast%Y%m%d  from 2012-Jul-2 to 2012-Jul-7
		String str;
		String final_str = null;
		String final_title = null;
		String final_date = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:image");
			if (index1 != -1) {
				final_str = str;
			}
			index1 = str.indexOf("og:title");
			if (index1 != -1) {
				final_date = str;
			}
		}
		final_str = final_str.replaceAll(".*content=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_date = final_date.replaceAll(".*content=\"","");
		final_date = final_date.replaceAll("\".*","");
		final_title = "Safe Havens" + ": " + final_date;
		strip.setTitle(final_title);
		strip.setText("-NA-");
		return final_str;
    }

	@Override
	public String getUrlFromTime(Calendar cal) {
                String url = "http://www.safehavenscomic.com/comics/";
		url += _getMonthNameFromId(cal.get(Calendar.MONTH)) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.YEAR);
                return url;
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

	@Override
	protected boolean htmlNeeded() {
		return true;
	}
}
