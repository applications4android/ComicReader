package com.blogspot.applications4android.comicreader.comictypes;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.Strip;


/**
 * This class is useful for all those comics which are released daily and are indexed
 * by calendar date.
 */
// TODO: add unit-tests
public abstract class DailyComic extends Comic {
	/** use US timezone for dailycomic! */
	protected static TimeZone m_zone = TimeZone.getTimeZone("GMT-08:00");

	/** bound for this comic */
	protected Bound mBound;


	/**
	 * Constructor
	 */
	public DailyComic() {
		super();
		mBound = null;
	}

	/**
	 * For those days which do not have a comic strip
	 * If the given daily comic series WAS irregular previously but now has been
	 * regular, then you have to override this function!
	 * @param in the calendar which needs to take care of this
	 * @param increment which date needs to be shown instead.
	 */
	public void addException(Calendar in, int increment) {
	}

	/**
	 * Helper function to get the current calendar
	 * @return calendar
	 */
	public Calendar getCurrentCal() {
		Strip s = getCurrentStrip();
		return getTimeFromUrl(s.uid());
	}

	@Override
	public Bound getBound() {
		if(mBound != null) {
			return mBound;
		}
		Calendar first = getFirstCalendar();
		first.set(Calendar.SECOND, 0);
		first.set(Calendar.MINUTE, 0);
		first.set(Calendar.HOUR_OF_DAY, 0);
		Calendar latest = getLatestCalendar();
		latest.set(Calendar.SECOND, 0);
		latest.set(Calendar.MINUTE, 0);
		latest.set(Calendar.HOUR_OF_DAY, 0);
		mBound = new Bound(first.getTimeInMillis(), latest.getTimeInMillis());
		return mBound;
	}

	@Override
	public int dialogType() {
		return Comic.DIALOG_DATE;
	}

	@Override
	protected String getLatestStripUrl() {
		return getUrlFromTime(getLatestCalendar());
	}

	@Override
	protected String getFirstStripUrl() {
		return getUrlFromTime(getFirstCalendar());
	}

	@Override
	protected String getNextStripUrl() {
		Calendar cal = getCurrentCal();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		addException(cal, 1);
		return getUrlFromTime(cal);
	}

	@Override
	protected String getPreviousStripUrl() {
		Calendar cal = getCurrentCal();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		addException(cal, -1);
		return getUrlFromTime(cal);
	}

	@Override
	protected String getRandomStripUrl() {
		long first = getFirstCalendar().getTimeInMillis();
		long now = getLatestCalendar().getTimeInMillis();
		long delta = now-first+1;
		Random rnd = new Random();
		long num = rnd.nextLong();
		// always the numbers must be positive!
		if(num < 0) {
			num = -num;
		}
		num = rnd.nextLong() % delta;
		if(num < 0) { num = -num; }
		num = num+first;
		Date time = new Date(num);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		addException(cal, 1);
		return getUrlFromTime(cal);
	}


	////// abstract functions //////
	/**
	 * Get a Calendar instance for the first comic strip.
	 * @return Calendar instance for the first strip
	 */
	protected abstract Calendar getFirstCalendar();

	/**
	 * Get a Calendar instance for the latest comic strip.
	 * @return Calendar instance for the latest strip
	 */
	protected abstract Calendar getLatestCalendar();

	/**
	 * Helper function to convert the comic html URL into Calendar instance.
	 * @param url comic html url
	 * @return the calendar instance.
	 */
	protected abstract Calendar getTimeFromUrl(String url);

	/**
	 * Helper function to convert the Calendar instance into url
	 * @param cal calendar instance
	 * @return comic url
	 */
	public abstract String getUrlFromTime(Calendar cal);
	////// abstract functions //////


	@Override
	protected String[] urlsNotForCaching() {
		return null;
	}

}
