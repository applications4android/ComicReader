package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class SluggyFreelance extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.sluggy.com";
	}

	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(1997, 8, 25); // 2007,11,1
		return first;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace(
				"http://www.sluggy.com/comics/archives/daily/", "");
		int year = Integer.parseInt(str.substring(0, 2)) + 2000;
		int month = Integer.parseInt(str.substring(2, 4)) - 1;
		int day = Integer.parseInt(str.substring(4, 6));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {

		String url = String.format(
				"http://www.sluggy.com/comics/archives/daily/%d%02d%02d",
				getYear(cal), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		
		return url;
	}

	private int getYear(Calendar cal) {
		String year = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		return Integer.parseInt(year);
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
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<img src=\"/images/comics");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"", "");
		final_str = final_str.replaceAll("\".*", "");
		final_str = getComicWebPageUrl() + final_str;
		final_title = final_title.replaceAll(".*for\\s", "");
		final_title = final_title.replaceAll("\".*", "");
		strip.setTitle("Sluggy freelance: " + final_title);
		strip.setText("-NA-");
		return final_str;
	}

}
