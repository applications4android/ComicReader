package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MikeLester extends DailyGoComicsCom {
	public MikeLester() {
		super();
		mComicName = "mike-lester";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 8, 15);
	}
}
