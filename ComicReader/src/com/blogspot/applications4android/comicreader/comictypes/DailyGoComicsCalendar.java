package com.blogspot.applications4android.comicreader.comictypes;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.RandUtils;


/**
 * Class responsible for querying the calendar.js from gocomics
 */
// TODO: add unit-tests
public class DailyGoComicsCalendar {
	/** current year */
	public int mCurrYr = -1;
	/** current month */
	public int mCurrMon = -1;
	/** array of valid days for this month */
	public int[] mValidDays = null;

	/** current position in the array */
	private int mValidCurr = -1;
	/** instance of the gocomics */
	private DailyGoComicsCom mGcc;


	/**
	 * set the gocomics instance for this object
	 * @param gcc gocomics instance
	 */
	public void setGoComics(DailyGoComicsCom gcc) {
		mGcc = gcc;
	}

	/**
	 * Helper function to construct the calendar instance from the current temporary state
	 * @return calendar instance
	 */
	public Calendar getCurrentCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(mCurrYr, mCurrMon-1, mValidDays[mValidCurr]);
		return cal;
	}

	/**
	 * get the previous date wrt the current state
	 * @return previous date
	 */
	public Calendar getPreviousDate() {
		mValidCurr--;
		if(mValidCurr < 0) {
			findValidDays(mCurrYr, mCurrMon-1, -1);
			mValidCurr = mValidDays.length - 1;
		}
		Calendar cal = getCurrentCalendar();
		return cal;
	}

	/**
	 * get the next date wrt the current state
	 * @return next date
	 */
	public Calendar getNextDate() {
		mValidCurr++;
		if(mValidCurr >= mValidDays.length) {
			findValidDays(mCurrYr, mCurrMon+1, 1);
			mValidCurr = 0;
		}
		Calendar cal = getCurrentCalendar();
		return cal;
	}

	/**
	 * This will be used by first/latest calendar 'getters' in gocomics
	 * @param date date of the first/latest calendar
	 * @param curr >0 means first calendar, <0 means latest calendar, else the same date as in 'date'
	 * @return the current calendar
	 */
	public Calendar setDate(Calendar date, int curr) {
		int inc = (curr > 0)? 1 : -1;
		mCurrYr = date.get(Calendar.YEAR);
		mCurrMon = date.get(Calendar.MONTH);
		int dt = date.get(Calendar.DAY_OF_MONTH);
		findValidDays(mCurrYr, mCurrMon+1, inc);
		if(curr > 0) {
			mValidCurr = 0;
		}
		else if(curr < 0) {
			mValidCurr = mValidDays.length-1;
		}
		else {
			int i = 0;
			for(int d : mValidDays) {
				if(d == dt) {
					mValidCurr = i;
					break;
				}
				i++;
			}
			if(i == mValidDays.length) { // not found
				mValidCurr = 0;
			}
		}
		Calendar cal = getCurrentCalendar();
		return cal;
	}

	/**
	 * get a random date wrt the given bounds
	 * @return random date
	 */
	public Calendar getRandomDate(Calendar first, Calendar latest) {
		long firstm = first.getTimeInMillis();
		long nowm = latest.getTimeInMillis();
		long delta = nowm - firstm + 1;
		Date time = new Date(RandUtils.getPositiveLong(delta, firstm));
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		mCurrYr = cal.get(Calendar.YEAR);
		mCurrMon = cal.get(Calendar.MONTH) + 1;
		findValidDays(mCurrYr, mCurrMon, 1);
		mValidCurr = RandUtils.getPositiveInt(mValidDays.length);
		cal = getCurrentCalendar();
		return cal;
	}

	/**
	 * tells whether the given year and month lie within the first/latest comic dates
	 * @param yr year
	 * @param mon month
	 * @return true if its within the range
	 */
	private boolean withinRange(int yr, int mon) {
		// current date
		Calendar cal = Calendar.getInstance();
		int now_year = cal.get(Calendar.YEAR);
		int now_month = cal.get(Calendar.MONTH) + 1;
		// first date
		cal = mGcc.getFirstStripCalendar();
		int first_year = cal.get(Calendar.YEAR);
		int first_month = cal.get(Calendar.MONTH) + 1;
		if(yr < first_year)  return false;
		if(yr > now_year)    return false;
		if((yr == first_year) && (mon < first_month)) return false;
		if((yr == now_year) && (mon > now_month))     return false;
		return true;
	}

	/**
	 * gets the valid days for the given year and month.
	 * Also update them if there are no valid days in the current month
	 * @param year desired year
	 * @param mon desired month
	 * @param increment whether to increment or decrement
	 */
	public void findValidDays(int year, int mon, int increment) {
		int[] arr = null;
		// out of bounds?
		if(mon <= 0) {
			mon = 12;
			year--;
		}
		if(mon > 12) {
			mon = 1;
			year++;
		}
		while(true) {
			arr = getValidDays(year, mon);
			if(arr != null) {
				mValidDays = arr;
				mCurrMon = mon;
				mCurrYr = year;
				mValidCurr = mValidDays.length - 1;
				break;
			}
			// nothing found in the current month, so move forward to next/prev month
			if(increment < 0) {
				mon--;
				if(mon <= 0) {
					mon = 12;
					year--;
				}
			}
			else {
				mon++;
				if(mon > 12) {
					mon = 1;
					year++;
				}
			}
			// bound check!
			if(!withinRange(year, mon)) {
				break;
			}
		}
	}

	/**
	 * gets the valid days for the given year and month
	 * @param year desired year
	 * @param month desired month
	 * @return the array of valid days for this month
	 */
	private int[] getValidDays(int year, int month) {
		try {
			String str = Downloader.downloadToString(new URI(mGcc.getCalendarJsUrl(year, month)));
			Log.d("DGCC", str);
			// no result :(
			if((str == null) || (str.length() <= 1)) {
				return null;
			}
			// remove the leading and trailing brackets
			str = str.replaceFirst("\\[\"", "");
			str = str.replaceFirst("\"\\]", "");
			// split the dates
			String[] days = str.split("\",\"");
			int numdays = days.length;
			if(numdays <= 0) {
				return null;
			}
			int[] day_i = new int[numdays];
			// get only the days for this month!
			for(int i=0;i<numdays; i++ ) {
				days[i] = days[i].substring(days[i].length()-2);
				day_i[i] = Integer.parseInt(days[i]);
			}
			return day_i; // days;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
