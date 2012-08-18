package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.core.Strip;



public class DailyOregonlive extends DailyComic {
	/** set this comic name during the constructor of the child classes */
	protected String mComicName;
	/** set this comic full name during the constructor of the child classes */
	protected String mComicFullName;
	/** set this first calendar date during the constructor of the child classes */
	protected Calendar mFirstCal;


	/**
	 * Constructor
	 */
	public DailyOregonlive() {
		super();
		mComicName = null;
		mComicFullName = null;
		mFirstCal = null;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.oregonlive.com/comics-kingdom/?feature_id="+mComicName;
	}

	@Override
	protected Calendar getFirstCalendar() {
		return mFirstCal;
	}

	@Override
	protected Calendar getLatestCalendar() {
		return Calendar.getInstance(m_zone);
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.substring(url.length()-12,url.length()-4);
		int year = Integer.parseInt(str.substring(0,4));
		int month = Integer.parseInt(str.substring(4,6)) - 1;
		int day = Integer.parseInt(str.substring(6));
		Calendar date = Calendar.getInstance();
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar in) {
		return String.format(_urlFormat(),
				in.get(Calendar.YEAR), in.get(Calendar.MONTH)+1, in.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) {
		String date = url.substring(url.length()-12, url.length()-4);
		strip.setTitle(mComicFullName + ": " + date);
		strip.setText("-NA-");
		return url;
    }


	/**
	 * Returns the url-format
	 * @return formatted string
	 */
	private String _urlFormat() {
		return "http://content.comicskingdom.net/"+mComicName+"/"+mComicName+".%4d%02d%02d.gif";
	}

	@Override
	protected String[] urlsNotForCaching() {
		return null;
	}

}
