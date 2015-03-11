package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Penny Arcade comics
 */
public class PennyArcade extends DailyComic {

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		//TODO: this has been done as there are a lot of exceptions in the beginning
		first.set(2001, 11, 31);   // 2001/12/31
		return first;
	}

	public String getComicWebPageUrl() {
		return "http://www.penny-arcade.com/comic/";
	}

	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url;
		str = str.replaceAll(".*comic/", "");
		String[] time = str.split("/");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]) - 1;
		int day = Integer.parseInt(time[2]);
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	protected String getNextStripUrl() {
		Calendar cal = getCurrentCal();
		cal.add(Calendar.DAY_OF_MONTH, 3);
		addException(cal, 1);
		return getUrlFromTime(cal);
	}

	@Override
	protected String getPreviousStripUrl() {
		Calendar cal = getCurrentCal();
		cal.add(Calendar.DAY_OF_MONTH, -2);
		addException(cal, -1);
		return getUrlFromTime(cal);
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
/* Strips are only published on Monday, Wednesday and Friday so make sure
   we land on one of those days by backing up. */
		if ( 	cal.get(cal.DAY_OF_WEEK)==cal.TUESDAY ||
			cal.get(cal.DAY_OF_WEEK)==cal.THURSDAY ||
			cal.get(cal.DAY_OF_WEEK)==cal.SATURDAY )
		{
                        cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		if ( 	cal.get(cal.DAY_OF_WEEK)==cal.SUNDAY )
		{
                        cal.add(Calendar.DAY_OF_MONTH, -2);
		}
                addException(cal, -1);
//Log.d("Penny_Arcade", String.format("XXXURL/%04d/%02d/%02d",
//	cal.get(Calendar.YEAR),
//	cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)));

		return String.format("http://www.penny-arcade.com/comic/%04d/%02d/%02d/",
			cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		String final_date = null;
		String curr_date = null;
		while((str = reader.readLine()) != null) {
			// one such server
			int index1 = str.indexOf("http://art.penny-arcade.com/photos");
			int index3 = str.indexOf("http://penny-arcade.smugmug.com/photos");
			if (index1 != -1 || index3 != -1) {
				final_str = str;
				final_date = str;
			}
			else {
				// second such server
				index1 = str.indexOf("http://www.penny-arcade.com/images");
				if (index1 != -1) {
					final_str = str;
					final_date = str;
				}
			}
			int index2 = str.indexOf("btnNews btn");
			if (index2 != -1) {
				curr_date = str;
			}
			
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
//Log.d("Penny_Arcade", "final_string " + final_str);
		final_date = final_date.replaceAll(".*alt=\"","");
		final_date = final_date.replaceAll("\".*","");
//Log.d("Penny_Arcade", "final_date " + final_date);

//		curr_date = curr_date.replaceAll(".*.com", "");
//		curr_date = curr_date.replaceAll("\".*", "");
//		curr_date = "http://penny-arcade.com/comic"+curr_date;	
//Log.d("Penny_Arcade", "curr_date " + curr_date);

		final_title = "Penny Arcade" + ": " + final_date;
//Log.d("Penny_Arcade", "final_title " + final_title);
		strip.setTitle(final_title);
		strip.setText("-NA-");
		return final_str;
	}
}
