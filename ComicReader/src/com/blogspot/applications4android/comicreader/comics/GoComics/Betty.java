package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Betty extends DailyGoComicsCom {
	public Betty() {
		super();
		mComicName = "betty";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 0, 1);
	}
}
