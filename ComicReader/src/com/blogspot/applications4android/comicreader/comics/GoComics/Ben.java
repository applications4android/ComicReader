package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Ben extends DailyGoComicsCom {
	public Ben() {
		super();
		mComicName = "ben";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 0, 1);
	}
}
