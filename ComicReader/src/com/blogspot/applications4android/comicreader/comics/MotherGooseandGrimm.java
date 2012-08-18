package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Class for MotherGooseandGrimm comic series.
 */
public class MotherGooseandGrimm extends DailyComic {

    public String getComicWebPageUrl() {
		return "http://www.grimmy.com/comics.php";
	}

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(1994, 0, 1); // 1994-01-01
		return first;
	}

	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.substring(url.length()-14,url.length()-4);
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
		return String.format("http://www.grimmy.com/images/MGG_Archive/MGG_%4d/MGG-%4d-%02d-%02d.gif",
				cal.get(Calendar.YEAR),cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String url_str = url;
		String date = url_str.substring(url_str.length()-14,url_str.length()-4);
		strip.setTitle("Mother Goose & Grimm: " + date);
		strip.setText("-NA-");
		return url;
	}
}
