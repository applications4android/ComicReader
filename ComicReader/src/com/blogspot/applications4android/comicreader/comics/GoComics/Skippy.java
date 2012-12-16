package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Skippy extends DailyGoComicsCom {
	public Skippy() {
		super();
		mComicName = "skippy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 7, 28);
	}
}
