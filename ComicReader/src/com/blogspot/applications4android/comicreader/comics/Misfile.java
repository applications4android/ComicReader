package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Misfile extends DailyComic {
	private Calendar mFirstCal = null;
	private Calendar mLatestCal = null;

	public Misfile() {
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 1, 22);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.misfile.com";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("comic2");
			if (index1 != -1) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*src='","");
		final_str = final_str.replaceAll("'.*","");
		final_str = "http://misfile.com/" + final_str;
		String date = url.replaceAll(".*date=", "");
		strip.setTitle("Misfile: " + date); 
		strip.setText("-NA-");
		return final_str;
	}

	@Override
	protected Calendar getFirstCalendar() {
		return mFirstCal;
	}

	@Override
	protected Calendar getLatestCalendar() {
		if(mLatestCal != null) {
			return mLatestCal;
		}
		mLatestCal = Calendar.getInstance(m_zone);
		return mLatestCal;
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String date = url.replaceAll(".*date=", "");
		String[] elements = date.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(elements[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(elements[1])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(elements[2]));
		return cal;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://www.misfile.com/?date=%04d-%02d-%02d",
							cal.get(Calendar.YEAR),
							cal.get(Calendar.MONTH)+1,
							cal.get(Calendar.DAY_OF_MONTH));
	}
}
