package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PeanutsHolidayCountdown extends DailyGoComicsCom {
	public PeanutsHolidayCountdown() {
		super();
		mComicName = "peanuts-holiday-countdown";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 10, 27);
	}
}
