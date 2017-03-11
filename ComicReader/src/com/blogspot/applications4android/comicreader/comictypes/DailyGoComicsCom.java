package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blogspot.applications4android.comicreader.core.Strip;


/**
 * Base class for all comic series from gocomics.com
 */
// TODO: add unit-tests
public class DailyGoComicsCom extends DailyComic {
	/** set this comic name during the constructor of the child classes */
	protected String mComicName;
	/** set this first calendar date during the constructor of the child classes */
	protected Calendar mFirstCal;

	/** date for the latest calendar */
	private Calendar mLatestCal = null;
	/** gocomics calendar.js fetcher */
	private DailyGoComicsCalendar mGocc;


	/**
	 * Constructor
	 */
	public DailyGoComicsCom() {
		super();
		mComicName = null;
		mFirstCal = null;
		mLatestCal = null;
		mGocc = new DailyGoComicsCalendar();
		mGocc.setGoComics(this);
	}

	/**
	 * Get the first strip calendar
	 * @return first strip calendar
	 */
	public Calendar getFirstStripCalendar() {
		return mFirstCal;
	}

	/**
	 * get the url for the calendar json object for the given year and month
	 * @param year year of interest
	 * @param month month of interest
	 * @return string containing the json url
	 */
	public String getCalendarJsUrl(int year, int month) {
		return ("http://www.gocomics.com/calendar/"+mComicName+"/"+year+"/"+month);
	}

	@Override
	public String getComicWebPageUrl() {
		return ("http://www.gocomics.com/" + mComicName);
	}

	@Override
	public Strip getPreviousSessionStrip() {
		Calendar last = getTimeFromUrl(mPrevSessionUid);
		mGocc.setDate(last, 0);
		return super.getPreviousSessionStrip();
	}

	@Override
	protected String getFirstStripUrl() {
		Calendar cal = mGocc.setDate(mFirstCal, 1);
		return getUrlFromTime(cal);
	}

	@Override
	protected String getLatestStripUrl() {
		_setLatestCalendar();
		Calendar cal = mGocc.setDate(mLatestCal, -1);
		return getUrlFromTime(cal);
	}

	@Override
	protected String getNextStripUrl() {
		mGocc.setDate(getCurrentCal(), 0);
		Calendar curr = mGocc.getNextDate();
		return getUrlFromTime(curr);
	}

	@Override
	protected String getPreviousStripUrl() {
		mGocc.setDate(getCurrentCal(), 0);
		Calendar curr = mGocc.getPreviousDate();
		return getUrlFromTime(curr);
	}

	@Override
	protected String getRandomStripUrl() {
		return getUrlFromTime(mGocc.getRandomDate(getFirstCalendar(), getLatestCalendar()));
	}

	@Override
	protected Calendar getFirstCalendar() {
		return mFirstCal;
	}

	@Override
	protected Calendar getLatestCalendar() {
		_setLatestCalendar();
		return mLatestCal;
	}

	@Override
	protected Calendar getTimeFromUrl(String url) {
		String str = url.substring(url.length()-11, url.length()-1);
		String[] time = str.split("/");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]) - 1;
		int day = Integer.parseInt(time[2]);
		Calendar date = Calendar.getInstance(m_zone);
		date.set(year, month, day);
		return date;
	}

	@Override
	public String getUrlFromTime(Calendar cal) {
		return String.format(_getUrlFormat(),
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH)+1,
				cal.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
        String line;
        String final_str = null;
        String final_title = null;
        String final_date = null;
        while((line = reader.readLine()) != null) {

            if (line.contains("data-image")) {
                final_str = line;
            }

            if (line.contains("data-title")) {
                final_title = line;
            }

            if (line.contains("data-date")) {
                final_date = line;
            }
        }
    	final_str = final_str.replaceAll(".*=\"","");
        final_str = final_str.replaceAll("\".*","");

    	final_title = final_title.replaceAll(".*=\"","");
        final_title = final_title.replaceAll("\".*","");
        final_title = final_title.replaceAll("&amp;","&");

        final_date = final_date.replaceAll(".*=\"","");
        final_date = final_date.replaceAll("\".*","");
        final_title=final_title+": "+final_date;

        strip.setTitle(final_title);
        strip.setText("-NA-");

        return final_str;
    }

	@Override
	protected String[] urlsNotForCaching() {
		return null;
	}


	/**
	 * Url format for a comic strip
	 * @return the format string
	 */
	private String _getUrlFormat() {
		return ("http://www.gocomics.com/" + mComicName + "/%4d/%02d/%02d/");
	}

	/**
	 * Helper function to set latest calendar
	 */
	private void _setLatestCalendar() {
		if(mLatestCal != null) {
			return;
		}
		mLatestCal = Calendar.getInstance(m_zone);
		mGocc.findValidDays(mLatestCal.get(Calendar.YEAR), mLatestCal.get(Calendar.MONTH) + 1, -1);
		mLatestCal = mGocc.getCurrentCalendar();
	}

}
