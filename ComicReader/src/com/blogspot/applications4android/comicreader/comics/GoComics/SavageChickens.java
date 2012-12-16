package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SavageChickens extends DailyGoComicsCom {
	public SavageChickens() {
		super();
		mComicName = "savage-chickens";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 5, 20);
	}
}
