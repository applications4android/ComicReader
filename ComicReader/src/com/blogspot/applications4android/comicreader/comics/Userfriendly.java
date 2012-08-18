package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;



public class Userfriendly extends DailyComic {

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(1997, 10, 17); // 1997/11/17
		return first;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.userfriendly.org/";
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.substring(url.length()-9, url.length()-1);
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(4, 6)) - 1;
		int day = Integer.parseInt(str.substring(6));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://ars.userfriendly.org/cartoons/?id=%4d%02d%02d/",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
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
		String final_date = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img border=\"0\" src=\"");
			if (index1 != -1) {
				final_str = str;
				final_date = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_date = final_date.replaceAll(".*id=","");
		final_date = final_date.replaceAll("\".*","");
		final_title = "Userfriendly" + ": " + final_date;
		strip.setTitle(final_title); 
		strip.setText("-NA-");
		return final_str;
	}
}
