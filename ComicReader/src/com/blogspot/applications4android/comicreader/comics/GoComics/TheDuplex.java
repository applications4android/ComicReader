package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheDuplex extends DailyGoComicsCom {
	public TheDuplex() {
		super();
		mComicName = "duplex";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 7, 12);
	}
}
