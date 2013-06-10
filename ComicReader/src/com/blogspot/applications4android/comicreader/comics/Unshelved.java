package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class Unshelved extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.unshelved.com/";
	}

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2002, 2, 16); // 2002,Feb,16
		return first;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace("http://www.unshelved.com/", "");
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
		String surl = String.format("http://www.unshelved.com/%4d-%02d-%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));

		return surl;
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {

		// Get Title and URL
		String str;
		String final_title = null;
		String final_url = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("Unshelved strip for");
			if (index1 != -1) {
				final_title = str;
				final_url = str;
			}
		}
		final_url = final_url.replaceAll(".*src=\"", "");
		final_url = final_url.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*.for\\s", "");
		final_title = final_title.replaceAll("\".*", "");
		strip.setTitle("Unshelved: " + final_title);
		strip.setText("-NA-");
		return final_url;
	}

}
