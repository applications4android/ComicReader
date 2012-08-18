package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class USAcres extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.garfield.com/usacres/todayscomic.html";
	}

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2010, 2, 3); // 2010&addr=100303	
		return first;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace("http://www.garfield.com/usacres/vault.html?yr=", "");
		str = str.replaceAll("&addr=", "");
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(6,8)) - 1;
		int day = Integer.parseInt(str.substring(8));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String date = url.replace("http://www.garfield.com/usacres/vault.html?yr=", "");
		date = date.replaceAll("&addr=", "");
		int year = Integer.parseInt(date.substring(0, 4)) - 24;
		String rest = date.substring(6);
		rest = new StringBuffer(rest).insert(2, "-").toString();
		int actual_yr = year+24;
		strip.setTitle("USAcres: " + actual_yr +"-"+rest);
		strip.setText("-NA-");
		return "http://strips.orsonsfarm.com/usacresstrips/USA"+year+"-"+rest+".gif";
    }

	@Override
	public String getUrlFromTime(Calendar cal) {
		int yr2d = cal.get(Calendar.YEAR) - 2000;
		String str = String.format("http://www.garfield.com/usacres/vault.html?yr=%4d&addr=%02d%02d%02d",
									cal.get(Calendar.YEAR), yr2d, cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		return str;
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}
}