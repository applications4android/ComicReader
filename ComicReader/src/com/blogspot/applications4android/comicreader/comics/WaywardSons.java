package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;



public class WaywardSons extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://waywardsons.keenspot.com/";
	}

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2010, 4, 31); // 2010 05 31
		return first;
	}

	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace("http://waywardsons.keenspot.com/d/", "");
		str = str.replace(".html", "");
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(4, 6)) - 1;
		int day = Integer.parseInt(str.substring(6));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://waywardsons.keenspot.com/d/%4d%02d%02d.html",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String date = strip.uid().replace("http://waywardsons.keenspot.com/d/", "");
		date = date.replace(".html", "");
		String str = "http://cdn.waywardsons.keenspot.com/comics/" + date + ".jpg";
		strip.setTitle("Wayward Sons: " + date);
		strip.setText("-NA-");
		return str;
	}
}
