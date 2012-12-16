package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SpottheFrog extends DailyGoComicsCom {
	public SpottheFrog() {
		super();
		mComicName = "spot-the-frog";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 0, 5);
	}
}
