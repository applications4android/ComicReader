package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Ziggy extends DailyGoComicsCom {
	public Ziggy() {
		super();
		mComicName = "ziggy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1971, 6, 5);
	}
}
