package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class DayByDay extends DailyComic {

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2002, 10, 1); // 2002/11/01/
		return first;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.daybydaycartoon.com/";
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.substring(url.length()-11, url.length()-1);
		String[] time = str.split("/");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]) - 1;
		int day = Integer.parseInt(time[2]);
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		String final_date = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<p><img alt=");
			if (index1 != -1) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_date = final_str;
		final_date = final_date.replaceAll(".*/","");
		final_date = final_date.replaceAll(".jpg", "");
		final_date = final_date.replaceAll(".gif", "");
		final_title = "Day By Day: " + final_date;
		strip.setTitle(final_title); 
		strip.setText("-NA-");
		return final_str;
    }

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://www.daybydaycartoon.com/%4d/%02d/%02d/",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}
}
