package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Think extends DailyGoComicsCom {
	public Think() {
		super();
		mComicName = "think";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 7, 25);
	}
}
