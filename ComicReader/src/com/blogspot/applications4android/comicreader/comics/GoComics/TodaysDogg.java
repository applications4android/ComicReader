package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TodaysDogg extends DailyGoComicsCom {
	public TodaysDogg() {
		super();
		mComicName = "todays-dogg";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 31);
	}
}
