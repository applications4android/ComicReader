package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheBarn extends DailyGoComicsCom {
	public TheBarn() {
		super();
		mComicName = "thebarn";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 1, 2);
	}
}
