package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Penny Arcade comics
 * NOTE: this fails with the android emulator running at APT=10, but works
 * OK with the current OS on a real phone.
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
		return "https://www.penny-arcade.com/comic/";
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
   	String str_url;
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
	str_url=String.format("https://www.penny-arcade.com/comic/%04d/%02d/%02d/",
	cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
//Log.d("Penny_Arcade", str_url);

	return str_url;
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
//Log.d("Penny_Arcade", "in parse");
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("photos.smugmug.com");
			if (index1 != -1 ) {
				final_str = str;
			}
			int index2 = str.indexOf("<h2>");
			if (index2 != -1 ) {
				final_title = str;
			}
			
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
//Log.d("Penny_Arcade", "final_str " + final_str);

		final_title = final_title.replaceAll(".*<h2>","Penny Arcade: ");
		final_title = final_title.replaceAll("</h2>.*","");
//Log.d("Penny_Arcade", "final_title " + final_title);
		strip.setTitle(final_title);
		strip.setText("-NA-");
		return final_str;
	}
}
