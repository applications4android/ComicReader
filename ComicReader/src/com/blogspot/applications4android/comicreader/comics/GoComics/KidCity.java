package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class KidCity extends DailyGoComicsCom {
	public KidCity() {
		super();
		mComicName = "kidcity";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 5);
	}
}
