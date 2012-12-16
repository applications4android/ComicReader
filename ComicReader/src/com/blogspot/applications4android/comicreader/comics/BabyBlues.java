package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class BabyBlues extends DailyComic {
	private Calendar mFirstCal;
	private Calendar mLatestCal = null;

	public BabyBlues() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1997, 0, 1);
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
		url = url.replace("http://babyblues.com/archive/index.php\\?&GoToDay=", "");
		String[] elements = url.split("/");
		int month = Integer.parseInt(elements[0]);
		int date = Integer.parseInt(elements[1]);
		int year = Integer.parseInt(elements[2]);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return cal;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://babyblues.com/archive/index.php?&GoToDay=%02d/%02d/%04d",
							cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://babyblues.com";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		return null;
		/*
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:image");
			if((final_str == null) && (index1 != -1)) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*content=\"","");
		final_str = final_str.replaceAll("\".*","");
		strip.setTitle(this.getClass().getSimpleName() + ": " + _getTimeStringFromUrl(strip.uid()));
		strip.setText("-NA-");
		return final_str;
		*/
	}

}
