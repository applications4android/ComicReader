package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheGrizzwells extends DailyGoComicsCom {
	public TheGrizzwells() {
		super();
		mComicName = "thegrizzwells";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 23);
	}
}
