package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class USAcres extends DailyGoComicsCom {
	public USAcres() {
		super();
		mComicName = "us-acres";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 2, 3);
	}
}
