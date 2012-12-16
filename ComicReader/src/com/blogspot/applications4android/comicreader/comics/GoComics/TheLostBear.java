package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheLostBear extends DailyGoComicsCom {
	public TheLostBear() {
		super();
		mComicName = "the-lost-bear";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 9, 3);
	}
}
