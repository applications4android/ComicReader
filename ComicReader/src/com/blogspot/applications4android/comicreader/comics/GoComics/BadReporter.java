package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BadReporter extends DailyGoComicsCom {
	public BadReporter() {
		super();
		mComicName = "badreporter";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 7, 12);
	}
}
