package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ScaryGary extends DailyGoComicsCom {
	public ScaryGary() {
		super();
		mComicName = "scarygary";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 1, 2);
	}
}
