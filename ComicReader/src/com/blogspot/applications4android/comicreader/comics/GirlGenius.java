package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class GirlGenius extends DailyComic {

	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(2002, 10, 4);   // Nov 4, 2002
		return first;
	}

	public String getComicWebPageUrl() {
		return "http://www.girlgeniusonline.com/comic.php";
	}

	@Override
	protected Calendar getLatestCalendar() {
		Calendar in = Calendar.getInstance(m_zone);
		addException(in, -1);
		return in;
	}

	@Override
	public void addException(Calendar in, int increment) {
		// exception
		int dow = in.get(Calendar.DAY_OF_WEEK);
		int val;
		switch(dow) {
		case Calendar.MONDAY:
		case Calendar.WEDNESDAY:
		case Calendar.FRIDAY:
			val = 0;
			break;
		case Calendar.SUNDAY:
			val = (increment < 0)? -2 : 1;
			break;
		case Calendar.TUESDAY:
		case Calendar.THURSDAY:
			val = (increment < 0)? -1 : 1;
			break;
		default:
			val = (increment < 0)? -1 : 2;
		}
		in.add(Calendar.DAY_OF_MONTH, val);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url;
		int len = str.length();
		int year = Integer.parseInt(str.substring(len-8,len-4));
		int month = Integer.parseInt(str.substring(len-4, len-2)) - 1;
		int day = Integer.parseInt(str.substring(len-2,	len));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format("http://www.girlgeniusonline.com/comic.php?date=%4d%02d%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String url_link = url;
		String str_title = url_link;
		url_link = url_link.replaceAll(".*date=","http://www.girlgeniusonline.com/ggmain/strips/ggmain");
		url_link = url_link+".jpg";
		str_title = str_title.replaceAll(".*date=","");
		int len = str_title.length();
		str_title = "Girl Genius: " + str_title.substring(len-8, len-4) + "-" + str_title.substring(len-4, len-2) + "-" + str_title.substring(len-2, len);
		strip.setTitle(str_title);
		strip.setText(" -NA- ");
		return url_link;
	}
}
