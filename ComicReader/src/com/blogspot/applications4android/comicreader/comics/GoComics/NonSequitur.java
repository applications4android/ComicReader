package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NonSequitur extends DailyGoComicsCom {
	public NonSequitur() {
		super();
		mComicName = "nonsequitur";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1992, 1, 16);
	}
}
