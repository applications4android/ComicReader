package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Blondie extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.blondie.com";
	}

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2007, 10, 1); // 2007,11,1
		return first;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace("http://www.blondie.com/strip.php?comic=", "");
		String[] time = str.split("-");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]) - 1;
		int day = Integer.parseInt(time[2]);
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://www.blondie.com/strip.php?comic=%d-%d-%d",
							cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String date = url.replace("http://www.blondie.com/strip.php?comic=", "");
		String[] time = date.split("-");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]);
		int day = Integer.parseInt(time[2]);
		String surl = String.format("http://www.blondie.com/dailies/%d/%d/%d.jpg",year,month,day);
		strip.setTitle("Blondie: " + date);
		strip.setText("-NA-");
		return surl;
	}

}
