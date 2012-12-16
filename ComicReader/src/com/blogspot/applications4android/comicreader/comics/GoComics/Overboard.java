package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Overboard extends DailyGoComicsCom {
	public Overboard() {
		super();
		mComicName = "overboard";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1998, 0, 1);
	}
}
