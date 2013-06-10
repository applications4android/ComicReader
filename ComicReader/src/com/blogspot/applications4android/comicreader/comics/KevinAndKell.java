package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class KevinAndKell extends DailyComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.kevinandkell.com/";
	}

	
	@Override
	protected Calendar getFirstCalendar() {
		Calendar first = Calendar.getInstance();
		first.set(1995, 8, 3); // 1995,Sept,3
		return first;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.replace("http://www.kevinandkell.com/", "");	
		String[] time = str.split("/");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1].substring(2,4))- 1;
		int day = Integer.parseInt(time[1].substring(4,6));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		String surl =  String.format("http://www.kevinandkell.com/%4d/kk%02d%02d.html",
				 cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		
		return surl;
	}


	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {

		//Get URL
		Calendar cal = getTimeFromUrl(url);
		String surl = String.format("http://www.kevinandkell.com/%4d/strips/kk%4d%02d%02d.gif",
									cal.get(Calendar.YEAR), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		//Get Title
		String str;
		String final_title = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("\"caption\"");
			if (index1 != -1) {
				final_title = str;
			}
		}
		final_title = final_title.replaceAll(".*.>&quot;","");
		final_title = final_title.replaceAll("&quot;.*","");
		strip.setTitle("Kevin and Kell: " + final_title);		
		strip.setText("-NA-");
		return surl;
	}

}
