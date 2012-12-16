package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class StoneSoup extends DailyGoComicsCom {
	public StoneSoup() {
		super();
		mComicName = "stonesoup";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1995, 10, 20);
	}
}
